import {QueryCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

export const handler = async (event) => {
    try {
        const { userName } = event.pathParameters;
        const decodedUserName = decodeURIComponent(userName);
        const username = event.requestContext.authorizer.username;
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
                ':userName': username
            },
            Limit: limit,
            ScanIndexForward: false,
            ...(lastEvaluatedKey && { ExclusiveStartKey: lastEvaluatedKey })
        }));


        return {
            statusCode: 200,
            headers: {
                'Access-Control-Allow-Origin': process.env.ACCESS_CONTROL_ALLOW_ORIGIN_1,
                "Access-Control-Allow-Credentials": "true",
                "Access-Control-Allow-Headers": "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token",
                "Access-Control-Allow-Methods": "GET, OPTIONS",
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
                'Access-Control-Allow-Origin': process.env.ACCESS_CONTROL_ALLOW_ORIGIN_1,
                "Access-Control-Allow-Credentials": "true",
                "Access-Control-Allow-Headers": "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token",
                "Access-Control-Allow-Methods": "GET, OPTIONS",
            },
            body: JSON.stringify({ message: '작성자별 게시글 조회에 실패했습니다.' })
        };
    }
};
