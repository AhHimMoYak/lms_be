import {UpdateCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

export const handler = async (event) => {
    try{
        const {commentId} = event.pathParameters;
        const { content } = JSON.parse(event.body);

        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;

        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        const response = await docClient.send(new UpdateCommand({
            TableName: process.env.COMMENT_TABLE,
            Key: {id:commentId},
            UpdateExpression: 'set content = :content, updatedAt = :updatedAt',
            ExpressionAttributeValues: {
                ':content' : content,
                ':updatedAt': new Date().toISOString()
            },
            ReturnValues: 'ALL_NEW'
        }));
        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(response.Attributes)
        };
    }catch (error) {
        console.error('댓글 수정 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '댓글 수정에 실패했습니다.' })
        };
    }
};
