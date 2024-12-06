import {GetCommand, PutCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "./aws-client.js";
import {v4 as uuidv4} from "uuid";

export const handler = async (event) => {
    try {
        const DYNAMODB_TABLE = process.env.DYNAMODB_TABLE;

        if (!DYNAMODB_TABLE) {
            throw new Error("DYNAMODB_TABLE 환경 변수가 설정되지 않았습니다.");
        }

        // 경로 매개변수에서 courseId와 examId 가져오기
        const {courseId, examId} = event.pathParameters || {};
        if (!courseId || !examId) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Credentials": true,
                },
                body: JSON.stringify({error: "courseId와 examId는 필수입니다."}),
            };
        }

        if (!event.body) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Credentials": true,
                },
                body: JSON.stringify({error: "요청 본문이 비어 있습니다."}),
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
                Key: {courseId, examId},
            })
        );

        if (!existingExam.Item) {
            return {
                statusCode: 404,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Credentials": true,
                },
                body: JSON.stringify({error: "시험을 찾을 수 없습니다."}),
            };
        }

        // 시험 이름 수정
        const updatedExamName = body.examName || existingExam.Item.examName;

        // 퀴즈 추가, 수정 또는 삭제
        const existingQuizzes = existingExam.Item.quizzes || [];
        let updatedQuizzes = [...existingQuizzes];

        if (body.quiz) {
            const {id, question, options, correctAnswer, explanation, action} = body.quiz;

            if (action === "add") {
                // 새 퀴즈 추가
                updatedQuizzes.push({
                    id: `quiz_${uuidv4()}`, // quizId 자동 생성
                    question: question || "",
                    options: options || ["", "", "", ""],
                    correctAnswer: correctAnswer || "", // 정답 (인덱스 번호)
                    explanation: explanation || "",
                });
            } else if (action === "update") {
                // 기존 퀴즈 수정
                const quizIndex = updatedQuizzes.findIndex((q) => q.id === id);

                if (quizIndex >= 0) {
                    updatedQuizzes[quizIndex] = {
                        ...updatedQuizzes[quizIndex],
                        question: question || updatedQuizzes[quizIndex].question,
                        options: options || updatedQuizzes[quizIndex].options,
                        correctAnswer: correctAnswer || updatedQuizzes[quizIndex].correctAnswer,
                        explanation: explanation || updatedQuizzes[quizIndex].explanation,
                    };
                } else {
                    return {
                        statusCode: 404,
                        headers: {
                            "Content-Type": "application/json",
                            "Access-Control-Allow-Origin": "*",
                            "Access-Control-Allow-Credentials": true,
                        },
                        body: JSON.stringify({error: "수정할 퀴즈를 찾을 수 없습니다."}),
                    };
                }
            } else if (action === "delete") {
                // 퀴즈 삭제
                updatedQuizzes = updatedQuizzes.filter((quiz) => quiz.id !== id);

                if (updatedQuizzes.length === existingQuizzes.length) {
                    return {
                        statusCode: 404,
                        headers: {
                            "Content-Type": "application/json",
                            "Access-Control-Allow-Origin": "*",
                            "Access-Control-Allow-Credentials": true,
                        },
                        body: JSON.stringify({error: "삭제할 퀴즈를 찾을 수 없습니다."}),
                    };
                }
            } else {
                return {
                    statusCode: 400,
                    headers: {
                        "Content-Type": "application/json",
                        "Access-Control-Allow-Origin": "*",
                        "Access-Control-Allow-Credentials": true,
                    },
                    body: JSON.stringify({error: "올바르지 않은 action입니다. (add, update, delete 중 하나여야 합니다)"}),
                };
            }
        }

        // 데이터 업데이트
        const updatedExam = {
            ...existingExam.Item,
            examName: updatedExamName,
            quizzes: updatedQuizzes,
            updatedAt: new Date().toISOString(), // 업데이트 시간 추가
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
                "Access-Control-Allow-Credentials": true,
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
                "Access-Control-Allow-Credentials": true,
            },
            body: JSON.stringify({
                message: `시험 수정에 실패했습니다: ${error.message}`,
            }),
        };
    }
};
