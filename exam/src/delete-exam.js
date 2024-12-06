import {GetCommand, DeleteCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "./aws-client.js";

export const handler = async (event) => {
    try {
        // Path Parameters에서 courseId와 examId 가져오기
        const {courseId, examId} = event.pathParameters || {};

        if (!courseId || !examId) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify({error: "courseId 또는 examId가 누락되었습니다."}),
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
                body: JSON.stringify({error: "DYNAMODB_TABLE 환경 변수가 설정되지 않았습니다."}),
            };
        }

        // 시험 데이터 확인 (존재 여부 확인)
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
                },
                body: JSON.stringify({error: "삭제하려는 시험이 존재하지 않습니다."}),
            };
        }

        // 시험 삭제
        const deleteParams = {
            TableName: DYNAMODB_TABLE,
            Key: {courseId, examId},
        };

        await docClient.send(new DeleteCommand(deleteParams));

        return {
            statusCode: 200,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({message: "시험이 성공적으로 삭제되었습니다."}),
        };
    } catch (error) {
        console.error("시험 삭제 실패:", error);
        return {
            statusCode: 500,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                message: `시험 삭제에 실패했습니다: ${error.message}`,
            }),
        };
    }
};
