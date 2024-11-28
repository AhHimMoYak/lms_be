const AWS = require('aws-sdk');
const dynamoDB = new AWS.DynamoDB.DocumentClient();

export const handler = async (event) => {
    try {
        const {courseProvideId, examId, username, answers} = JSON.parse(event.body);

        // Validate incoming data
        if (!courseProvideId || !examId || !username || !answers) {
            return {
                statusCode: 400,
                body: JSON.stringify({message: '필드 값이 유효하지 않습니다.'}),
            };
        }

        // Retrieve exam data from DynamoDB
        const examParams = {
            TableName: 'exam-api',
            Key: {examId: examId},
        };

        const examData = await dynamoDB.get(examParams).promise();

        if (!examData.Item) {
            return {
                statusCode: 404,
                body: JSON.stringify({message: '시험을 찾을 수 없음.'}),
            };
        }

        const quizzes = examData.Item.quizzes;

        // 시험 답안 비교
        const results = quizzes.map((quiz, index) => {
            const isCorrect = quiz.correctAnswer === answers[index];
            return {
                question: quiz.question,
                userAnswer: answers[index],
                correctAnswer: quiz.correctAnswer,
                isCorrect: isCorrect,
            };
        });

        // DynamoDB에 결과 저장
        const submissionParams = {
            TableName: 'exam-submit-api', // Replace with your DynamoDB table for submissions
            Item: {
                submissionId: `${examId}-${username}`, // Unique ID for submission
                examId: examId,
                courseProvideId: courseProvideId,
                username: username,
                answers: answers,
                status: 'COMPLETED', // Mark exam as completed
                submittedAt: new Date().toISOString(),
                results: results,
            },
        };

        await dynamoDB.put(submissionParams).promise();

        return {
            statusCode: 200,
            body: JSON.stringify({
                message: 'Exam submitted successfully.',
                results: results,
                status: 'COMPLETED',
            }),
        };
    } catch (error) {
        console.error('Error:', error);
        return {
            statusCode: 500,
            body: JSON.stringify({message: '시험 제출 실패.'}),
        };
    }
};
