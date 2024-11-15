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

const client = new DynamoDBClient({ region: process.env.REGION });
const docClient = DynamoDBDocumentClient.from(client);
const TABLE_NAME = process.env.DYNAMODB_TABLE;

// 퀴즈 생성 핸들러 (코스 ID 포함)
export const createQuiz = async (event) => {
    try {
        if (!event.body) {
            throw new Error("요청 본문이 비어 있습니다.");
        }

        const body = JSON.parse(event.body);

        // 새 퀴즈 객체 생성
        const post = {
            id: `post_${uuidv4()}`,               // UUID를 사용한 고유 ID 생성
            courseId: body.courseId,               // 코스 ID 추가
            question: body.question,               // 문제
            choices: body.choices || ['', '', '', ''], // 답안 배열 (최대 4개)
            correctAnswer: body.correctAnswer,     // 정답 인덱스
            explanation: body.explanation || ''    // 해설
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

// 특정 퀴즈 조회 핸들러
export const getQuiz = async (event) => {
    try {
        if (!event.pathParameters || !event.pathParameters.id) {
            throw new Error("필수 매개변수가 누락되었습니다. (id 필요)");
        }

        const { id } = event.pathParameters;

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

// 특정 코스 ID의 퀴즈 목록 조회 핸들러
export const listQuizByCourse = async (event) => {
    try {
        if (!event.pathParameters || !event.pathParameters.courseId) {
            throw new Error("필수 매개변수가 누락되었습니다. (courseId 필요)");
        }

        const { courseId } = event.pathParameters;
        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;

        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        // DynamoDB Query 명령 실행 (courseId 기반 필터링)
        const response = await docClient.send(new QueryCommand({
            TableName: TABLE_NAME,
            IndexName: 'courseId-index',
            KeyConditionExpression: 'courseId = :courseId',
            ExpressionAttributeValues: {
                ':courseId': courseId
            },
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
        console.error('코스별 퀴즈 목록 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '코스별 퀴즈 목록 조회에 실패했습니다.' })
        };
    }
};

// 퀴즈 수정 핸들러
export const updateQuiz = async (event) => {
    try {
        if (!event.pathParameters || !event.pathParameters.id) {
            throw new Error("필수 매개변수가 누락되었습니다. (id 필요)");
        }
        if (!event.body) {
            throw new Error("요청 본문이 비어 있습니다.");
        }

        const { id } = event.pathParameters;
        const { question, choices, correctAnswer, explanation } = JSON.parse(event.body);

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
export const deleteQuiz = async (event) => {
    try {
        if (!event.pathParameters || !event.pathParameters.id) {
            throw new Error("필수 매개변수가 누락되었습니다. (id 필요)");
        }

        const { id } = event.pathParameters;

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

// 퀴즈 답변 제출 핸들러
export const answerQuiz = async (event) => {
    try {
        if (!event.body) {
            throw new Error("요청 본문이 비어 있습니다.");
        }

        const body = JSON.parse(event.body);

        if (!body.id || typeof body.answer !== 'number') {
            throw new Error("필수 매개변수가 누락되었습니다. (id, answer 필요)");
        }

        const { id, answer } = body;

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

        const isCorrect = Item.correctAnswer === answer;
        const message = isCorrect ? "정답입니다!" : "오답입니다. 다시 시도해보세요.";

        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({
                message,
                ...(isCorrect && { explanation: Item.explanation })
            })
        };
    } catch (error) {
        console.error('퀴즈 정답 확인 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: `퀴즈 정답 확인에 실패했습니다: ${error.message}` })
        };
    }
};
