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

// Create User
export const handler = async (event) => {
    const { id, name, username, email, group, company_number, gender, birthdate, phone_number, account_status, create_at } = JSON.parse(event.body);

    const params = {
        TableName: TABLE_NAME,
        Item: {
            id: { S: id },
            name: { S: name },
            username: { S: username },
            email: { S: email },
            group: { S: group },
            company_number: { N: company_number.toString() },
            gender: { S: gender },
            birthdate: { S: birthdate },
            phone_number: { S: phone_number },
            account_status: { S: account_status },
            last_login: { S: last_login },
            last_logout: { S: last_logout },
            create_at: { S: create_at },
        },
    };
    await dynamoDb.send(new PutItemCommand(params));
    return { statusCode: 201, body: JSON.stringify({ message: "User created successfully" }) };
};
