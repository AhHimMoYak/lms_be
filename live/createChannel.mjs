import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
    DynamoDBDocumentClient,
    PutCommand,
} from "@aws-sdk/lib-dynamodb";
import {
    IvsClient,
    CreateChannelCommand,
    ChannelLatencyMode,
    ChannelType} from "@aws-sdk/client-ivs";
import { v4 as uuidv4 } from 'uuid';

// DynamoDB 클라이언트 초기화
const client = new DynamoDBClient({ region: process.env.REGION });
const docClient = DynamoDBDocumentClient.from(client);
const TABLE_NAME = process.env.DYNAMODB_TABLE;

export const handler = async (event) => {
    const body = JSON.parse(event.body);
    const now = new Date().toISOString();
    const id = `live_${uuidv4()}`

    let response;

    try {
        const ivsClient = new IvsClient();
        response = await ivsClient.send(new CreateChannelCommand({
                name: id,
                type: ChannelType.StandardChannelType,
                latencyMode: ChannelLatencyMode.LowLatency,
                authorized: false,
                insecureIngest: false,
                tags: {"Name": "ahim"}
            }));
    } catch (error){
        console.error("라이브 채널 생성 실패");
        console.error(error)
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '라이브 채널 생성에 실패했습니다.' })
        };
    }

    try {
        const live = {
            id: id,
            createdAt: now,          // 생성 시간
            updatedAt: now,          // 수정 시간
            streamKey: response.streamKey.value,
            ingestEndpoint: response.channel.ingestEndpoint,
            playbackUrl: response.channel.playbackUrl,
            title: body.title,
            instructor: body.instructor,
            courseProvideId: body.courseProvideId,
            course: body.course,
            startTime: body.startTime,
            endTime: body.endTime,
            status: 'NOT_STARTED',     // 게시글 상태
        };

        await docClient.send(new PutCommand({
            TableName: TABLE_NAME,
            Item: live
        }));

        return {
            statusCode: 201,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(live)
        };
    } catch (error) {
        console.error('라이브 채널 정보 저장 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '라이브 채널 생성에 실패했습니다.' })
        };
    }
};