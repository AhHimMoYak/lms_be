import { QueryCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "./aws-client.js";

export const handler = async (event) => {
    try {
        // Path Parameters에서 courseId 가져오기
        const { courseId } = event.pathParameters || {};

        if (!courseId) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({ error: "courseId가 누락되었습니다." }),
            };
        }

        // DynamoDB에서 시험 목록 가져오기
        const params = {
            TableName: process.env.DYNAMODB_TABLE,
            KeyConditionExpression: "courseId = :courseId",
            ExpressionAttributeValues: {
                ":courseId": courseId,
            },
        };

        const result = await docClient.send(new QueryCommand(params));

        // 시험 데이터를 UI에 맞는 형태로 가공
        const formattedExams = result.Items.map((exam) => ({
            examId: exam.examId,
            examName: exam.examName,
            description: exam.description || "",
            duration: exam.duration || "60분", // 시험 시간 (기본값 60분)
            questionCount: exam.quizzes ? exam.quizzes.length : 0, // 문제 개수
            passingScore: exam.passingScore || 100, // 배점
            status: exam.status || "미응시", // 상태 (예: 미응시, 진행중, 완료)
            score: exam.score || null, // 점수
        }));

        return {
            statusCode: 200,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify(formattedExams),
        };
    } catch (error) {
        console.error("시험 목록 조회 실패:", error);
        return {
            statusCode: 500,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                message: `시험 목록 조회에 실패했습니다: ${error.message}`,
            }),
        };
    }
};
