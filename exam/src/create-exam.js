import { v4 as uuidv4 } from "uuid"; // UUID 생성 모듈
import { PutCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "./aws-client.js"; // AWS 클라이언트 가져오기

export const handler = async (event) => {
    try {
        // 요청에서 courseId와 examName 가져오기
        const { courseId, examName } = JSON.parse(event.body);

        if (!courseId || !examName) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({
                    error: "courseId와 examName은 필수 항목입니다.",
                }),
            };
        }

        // 자동으로 examId 생성
        const examId = uuidv4();

        // DynamoDB에 새 데이터 삽입
        const params = {
            TableName: process.env.DYNAMODB_TABLE,
            Item: {
                courseId: courseId,       // DynamoDB Partition Key
                examId: examId,           // 자동 생성된 시험 ID
                examName: examName,       // 시험 이름
                quizzes: [],              // 초기 문제 리스트 (빈 배열)
                answers: [],              // 초기 정답 리스트 (빈 배열)
                explanations: [],         // 초기 해설 리스트 (빈 배열)
            },
        };

        await docClient.send(new PutCommand(params));

        // 성공 응답
        return {
            statusCode: 201,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                message: "시험이 성공적으로 생성되었습니다.",
                examId: examId, // 생성된 시험 ID 반환
            }),
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
