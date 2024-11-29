import { DynamoDBDocumentClient, PutCommand, GetCommand } from "@aws-sdk/lib-dynamodb";
import { DynamoDBClient } from "@aws-sdk/client-dynamodb";

const client = new DynamoDBClient({ region: process.env.REGION });
const docClient = DynamoDBDocumentClient.from(client); // DocumentClient 생성

export const handler = async (event) => {
    try {
        const { examId, username, answers, courseId, courseProvideId } = JSON.parse(event.body);

        // 필수 필드 검증
        if (!examId || !username || !answers || !courseId || !courseProvideId) {
            return {
                statusCode: 400,
                headers: { "Content-Type": "application/json", "Access-Control-Allow-Origin": "*" },
                body: JSON.stringify({ error: "필드 값이 유효하지 않습니다. 모든 필드를 입력해야 합니다." }),
            };
        }

        // 시험 데이터 조회
        const getParams = { TableName: process.env.DYNAMODB_TABLE, Key: { examId, courseId } };
        const examData = await docClient.send(new GetCommand(getParams));

        // 시험 데이터가 없을 경우
        if (!examData.Item) {
            return {
                statusCode: 404,
                headers: { "Content-Type": "application/json", "Access-Control-Allow-Origin": "*" },
                body: JSON.stringify({ error: "시험 데이터를 찾을 수 없습니다." }),
            };
        }

        const quizzes = examData.Item.quizzes;

        // 제출된 답변 검증 및 정답 비교
        const results = quizzes.map((quiz, index) => ({
            question: quiz.question,
            userAnswer: answers[index],
            correctAnswer: quiz.correctAnswer,
            isCorrect: quiz.correctAnswer === answers[index],
        }));

        // 제출 데이터 저장
        const putParams = {
            TableName: process.env.EXAM_SUBMIT_TABLE,
            Item: {
                submissionId: `${examId}-${username}`,
                examId,
                courseId,
                courseProvideId,
                username,
                answers,
                status: "COMPLETED",
                submittedAt: new Date().toISOString(),
                results,
            },
        };

        await docClient.send(new PutCommand(putParams));

        // 성공 응답
        return {
            statusCode: 200,
            headers: { "Content-Type": "application/json", "Access-Control-Allow-Origin": "*" },
            body: JSON.stringify({
                message: "시험 제출 성공",
                results,
                status: "COMPLETED",
            }),
        };
    } catch (error) {
        console.error("시험 제출 실패:", error);
        // 에러 응답
        return {
            statusCode: 500,
            headers: { "Content-Type": "application/json", "Access-Control-Allow-Origin": "*" },
            body: JSON.stringify({ error: `시험 제출 실패: ${error.message}` }),
        };
    }
};
