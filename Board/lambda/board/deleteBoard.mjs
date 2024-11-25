import { DeleteCommand, QueryCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "../aws-clients.mjs";

export const handler = async (event) => {
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
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };
    } catch (error) {
        console.error('게시글 삭제 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '게시글 삭제에 실패했습니다.' })
        };
    }
};
