import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
    DynamoDBDocumentClient,
    PutCommand,
    GetCommand,
    UpdateCommand,
    DeleteCommand,
    QueryCommand,
    ScanCommand
} from "@aws-sdk/lib-dynamodb";
import { v4 as uuidv4 } from 'uuid';

const client = new DynamoDBClient({region:"ap-northeast-2"});
const docClient = DynamoDBDocumentClient.from(client);
const TABLE_NAME = "course-board";

export const createBoard = async (event) => {
    try {
        const body = JSON.parse(event.body);
        const now = new Date().toISOString();

        // 새 게시글 객체 생성
        const post = {
            id: `post_${uuidv4()}`,
            createdAt: now,
            updatedAt: now,
            title: body.title,
            content: body.content,
            commentCount: 0,
            tags:[ body.username, body.institutionName, body.courseName, body.type]
        };

        await docClient.send(new PutCommand({
            TableName: TABLE_NAME,
            Item: post
        }));

        return {
            statusCode: 201,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(post)
        };
    } catch (error) {
        console.error('게시글 생성 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '게시글 생성에 실패했습니다.' })
        };
    }
};



export const getBoards = async(event)=>{
    try{
        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;
        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;
        // DynamoDB Scan 명령 실행
        const response = await docClient.send(new ScanCommand({
            TableName: TABLE_NAME,
            Limit: limit,
            ...(lastEvaluatedKey && { ExclusiveStartKey: lastEvaluatedKey })
        }));

        // 결과를 최신순으로 정렬
        const sortedItems = response.Items?.sort((a, b) =>
            new Date(b.createdAt) - new Date(a.createdAt)
        ) || [];
        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({
                items: sortedItems,
                lastEvaluatedKey: response.LastEvaluatedKey
            })
        };
    } catch (error) {
        console.error('게시글 목록 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '게시글 목록 조회에 실패했습니다.' })
        };
    }
};

export const getBoard = async (event) => {
    let id, createdAt;

    console.log('Received event:', event); // 전체 이벤트 로그
    try {
        if (event.pathParameters) {
            ({ id, createdAt } = event.pathParameters);
        } else if (event.body) {
            const body = typeof event.body === 'string' ? JSON.parse(event.body) : event.body;
            if (body.pathParameters) {
                ({ id, createdAt } = body.pathParameters);
            }
        }

        if (!id || !createdAt) { // 두 키가 모두 필요한 경우 확인
            return {
                statusCode: 400,
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify({
                    message: '필수 매개변수가 누락되었습니다. (id와 createdAt이 필요합니다)',
                    event: event
                })
            };
        }

        const { Item } = await docClient.send(new GetCommand({
            TableName: TABLE_NAME,
            Key: {
                id: id,
                createdAt: createdAt  // createdAt 추가
            }
        }));

        if (!Item) {
            return {
                statusCode: 404,
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify({ message: '게시글을 찾을 수 없습니다.' })
            };
        }

        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(Item)
        };
    } catch (error) {
        console.error('게시글 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({
                message: '게시글 조회에 실패했습니다.',
                error: error.message,
                event: event
            })
        };
    }
};

export const getBoardsByUser = async (event) => {
    try {
        const { username } = event.pathParameters;
        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;

        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        // userId 값 확인
        console.log('Received userId:', username);
        console.log('Query limit:', limit);
        console.log('Last Evaluated Key:', lastEvaluatedKey);

        const response = await docClient.send(new ScanCommand({
            TableName: TABLE_NAME,
            FilterExpression: 'contains(tags, :tagValue)',
            ExpressionAttributeValues: {
                ':tagValue': username
            },
            Limit: limit,
            ScanIndexForward: false,
            ...(lastEvaluatedKey && { ExclusiveStartKey: lastEvaluatedKey })
        }));

        // DynamoDB 응답 확인
        console.log('DynamoDB query response:', response);

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

export const getBoardsByCourseProvide = async (event) => {
    try {
        const { courseProvideId, type } = event.pathParameters;
        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;
        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        const response = await docClient.send(new QueryCommand({
            TableName: TABLE_NAME,
            IndexName: 'CourseProvideIndex',
            KeyConditionExpression: 'courseProvideId = :courseProvideId',
            FilterExpression: '#type = :type',  // #type을 사용하여 예약어 우회
            ExpressionAttributeNames: {
                '#type': 'type'  // 'type'을 #type으로 매핑
            },
            ExpressionAttributeValues: {
                ':courseProvideId': courseProvideId,
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
                lastEvaluatedKey: response.LastEvaluatedKey
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



export const getBoardsByInstitution = async (event) => {
    try {
        const { institutionId, type } = event.pathParameters;
        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;
        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;
        const compositeKeyForInstitution = `${institutionId}#${type}`;

        const response = await docClient.send(new QueryCommand({
            TableName: TABLE_NAME,
            IndexName: 'InstitutionTypeIndex',
            KeyConditionExpression: 'compositeKeyForInstitution = :compositeKeyForInstitution',
            ExpressionAttributeValues: {
                ':compositeKeyForInstitution': compositeKeyForInstitution
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
        console.error('코스 및 타입별 게시글 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '코스 및 타입별 게시글 조회에 실패했습니다.' })
        };
    }
};

export const updateBoard = async (event) => {
    try {
        const { id, createdAt } = event.pathParameters;
        const { title, content } = JSON.parse(event.body);

        // DynamoDB Update 명령 실행
        const response = await docClient.send(new UpdateCommand({
            TableName: TABLE_NAME,
            Key: { id, createdAt },
            UpdateExpression: 'set title = :title, content = :content, updatedAt = :updatedAt',
            ExpressionAttributeValues: {
                ':title': title,
                ':content': content,
                ':updatedAt': new Date().toISOString()
            },
            ReturnValues: 'ALL_NEW'
        }));

        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(response.Attributes)
        };
    } catch (error) {
        console.error('게시글 수정 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '게시글 수정에 실패했습니다.' })
        };
    }
};

export const deleteBoard = async (event) => {
    try {
        const { id, createdAt } = event.pathParameters;

        // DynamoDB Delete 명령 실행
        await docClient.send(new DeleteCommand({
            TableName: TABLE_NAME,
            Key: { id, createdAt }
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