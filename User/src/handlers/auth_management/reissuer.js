import { CognitoIdentityProviderClient, AdminInitiateAuthCommand } from "@aws-sdk/client-cognito-identity-provider";

const cognitoClient = new CognitoIdentityProviderClient({ region: process.env.AWS_REGION });

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
        "Access-Control-Allow-Headers": "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token,Cookie",
        "Access-Control-Expose-Headers": "Set-Cookie",
    };

    const origin = event.headers.origin;
    if (allowedOrigins.includes(origin)) {
        corsHeaders["Access-Control-Allow-Origin"] = origin;
    } else {
        corsHeaders["Access-Control-Allow-Origin"] = process.env.ACCESS_CONTROL_ALLOW_ORIGIN;
    }

    // OPTIONS 요청 처리 (Preflight)
    if (event.httpMethod === "OPTIONS") {
        return {
            statusCode: 200,
            headers: corsHeaders,
            body: null, // OPTIONS 요청은 응답 본문이 필요 없음
        };
    }

    try {
        const cookies = event.headers.Cookie || event.headers.cookie;
        if (!cookies) {
            return {
                statusCode: 401,
                headers: corsHeaders,
                body: JSON.stringify({ message: "No cookies found." }),
            };
        }

        // Extract refresh token from cookies
        const refreshToken = cookies
            .split(";")
            .find((cookie) => cookie.trim().startsWith("refreshToken="))
            ?.split("=")[1];

        if (!refreshToken) {
            return {
                statusCode: 401,
                headers: corsHeaders,
                body: JSON.stringify({ message: "Refresh token is missing." }),
            };
        }

        // Call Cognito to refresh tokens
        const params = {
            UserPoolId: process.env.COGNITO_USER_POOL_ID,
            ClientId: process.env.COGNITO_CLIENT_ID,
            AuthFlow: "REFRESH_TOKEN_AUTH",
            AuthParameters: {
                REFRESH_TOKEN: refreshToken,
            },
        };

        const command = new AdminInitiateAuthCommand(params);
        const response = await cognitoClient.send(command);

        // Return new tokens
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

        return {
            statusCode: 200,
            headers: {
                ...corsHeaders,
            },
            multiValueHeaders: {
                "Set-Cookie": [
                    `accessToken=${response.AuthenticationResult.AccessToken}; ${Object.entries(access_cookieOptions)
                        .map(([key, value]) => `${key}=${value}`)
                        .join("; ")}`,
                    `idToken=${response.AuthenticationResult.IdToken}; ${Object.entries(id_cookieOptions)
                        .map(([key, value]) => `${key}=${value}`)
                        .join("; ")}`,
                ],
            },
            body: JSON.stringify({
                message: "Token refresh successful",
            }),
        };
    } catch (error) {
        console.error("Error refreshing token:", error);
        return {
            statusCode: 500,
            headers: corsHeaders,
            body: JSON.stringify({ message: "Failed to refresh token.", error: error.message }),
        };
    }
};
