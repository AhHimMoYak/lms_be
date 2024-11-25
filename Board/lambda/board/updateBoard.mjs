import {UpdateCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

export const handler = async (event) => {
    try {
        const { boardId } = event.pathParameters;
        const { title, content } = JSON.parse(event.body);

        // DynamoDB Update 명령 실행
        const response = await docClient.send(new UpdateCommand({
            TableName: process.env.BOARD_TABLE,
            Key: {
                id: boardId
            },
            UpdateExpression: 'set title = :title, content = :content, updatedAt = :updatedAt',
            ExpressionAttributeValues: {
                ':title': title,
                ':content': content,
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
    } catch (error) {
        console.error('게시글 수정 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '게시글 수정에 실패했습니다.' })
        };
    }
};
