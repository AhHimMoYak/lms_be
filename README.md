# 기업대상 직무교육 LMS, CMS 플랫폼

메가존클라우드 교육과정에서 배운 클라우드네이티브 기술을 활용하여 AWS 기반으로 기업 대상 직무교육 LMS, CMS 플랫폼을 구현하는 것을 목표로 하였습니다.

<details>
  <summary>
    ${\normalsize{\color{#6580DD}클라우드\ 네이티브\ 기술의\ 이점}}$
  </summary>
  <br />
  
  클라우드 네이티브 기술을 활용하면 기업의 교육 플랫폼이 보다 효율적이고 확장 가능한 구조를 가질 수 있습니다.
  
  <br />

  >
  > <br />
  > 
  > - **애플리케이션의 효율적인 확장** <br /><br />
  > 클라우드 환경에서 애플리케이션을 필요에 맞게 자동으로 확장하거나 축소할 수 있어, 수요 변화에 빠르게 대응할 수 있습니다.
  >
  > <br />
  >

  >
  > <br />
  > 
  > - **탄력적인 자원 관리** <br /><br />
  > 클라우드 인프라를 통해 리소스를 필요에 따라 최적화하여 운영할 수 있으며, 비용 효율적인 자원 관리를 할 수 있습니다.
  >
  > <br />
  >

  >
  > <br />
  > 
  > - **고가용성 및 내구성** <br /><br />
  > 클라우드 환경에서는 여러 지역에 분산된 인프라를 활용할 수 있어, 시스템의 고가용성 및 장애 복구 능력이 향상됩니다.
  >
  > <br />
  >

  >
  > <br />
  > 
  > - **CI/CD (지속적 통합/지속적 배포)** <br /><br />
  > 클라우드 네이티브 아키텍처는 빠른 개발 및 배포 주기를 지원하므로, 교육 플랫폼의 기능 업데이트나 개선 작업을 더 신속하게 수행할 수 있습니다.
  >
  > <br />
  >

  >
  > <br />
  > 
  > - **서비스 기반 아키텍처 (Microservices)** <br /><br />
  > 애플리케이션을 독립적인 서비스로 나누어 관리하면, 각 서비스가 독립적으로 배포되고 확장될 수 있어, 플랫폼의 유지 보수와 기능 개선이 용이해집니다.
  >
  > <br />
  >

  >
  > <br />
  > 
  > - **자동화 및 DevOps** <br /><br />
  > 클라우드 네이티브 기술은 DevOps와의 통합을 통해 자동화된 배포, 모니터링, 관리 시스템을 구축할 수 있어 운영의 효율성을 높일 수 있습니다.
  >
  > <br />
  > 
</details>

## 프로젝트 개요

### 프로젝트 간략 소개

- **LMS & CMS 솔루션**:  
  기업을 대상으로 하는 학습 관리 시스템(LMS)과 콘텐츠 관리 시스템(CMS)을 통합하여 제공합니다.
  
- **비개발자 친화적 구성**:  
  비개발자도 손쉽게 학습 과정을 설계하고 관리할 수 있는 사용자 중심의 인터페이스를 제공합니다.
  
- **집중력 강화 솔루션**:  
  원격 훈련 환경에서 참여를 유도하고 학습자의 집중력을 높일 수 있는 다양한 기능과 도구를 지원합니다.

### 개발 기간
 2024.10.16 ~ 2024.12.12 (1개월 27일)

### 팀원 소개
Megazone Cloud Java 기반 SaaS개발자 양성 과정 2조

<img width="1420" alt="image" src="https://github.com/user-attachments/assets/baa167f4-6ad2-4738-8784-d09e289b2dba" />

<img width="1420" alt="image" src="https://github.com/user-attachments/assets/a4b12f67-53fd-4b63-9384-6a2e5cfef499" />

## 인프라 아키텍처
![image](https://github.com/user-attachments/assets/d9dd639b-4f01-4322-98a6-0637f63d50ba)

우리 프로젝트는 **"One Time One Set(한 번에 세팅)"** 을 목표로 하였습니다. 이를 실현하기 위하여 **IaC(Infrastructure as Code)** 를 기반으로하는 **`Serverless Framework`** 를 활용해 인프라를 효율적으로 구성하였습니다.

**`Serverless Framework`** 로 부터 작성한 **`IaC`** 는 **`AWS Cloud Formation`** 의 스택으로 단계적으로 인프라가 구성되게 됩니다.

 처음에는 **`SpringBoot`** 로 시작하였지만, **MSA** 를 실현하기 위해 **`이벤트기반 아키텍처(EDA, Event - driven Architecture)`** 를 선택하였습니다.

 <details>
  <summary>
    $\rm{\normalsize{\color{#6580DD} IaC 예시\ (Serverless Framework\ 와\ ClouodFormation)}}$
  </summary>
   <br />

   ```yaml
service: build-curriculum

provider:
  name: aws
  runtime: nodejs20.x
  stage: ${opt:stage, 'dev'}
  region: ap-northeast-2
  apiGateway:
    binaryMediaTypes:
      - "*/*"
  environment:
    REGION: ${self:provider.region}
    BUCKET_NAME: ahimmoyak-contents
    AWS_BUCKET_BASE_URL: https://${self:provider.environment.BUCKET_NAME}.s3.${self:provider.region}.amazonaws.com
    DOWNLOAD_PATH: contents
    ACCESS_CONTROL_ALLOW_ORIGIN: ${env:ACCESS_CONTROL_ALLOW_ORIGIN}
    ACCESS_CONTROL_ALLOW_ORIGIN_1: ${env:ACCESS_CONTROL_ALLOW_ORIGIN_1}
    ACCESS_CONTROL_ALLOW_ORIGIN_2: ${env:ACCESS_CONTROL_ALLOW_ORIGIN_2}
    ACCESS_CONTROL_ALLOW_ORIGIN_3: ${env:ACCESS_CONTROL_ALLOW_ORIGIN_3}

  iam:
    role:
      statements:
        - Effect: Allow
          Action:
            - s3:ListBucket
            - s3:GetObject
            - s3:PutObject
            - s3:DeleteObject
            - s3:ListMultipartUploadParts,
            - s3:AbortMultipartUpload
          Resource:
            - arn:aws:s3:::${self:provider.environment.BUCKET_NAME}
            - arn:aws:s3:::${self:provider.environment.BUCKET_NAME}/*
  tags:
    Name: ahim

functions:
  uploadFile:
    handler: src/upload-contents.handler
    events:
      - http:
          path: /v1/files/upload
          method: post
          cors:
            origin: ${self:provider.environment.ACCESS_CONTROL_ALLOW_ORIGIN_3}
            headers:
              - Content-Type
              - Authorization
              - X-Requested-With
            allowCredentials: true
          authorizer:
            type: TOKEN
            authorizerId:
              Ref: AuthorizerJWTApiGateway

  completeMultipartUploads:
    handler: src/complete-multipart-upload.handler
    events:
      - http:
          path: /v1/files/complete-multipart
          method: post
          cors:
            origin: ${self:provider.environment.ACCESS_CONTROL_ALLOW_ORIGIN_3}
            headers:
              - Content-Type
              - Authorization
              - X-Requested-With
            allowCredentials: true
          authorizer:
            type: TOKEN
            authorizerId:
              Ref: AuthorizerJWTApiGateway

resources:
  Resources:
    ContentBucket:
      Type: AWS::S3::Bucket
      Properties:
        BucketName: ${self:provider.environment.BUCKET_NAME}
        PublicAccessBlockConfiguration:
          BlockPublicAcls: false
          BlockPublicPolicy: false
          IgnorePublicAcls: false
          RestrictPublicBuckets: false
        AccessControl: Private
        CorsConfiguration:
          CorsRules:
            - AllowedOrigins:
                - "*"
              AllowedMethods:
                - GET
                - PUT
                - POST
              AllowedHeaders:
                - "*"
              ExposedHeaders:
                - ETag
              MaxAge: 3000

    ContentBucketPolicy:
      Type: AWS::S3::BucketPolicy
      Properties:
        Bucket: !Ref ContentBucket
        PolicyDocument:
          Version: "2012-10-17"
          Statement:
            - Effect: Allow
              Principal: "*"
              Action:
                - s3:ListBucket
                - s3:GetObject
                - s3:PutObject
                - s3:DeleteObject
              Resource:
                - arn:aws:s3:::${self:provider.environment.BUCKET_NAME}
                - arn:aws:s3:::${self:provider.environment.BUCKET_NAME}/*

    GatewayResponseDefault4XX:
      Type: "AWS::ApiGateway::GatewayResponse"
      Properties:
        ResponseParameters:
          gatewayresponse.header.Access-Control-Allow-Origin: "'*'"
          gatewayresponse.header.Access-Control-Allow-Headers: "'*'"
        ResponseType: DEFAULT_4XX
        RestApiId:
          Ref: "ApiGatewayRestApi"

    GatewayResponseDefault5XX:
      Type: "AWS::ApiGateway::GatewayResponse"
      Properties:
        ResponseParameters:
          gatewayresponse.header.Access-Control-Allow-Origin: "'*'"
          gatewayresponse.header.Access-Control-Allow-Headers: "'*'"
        ResponseType: DEFAULT_5XX
        RestApiId:
          Ref: "ApiGatewayRestApi"

    AuthorizerJWTApiGateway:
      Type: AWS::ApiGateway::Authorizer
      Properties:
        Name: AuthorizerJWT
        Type: TOKEN
        IdentitySource: method.request.header.Cookie
        RestApiId:
          Ref: ApiGatewayRestApi
        AuthorizerUri:
          Fn::Join:
            - ""
            - - "arn:aws:apigateway:"
              - Ref: AWS::Region
              - ":lambda:path/2015-03-31/functions/"
              - "arn:aws:lambda:ap-northeast-2:503561434552:function:lambda-authorizer-service-prod-AuthorizerJWT"
              - "/invocations"
        AuthorizerResultTtlInSeconds: 1 # TTL 설정 (초 단위)
    LambdaInvokePermissionForApiGateway:
      Type: AWS::Lambda::Permission
      Properties:
        FunctionName: "arn:aws:lambda:ap-northeast-2:503561434552:function:lambda-authorizer-service-prod-AuthorizerJWT"
        Action: lambda:InvokeFunction
        Principal: apigateway.amazonaws.com
        SourceArn:
          Fn::Join:
            - ""
            - - "arn:aws:execute-api:"
              - Ref: AWS::Region
              - ":"
              - Ref: AWS::AccountId
              - ":"
              - Ref: ApiGatewayRestApi
              - "/*"

package:
  patterns:
    - "!./**"
    - "src/**"
    - "package.json"
    - "node_modules/**"

plugins:
  - serverless-domain-manager
  - serverless-plugin-dotenv

custom:
  dotenv:
    path: env.${self:provider.stage}
  customDomain:
    domainName: api.ahimmoyak.click
    basePath: "builder"
    stage: dev
    createRoute53Record: true
    endpointType: REGIONAL
    certificateName: "arn:aws:acm:ap-northeast-2:503561434552:certificate/c56e0793-3d74-4357-9d42-bebb8e34511c"

   ```

<br />

<code>resources</code> 이전까지는 <code>Serverless Framework</code>로 **API** 를 한번에 배포 하고 있으며, 그 이후는 <code>CloudFromation</code>에서 인프라 구축을 하고 있습니다.<br />
<br /> 
<code>CloudFormation</code> 섹션부터 실행된후 <code>Serverless Framework</code> 섹션이 실행됩니다.<br />
<code>Serverless Framework</code> 섹션에서도 인프라 구축이 가능하지만, <code>CloudFormation</code>에서 좀 더 세부적인 인프라 설정이 가능합니다.<br />
<br />
위 **yaml** 을 작성하고 **AWS** 만 있다면, 누구나 시간과 장소에 상관없이 동일한 환경 구축이 손쉽게 가능합니다.

 </details>

 ## 기술 스택 & Tools

**Language**
<div align="start">
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/nodedotjs-5FA04E?style=for-the-badge&logo=nodedotjs&logoColor=white">
    <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
    <img src="https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white">
</div>
<br />
  
 **Framework**
<div align="start">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=white">
  <img src="https://img.shields.io/badge/serverless-FD5750?style=for-the-badge&logo=serverless&logoColor=white">
</div>
<br />

**Storage**
<div>
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/amazon rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">
  <img src="https://img.shields.io/badge/amazon s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">
  <img src="https://img.shields.io/badge/amazon dynamodb-4053D6?style=for-the-badge&logo=amazondynamodb&logoColor=white">
</div>
<br />

**Build Tool & CI/CD Tool**
<div align="start">
  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  <img src="https://img.shields.io/badge/Github Actions-2088FF?style=for-the-badge&logo=Github Actions&logoColor=white">
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
</div>
<br />

**AWS**
<div align="start">
  <img src="https://img.shields.io/badge/amazon ecs-FF9900?style=for-the-badge&logo=amazonecs&logoColor=white">
  <img src="https://img.shields.io/badge/amazon route53-C4FFF?style=for-the-badge&logo=amazonroute53&logoColor=white">
  <img src="https://img.shields.io/badge/amazon cloudwatch-FF4F8B?style=for-the-badge&logo=amazoncloudwatch&logoColor=white">
  <img src="https://img.shields.io/badge/amazon api gateway-FF4F8B?style=for-the-badge&logo=amazonapigateway&logoColor=white">
  <img src="https://img.shields.io/badge/amazon iam-DD344C?style=for-the-badge&logo=amazoniam&logoColor=white">
  <img src="https://img.shields.io/badge/aws lambda-FF9900?style=for-the-badge&logo=awslambda&logoColor=white">
  <img src="https://img.shields.io/badge/aws fargate-FF9900?style=for-the-badge&logo=awsfargate&logoColor=white">
  <img src="https://img.shields.io/badge/amazon cognito-DD344C?style=for-the-badge&logo=amazoncognito&logoColor=white">
  <img src="https://img.shields.io/badge/amazon ivs-43B02A?style=for-the-badge&logo=amazonivs&logoColor=white">
  <img src="https://img.shields.io/badge/amazon cloudformation-FF0069?style=for-the-badge&logo=amazoncloudformation&logoColor=white">
</div>
<br />

**Tools**
<div>
  <img src="https://img.shields.io/badge/GITHUB-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/NOTION-181717?style=for-the-badge&logo=notion&logoColor=white">
  <img src="https://img.shields.io/badge/googlesheets-34A853?style=for-the-badge&logo=googlesheets&logoColor=white">
  <img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white">
</div>
<br />

## 기술적 의사결정

| **주요 선택 기술** | **선택 이유 및 근거** |
| --- | --- |
| **Serverless (이벤트 기반 아키텍처)** | <ul> <li> **기술적 이유**: 다양한 서비스를 독립적인 기능으로 분리하기 위해 AWS API Gateway와 AWS Lambda를 활용한 이벤트 기반 아키텍처를 도입하였습니다. 이를 통해 각 서비스의 독립성을 높이고 유지보수를 용이하게 만들었습니다. </li> <li> **비용적 이점**: AWS의 완전 관리형 서비스를 활용하여 서버 부하에 대한 걱정을 줄였고, 사용량에 따라 비용을 지불하는 온디맨드 서비스 모델을 채택하여 초기 비용을 절감할 수 있었습니다. </li></ul>|
| **ECS Fargate** | <ul> <li> **기술적 이유**: 분리되지 않은 나머지 기능들은 엔터티 간 강한 연관 관계로 신중한 접근이 필요했기에, IaC(Infrastructure as Code)를 도입하여 효율적으로 관리하고자 했습니다. </li> <li> **배포 및 관리**: AWS ECS Fargate를 활용해 서버리스 환경에서 컨테이너를 효율적으로 운영하고, 자동 확장과 관리가 가능하도록 배포 환경을 구성하였습니다. </li> <li> **비용적 이점**: Fargate는 EC2 인스턴스를 직접 관리하지 않아 서버 인프라를 프로비저닝하거나 관리하는 데 드는 비용과 시간을 절감할 수 있습니다. 또한, **사용한 만큼만 비용을 지불**하는 모델을 적용하여 운영 비용을 최적화할 수 있었습니다. </li> </ul> |
| **AWS S3** | <ul> <li> **기술적 이유**: S3는 이론상 무제한에 가까운 저장 용량을 제공하므로, 서비스의 성장에 따라 유연하게 확장할 수 있습니다. </li> <li> **업로드 효율성**: 사전 서명된 URL(Presigned URL)을 활용하면 클라이언트가 서버를 거치지 않고 직접 파일을 업로드할 수 있어, 서버 부하를 줄이는 데 유리합니다. </li> <li> **비용적 이점**: S3는 서울 리전 기준으로 첫 50TB까지 1GB당 33원으로 제공되어, 비용 면에서도 매우 효율적입니다. </li> </ul> |
| **AWS IVS** | <ul> <li> **기술적 이유**: 실시간 강의와 퀴즈를 구현하기 위해 다양한 기술을 검토한 결과, WEBRTC와 RTMP 방식을 고려했습니다. </li> <li> **실시간 강의 구현**: ‘집체 교육’을 대체하는 실시간 강의를 목표로 했으며, 이를 위해 강사와 수강생 간의 ‘1:n’ 관계를 바탕으로 참여도와 집중도를 유도하고자 했습니다. </li> <li> **비용적 이점**: 실시간 스트리밍에 최적화된 AWS IVS를 활용하여, 높은 품질의 실시간 강의 환경을 제공할 수 있다고 판단했습니다. </li> </ul> |


## 서비스 핵심기능

## Endpoint & API 설계 명세

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}Endpoint}}$
  </summary>

| No. | 기능 | Endpoint | User |
| --- | --- | --- | --- |
| 1 | 일반 Cognito 사용자 로그인 | auth/v1/signin | 모든 사용자 |
| 2 | 일반 Cognito 사용자 회원가입 | auth/v1/signup | 모든 사용자 |
| 3 | OAuth (Google) 회원가입 또는 로그인 페이지 전송 | auth/v1/google?mode=signin | 모든 사용자 |
| 4 | OAuth (Google) 회원가입 또는 로그인 페이지 전송 | auth/v1/google?mode=signup | 모든 사용자 |
| 5 | OAuth 인증 코드 받기 | auth/google/callback | OAuth |
| 6 | 일반 Cognito 사용자 이메일 확인 | auth/v1/confirm | 모든 사용자 |
| 7 | 일반 Cognito 사용자 이메일 확인 코드 재전송 | auth/v1/resend | 모든 사용자 |
| 8 | 사용자 accessToken, idToken 재발급 | auth/refresh | 모든 사용자 |
| 9 | 사용자 Cognito 정보 조회 | auth/v1/info | 모든 사용자 |
| 10 | 사용자 개인정보 조회 | users/v1/user | 모든 사용자 |
| 11 | 사용자 개인정보 수정 | users/v1/user | 모든 사용자 |
| 12 | 실시간 스트리밍 채널 생성 | /v1/live | 훈련 기관 |
| 13 | 채널 정보 조회 | /v1/live/{channelId}/channel | 훈련 기관 |
| 14 | 스트리밍 정보 조회 | /v1/live/{channelId} | 훈련 기관, 사원 |
| 15 | 스트리밍 리스트 조회 | /v1/live/list/{courseProvideId} | 훈련 기관, 사원 |
| 16 | 실시간 스트리밍 채널 삭제 | /v1/live/{channelId} | 훈련 기관 |
| 17 | 콘텐츠 업로드 | /builder/v1/files/upload | 훈련 기관 |
| 18 | 업로드한 콘텐츠 매핑 | /v1/courses/{courseId}/curriculums/{curriculumId}/save | 훈련 기관 |
| 19 | 콘텐츠 상세 보기 | /api/v1/courses/curriculums/contents/{contentId} | 사원 |
| 20 | 코스와 타입에 따른 게시글 목록 조회 | /board/v1/courses/{courseId}/{type} | 훈련 기관, 사원 |
| 21 | 코스 게시글 상세 조회 | /board/v1/{boardId} | 훈련 기관, 사원 |
| 22 | 코스 게시글 작성 | /board/v1/courses | Notice - 훈련 기관, QnA - 사원 |
| 23 | 코스 게시글 수정 | /board/v1/{boardId} | Notice - 훈련 기관, QnA - 사원 |
| 24 | 코스 게시글 삭제 | /board/v1/{boardId} | Notice - 훈련 기관, QnA - 사원 |
| 25 | 내가 작성한 게시물 조회 | /board/v1/student/{userName} | 사원 |
| 26 | 댓글 작성 | /board/v1/{boardId}/comments | 훈련 기관, 사원 |
| 27 | 댓글 삭제 | /board/v1/comments/{commentId} | 훈련 기관, 사원 |
| 28 | 댓글 조회 | /board/v1/{boardId}/comments | 훈련 기관, 사원 |
| 29 | 댓글 수정 | /board/v1/comments/{commentId} | 훈련 기관, 사원 |
| 30 | 시험 리스트 조회 | /v1/{courseId}/get | 훈련 기관 |
| 31 | 시험 생성 | /v1/{courseId}/create | 훈련 기관 |
| 32 | 시험 수정 | /v1/{courseId}/{examId}/edit | 훈련 기관 |
| 33 | 시험 삭제 | /v1/{courseId}/{examId}/delete | 훈련 기관 |
| 34 | 시험 응시자 조회 | /v1/{courseId}/{examId}/check | 훈련 기관 |
| 35 | 시험 상세 조회 | /v1/{courseId}/{examId}/detail | 훈련 기관 |
| 36 | 시험 응시 | /v1/exams/{examId}/submit | 사원 |
| 37 | 자신의 훈련기관 상세 조회 | /v1/institutions/detail | 교육 기관 |
| 38 | 훈련기관 상세 조회 | /v1/institutions/{institutionId}/detail | 사원, 회사 |
| 39 | 훈련기관 상세 조회 정보 수정 | /v1/institutions/{institutionId}/detail | 교육 기관 |
| 40 | 수강신청 내역 조회(훈련기관) | /v1/institutions | 훈련 기관 |
| 41 | 수강 신청에 대한 응답(훈련기관) | /v1/institutions/{courseProvideId}/response | 훈련 기관 |
| 42 | 수강신청 상세 조회(훈련기관) | /v1/institutions/{courseProvideId}/courseProvideDetail | 훈련 기관 |
| 43 | 코스(과정) 배정 및 수강신청 응답 | /v1/institutions/{courseProvideId}/registration | 훈련 기관 |
| 44 | 회사(기업) 조회 | /v1/students/visitor/companies | 사원, 일반 유저 |
| 45 | 회사(기업) 상세 조회 | /v1/students/companies/info | 사원, 회사 |
| 46 | 내회사 삭제(사원) | /v1/students/companies/affiliations | 사원 |
| 47 | 자신의 회사(기업) 정보 수정 | /v1/companies | 회사 |
| 48 | 사용자 삭제(회사 담당자) | /v1/companies/employees | 회사 |
| 49 | 사원 목록 조회 | /v1/companies/employees | 회사 |
| 50 | 수강신청 요청 | /v1/companies/courseProvides | 회사 |
| 51 | 수강신청 내역 조회 | /v1/companies/courseProvides/list | 회사 |
| 52 | 회사 대시보드 정보 | /v1/companies/dashboard/info | 회사 |
| 53 | 수강신청 대상 사원 정보 제출 | /v1/companies/courseProvides/employees | 회사 |
| 54 | 회사(기업) 생성 | /v1/students/visitor/companies | 일반 유저 |
| 55 | 훈련기관 생성 | /v1/institutions | 일반 유저 |
| 56 | 수강한 코스 조회 | /v1/courses/courseList | 사원 |
| 57 | 회사(기업) 이메일 검사 | /v1/students/visitor/companies/emails/check | 일반 유저 |
| 58 | 회사 목록 조회 | /v1/students/visitor/companies/affiliations | 일반 유저 |
| 59 | 월별 인기 있는 코스 | /analysis/api/echart/basic-line/popularity-courses | 훈련 기관 |
| 60 | 월별 회사 및 수강생, 진행중인 강의 수 | /analysis/api/echart/basic-bar/monthly-courses | 훈련 기관 |
| 61 | 분기별 회사, 수강생 진행중인 강의 통계 | /analysis/api/echart/echart/basic-pie/quarter-courses | 훈련 기관 |
| 62 | 강의별 회사 출석율 | /analysis/api/echart/horizontal-bar/company-attendances | 훈련 기관 |
| 63 | 훈련기관과 체결된 회사 정보 | /analysis/api/aggrid/companies | 훈련 기관 |
| 64 | 운영 중인 코스 정보 | /analysis/api/aggrid/course-provides | 훈련 기관 |
| 65 | 회사별 총 출석율 | /analysis/api/aggrid/company-attendance | 훈련 기관 |
| 66 | 총 코스 정보(평가) | /analysis/api/aggrid/course-rate-data | 훈련 기관 |

</details>

<a href="https://docs.google.com/spreadsheets/d/1FbEZzaVYbarLD038AXRxwZf7xKk-NhfY8tzmMPsJt2Y/edit?gid=1512902059#gid=1512902059">API 설계 명세 링크</a>

## ERD & DynamoDB 테이블 명세
<details>
  <summary>
      $\rm{\normalsize{\color{#6580DD}MySQL\ Schema}}$
  </summary>

<br />

```MySQL
CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL,
  name VARCHAR(20) NOT NULL,
  password VARCHAR(60) NOT NULL,
  birth DATE NOT NULL,
  phone VARCHAR(20),
  email VARCHAR(100) NOT NULL,
  gender VARCHAR(20) NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'NORMAL'
);

CREATE TABLE course (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  instructor VARCHAR(255) NOT NULL,
  institution_id BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  introduction VARCHAR(255),
  image BIGINT,
  state VARCHAR(20) NOT NULL,
  FOREIGN KEY (institution_id) REFERENCES institution(id),
  FOREIGN KEY (image) REFERENCES image(id)
);

CREATE TABLE enrollment (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  course_provide_id BIGINT NOT NULL,
  state VARCHAR(20) NOT NULL,
  certificate_date DATE,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (course_provide_id) REFERENCES course_provide(id)
);

CREATE TABLE curriculum (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  course_id BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  idx INT NOT NULL,
  FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE contents (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  curriculum_id BIGINT NOT NULL,
  title VARCHAR(100) NOT NULL,
  type VARCHAR(50) NOT NULL,
  idx INT NOT NULL,
  FOREIGN KEY (curriculum_id) REFERENCES curriculum(id)
);

CREATE TABLE contents_history (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  enrollment_id BIGINT NOT NULL,
  contents_id BIGINT NOT NULL,
  state VARCHAR(20),
  progress BIGINT,
  FOREIGN KEY (enrollment_id) REFERENCES enrollment(id),
  FOREIGN KEY (contents_id) REFERENCES contents(id)
);

CREATE TABLE contents_video (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  contents_id BIGINT NOT NULL,
  path VARCHAR(255),
  origin_name VARCHAR(255),
  saved_name VARCHAR(255),
  postfix VARCHAR(20),
  time_amount BIGINT,
  FOREIGN KEY (contents_id) REFERENCES contents(id)
);

CREATE TABLE contents_material (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  contents_id BIGINT NOT NULL,
  path VARCHAR(255),
  origin_name VARCHAR(255),
  saved_name VARCHAR(255),
  postfix VARCHAR(20),
  FOREIGN KEY (contents_id) REFERENCES contents(id)
);

CREATE TABLE live_streaming (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  course_provide_id BIGINT NOT NULL,
  title VARCHAR(100),
  start_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP,
  state VARCHAR(20),
  FOREIGN KEY (course_provide_id) REFERENCES course_provide(id)
);

CREATE TABLE live_quiz (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  live_id BIGINT NOT NULL,
  question VARCHAR(255),
  answer INT NOT NULL,
  solution VARCHAR(255),
  FOREIGN KEY (live_id) REFERENCES live_streaming(id)
);

CREATE TABLE live_quiz_option (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  live_quiz_id BIGINT NOT NULL,
  text VARCHAR(255),
  idx INT NOT NULL,
  FOREIGN KEY (live_quiz_id) REFERENCES live_quiz(id)
);

CREATE TABLE attend_history (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  enrollment_id BIGINT NOT NULL,
  live_id BIGINT NOT NULL,
  rate INT,
  attendance TINYINT,
  FOREIGN KEY (enrollment_id) REFERENCES enrollment(id),
  FOREIGN KEY (live_id) REFERENCES live_streaming(id)
);

CREATE TABLE live_quiz_answer (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  attend_history_id BIGINT NOT NULL,
  live_quiz_id BIGINT NOT NULL,
  answer INT,
  FOREIGN KEY (attend_history_id) REFERENCES attend_history(id),
  FOREIGN KEY (live_quiz_id) REFERENCES live_quiz(id)
);

CREATE TABLE course_board (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  course_provide_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  content TEXT,
  type VARCHAR(20) NOT NULL,
  FOREIGN KEY (course_provide_id) REFERENCES course_provide(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE course_comment (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  content TEXT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES course_board(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Company and affiliation tables
CREATE TABLE company (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  owner_name VARCHAR(255),
  business_number VARCHAR(255) NOT NULL,
  email VARCHAR(100),
  phone VARCHAR(20) NOT NULL
);

CREATE TABLE affiliation (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  company_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  is_supervisor TINYINT NOT NULL,
  FOREIGN KEY (company_id) REFERENCES company(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE institution (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  owner_name VARCHAR(255),
  business_number VARCHAR(255) NOT NULL,
  certified_number VARCHAR(255) NOT NULL,
  email VARCHAR(255),
  phone VARCHAR(20)
);

CREATE TABLE manager (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  institution_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (institution_id) REFERENCES institution(id)
);

CREATE TABLE image (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  path VARCHAR(255) NOT NULL,
  origin_name VARCHAR(255) NOT NULL,
  saved_name VARCHAR(255) NOT NULL,
  postfix VARCHAR(20) NOT NULL
);

CREATE TABLE course_provide (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  company_id BIGINT NOT NULL,
  institution_id BIGINT NOT NULL,
  course_id BIGINT NOT NULL,
  begin_date DATE NOT NULL,
  end_date DATE NOT NULL,
  state VARCHAR(20) NOT NULL,
  attendee_count INT NOT NULL,
  deposit BIGINT,
  FOREIGN KEY (company_id) REFERENCES company(id),
  FOREIGN KEY (institution_id) REFERENCES institution(id),
  FOREIGN KEY (course_id) REFERENCES course(id)
);

-- Foreign Key Constraints (Already covered above)
ALTER TABLE course
  ADD CONSTRAINT fk_course_institution FOREIGN KEY (institution_id) REFERENCES institution(id);

ALTER TABLE course_provide
  ADD CONSTRAINT fk_course_provide_course FOREIGN KEY (course_id) REFERENCES course(id),
  ADD CONSTRAINT fk_course_provide_institution FOREIGN KEY (institution_id) REFERENCES institution(id),
  ADD CONSTRAINT fk_course_provide_company FOREIGN KEY (company_id) REFERENCES company(id);
```
    
  </details>

<a href="https://dbdiagram.io/d/ahmy-devton-6719cb9997a66db9a314d728">ERD 링크(db Diagram.io)</a>



## 피드백

1. 정부와 관련된 서비스는 수익성을 기대하기 어렵기 때문에 BEP(Break - evne Point)를 기대하기 어려움
2. 통합 LMS의 실시간 강의는 BEP를 기대하기 어렵기 때문에, 단순히 LMS를 만들어서 판매하는 것이 더 이득이 될 것
3. 직무교육 LMS는 집중력 유도 보다는 수료가 더 중요한 것, 라이브 스트리밍이 중요하지 않음
4. 하지만, 현실적인 문제에 대하여 솔루션을 내놓고 다른 시각으로 접근하여 도전해본것이 매우 인상적임
5. 완전한 LMS보다는 일부의 특정 문제점에 대하여 선택과 집중을 하여 구현해보는 것도 좋은 방법
6. 이벤트 기반의 서버리스 아키텍처와 ECS를 같이 운영 배포하는 것은 잘못 되었음. ECS의 경우 매우 많이 복잡하게 얽혀 있는 경우 어쩔수 없이 한 번에 올려 운영하는 경우는 있지만, 완전한 서버리스로 운영하는 것이 더 좋음
7. GitHub Actions에서 ECR에 제대로 업로드 되었는지 단계를 추가하지 않은 것이 아쉬움
