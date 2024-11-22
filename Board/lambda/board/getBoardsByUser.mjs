import {QueryCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

export const handler = async (event) => {
    try {
        const { userName } = event.pathParameters;
        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;

        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        const response = await docClient.send(new QueryCommand({
            TableName: process.env.BOARD_TABLE,
            IndexName: 'UserIndex',
            KeyConditionExpression: 'userName = :userName',
            ExpressionAttributeValues: {
                ':userName': userName
            },
            Limit: limit,
            ScanIndexForward: false,
            ...(lastEvaluatedKey && { ExclusiveStartKey: lastEvaluatedKey })
        }));

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
    } catch (error) {
        console.error('작성자별 게시글 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '작성자별 게시글 조회에 실패했습니다.' })
        };
    }
};
