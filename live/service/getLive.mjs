import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
    DynamoDBDocumentClient,
    GetCommand,
} from "@aws-sdk/lib-dynamodb";


// DynamoDB 클라이언트 초기화
const client = new DynamoDBClient({ region: process.env.REGION });
const docClient = DynamoDBDocumentClient.from(client);
const TABLE_NAME = process.env.DYNAMODB_TABLE;


export const handler = async (event) => {
    try {
        console.log('Event:', JSON.stringify(event, null, 2));  // 디버깅을 위한 로그 추가

        let id;

        // event.pathParameters가 있는 경우 (API Gateway를 통한 호출)
        if (event.pathParameters) {
            ({ id } = event.pathParameters);
        }
        // event.body가 있는 경우 (직접 Lambda 호출)
        else if (event.body) {
            const body = typeof event.body === 'string' ? JSON.parse(event.body) : event.body;
            if (body.pathParameters) {
                ({ id } = body.pathParameters);
            }
        }

        // 필수 매개변수 검증
        if (!id) {
            return {
                statusCode: 400,
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify({
                    message: '필수 매개변수가 누락되었습니다. (id, startTime 이 필요합니다)',
                    event: event  // 디버깅을 위해 이벤트 객체 포함
                })
            };
        }

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

        const channelResponse = {
            id: Item.id,
            createdAt: Item.createdAt,
            updatedAt: Item.updatedAt,
            playbackUrl: Item.playbackUrl,
            title: Item.title,
            instructor: Item.instructor,
            course: Item.course,
            startTime: Item.startTime,
            endTime: Item.endTime,
            status: Item.status
        }

        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(channelResponse)
        };
    } catch (error) {
        console.error('채널 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({
                message: '게시글 조회에 실패했습니다.',
                error: error.message,  // 디버깅을 위해 오류 메시지 포함
                event: event  // 디버깅을 위해 이벤트 객체 포함
            })
        };
    }
};