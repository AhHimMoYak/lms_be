import { CognitoIdentityProviderClient, AdminCreateUserCommand } from "@aws-sdk/client-cognito-identity-provider";
import { randomBytes } from "crypto";
import axios from "axios";
import DataTransformer from "../../utils/DataTransformer.js";
import GoogleCognitoSignInService from "../../service/googleSignInService.js";
import GoogleCognitoSignUpService from "../../service/googleSignUpService.js";

// Cognito client init
const cognitoClient = new CognitoIdentityProviderClient({ region: process.env.AWS_REGION });

// Google Oauth URL
const GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";

// Google login URL init
const generateGoogleLoginUrl = (clientId, redirectUri, mode) => {
    const randomToken = randomBytes(16).toString("hex"); // CSRF 방지를 위한 상태 토큰 생성
    const requireMode = JSON.stringify({ mode });
    const combinedData = { random: randomToken, requireMode };

    const state = Buffer.from(JSON.stringify(combinedData)).toString("base64");
    const scope = "openid email profile"; // 요청할 Google 사용자 정보 범위

    const params = new URLSearchParams({
        client_id: clientId,
        redirect_uri: redirectUri,
        response_type: "code",
        scope: scope,
        state: state,
    });

    return `${GOOGLE_AUTH_URL}?${params.toString()}`;
};

// Google login URL 전달
export const handler = async (event) => {
    if (event.httpMethod === "GET" && event.path === "/auth/v1/google") {
        const mode = event.queryStringParameters?.mode;
        console.log(mode);

        // Google URL 생성 및 리디렉션
        const requiredUrl = generateGoogleLoginUrl(process.env.GOOGLE_CLIENT_ID, process.env.GOOGLE_REDIRECT_URI, mode);

        return {
            statusCode: 200,
            headers: {
                "Access-Control-Allow-Origin": process.env.ACCESS_CONTROL_ALLOW_ORIGIN,
                "Access-Control-Allow-Credentials": true,
            },
            body: JSON.stringify({ requiredUrl }),
        };
    }

    // Google OAuth 콜백 처리
    if (event.httpMethod === "GET" && event.path === "/auth/google/callback") {
        const code = event.queryStringParameters?.code;
        const state = event.queryStringParameters?.state;

        if (!code || !state) {
            // 등록 취소시 행동 추가
            return {
                statusCode: 400,
                body: JSON.stringify({ message: "Code and state are required" }),
            };
        }

        // state mode encoding 및 조건 확인
        const transformer = new DataTransformer();
        const mode = transformer.decoding(state);

        if (!["signup", "signin"].includes(mode)) {
            return {
                statusCode: 400,
                body: JSON.stringify({ message: "Invalid mode in state parameter" }),
            };
        }

        try {
            // Google 인증 코드를 토큰으로 교환
            const tokenResponse = await axios.post("https://oauth2.googleapis.com/token", {
                code,
                client_id: process.env.GOOGLE_CLIENT_ID,
                client_secret: process.env.GOOGLE_CLIENT_SECRET,
                redirect_uri: process.env.GOOGLE_REDIRECT_URI,
                grant_type: "authorization_code",
            });

            const idToken = tokenResponse.data.id_token;

            // Google token 유효성 검사
            const googleTokenInfoResponse = await axios.get(`https://oauth2.googleapis.com/tokeninfo`, {
                params: {
                    id_token: idToken,
                },
            });

            const googlePayload = googleTokenInfoResponse.data;

            if (googlePayload.error) {
                return {
                    statusCode: 401,
                    body: JSON.stringify({ message: "Invalid Google ID Token" }),
                };
            }

            const { email, sub: googleId } = googlePayload;
            console.log("google payload email : ", email);

            const signInService = new GoogleCognitoSignInService(process.env.AWS_REGION, process.env.COGNITO_CLIENT_ID);
            const signUpService = new GoogleCognitoSignUpService(
                process.env.AWS_REGION,
                process.env.COGNITO_USER_POOL_ID,
                process.env.GOOGLE_GROUP_NAME
            );

            // 회원가입 또는 로그인 로직 실행
            if (mode === "signup") {
                await signUpService.handleGoogleSignUp(email, googleId);
            }
            const tokens = await signInService.handleGoogleLogin(email, idToken);

            // Create a secure, HTTP-only cookie with the access token
            const access_cookieOptions = {
                domain: ".ahimmoyak.click",
                httpOnly: true,
                secure: true, // 로컬 환경에서는 false
                sameSite: "Lax", // sameSite 제한을 완화
                maxAge: 3600, // 1 hour
                path: "/",
            };

            const id_cookieOptions = {
                domain: ".ahimmoyak.click",
                httpOnly: true,
                secure: true, // 로컬 환경에서는 false
                sameSite: "Lax", // sameSite 제한을 완화
                maxAge: 3600, // 1 hour
                path: "/",
            };

            const refresh_cookieOptions = {
                domain: ".ahimmoyak.click",
                httpOnly: true,
                secure: true, // 로컬 환경에서는 false
                sameSite: "Lax", // sameSite 제한을 완화
                expires: new Date(Date.now() + 14 * 24 * 3600 * 1000).toUTCString(), // 2주
                path: "/auth/refresh",
            };

            return {
                statusCode: 302,
                headers: {
                    Location: process.env.ACCESS_CONTROL_ALLOW_ORIGIN, // 클라이언트로 리다이렉트할 URL
                    "Access-Control-Allow-Origin": process.env.ACCESS_CONTROL_ALLOW_ORIGIN,
                    "Access-Control-Allow-Credentials": "true",
                    "Access-Control-Expose-Headers": "Set-Cookie", // Set-Cookie 헤더를 노출하도록 설정
                },
                multiValueHeaders: {
                    "Set-Cookie": [
                        `accessToken=${tokens.accessToken}; ${Object.entries(access_cookieOptions)
                            .map(([key, value]) => `${key}=${value}`)
                            .join("; ")}`,
                        `idToken=${tokens.idToken}; ${Object.entries(id_cookieOptions)
                            .map(([key, value]) => `${key}=${value}`)
                            .join("; ")}`,
                        `refreshToken=${tokens.refreshToken}; ${Object.entries(refresh_cookieOptions)
                            .map(([key, value]) => `${key}=${value}`)
                            .join("; ")}`,
                    ],
                },
                body: JSON.stringify({
                    message: "Tokens set in cookies successfully",
                }),
            };
        } catch (error) {
            console.error("Google Login Error:", error);
            return {
                statusCode: 400,
                headers: {
                    "Access-Control-Allow-Origin": process.env.ACCESS_CONTROL_ALLOW_ORIGIN,
                    "Access-Control-Allow-Credentials": true,
                },
                body: JSON.stringify({ error: error.message }),
            };
        }
    }

    // 지원되지 않는 HTTP 메서드에 대한 처리
    return {
        statusCode: 405,
        body: JSON.stringify({ error: "Not Allowed HTTP Method" }),
    };
};
