import { GetCommand, UpdateCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "../aws-clients.mjs";

const allowedOrigins = [
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN_1,
    process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3,
];

export const handler = async (event) => {
    let boardId;
    const origin = event.headers.origin;
    const responseHeaders = {
        "Access-Control-Allow-Credentials": "true",
        "Access-Control-Allow-Headers": "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token",
        "Access-Control-Allow-Methods": "GET, POST, OPTIONS, DELETE, PATCH",
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
        if (event.pathParameters) {
            ({ boardId } = event.pathParameters);
        }

        if (!boardId) {
            return {
                statusCode: 400,
                headers: responseHeaders,
                body: JSON.stringify({
                    message: '필수 매개변수가 누락되었습니다.',
                    event: event
                })
            };
        }

        // Fetch the item
        const { Item } = await docClient.send(new GetCommand({
            TableName: process.env.BOARD_TABLE,
            Key: {
                id: boardId
            }
        }));

        if (!Item) {
            return {
                statusCode: 404,
                headers: responseHeaders,
                body: JSON.stringify({ message: '게시글을 찾을 수 없습니다.' })
            };
        }

        // Increment the view count
        await docClient.send(new UpdateCommand({
            TableName: process.env.BOARD_TABLE,
            Key: {
                id: boardId
            },
            UpdateExpression: "SET #view = if_not_exists(#view, :start) + :inc",
            ExpressionAttributeNames: {
                "#view": "view"
            },
            ExpressionAttributeValues: {
                ":start": 0,
                ":inc": 1
            }
        }));

        // Return the item
        return {
            statusCode: 200,
            headers: responseHeaders,
            body: JSON.stringify(Item)
        };
    } catch (error) {
        console.error('게시글 조회 실패:', error);
        return {
            statusCode: 500,
            headers: responseHeaders,
            body: JSON.stringify({
                message: '게시글 조회에 실패했습니다.',
                error: error.message,
                event: event
            })
        };
    }
};
