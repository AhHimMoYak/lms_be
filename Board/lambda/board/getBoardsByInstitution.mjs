import {QueryCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

export const handler = async (event) => {
    try {
        const { institutionId, type } = event.pathParameters;
        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;
        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        const response = await docClient.send(new QueryCommand({
            TableName: process.env.BOARD_TABLE,
            IndexName: 'InstitutionIndex',
            KeyConditionExpression: 'institutionId = :institutionId',
            FilterExpression: '#type = :type',  // #type을 사용하여 예약어 우회
            ExpressionAttributeNames: {
                '#type': 'type'  // 'type'을 #type으로 매핑
            },
            ExpressionAttributeValues: {
                ':institutionId': institutionId,
                ':type': type
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
