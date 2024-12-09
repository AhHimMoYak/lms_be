import {DeleteCommand, UpdateCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

const allowedOrigins = [
    "https://lms.local.ahimmoyak.click",
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
        const { boardId, commentId } = event.pathParameters;
        let institutionNum = 0;
        if(origin===process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3){
            institutionNum = 1;
        }
        await docClient.send(new DeleteCommand({
            TableName: process.env.COMMENT_TABLE,
            Key: {
                id:commentId
            }
        }));
        await docClient.send(new UpdateCommand({
            TableName: process.env.BOARD_TABLE,
            Key: { id: boardId },
            UpdateExpression: "set commentCount = commentCount - :commentCount, institutionComment = institutionComment - :institutionComment",
            ExpressionAttributeValues: {
                ":commentCount": 1,
                ":institutionComment" : institutionNum
            }
        }));

        return {
            statusCode: 200,
            headers: responseHeaders,
            body: JSON.stringify({ message: '댓글이 성공적으로 삭제되었습니다.'})
        };
    } catch (error) {
        console.error('댓글 삭제 실패:', error);
        return {
            statusCode: 500,
            headers: responseHeaders,
            body: JSON.stringify({ message: '댓글 삭제에 실패했습니다.' })
        };
    }
};