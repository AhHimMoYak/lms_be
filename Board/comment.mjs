import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
    DynamoDBDocumentClient,
    PutCommand,
    UpdateCommand,
    DeleteCommand,
    QueryCommand,
} from "@aws-sdk/lib-dynamodb";
import { v4 as uuidv4 } from 'uuid';

const client = new DynamoDBClient({region:"ap-northeast-2"});
const docClient = DynamoDBDocumentClient.from(client);
const TABLE_NAME = "comments";

export const createComment = async (event) => {
    try {
        const body = JSON.parse(event.body);
        const now = new Date().toISOString();

        const comment = {
            id: `comment_${uuidv4()}`,
            createdAt: now,
            updatedAt: now,
            content: body.content,
            userName: body.userName,
            boardId : body.boardId
        };

        await docClient.send(new PutCommand({
            TableName: TABLE_NAME,
            Item: comment
        }));

        return{
            statusCode : 201,
            headers:{
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(comment)
        };
    }catch (error){
        console.error('댓글 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '댓글 생성에 실패했습니다.' })
        };
    }
}

export const getComments = async (event) => {
    try {
        const {boardId} = event.pathParameters;
        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;

        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        const response = await docClient.send(new QueryCommand({
            TableName: TABLE_NAME,
            IndexName: 'BoardIdIndex',
            KeyConditionExpression: 'boardId = :boardId',
            ExpressionAttributeValues: {
                ':boardId': boardId
            },
            Limit: limit,
            ScanIndexForward: false,
            ...(lastEvaluatedKey && {ExclusiveStartKey: lastEvaluatedKey})

        }));
        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({
                items: response.Items || [],
                lastEvaluatedKey: response.LastEvaluatedKey
            })
        };
    }catch (error) {
        console.error('작성자별 게시글 조회 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '작성자별 게시글 조회에 실패했습니다.' })
        };
    }
};

export const updateComment = async (event) => {
    try{
        const {id, createdAt} = event.pathParameters;
        const { content } = JSON.parse(event.body);

        const limit = event.queryStringParameters?.limit
            ? parseInt(event.queryStringParameters.limit)
            : 10;

        const lastEvaluatedKey = event.queryStringParameters?.lastEvaluatedKey
            ? JSON.parse(event.queryStringParameters.lastEvaluatedKey)
            : null;

        const response = await docClient.send(new UpdateCommand({
            TableName: TABLE_NAME,
            Key: {id, createdAt},
            UpdateExpression: 'set content = :content, updatedAt = :updatedAt',
            ExpressionAttributeValues: {
                ':content' : content,
                ':updatedAt': new Date().toISOString()
            },
            ReturnValues: 'ALL_NEW'
        }));
        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(response.Attributes)
        };
    }catch (error) {
        console.error('댓글 수정 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '댓글 수정에 실패했습니다.' })
        };
    }
};

export const deleteComment = async (event) => {
    try {
        const { id, createdAt } = event.pathParameters;

        await docClient.send(new DeleteCommand({
            TableName: TABLE_NAME,
            Key: {
                id,
                createdAt
            }
        }));

        return {
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '댓글이 성공적으로 삭제되었습니다.'})
        };
    } catch (error) {
        console.error('댓글 삭제 실패:', error);
        return {
            statusCode: 500,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({ message: '댓글 삭제에 실패했습니다.' })
        };
    }
};