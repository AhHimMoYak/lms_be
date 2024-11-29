import { GetCommand, PutCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "./aws-client.js";
import { v4 as uuidv4 } from "uuid";

export const handler = async (event) => {
    try {
        // 환경 변수 가져오기
        const DYNAMODB_TABLE = process.env.DYNAMODB_TABLE;

        if (!DYNAMODB_TABLE) {
            throw new Error("DYNAMODB_TABLE 환경 변수가 설정되지 않았습니다.");
        }

        // Path Parameters에서 courseId, examId 가져오기
        const { courseId, examId } = event.pathParameters || {};

        if (!courseId || !examId) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({ error: "courseId 또는 examId가 누락되었습니다." }),
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

        // 기존 시험 데이터 가져오기
        const existingExam = await docClient.send(
            new GetCommand({
                TableName: DYNAMODB_TABLE,
                Key: {
                    courseId: courseId,
                    examId: examId,
                },
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

        // 데이터 업데이트
        const updatedExam = {
            examId: examId, // 기존 examId 사용
            courseId: courseId,
            title: body.title,
            description: body.description || existingExam.Item.description,
            quizzes: body.quizzes.map((quiz) => ({
                id: quiz.id || `quiz_${uuidv4()}`, // 기존 퀴즈 ID가 있으면 그대로 사용
                question: quiz.question,
                choices: quiz.choices || ["", "", "", ""],
                correctAnswer: quiz.correctAnswer,
                explanation: quiz.explanation || "",
            })),
        };

        // DynamoDB에 데이터 업데이트
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
            body: JSON.stringify(updatedExam),
        };
    } catch (error) {
        console.error("시험 수정 실패:", error);
        return {
            statusCode: 500,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                message: `시험 수정에 실패했습니다: ${error.message}`,
            }),
        };
    }
};
