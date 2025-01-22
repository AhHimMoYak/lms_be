import { CognitoIdentityProviderClient, SignUpCommand, AdminAddUserToGroupCommand } from "@aws-sdk/client-cognito-identity-provider";
import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import { DynamoDBDocumentClient, PutCommand } from "@aws-sdk/lib-dynamodb";

const dbClient = new DynamoDBClient({ region: process.env.REGION });
const dynamodb = DynamoDBDocumentClient.from(dbClient);

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
    if (allowedOrigins.includes(origin)) {
        responseHeaders["Access-Control-Allow-Origin"] = origin;
    } else {
        responseHeaders["Access-Control-Allow-Origin"] = process.env.ACCESS_CONTROL_ALLOW_ORIGIN;
    }

    if (event.httpMethod === "OPTIONS") {
        return {
            statusCode: 200,
            headers: responseHeaders,
            body: null,
        };
    }

    try {
        const { username, email, password, birthdate, gender, name, phone_number } = JSON.parse(event.body);
        const command = new SignUpCommand({
            ClientId: process.env.COGNITO_CLIENT_ID,
            Username: username,
            Password: password,
            UserAttributes: [{ Name: "email", Value: email }],
        });

        const response = await client.send(command);

        const sub = response.UserSub;
        console.log("User Sub:", sub);

        // 임시 사용자 저장
        const params = {
            TableName: process.env.USER_TABLE,
            Item: {
                id: sub,
                username: username,
                name: name,
                email: email,
                birthdate: birthdate,
                gender: gender,
                phone_number: phone_number,
                create_at: Math.floor(Date.now() / 1000),
                // ttl: Math.floor((Date.now() + 60 * 60 * 1000) / 1000), // 1시간 TTL
            },
        };

        await dynamodb.send(new PutCommand(params));

        // 사용자 그룹에 추가
        const addToGroupParams = {
            UserPoolId: process.env.COGNITO_USER_POOL_ID,
            GroupName: "Visitor",
            Username: username,
        };

        await client.send(new AdminAddUserToGroupCommand(addToGroupParams));

        return {
            statusCode: 201,
            headers: responseHeaders,
            body: JSON.stringify({ userId: sub }),
        };
    } catch (error) {
        console.error("Signup error:", error);
        return {
            statusCode: 500,
            headers: responseHeaders,
            body: JSON.stringify({ error: error.message }),
        };
    }
};
