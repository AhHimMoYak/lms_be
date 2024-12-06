import { GetCommand, PutCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "./aws-client.js";

export const handler = async (event) => {
    try {
        const { courseId, examId, quizId } = event.pathParameters || {};

        if (!courseId || !examId || !quizId) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({ error: "courseId, examId 또는 quizId가 누락되었습니다." }),
            };
        }

        // 환경 변수 확인
        const DYNAMODB_TABLE = process.env.DYNAMODB_TABLE;
        if (!DYNAMODB_TABLE) {
            return {
                statusCode: 500,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({ error: "DYNAMODB_TABLE 환경 변수가 설정되지 않았습니다." }),
            };
        }

        // 시험 데이터 가져오기
        const existingExam = await docClient.send(
            new GetCommand({
                TableName: DYNAMODB_TABLE,
                Key: { courseId, examId },
            })
        );

        if (!existingExam.Item) {
            return {
                statusCode: 404,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({ error: "시험을 찾을 수 없습니다." }),
            };
        }

        // 퀴즈 배열에서 해당 quizId 삭제
        const existingQuizzes = existingExam.Item.quizzes || [];
        const updatedQuizzes = existingQuizzes.filter((quiz) => quiz.id !== quizId);

        if (existingQuizzes.length === updatedQuizzes.length) {
            return {
                statusCode: 404,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({ error: "삭제하려는 퀴즈를 찾을 수 없습니다." }),
            };
        }

        // 데이터 업데이트
        const updatedExam = {
            ...existingExam.Item,
            quizzes: updatedQuizzes,
        };

        await docClient.send(
            new PutCommand({
                TableName: DYNAMODB_TABLE,
                Item: updatedExam,
            })
        );

        // 성공 응답
        return {
            statusCode: 200,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({ message: "퀴즈가 성공적으로 삭제되었습니다." }),
        };
    } catch (error) {
        console.error("퀴즈 삭제 실패:", error);
        return {
            statusCode: 500,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                message: `퀴즈 삭제에 실패했습니다: ${error.message}`,
            }),
        };
    }
};
