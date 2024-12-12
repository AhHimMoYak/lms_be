import {QueryCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

const allowedOrigins = [
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN_1,
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3,
];

export const handler = async (event) => {
    const origin = event.headers.origin;
    const responseHeaders = {
        "Access-Control-Allow-Credentials": "true",
        "Access-Control-Allow-Headers": "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token",
        "Access-Control-Allow-Methods": "GET, OPTIONS",
    };

    if (allowedOrigins.includes(origin)) {
        responseHeaders["Access-Control-Allow-Origin"] = origin;
    } else {
        responseHeaders["Access-Control-Allow-Origin"] = process.env.ACCESS_CONTROL_ALLOW_ORIGIN;
    }

    if (event.httpMethod === "OPTIONS") {
        return {
            statusCode: 200,
            headers: responseHeaders,
            body: null, // OPTIONS 요청은 응답 본문이 필요 없음
        };
    }

    try {
        console.log(origin);
        const { courseId, type } = event.pathParameters;
        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;
        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;
        const courseIdNumber = Number(courseId);
        const response = await docClient.send(new QueryCommand({
            TableName: process.env.BOARD_TABLE,
            IndexName: 'CourseIndex',
            KeyConditionExpression: 'courseId = :courseId',
            FilterExpression: '#type = :type',
            ExpressionAttributeNames: {
                '#type': 'type'
            },
            ExpressionAttributeValues: {
                ':courseId': courseIdNumber,
                ':type': type
            },
            Limit: limit,
            ScanIndexForward: false,
            ...(lastEvaluatedKey && { ExclusiveStartKey: lastEvaluatedKey })
        }));
        const countResponse = await docClient.send(new QueryCommand({
            TableName: process.env.BOARD_TABLE,
            IndexName: 'CourseIndex',
            KeyConditionExpression: 'courseId = :courseId',
            FilterExpression: '#type = :type',
            ExpressionAttributeNames: {
                '#type': 'type'
            },
            ExpressionAttributeValues: {
                ':courseId': courseIdNumber,
                ':type': type
            },
            Select: 'COUNT' // 총 개수만 반환
        }));

        return {
            statusCode: 200,
            headers: responseHeaders,
            body: JSON.stringify({
                items: response.Items || [],
                lastEvaluatedKey: response.LastEvaluatedKey || null,
                totalCount : countResponse.Count
            })
        };
    } catch (error) {
        console.error('코스 및 타입별 게시글 조회 실패:', error.message, error.stack);
        return {
            statusCode: 500,
            headers: responseHeaders,
            body: JSON.stringify({ message: '코스 및 타입별 게시글 조회에 실패했습니다.', error: error.message })
        };
    }
};
