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

// DynamoDB 클라이언트 초기화
const client = new DynamoDBClient({ region: process.env.REGION });
const docClient = DynamoDBDocumentClient.from(client);
const TABLE_NAME = process.env.DYNAMODB_TABLE;

// 퀴즈 생성 핸들러
export const createPost = async (event) => {
    try {
        if (!event.body) {
            throw new Error("요청 본문이 비어 있습니다.");
        }

        const body = JSON.parse(event.body);

        // 새 퀴즈 객체 생성
        const post = {
            id: `post_${uuidv4()}`,           // UUID를 사용한 고유 ID 생성
            question: body.question,           // 문제
            choices: body.choices || ['', '', '', ''],  // 답안 배열 (최대 4개)
            correctAnswer: body.correctAnswer, // 정답 인덱스
            explanation: body.explanation || '' // 해설
        };

        // DynamoDB에 퀴즈 저장
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
        console.error('퀴즈 생성 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: `퀴즈 생성에 실패했습니다: ${error.message}` })
        };
    }
};

// 퀴즈 조회 핸들러
export const getPost = async (event) => {
    try {
        if (!event.pathParameters || !event.pathParameters.id) {
            throw new Error("필수 매개변수가 누락되었습니다. (id 필요)");
        }

        const { id } = event.pathParameters;

        // DynamoDB에서 퀴즈 조회
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
                body: JSON.stringify({ message: '퀴즈를 찾을 수 없습니다.' })
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
        console.error('퀴즈 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({
                message: `퀴즈 조회에 실패했습니다: ${error.message}`
            })
        };
    }
};

// 퀴즈 목록 조회 핸들러
export const listPosts = async (event) => {
    try {
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

        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({
                items: response.Items,
                lastEvaluatedKey: response.LastEvaluatedKey
            })
        };
    } catch (error) {
        console.error('퀴즈 목록 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '퀴즈 목록 조회에 실패했습니다.' })
        };
    }
};

// 퀴즈 수정 핸들러
export const updatePost = async (event) => {
    try {
        if (!event.pathParameters || !event.pathParameters.id) {
            throw new Error("필수 매개변수가 누락되었습니다. (id 필요)");
        }
        if (!event.body) {
            throw new Error("요청 본문이 비어 있습니다.");
        }

        const { id } = event.pathParameters;
        const { question, choices, correctAnswer, explanation } = JSON.parse(event.body);

        // DynamoDB Update 명령 실행
        const response = await docClient.send(new UpdateCommand({
            TableName: TABLE_NAME,
            Key: { id },
            UpdateExpression: 'set question = :question, choices = :choices, correctAnswer = :correctAnswer, explanation = :explanation',
            ExpressionAttributeValues: {
                ':question': question,
                ':choices': choices || ['', '', '', ''],
                ':correctAnswer': correctAnswer,
                ':explanation': explanation || ''
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
        console.error('퀴즈 수정 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: `퀴즈 수정에 실패했습니다: ${error.message}` })
        };
    }
};

// 퀴즈 삭제 핸들러
export const deletePost = async (event) => {
    try {
        if (!event.pathParameters || !event.pathParameters.id) {
            throw new Error("필수 매개변수가 누락되었습니다. (id 필요)");
        }

        const { id } = event.pathParameters;

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
        console.error('퀴즈 삭제 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: `퀴즈 삭제에 실패했습니다: ${error.message}` })
        };
    }
};

// 작성자별 퀴즈 목록 조회 핸들러
export const getPostsByAuthor = async (event) => {
    try {
        if (!event.pathParameters || !event.pathParameters.authorId) {
            throw new Error("필수 매개변수가 누락되었습니다. (authorId 필요)");
        }

        const { authorId } = event.pathParameters;
        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;

        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        // DynamoDB Query 명령 실행
        const response = await docClient.send(new QueryCommand({
            TableName: TABLE_NAME,
            IndexName: 'authorId-index',
            KeyConditionExpression: 'authorId = :authorId',
            ExpressionAttributeValues: {
                ':authorId': authorId
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
        console.error('작성자별 퀴즈 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: `작성자별 퀴즈 조회에 실패했습니다: ${error.message}` })
        };
    }
};
