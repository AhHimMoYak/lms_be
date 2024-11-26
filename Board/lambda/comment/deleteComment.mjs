import {DeleteCommand, UpdateCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

export const handler = async (event) => {
    try {
        const { commentId } = event.pathParameters;
        const body = JSON.parse(event.body);

        await docClient.send(new DeleteCommand({
            TableName: process.env.COMMENT_TABLE,
            Key: {
                id:commentId
            }
        }));

        await docClient.send(new UpdateCommand({
            TableName: process.env.BOARD_TABLE,
            Key: { id: body.boardId },
            UpdateExpression: "set commentCount = commentCount - :commentCount, institutionComment = institutionComment - :institutionComment",
            ExpressionAttributeValues: {
                ":commentCount": 1,
                ":institutionComment" : Number(body.institutionComment)
            }
        }));

        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '댓글이 성공적으로 삭제되었습니다.'})
        };
    } catch (error) {
        console.error('댓글 삭제 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '댓글 삭제에 실패했습니다.' })
        };
    }
};