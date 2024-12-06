import { QueryCommand } from "@aws-sdk/lib-dynamodb";
import { docClient } from "./aws-client.js";

export const handler = async (event) => {
    try {
        const provideTable = process.env.PROVIDE_TABLE; // 제공 테이블
        const examTable = process.env.EXAM_TABLE; // 시험 테이블

        const { provideId } = event.pathParameters || {};

        if (!provideId) {
            return {
                statusCode: 400,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Credentials": true,
                },
                body: JSON.stringify({ error: "provideId가 누락되었습니다." }),
            };
        }

        // 제공 데이터 조회
        const provideParams = {
            TableName: provideTable,
            IndexName: "ProvideIndex",
            KeyConditionExpression: "provideId = :provideId",
            ExpressionAttributeValues: {
                ":provideId": provideId,
            },
        };

        const provideResult = await docClient.send(new QueryCommand(provideParams));
        if (!provideResult.Items || provideResult.Items.length === 0) {
            return {
                statusCode: 404,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Credentials": true,
                },
                body: JSON.stringify({ error: "해당 provideId에 대한 데이터를 찾을 수 없습니다." }),
            };
        }

        const provideData = provideResult.Items[0];
        const { students, courseId } = provideData;

        // 시험 데이터 조회
        const examParams = {
            TableName: examTable,
            KeyConditionExpression: "courseId = :courseId",
            ExpressionAttributeValues: {
                ":courseId": courseId,
            },
        };

        const examResult = await docClient.send(new QueryCommand(examParams));
        if (!examResult.Items || examResult.Items.length === 0) {
            return {
                statusCode: 404,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Credentials": true,
                },
                body: JSON.stringify({ error: "해당 courseId에 대한 시험 데이터를 찾을 수 없습니다." }),
            };
        }

        // 수강생 데이터 가공
        const formattedExams = examResult.Items.map((exam) => ({
            examId: exam.examId,
            examName: exam.examName,
            duration: exam.duration,
            quizCount: exam.quizzes ? exam.quizzes.length : 0,
            results: students.map((student) => {
                const submission = exam.submissions?.find((sub) => sub.studentId === student.id);
                return {
                    studentId: student.id,
                    studentName: student.name,
                    progress: student.progress,
                    lastAccess: student.lastAccess,
                    status: submission ? "완료" : "미응시",
                    score: submission ? submission.score : null,
                    submittedAt: submission ? submission.submittedAt : null,
                };
            }),
        }));

        return {
            statusCode: 200,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Credentials": true,
            },
            body: JSON.stringify({
                provideDetails: {
                    provideId,
                    company: provideData.company,
                    courseTitle: provideData.courseTitle,
                    startDate: provideData.startDate,
                    endDate: provideData.endDate,
                    status: provideData.status,
                },
                exams: formattedExams,
            }),
        };
    } catch (error) {
        console.error("제공 데이터 조회 실패:", error);
        return {
            statusCode: 500,
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Credentials": true,
            },
            body: JSON.stringify({
                message: `제공 데이터 조회에 실패했습니다: ${error.message}`,
            }),
        };
    }
};
