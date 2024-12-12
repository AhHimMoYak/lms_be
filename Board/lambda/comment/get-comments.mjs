import { QueryCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "../aws-clients.mjs";

const allowedOrigins = [
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN_1,
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3,
];

export const handler = async (event) => {
    const origin = event.headers.origin;
    const responseHeaders = {
        "Access-Control-Allow-Credentials": "true",
        "Access-Control-Allow-Headers": "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token",
        "Access-Control-Allow-Methods": "GET, POST, PATCH, DELETE, OPTIONS",
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
        const { boardId } = event.pathParameters;
        console.log("Received boardId:", boardId); // 디버깅 로그

        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;

        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        // 필터링 조건 확인
        const filterInstitution = event.queryStringParameters?.isInstitution === "true";

        const params = {
            TableName: process.env.COMMENT_TABLE,
            IndexName: "BoardIdIndex",
            KeyConditionExpression: "boardId = :boardId",
            ExpressionAttributeValues: {
                ":boardId": boardId,
            },
            Limit: limit,
            ScanIndexForward: true,
            ...(lastEvaluatedKey && { ExclusiveStartKey: lastEvaluatedKey }),
        };

        // 필터링 조건 추가
        if (filterInstitution) {
            params.FilterExpression = "is_institution = :isInstitution";
            params.ExpressionAttributeValues[":isInstitution"] = "true";
        }

        const response = await docClient.send(new QueryCommand(params));

        console.log("Query response items:", response.Items);
        return {
            statusCode: 200,
            headers: responseHeaders,
            body: JSON.stringify({
                items: response.Items || [],
                lastEvaluatedKey: response.LastEvaluatedKey,
            }),
        };
    } catch (error) {
        console.error("댓글 조회 실패:", error);
        return {
            statusCode: 500,
            headers: responseHeaders,
            body: JSON.stringify({ message: "댓글 조회에 실패했습니다." }),
        };
    }
};
