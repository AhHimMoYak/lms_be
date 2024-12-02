import {v4 as uuidv4} from "uuid";
import {PutCommand, UpdateCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

export const handler = async (event) => {
    try {
        const body = JSON.parse(event.body);
        const now = new Date().toISOString();
        const { boardId } = event.pathParameters;

        const comment = {
            id: `comment_${uuidv4()}`,
            createdAt: now,
            updatedAt: now,
            content: body.content,
            userName: body.userName,
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
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(comment)
        };
    } catch (error) {
        console.error('댓글 생성 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '댓글 생성에 실패했습니다.' })
        };
    }
}
