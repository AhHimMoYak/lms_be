import {DeleteCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "./aws-client.js";

export const handler = async (event) => {
    try {
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

        const deleteParams = {
            TableName: process.env.DYNAMODB_TABLE,
            Key: {
                courseId: courseId,
                examId: examId,
            },
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
