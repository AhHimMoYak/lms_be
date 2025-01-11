import { v4 as uuidv4 } from "uuid";
import { PutCommand } from "@aws-sdk/lib-dynamodb";
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
        "Access-Control-Allow-Methods": "POST, OPTIONS",
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
        console.log("이건 뭐d? " , origin);
        const body = JSON.parse(event.body);
        const now = new Date().toISOString();
        const username = event.requestContext.authorizer.username;

        // 새 게시글 객체 생성
        const post = {
            id: `post_${uuidv4()}`,
            createdAt: now,
            updatedAt: now,
            title: body.title,
            content: body.content,
            commentCount: 0,
            institutionComment: 0,
            view: 0,
            userName: username,
            courseId: Number(body.courseId),
            type: body.type,
        };

        await docClient.send(new PutCommand({
            TableName: process.env.BOARD_TABLE,
            Item: post
        }));

        return {
            statusCode: 201,
            headers: responseHeaders,
            body: JSON.stringify(post)
        };
    } catch (error) {
        console.error('게시글 생성 실패:', error);
        return {
            statusCode: 500,
            headers: responseHeaders,
            body: JSON.stringify({ message: '게시글 생성에 실패했습니다.' })
        };
    }
};
