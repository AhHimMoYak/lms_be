import { GetCommand, PutCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "./aws-client.js";
import { v4 as uuidv4 } from "uuid";

export const handler = async (event) => {
    try {
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

        // 기존 시험 데이터 가져오기
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

        // 시험 이름 수정
        const updatedExamName = body.examName || existingExam.Item.examName;

        // 퀴즈 추가 또는 수정
        const existingQuizzes = existingExam.Item.quizzes || [];
        let updatedQuizzes = [...existingQuizzes];

        if (body.quiz) {
            if (!body.quiz.id) {
                // 새 퀴즈 추가 (quizId 자동 생성)
                updatedQuizzes.push({
                    id: `quiz_${uuidv4()}`, // quizId 자동 생성
                    question: body.quiz.question,
                    options: body.quiz.options || ["", "", "", ""],
                    correctAnswer: body.quiz.correctAnswer, // 인덱스 번호로 정답 저장
                    explanation: body.quiz.explanation || "",
                });
            } else {
                // 기존 퀴즈 수정
                const quizIndex = updatedQuizzes.findIndex((q) => q.id === body.quiz.id);

                if (quizIndex >= 0) {
                    updatedQuizzes[quizIndex] = {
                        ...updatedQuizzes[quizIndex],
                        ...body.quiz,
                        correctAnswer: body.quiz.correctAnswer,
                    };
                } else {
                    return {
                        statusCode: 404,
                        headers: {
                            "Content-Type": "application/json",
                            "Access-Control-Allow-Origin": "*",
                        },
                        body: JSON.stringify({ error: "수정할 퀴즈를 찾을 수 없습니다." }),
                    };
                }
            }
        }

        // 데이터 업데이트
        const updatedExam = {
            ...existingExam.Item,
            examName: updatedExamName,
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
