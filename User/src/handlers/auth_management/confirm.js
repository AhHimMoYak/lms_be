// src/handlers/confirm.js
import { CognitoIdentityProviderClient, ConfirmSignUpCommand } from "@aws-sdk/client-cognito-identity-provider";

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
    const responseHeaders = {
        "Access-Control-Allow-Credentials": "true",
        "Access-Control-Allow-Headers": "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token,Cookie",
        "Access-Control-Allow-Methods": "POST, OPTIONS",
    };

    const origin = event.headers.origin;
    // 요청 Origin이 허용된 Origin 목록에 포함되었는지 확인
    if (allowedOrigins.includes(origin)) {
        responseHeaders["Access-Control-Allow-Origin"] = origin;
    } else {
        responseHeaders["Access-Control-Allow-Origin"] = process.env.ACCESS_CONTROL_ALLOW_ORIGIN;
    }

    // OPTIONS 요청 처리 (Preflight)
    if (event.httpMethod === "OPTIONS") {
        return {
            statusCode: 200,
            headers: responseHeaders,
            body: null, // OPTIONS 요청은 응답 본문이 필요 없음
        };
    }

    try {
        const { username, confirmationCode } = JSON.parse(event.body);
        const command = new ConfirmSignUpCommand({
            ClientId: process.env.COGNITO_CLIENT_ID,
            Username: username,
            ConfirmationCode: confirmationCode,
        });
        const response = await client.send(command);
        return {
            statusCode: 200,
            headers: responseHeaders,
        };
    } catch (error) {
        console.error("Confirm signup error:", error);
        return {
            statusCode: 500,
            headers: responseHeaders,
            body: JSON.stringify({ error: error.message }),
        };
    }
};
