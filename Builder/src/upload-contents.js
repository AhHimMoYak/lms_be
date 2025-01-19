import {
  CreateMultipartUploadCommand,
  UploadPartCommand,
  CompleteMultipartUploadCommand,
} from "@aws-sdk/client-s3";
import { getSignedUrl } from "@aws-sdk/s3-request-presigner";
import { s3Client } from "./aws-clients.js";
import { v4 as uuidv4 } from "uuid";
import path from "path";

export const handler = async (event) => {
  try {
    const body = event.isBase64Encoded
      ? JSON.parse(Buffer.from(event.body, "base64").toString())
      : JSON.parse(event.body);

    const {
      fileName,
      contentType,
      courseId,
      curriculumId,
      institutionId,
      contentTitle,
      fileSize,
      videoDuration,
    } = body;

    const missingFields = [];
    if (!fileName) missingFields.push("fileName");
    if (!contentType) missingFields.push("contentType");
    if (!courseId) missingFields.push("courseId");
    if (!curriculumId) missingFields.push("curriculumId");
    if (!institutionId) missingFields.push("institutionId");
    if (!contentTitle) missingFields.push("contentTitle");
    if (!fileSize) missingFields.push("fileSize");

    if (missingFields.length > 0) {
      return {
        statusCode: 400,
        headers: {
          "Access-Control-Allow-Origin":
            process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3,
          "Access-Control-Allow-Credentials": true,
        },
        body: JSON.stringify({
          error: "요청에서 필수 데이터가 누락되었습니다.",
          missingFields,
        }),
      };
    }

    let decodedFileName;
    try {
      decodedFileName = Buffer.from(fileName, "base64").toString("utf-8");
    } catch (error) {
      console.error("파일 이름 디코딩 오류:", error);
      throw new Error("파일 이름 디코딩 실패");
    }

    console.log("Decoded fileName:", decodedFileName);

    let fileExtension = path.extname(decodedFileName).toLowerCase().slice(1);
    let folderName;

    if (
      fileExtension === "jpeg" ||
      fileExtension === "jpg" ||
      fileExtension === "png"
    ) {
      fileExtension = "jpg";
      folderName = "MATERIAL";
    } else if (fileExtension === "mp4") {
      folderName = "VIDEO";
    } else if (fileExtension === "pdf") {
      folderName = "MATERIAL";
    } else if (fileExtension === "avi" || fileExtension === "wav") {
      return {
        statusCode: 400,
        headers: {
          "Access-Control-Allow-Origin":
            process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3,
          "Access-Control-Allow-Credentials": true,
        },
        body: JSON.stringify({
          error: "지원하지 않는 파일 형식입니다.",
        }),
      };
    } else {
      folderName = "OTHERS";
    }

    const uniqueFileName = `${uuidv4()}_${path.basename(
      decodedFileName,
      path.extname(decodedFileName)
    )}.${fileExtension}`;
    const s3Key = `${process.env.DOWNLOAD_PATH}/${courseId}/${curriculumId}/${folderName}/${uniqueFileName}`;

    // **1. 멀티파트 업로드 시작**
    const createMultipartUploadCommand = new CreateMultipartUploadCommand({
      Bucket: process.env.BUCKET_NAME,
      Key: s3Key,
      ContentType: contentType,
    });

    const createMultipartUploadResponse = await s3Client.send(
      createMultipartUploadCommand
    );
    const uploadId = createMultipartUploadResponse.UploadId;

    if (!uploadId) {
      return {
        statusCode: 500,
        headers: {
          "Access-Control-Allow-Origin":
            process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3,
          "Access-Control-Allow-Credentials": true,
        },
        body: JSON.stringify({
          error: "멀티파트 업로드 ID 생성 실패",
        }),
      };
    }

    // **2. Presigned URL 생성**
    const partSize = 5 * 1024 * 1024; // 각 청크 크기 5MB
    const totalParts = Math.ceil(fileSize / partSize);
    const presignedUrls = [];

    for (let i = 1; i <= totalParts; i++) {
      const uploadPartCommand = new UploadPartCommand({
        Bucket: process.env.BUCKET_NAME,
        Key: s3Key,
        PartNumber: i,
        UploadId: uploadId,
        ContentType: contentType,
      });

      const presignedUrl = await getSignedUrl(s3Client, uploadPartCommand, {
        expiresIn: 3600,
      });
      presignedUrls.push(presignedUrl);
    }

    return {
      statusCode: 200,
      headers: {
        "Access-Control-Allow-Origin":
          process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3,
        "Access-Control-Allow-Credentials": true,
      },
      body: JSON.stringify({
        uploadId,
        presignedUrls,
        s3Key,
        uniqueFileName,
        originalFileName: decodedFileName,
        contentTitle,
        institutionId,
        fileSize,
        videoDuration,
        s3Url: `${process.env.AWS_BUCKET_BASE_URL}/${s3Key}`,
        contentType: folderName,
      }),
    };
  } catch (error) {
    console.error("Error:", error);
    return {
      statusCode: 500,
      headers: {
        "Access-Control-Allow-Origin":
          process.env.ACCESS_CONTROL_ALLOW_ORIGIN_3,
        "Access-Control-Allow-Credentials": true,
      },
      body: JSON.stringify({
        error: "파일 업로드 URL 생성 중 오류가 발생했습니다.",
        details: error.message,
      }),
    };
  }
};
