import { CognitoIdentityProviderClient, GlobalSignOutCommand, GetUserCommand } from "@aws-sdk/client-cognito-identity-provider";

const client = new CognitoIdentityProviderClient({
    region: process.env.AWS_REGION,
});

const allowedOrigins = [
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN,
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN_1,
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN_2,
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3,
];

const responseHeaders = {
    "Access-Control-Allow-Credentials": "true",
    "Access-Control-Allow-Headers": "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token,Cookie",
    "Access-Control-Allow-Methods": "POST, OPTIONS,GET,PUT",
};

export const handler = async (event) => {
    const origin = event.headers.origin;
    if (allowedOrigins.includes(origin)) {
        responseHeaders["Access-Control-Allow-Origin"] = origin;
    } else {
        responseHeaders["Access-Control-Allow-Origin"] = process.env.ACCESS_CONTROL_ALLOW_ORIGIN;
    }

    try {
        const cookies = event.headers.Cookie || event.headers.cookie;
        if (!cookies) {
            throw new Error("Cookie header is missing");
        }

        const accessToken = cookies
            .split(";")
            .map((cookie) => cookie.trim())
            .find((cookie) => cookie.startsWith("accessToken="))
            ?.split("=")[1];

        if (!accessToken) {
            throw new Error("Access token is missing in cookies");
        }

        const access_cookieOptions = {
            domain: ".ahimmoyak.click",
            httpOnly: true,
            secure: true, // 로컬 환경에서는 false
            sameSite: "Lax", // sameSite 제한을 완화
            maxAge: 0, // 1 hour
            path: "/",
        };

        const id_cookieOptions = {
            domain: ".ahimmoyak.click",
            httpOnly: true,
            secure: true, // 로컬 환경에서는 false
            sameSite: "Lax", // sameSite 제한을 완화
            maxAge: 0, // 1 hour
            path: "/",
        };

        const refresh_cookieOptions = {
            domain: ".ahimmoyak.click",
            httpOnly: true,
            secure: true, // 로컬 환경에서는 false
            sameSite: "Lax", // sameSite 제한을 완화
            maxAge: 0, // 2주
            path: "/auth/refresh",
        };

        // Verify the access token
        try {
            const validateTokenCommand = new GetUserCommand({
                AccessToken: accessToken,
            });
            await client.send(validateTokenCommand);
        } catch (error) {
            if (error.name === "NotAuthorizedException") {
                console.warn("Access token is invalid or expired, skipping GlobalSignOut.");
                return {
                    statusCode: 200,
                    headers: {
                        ...responseHeaders,
                    },
                    multiValueHeaders: {
                        "Set-Cookie": [
                            `accessToken=""; ${Object.entries(access_cookieOptions)
                                .map(([key, value]) => `${key}=${value}`)
                                .join("; ")}`,
                            `idToken=""; ${Object.entries(id_cookieOptions)
                                .map(([key, value]) => `${key}=${value}`)
                                .join("; ")}`,
                            `refreshToken=""; ${Object.entries(refresh_cookieOptions)
                                .map(([key, value]) => `${key}=${value}`)
                                .join("; ")}`,
                        ],
                    },
                    body: JSON.stringify({ message: "Successfully signed out" }),
                };
            }
            throw error;
        }

        const command = new GlobalSignOutCommand({ AccessToken: accessToken });
        await client.send(command);

        return {
            statusCode: 200,
            headers: {
                ...responseHeaders,
            },
            multiValueHeaders: {
                "Set-Cookie": [
                    `accessToken=""; ${Object.entries(access_cookieOptions)
                        .map(([key, value]) => `${key}=${value}`)
                        .join("; ")}`,
                    `idToken=""; ${Object.entries(id_cookieOptions)
                        .map(([key, value]) => `${key}=${value}`)
                        .join("; ")}`,
                    `refreshToken=""; ${Object.entries(refresh_cookieOptions)
                        .map(([key, value]) => `${key}=${value}`)
                        .join("; ")}`,
                ],
            },
            body: JSON.stringify({ message: "Successfully signed out" }),
        };
    } catch (error) {
        console.error("Signout error:", error);
        return {
            statusCode: 400,
            headers: {
                ...responseHeaders,
            },
            body: JSON.stringify({ error: error.message }),
        };
    }
};
