import { GetCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "./aws-client.js";

export const handler = async (event) => {
    try {
        // Path Parameters에서 courseId와 examId 가져오기
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

        // DynamoDB GetCommand를 사용하여 특정 exam 데이터 조회
        const params = {
            TableName: process.env.DYNAMODB_TABLE,
            Key: {
                courseId,
                examId,
            },
        };

        const result = await docClient.send(new GetCommand(params));

        if (!result.Item) {
            return {
                statusCode: 404,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({ error: "해당 exam이 존재하지 않습니다." }),
            };
        }

        // 조회된 결과 반환
        return {
            statusCode: 200,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify(result.Item),
        };
    } catch (error) {
        console.error("Exam 상세 조회 실패:", error);
        return {
            statusCode: 500,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                message: `Exam 상세 조회에 실패했습니다: ${error.message}`,
            }),
        };
    }
};
