import { S3Client, CompleteMultipartUploadCommand } from "@aws-sdk/client-s3";
import { s3Client } from "./aws-clients.js";

export const handler = async (event) => {
  try {
    const body = event.isBase64Encoded
      ? JSON.parse(Buffer.from(event.body, "base64").toString())
      : JSON.parse(event.body);

    const { uploadId, s3Key, parts } = body;

    // parts 배열의 각 파트 정보는 ETag와 PartNumber를 정확히 포함해야 합니다.
    const validParts = parts.map((part, index) => ({
      ETag: part.ETag, // S3에서 제공하는 ETag 값
      PartNumber: index + 1, // 각 파트 번호 (1부터 시작)
    }));

    const command = new CompleteMultipartUploadCommand({
      Bucket: process.env.BUCKET_NAME,
      Key: s3Key,
      UploadId: uploadId,
      MultipartUpload: {
        Parts: validParts,
      },
    });

    const response = await s3Client.send(command);

    return {
      statusCode: 200,
      headers: {
        "Access-Control-Allow-Origin":
          process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3,
        "Access-Control-Allow-Credentials": true,
      },
      body: JSON.stringify({
        message: "파일 업로드 완료",
        s3Key: response.Key,
        location: response.Location, // 업로드된 파일의 URL (S3 경로)
      }),
    };
  } catch (error) {
    console.error("Error completing multipart upload:", error);
    return {
      statusCode: 500,
      headers: {
        "Access-Control-Allow-Origin":
          process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3,
        "Access-Control-Allow-Credentials": true,
      },
      body: JSON.stringify({
        error: "Multipart 업로드 완료 중 오류 발생",
        details: error.message,
      }),
    };
  }
};
