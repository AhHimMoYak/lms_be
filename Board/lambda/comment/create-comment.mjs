import {v4 as uuidv4} from "uuid";
import {PutCommand, UpdateCommand} from "@aws-sdk/lib-dynamodb";
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
        const body = JSON.parse(event.body);
        const now = new Date().toISOString();
        const { boardId } = event.pathParameters;
        const username = event.requestContext.authorizer.username;

        const comment = {
            id: `comment_${uuidv4()}`,
            createdAt: now,
            updatedAt: now,
            content: body.content,
            userName: username,
            boardId: boardId,
            is_institution: body.is_institution
        };

        await docClient.send(new PutCommand({
            TableName: process.env.COMMENT_TABLE,
            Item: comment
        }));

        // 댓글이 성공적으로 생성되었을 때 게시글 상태 업데이트
        await docClient.send(new UpdateCommand({
            TableName: process.env.BOARD_TABLE,
            Key: { id: boardId },
            UpdateExpression:
                "set commentCount = commentCount + :commentCount, institutionComment = institutionComment + :institutionComment"
            ,
            ExpressionAttributeValues: {
                ":commentCount": 1,
                ":institutionComment": body.institutionComment
            }
        }));

        return {
            statusCode: 201,
            headers: responseHeaders,
            body: JSON.stringify(comment)
        };
    } catch (error) {
        console.error('댓글 생성 실패:', error);
        return {
            statusCode: 500,
            headers: responseHeaders,
            body: JSON.stringify({ message: '댓글 생성에 실패했습니다.' })
        };
    }
}
