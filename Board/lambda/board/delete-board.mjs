import { DeleteCommand, QueryCommand } from "@aws-sdk/lib-dynamodb";
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

        // 게시글 삭제
        await docClient.send(new DeleteCommand({
            TableName: process.env.BOARD_TABLE,
            Key: {
                id: boardId
            }
        }));

        const commentsQueryResult = await docClient.send(new QueryCommand({
            TableName: process.env.COMMENT_TABLE,
            IndexName: 'BoardIdIndex',
            KeyConditionExpression: 'boardId = :id',
            ExpressionAttributeValues: {
                ':id': boardId
            },
        }));

        if (commentsQueryResult.Items && commentsQueryResult.Items.length > 0) {
            const deletePromises = commentsQueryResult.Items.map(comment =>
                docClient.send(new DeleteCommand({
                    TableName: process.env.COMMENT_TABLE,
                    Key: { id: comment.id }
                }))
            );
            await Promise.all(deletePromises);
        }

        return {
            statusCode: 204,
            headers: responseHeaders
        };
    } catch (error) {
        console.error('게시글 삭제 실패:', error);
        return {
            statusCode: 500,
            headers: responseHeaders,
            body: JSON.stringify({ message: '게시글 삭제에 실패했습니다.' })
        };
    }
};
