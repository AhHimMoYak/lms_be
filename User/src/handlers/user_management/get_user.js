import { DynamoDBClient, PutItemCommand, GetItemCommand, UpdateItemCommand, DeleteItemCommand, QueryCommand } from "@aws-sdk/client-dynamodb";

const dynamoDb = new DynamoDBClient({ region: process.env.REGION });
const TABLE_NAME = process.env.USERS_TABLE;

// Helper to parse DynamoDB responses
const unmarshall = (item) => {
    return Object.entries(item).reduce((acc, [key, value]) => {
        acc[key] = Object.values(value)[0];
        return acc;
    }, {});
};

// Get User by ID
export const handler = async (event) => {
    const id = event.requestContext.authorizer.principalId;
    const params = {
        TableName: TABLE_NAME,
        Key: {
            id: { S: id },
        },
    };
    const result = await dynamoDb.send(new GetItemCommand(params));

    return {
        statusCode: 200,
        headers: {
            "Access-Control-Allow-Origin": "https://devton.ahimmoyak.click", // 클라이언트 URL
            "Access-Control-Allow-Credentials": "true",
        },
        body: JSON.stringify(unmarshall(result.Item)),
    };
};
