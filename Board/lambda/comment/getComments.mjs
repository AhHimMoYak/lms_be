import {QueryCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

export const handler = async (event) => {
    try {
        const {boardId} = event.pathParameters;
        console.log("Received boardId:", boardId); // 디버깅 로그

        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;

        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        const response = await docClient.send(new QueryCommand({
            TableName: process.env.COMMENT_TABLE,
            IndexName: 'BoardIdIndex',
            KeyConditionExpression: 'boardId = :boardId',
            ExpressionAttributeValues: {
                ':boardId': boardId
            },
            Limit: limit,
            ScanIndexForward: true,
            ...(lastEvaluatedKey && {ExclusiveStartKey: lastEvaluatedKey})

        }));
        console.log("Query response items:", response.Items);
        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({
                items: response.Items || [],
                lastEvaluatedKey: response.LastEvaluatedKey
            })
        };
    }catch (error) {
        console.error('댓글 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '댓글 조회에 실패했습니다.' })
        };
    }
};