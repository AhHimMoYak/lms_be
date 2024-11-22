import {DynamoDBClient} from "@aws-sdk/client-dynamodb";
import {
    DynamoDBDocumentClient,
    PutCommand,
    GetCommand,
    UpdateCommand,
    DeleteCommand,
    QueryCommand
} from "@aws-sdk/lib-dynamodb";
import {v4 as uuidv4} from 'uuid';

// 환경 변수로부터 DynamoDB 설정 정보 가져오기
const client = new DynamoDBClient({region: process.env.REGION || 'ap-northeast-2'});
const docClient = DynamoDBDocumentClient.from(client);
const TABLE_NAME = process.env.DYNAMODB_TABLE || 'YourDefaultTableName';

// 퀴즈 생성 핸들러
export const createExam = async (event) => {
    try {
        if (!event.body) throw new Error("요청 본문이 비어 있습니다.");

        const body = JSON.parse(event.body);

        const exam = {
            id: `exam_${uuidv4()}`,
            courseId: body.courseId,
            title: body.title,
            description: body.description,
            status: "NOT_STARTED", // 초기 상태 설정
            quizzes: body.quizzes.map((quiz) => ({
                id: `quiz_${uuidv4()}`,
                question: quiz.question,
                choices: quiz.choices || ['', '', '', ''],
                correctAnswer: quiz.correctAnswer,
                explanation: quiz.explanation || '',
            })),
        };

        await docClient.send(new PutCommand({
            TableName: TABLE_NAME,
            Item: exam,
        }));

        return {
            statusCode: 201,
            headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'},
            body: JSON.stringify(exam),
        };
    } catch (error) {
        console.error('시험 생성 실패:', error);
        return {
            statusCode: 500,
            headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'},
            body: JSON.stringify({message: `시험 생성에 실패했습니다: ${error.message}`}),
        };
    }
};

export const getExam = async (event) => {
    try {
        const {id} = event.pathParameters;
        if (!id) throw new Error("필수 매개변수가 누락되었습니다. (id 필요)");

        const {Item} = await docClient.send(new GetCommand({
            TableName: TABLE_NAME,
            Key: {id},
        }));

        if (!Item) {
            return {
                statusCode: 404,
                headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'},
                body: JSON.stringify({message: '시험을 찾을 수 없습니다.'}),
            };
        }

        return {
            statusCode: 200,
            headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'},
            body: JSON.stringify(Item),
        };
    } catch (error) {
        console.error('시험 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'},
            body: JSON.stringify({message: `시험 조회에 실패했습니다: ${error.message}`}),
        };
    }
};


export const listExamsByCourse = async (event) => {
    try {
        const {courseId} = event.pathParameters;
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
            ExpressionAttributeValues: {':courseId': courseId},
            Limit: limit,
            ...(lastEvaluatedKey && {ExclusiveStartKey: lastEvaluatedKey}),
        }));

        return {
            statusCode: 200,
            headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'},
            body: JSON.stringify({
                items: response.Items,
                lastEvaluatedKey: response.LastEvaluatedKey,
            }),
        };
    } catch (error) {
        console.error('코스별 시험 목록 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'},
            body: JSON.stringify({message: '코스별 시험 목록 조회에 실패했습니다.'}),
        };
    }
};


export const deleteExam = async (event) => {
    try {
        const {id} = event.pathParameters;

        await docClient.send(new DeleteCommand({
            TableName: TABLE_NAME,
            Key: {id},
        }));

        return {
            statusCode: 204,
            headers: {'Access-Control-Allow-Origin': '*'},
        };
    } catch (error) {
        console.error('시험 삭제 실패:', error);
        return {
            statusCode: 500,
            headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'},
            body: JSON.stringify({message: '시험 삭제에 실패했습니다.'}),
        };
    }
};

// 퀴즈 수정 핸들러
export const updateExam = async (event) => {
    try {
        if (!event.pathParameters || !event.pathParameters.id) {
            throw new Error("필수 매개변수가 누락되었습니다. (id 필요)");
        }
        if (!event.body) {
            throw new Error("요청 본문이 비어 있습니다.");
        }

        const { id } = event.pathParameters;
        const { title, description, status, quizzes } = JSON.parse(event.body);

        const response = await docClient.send(new UpdateCommand({
            TableName: TABLE_NAME,
            Key: { id },
            UpdateExpression:
                'SET #title = :title, #description = :description, #status = :status, #quizzes = :quizzes',
            ExpressionAttributeNames: {
                '#title': 'title',
                '#description': 'description',
                '#status': 'status', // 예약어 대체
                '#quizzes': 'quizzes',
            },
            ExpressionAttributeValues: {
                ':title': title,
                ':description': description,
                ':status': status,
                ':quizzes': quizzes.map((quiz) => ({
                    question: quiz.question,
                    choices: quiz.choices || ['', '', '', ''],
                    correctAnswer: quiz.correctAnswer,
                    explanation: quiz.explanation || '',
                })),
            },
            ReturnValues: 'ALL_NEW',
        }));

        return {
            statusCode: 200,
            headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' },
            body: JSON.stringify(response.Attributes),
        };
    } catch (error) {
        console.error('시험 수정 실패:', error);
        return {
            statusCode: 500,
            headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' },
            body: JSON.stringify({ message: `시험 수정에 실패했습니다: ${error.message}` }),
        };
    }
};

export const updateExamStatus = async (event) => {
    try {
        if (!event.pathParameters || !event.pathParameters.id) {
            throw new Error("필수 매개변수가 누락되었습니다. (id 필요)");
        }

        const {id} = event.pathParameters;
        const {status} = JSON.parse(event.body);

        const response = await docClient.send(new UpdateCommand({
            TableName: TABLE_NAME,
            Key: {id},
            UpdateExpression: 'SET status = :status',
            ExpressionAttributeValues: {
                ':status': status,
            },
            ReturnValues: 'ALL_NEW',
        }));

        return {
            statusCode: 200,
            headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'},
            body: JSON.stringify(response.Attributes),
        };
    } catch (error) {
        console.error('시험 상태 업데이트 실패:', error);
        return {
            statusCode: 500,
            headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'},
            body: JSON.stringify({message: `시험 상태 업데이트에 실패했습니다: ${error.message}`}),
        };
    }
};


// 퀴즈 답변 제출 핸들러
export const submitExamAnswers = async (event) => {
    try {
        if (!event.pathParameters || !event.pathParameters.id) {
            throw new Error("필수 매개변수가 누락되었습니다. (id 필요)");
        }
        if (!event.body) {
            throw new Error("요청 본문이 비어 있습니다.");
        }

        const { id } = event.pathParameters; // 시험 ID
        const { answers } = JSON.parse(event.body); // 제출된 답변 배열

        // DynamoDB에서 시험 데이터를 가져옵니다.
        const { Item: exam } = await docClient.send(
            new GetCommand({
                TableName: TABLE_NAME,
                Key: { id },
            })
        );

        if (!exam) {
            return {
                statusCode: 404,
                headers: { "Content-Type": "application/json", "Access-Control-Allow-Origin": "*" },
                body: JSON.stringify({ message: "시험을 찾을 수 없습니다." }),
            };
        }

        // 사용자 답변 검증
        if (!Array.isArray(answers) || answers.length !== exam.quizzes.length) {
            return {
                statusCode: 400,
                headers: { "Content-Type": "application/json", "Access-Control-Allow-Origin": "*" },
                body: JSON.stringify({
                    message: "제출된 답안 수가 시험 문제 수와 일치하지 않습니다.",
                }),
            };
        }

        // 정답 비교 로직
        const results = exam.quizzes.map((quiz, index) => {
            const isCorrect = quiz.correctAnswer === answers[index];
            return {
                question: quiz.question,
                correct: isCorrect,
                explanation: isCorrect ? quiz.explanation : null,
            };
        });

        const totalCorrect = results.filter((result) => result.correct).length;
        const score = Math.round((totalCorrect / exam.quizzes.length) * 100); // 점수 계산

        // 상태 업데이트 (시험 완료로 변경)
        await docClient.send(
            new UpdateCommand({
                TableName: TABLE_NAME,
                Key: { id },
                UpdateExpression: "SET status = :status",
                ExpressionAttributeValues: {
                    ":status": "COMPLETED",
                },
            })
        );

        return {
            statusCode: 200,
            headers: { "Content-Type": "application/json", "Access-Control-Allow-Origin": "*" },
            body: JSON.stringify({
                message: "시험 답안 제출이 완료되었습니다.",
                results,
                totalCorrect,
                score,
            }),
        };
    } catch (error) {
        console.error("시험 답변 제출 실패:", error);
        return {
            statusCode: 500,
            headers: { "Content-Type": "application/json", "Access-Control-Allow-Origin": "*" },
            body: JSON.stringify({ message: `시험 답변 제출에 실패했습니다: ${error.message}` }),
        };
    }
};
