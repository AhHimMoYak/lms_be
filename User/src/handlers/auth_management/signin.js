// src/handlers/signin.js
import { CognitoIdentityProviderClient, InitiateAuthCommand } from "@aws-sdk/client-cognito-identity-provider";

const client = new CognitoIdentityProviderClient({
    region: process.env.AWS_REGION,
});

// 허용된 Origin 목록
const allowedOrigins = [
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN,
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN_1,
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN_2,
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3,
];

export const handler = async (event) => {
    // CORS 설정
    const corsHeaders = {
        "Access-Control-Allow-Credentials": true,
        "Access-Control-Allow-Methods": "POST, OPTIONS",
        "Access-Control-Allow-Headers": "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token",
        "Access-Control-Expose-Headers": "Set-Cookie",
    };

    const origin = event.headers.origin;
    // 요청 Origin이 허용된 Origin 목록에 포함되었는지 확인
    if (allowedOrigins.includes(origin)) {
        corsHeaders["Access-Control-Allow-Origin"] = origin;
    } else {
        corsHeaders["Access-Control-Allow-Origin"] = process.env.ACCESS_CONTROL_ALLOW_ORIGIN;
    }

    console.log("allowedOrigns : ", allowedOrigins);
    console.log("input origin : ", origin);

    console.log("corsHeaders : ", corsHeaders);

    // OPTIONS 요청 처리 (Preflight)
    if (event.httpMethod === "OPTIONS") {
        return {
            statusCode: 200,
            headers: corsHeaders,
            body: null, // OPTIONS 요청은 응답 본문이 필요 없음
        };
    }

    try {
        const { username, password } = JSON.parse(event.body);
        const command = new InitiateAuthCommand({
            AuthFlow: "USER_PASSWORD_AUTH",
            ClientId: process.env.COGNITO_CLIENT_ID,
            AuthParameters: {
                USERNAME: username,
                PASSWORD: password,
            },
        });
        const response = await client.send(command);

        // Extract the tokens from the response
        const { AccessToken, IdToken, RefreshToken } = response.AuthenticationResult;

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
            statusCode: 200,
            headers: {
                ...corsHeaders,
            },
            multiValueHeaders: {
                "Set-Cookie": [
                    `accessToken=${AccessToken}; ${Object.entries(access_cookieOptions)
                        .map(([key, value]) => `${key}=${value}`)
                        .join("; ")}`,
                    `idToken=${IdToken}; ${Object.entries(id_cookieOptions)
                        .map(([key, value]) => `${key}=${value}`)
                        .join("; ")}`,
                    `refreshToken=${RefreshToken}; ${Object.entries(refresh_cookieOptions)
                        .map(([key, value]) => `${key}=${value}`)
                        .join("; ")}`,
                ],
            },
            body: JSON.stringify({
                message: "Login successful",
            }),
        };
    } catch (error) {
        console.error("Signin error:", error);
        return {
            statusCode: 400,
            headers: corsHeaders,
            body: JSON.stringify({ error: error.message }),
        };
    }
};
