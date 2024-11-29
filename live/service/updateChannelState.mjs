import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
    DynamoDBDocumentClient,
    UpdateCommand,
} from "@aws-sdk/lib-dynamodb";

// DynamoDB 클라이언트 초기화
const client = new DynamoDBClient({ region: process.env.REGION });
const docClient = DynamoDBDocumentClient.from(client);
const TABLE_NAME = process.env.DYNAMODB_TABLE;

export const handler = async (event) => {
    try {

        let status = event.detail.event_name === 'Session Created' ? 'ON' : 'END';
        if(event.detail.event_name === 'Stream Start'){
            status = 'ON';
        }else if (event.detail.event_name === 'Stream End'){
            status = 'END';
        }else {
            console.log('event name is not correct');
            return;
        }

        console.log(event.detail)

        // DynamoDB Update 명령 실행
        const response = await docClient.send(new UpdateCommand({
            TableName: TABLE_NAME,
            Key: { id: event.detail.channel_name },
            UpdateExpression: 'SET #status = :status, #updatedAt = :updatedAt',
            ExpressionAttributeNames: {
                '#status': 'status',
                '#updatedAt': 'updatedAt'
            },
            ExpressionAttributeValues: {
                ':status' : status,
                ':updatedAt': new Date().toISOString()
            },
            ReturnValues: 'ALL_NEW'
        }));
        console.log(response)
        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(response.Attributes)
        };
    } catch (error) {
        console.error('채널 상태 정보 수정 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '채널 상태 정보 수정에 실패했습니다.' })
        };
    }
};
