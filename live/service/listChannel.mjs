import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
    DynamoDBDocumentClient,
    QueryCommand,
} from "@aws-sdk/lib-dynamodb";

// DynamoDB 클라이언트 초기화
const client = new DynamoDBClient({ region: process.env.REGION });
const docClient = DynamoDBDocumentClient.from(client);
const TABLE_NAME = process.env.DYNAMODB_TABLE;

// 게시글 목록 조회 핸들러
export const handler = async (event) => {
    try {
        const { courseProvideId } = event.pathParameters;
        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;

        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        // DynamoDB Query 명령 실행
        const response = await docClient.send(new QueryCommand({
            TableName: TABLE_NAME,
            IndexName: 'courseProvideId-index',
            KeyConditionExpression: 'courseProvideId = :courseProvideId',
            ExpressionAttributeValues: {
                ':courseProvideId': courseProvideId
            },
            Limit: limit,
            ScanIndexForward: false,
            ...(lastEvaluatedKey && { ExclusiveStartKey: lastEvaluatedKey })
        }));

        const items = response.Items?.map(item => ({
            id: item.id,
            createdAt: item.createdAt,
            updatedAt: item.updatedAt,
            title: item.title,
            instructor: item.instructor,
            courseProvideId: item.courseProvideId,
            course: item.course,
            startTime: item.startTime,
            endTime: item.endTime,
            status: item.status
        })) || [];

        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({
                items: items,
                lastEvaluatedKey: response.LastEvaluatedKey
            })
        };
    } catch (error) {
        console.error('채널리스트 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '채널리스트 조회에 실패했습니다.' })
        };
    }
};
