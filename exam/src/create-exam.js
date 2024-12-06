import { v4 as uuidv4 } from "uuid"; // UUID 생성 모듈
import { PutCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "./aws-client.js"; // AWS 클라이언트 가져오기

export const handler = async (event) => {
    try {
        const DYNAMODB_TABLE = process.env.DYNAMODB_TABLE;

        if (!DYNAMODB_TABLE) {
            throw new Error("DYNAMODB_TABLE 환경 변수가 설정되지 않았습니다.");
        }

        // 요청 본문 유효성 검증
        if (!event.body) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Credentials": true,
                },
                body: JSON.stringify({
                    error: "요청 본문이 비어 있습니다. JSON 데이터를 포함해야 합니다.",
                }),
            };
        }

        let parsedBody;
        try {
            parsedBody = JSON.parse(event.body);
        } catch (error) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Credentials": true,
                },
                body: JSON.stringify({
                    error: "유효한 JSON 형식이 아닙니다.",
                }),
            };
        }

        // 요청 본문 파싱
        const { courseId, examName, duration = 60, description = "", quizzes = [] } = parsedBody;

        // 필수 필드 확인
        if (!courseId || !examName) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Credentials": true,
                },
                body: JSON.stringify({
                    error: "courseId와 examName은 필수 항목입니다.",
                }),
            };
        }

        // UUID 생성
        const examId = uuidv4();

        // 퀴즈 데이터 처리 (퀴즈마다 고유 ID 생성)
        const processedQuizzes = quizzes.map((quiz) => ({
            id: `quiz_${uuidv4()}`,
            question: quiz.question || "",
            options: quiz.options || ["", "", "", ""],
            correctAnswer: quiz.correctAnswer || "",
            explanation: quiz.explanation || "",
        }));

        // DynamoDB에 새 데이터 삽입
        const params = {
            TableName: DYNAMODB_TABLE,
            Item: {
                courseId,
                examId,
                examName,
                duration: `${duration}분`,
                description,
                quizzes: processedQuizzes,
                status: "미응시",
                score: null,
                createdAt: new Date().toISOString(),
                updatedAt: new Date().toISOString(),
            },
        };

        await docClient.send(new PutCommand(params));

        return {
            statusCode: 201,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Credentials": true,
            },
            body: JSON.stringify({
                message: "시험이 성공적으로 생성되었습니다.",
                exam: {
                    examId,
                    courseId,
                    examName,
                    duration: `${duration}분`,
                    description,
                    quizzes: processedQuizzes,
                },
            }),
        };
    } catch (error) {
        console.error("시험 생성 실패:", error);
        return {
            statusCode: 500,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Credentials": true,
            },
            body: JSON.stringify({
                message: `시험 생성에 실패했습니다: ${error.message}`,
            }),
        };
    }
};
