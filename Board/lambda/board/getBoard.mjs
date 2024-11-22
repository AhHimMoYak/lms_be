import {GetCommand} from "@aws-sdk/lib-dynamodb";
import {docClient} from "../aws-clients.mjs";

export const handler = async (event) => {
    let id;

    console.log('Received event:', event);
    try {
        if (event.pathParameters) {
            ({ id } = event.pathParameters);
        }

        if (!id) {
            return {
                statusCode: 400,
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify({
                    message: '필수 매개변수가 누락되었습니다.',
                    event: event
                })
            };
        }

        const { Item } = await docClient.send(new GetCommand({
            TableName: process.env.BOARD_TABLE,
            Key: {
                id: id
            }
        }));

        if (!Item) {
            return {
                statusCode: 404,
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify({ message: '게시글을 찾을 수 없습니다.' })
            };
        }

        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(Item)
        };
    } catch (error) {
        console.error('게시글 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({
                message: '게시글 조회에 실패했습니다.',
                error: error.message,
                event: event
            })
        };
    }
};
