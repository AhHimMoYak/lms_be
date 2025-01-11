import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
    DynamoDBDocumentClient,
    GetCommand,
    DeleteCommand,
} from "@aws-sdk/lib-dynamodb";
import {
    IvsClient,
    DeleteChannelCommand
} from "@aws-sdk/client-ivs";
import { v4 as uuidv4 } from 'uuid';

// DynamoDB 클라이언트 초기화
const client = new DynamoDBClient({ region: process.env.REGION });
const docClient = DynamoDBDocumentClient.from(client);
const TABLE_NAME = process.env.DYNAMODB_TABLE;

export const handler = async (event) => {
    try {
        const { id } = event.pathParameters;

        // DynamoDB에서 게시글 조회
        const { Item } = await docClient.send(new GetCommand({
            TableName: TABLE_NAME,
            Key: { id }
        }));

        if (!Item) {
            return {
                statusCode: 404,
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify({ message: '채널 찾을 수 없습니다.' })
            };
        }

        try {
            const ivsClient = new IvsClient();
            await ivsClient.send(new DeleteChannelCommand({
                arn: Item.arn
            }));
        } catch (error){
            console.error("라이브 채널 삭제 실패");
            console.error(error)
            return {
                statusCode: 500,
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify({ message: '라이브 채널 삭제에 실패했습니다.' })
            };
        }

        // DynamoDB Delete 명령 실행
        await docClient.send(new DeleteCommand({
            TableName: TABLE_NAME,
            Key: { id }
        }));

        return {
            statusCode: 204,
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        };
    } catch (error) {
        console.error('채널 삭제 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '채널 삭제에 실패했습니다.' })
        };
    }
};