import { QueryCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "../aws-clients.mjs";

export const handler = async (event) => {
    try {
        const { institutionId, type } = event.pathParameters;
        const commentCount = event.queryStringParameters?.commentCount ?? null;
        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;
        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        const queryParams = {
            TableName: process.env.BOARD_TABLE,
            IndexName: 'InstitutionIndex',
            KeyConditionExpression: 'institutionId = :institutionId',
            FilterExpression: '#type = :type',
            ExpressionAttributeNames: {
                '#type': 'type' // 'type'을 #type으로 매핑
            },
            ExpressionAttributeValues: {
                ':institutionId': institutionId,
                ':type': type
            },
            Limit: limit,
            ScanIndexForward: false,
            ...(lastEvaluatedKey && { ExclusiveStartKey: lastEvaluatedKey })
        };
        if (commentCount !== null) {
            queryParams.FilterExpression += commentCount === '1'
                ? ' AND #commentCount >= :commentCount'
                :  ' AND #commentCount = :commentCount';

            queryParams.ExpressionAttributeNames['#commentCount'] = 'commentCount';
            if (commentCount === '1') {
                queryParams.ExpressionAttributeValues[':commentCount'] = 1;
            } else {
                queryParams.ExpressionAttributeValues[':commentCount'] = 0;
            }
        }

        const response = await docClient.send(new QueryCommand(queryParams));
        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({
                items: response.Items || [],
                lastEvaluatedKey: response.LastEvaluatedKey || null
            })
        };
    } catch (error) {
        console.error('코스 및 타입별 게시글 조회 실패:', error.message, error.stack);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '코스 및 타입별 게시글 조회에 실패했습니다.', error: error.message })
        };
    }
};
