import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
    DynamoDBDocumentClient,
    UpdateCommand,
} from "@aws-sdk/lib-dynamodb";

// DynamoDB 클라이언트 초기화
const client = new DynamoDBClient({ region: process.env.REGION });
const docClient = DynamoDBDocumentClient.from(client);
const TABLE_NAME = process.env.DYNAMODB_TABLE;

export const handler = async (event) => {
    try {
        const { id } = event.pathParameters;
        const { title, startTime, endTime } = JSON.parse(event.body);

        // DynamoDB Update 명령 실행
        const response = await docClient.send(new UpdateCommand({
            TableName: TABLE_NAME,
            Key: { id },
            UpdateExpression: 'set title = :title, startTime = :startTime, endTime = :endTime, updatedAt = :updatedAt',
            ExpressionAttributeValues: {
                ':title': title,
                ':startTime': startTime,
                ':endTime': endTime,
                ':updatedAt': new Date().toISOString()
            },
            ReturnValues: 'ALL_NEW'
        }));

        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(response.Attributes)
        };
    } catch (error) {
        console.error('채널 정보 수정 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '채널 정보 수정에 실패했습니다.' })
        };
    }
};
