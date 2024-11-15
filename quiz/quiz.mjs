import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
    DynamoDBDocumentClient,
    PutCommand,
    GetCommand,
    UpdateCommand,
    DeleteCommand,
    QueryCommand
} from "@aws-sdk/lib-dynamodb";
import { v4 as uuidv4 } from 'uuid';

// 환경 변수로부터 DynamoDB 설정 정보 가져오기
const client = new DynamoDBClient({ region: process.env.REGION || 'ap-northeast-2' });
const docClient = DynamoDBDocumentClient.from(client);
const TABLE_NAME = process.env.DYNAMODB_TABLE || 'YourDefaultTableName';

// 퀴즈 생성 핸들러
export const createQuiz = async (event) => {
    try {
        if (!event.body) throw new Error("요청 본문이 비어 있습니다.");

        const body = JSON.parse(event.body);

        const post = {
            id: `post_${uuidv4()}`,
            courseId: body.courseId,
            question: body.question,
            choices: body.choices || ['', '', '', ''],
            correctAnswer: body.correctAnswer,
            explanation: body.explanation || ''
        };

        await docClient.send(new PutCommand({
            TableName: TABLE_NAME,
            Item: post
        }));

        return {
            statusCode: 201,
            headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' },
            body: JSON.stringify(post)
        };
    } catch (error) {
        console.error('퀴즈 생성 실패:', error);
        return {
            statusCode: 500,
            headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' },
            body: JSON.stringify({ message: `퀴즈 생성에 실패했습니다: ${error.message}` })
        };
    }
};

// 특정 퀴즈 조회 핸들러
export const getQuiz = async (event) => {
    try {
        const { id } = event.pathParameters;
        if (!id) throw new Error("필수 매개변수가 누락되었습니다. (id 필요)");

        const { Item } = await docClient.send(new GetCommand({
            TableName: TABLE_NAME,
            Key: { id }
        }));

        if (!Item) {
            return {
                statusCode: 404,
                headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' },
                body: JSON.stringify({ message: '퀴즈를 찾을 수 없습니다.' })
            };
        }

        return {
            statusCode: 200,
            headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' },
            body: JSON.stringify(Item)
        };
    } catch (error) {
        console.error('퀴즈 조회 실패:', error);
        return {
            statusCode: 500,
            headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' },
            body: JSON.stringify({ message: `퀴즈 조회에 실패했습니다: ${error.message}` })
        };
    }
};

// 특정 코스 ID의 퀴즈 목록 조회 핸들러
export const listQuizByCourse = async (event) => {
    try {
        const { courseId } = event.pathParameters;
        if (!courseId) throw new Error("필수 매개변수가 누락되었습니다. (courseId 필요)");

        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;

        let lastEvaluatedKey;
        if (event.queryStringParameters?.lastEvaluatedKey) {
            try {
                lastEvaluatedKey = JSON.parse(event.queryStringParameters.lastEvaluatedKey);
            } catch (error) {
                console.error("lastEvaluatedKey 파싱 실패:", error);
                throw new Error("lastEvaluatedKey 형식이 잘못되었습니다.");
            }
        }

        const response = await docClient.send(new QueryCommand({
            TableName: TABLE_NAME,
            IndexName: 'courseId-index',
            KeyConditionExpression: 'courseId = :courseId',
            ExpressionAttributeValues: { ':courseId': courseId },
            Limit: limit,
            ...(lastEvaluatedKey && { ExclusiveStartKey: lastEvaluatedKey })
        }));

        return {
            statusCode: 200,
            headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' },
            body: JSON.stringify({
                items: response.Items,
                lastEvaluatedKey: response.LastEvaluatedKey
            })
        };
    } catch (error) {
        console.error('코스별 퀴즈 목록 조회 실패:', error);
        return {
            statusCode: 500,
            headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' },
            body: JSON.stringify({ message: '코스별 퀴즈 목록 조회에 실패했습니다.' })
        };
    }
};

// 퀴즈 삭제 핸들러
export const deleteQuiz = async (event) => {
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
        console.error('퀴즈 삭제 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '퀴즈 삭제에 실패했습니다.' })
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
            Key: { id }, // id를 Key로 사용합니다.
            UpdateExpression: 'SET question = :question, choices = :choices, correctAnswer = :correctAnswer, explanation = :explanation',
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

// 퀴즈 답변 제출 핸들러
export const answerQuiz = async (event) => {
    try {
        if (!event.pathParameters || !event.pathParameters.id) {
            throw new Error("필수 매개변수가 누락되었습니다. (id 필요)");
        }
        if (!event.body) {
            throw new Error("요청 본문이 비어 있습니다.");
        }

        const { id } = event.pathParameters;
        const { answer } = JSON.parse(event.body); // 제출한 답변

        // DynamoDB에서 퀴즈 데이터를 가져옵니다.
        const { Item: quiz } = await docClient.send(new GetCommand({
            TableName: TABLE_NAME,
            Key: { id }
        }));

        if (!quiz) {
            return {
                statusCode: 404,
                headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' },
                body: JSON.stringify({ message: '퀴즈를 찾을 수 없습니다.' })
            };
        }

        // 제출한 답변이 정답인지 확인합니다.
        const isCorrect = quiz.correctAnswer === answer;
        const message = isCorrect ? "정답입니다!" : "오답입니다.";

        return {
            statusCode: 200,
            headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' },
            body: JSON.stringify({
                message,
                isCorrect,
                explanation: isCorrect ? quiz.explanation : null // 정답일 경우 해설을 포함
            })
        };
    } catch (error) {
        console.error('퀴즈 답변 제출 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: `퀴즈 답변 제출에 실패했습니다: ${error.message}` })
        };
    }
};
