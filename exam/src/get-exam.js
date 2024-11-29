import {QueryCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "./aws-client.js"; // 기존 AWS 클라이언트 가져오기

export const handler = async (event) => {
    try {
        // Path Parameters에서 courseId 가져오기
        const {courseId} = event.pathParameters || {};

        if (!courseId) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({error: "courseId가 누락되었습니다."}),
            };
        }

        // DynamoDB QueryCommand를 사용하여 데이터 조회
        const params = {
            TableName: process.env.DYNAMODB_TABLE,
            KeyConditionExpression: "courseId = :courseId",
            ExpressionAttributeValues: {
                ":courseId": courseId,
            },
        };

        const result = await docClient.send(new QueryCommand(params));

        // 결과 반환
        return {
            statusCode: 200,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify(result.Items),
        };
    } catch (error) {
        console.error("시험 데이터 조회 실패:", error);
        return {
            statusCode: 500,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                message: `시험 데이터 조회에 실패했습니다: ${error.message}`,
            }),
        };
    }
};
