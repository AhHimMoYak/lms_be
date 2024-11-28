import { PutCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "./aws-client.js";
import { v4 as uuidv4 } from "uuid"; // UUID 생성 라이브러리 사용

const TABLE_NAME = process.env.DYNAMODB_TABLE; // DynamoDB 테이블 이름

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

        if (!event.body) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({ error: "요청 본문이 비어 있습니다." }),
            };
        }

        // 요청 본문 파싱
        const body = event.isBase64Encoded
            ? JSON.parse(Buffer.from(event.body, "base64").toString())
            : JSON.parse(event.body);

        // 요청 데이터 검증
        if (!body.title || !body.quizzes || !Array.isArray(body.quizzes)) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({
                    error: "필수 필드(title, quizzes)가 누락되었거나 형식이 잘못되었습니다.",
                }),
            };
        }

        // 데이터 생성
        const exam = {
            examId: `exam_${uuidv4()}`, // 고유 ID
            courseId: courseId, // Path Parameters에서 가져온 courseId
            title: body.title,
            description: body.description || "",
            quizzes: body.quizzes.map((quiz) => ({
                id: `quiz_${uuidv4()}`, // 퀴즈 고유 ID
                question: quiz.question,
                choices: quiz.choices || ["", "", "", ""],
                correctAnswer: quiz.correctAnswer,
                explanation: quiz.explanation || "",
            })),
        };

        // DynamoDB에 데이터 저장
        await docClient.send(
            new PutCommand({
                TableName: TABLE_NAME,
                Item: exam,
            })
        );

        // 성공 응답
        return {
            statusCode: 201,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify(exam),
        };
    } catch (error) {
        console.error("시험 생성 실패:", error);
        return {
            statusCode: 500,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                message: `시험 생성에 실패했습니다: ${error.message}`,
            }),
        };
    }
};
