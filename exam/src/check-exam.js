import { DynamoDBDocumentClient, QueryCommand } from "@aws-sdk/lib-dynamodb";
import { DynamoDBClient } from "@aws-sdk/client-dynamodb";

const client = new DynamoDBClient({ region: process.env.REGION });
const docClient = DynamoDBDocumentClient.from(client);

export const handler = async (event) => {
    try {
        const { examId } = event.pathParameters; // URL에서 examId 가져오기

        // 필수 필드 검증
        if (!examId) {
            return {
                statusCode: 400,
                headers: { "Content-Type": "application/json", "Access-Control-Allow-Origin": "*" },
                body: JSON.stringify({ error: "examId가 필요합니다." }),
            };
        }

        // DynamoDB에서 examId를 기반으로 데이터 조회
        const queryParams = {
            TableName: process.env.EXAM_SUBMIT_TABLE,
            KeyConditionExpression: "examId = :examId",
            ExpressionAttributeValues: {
                ":examId": examId,
            },
        };

        const response = await docClient.send(new QueryCommand(queryParams));

        // 데이터가 없을 경우
        if (!response.Items || response.Items.length === 0) {
            return {
                statusCode: 404,
                headers: { "Content-Type": "application/json", "Access-Control-Allow-Origin": "*" },
                body: JSON.stringify({ error: "해당 examId로 데이터를 찾을 수 없습니다." }),
            };
        }

        // 성공적으로 조회된 데이터 반환
        return {
            statusCode: 200,
            headers: { "Content-Type": "application/json", "Access-Control-Allow-Origin": "*" },
            body: JSON.stringify({
                message: "데이터 조회 성공",
                data: response.Items,
            }),
        };
    } catch (error) {
        console.error("데이터 조회 실패:", error);
        return {
            statusCode: 500,
            headers: { "Content-Type": "application/json", "Access-Control-Allow-Origin": "*" },
            body: JSON.stringify({ error: `데이터 조회 실패: ${error.message}` }),
        };
    }
};
