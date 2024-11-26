import {v4 as uuidv4} from "uuid";
import {PutCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

export const handler = async (event) => {
    try {
        const body = JSON.parse(event.body);
        const now = new Date().toISOString();

        // 새 게시글 객체 생성
        const post = {
            id: `post_${uuidv4()}`,
            createdAt: now,
            updatedAt: now,
            title: body.title,
            content: body.content,
            commentCount: 0,
            userName: body.userName,
            courseId: Number(body.courseId),
            institutionId: Number(body.institutionId),
            course: body.course,
            type: body.type,
        };

        await docClient.send(new PutCommand({
            TableName: process.env.BOARD_TABLE,
            Item: post
        }));

        return {
            statusCode: 201,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(post)
        };
    } catch (error) {
        console.error('게시글 생성 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '게시글 생성에 실패했습니다.' })
        };
    }
};
