import {DeleteCommand, DynamoDBDocumentClient} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

export const handler = async (event) => {
    try {
        const { id } = event.pathParameters;

        // DynamoDB Delete 명령 실행
        await docClient.send(new DeleteCommand({
            TableName: process.env.BOARD_TABLE,
            Key: { id }
        }));

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
}