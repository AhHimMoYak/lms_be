# 📍 기업대상 직무교육 LMS, CMS 플랫폼 Backend

### 🎯 프로젝트 목표

> - **`SpringBoot`** 애플리케이션을 **`ECS`** 로 배포하고, 기능을 하나씩 **`serverless`** 환경으로 분리하여 **`이벤트 기반의 MSA`** 실현
> - MSA의 코드, 인프라 관리가 어려운 단점을 보완하기 위해 **“One Time One Set(한번에 세팅)”** 을 위한 **`IaC(Infrastructure as Code)`** 작성
> - 전문 개발인력을 두기 어려운 ‘훈련기관’을 고려하여 ‘비개발’인력도 손쉽게 교육 과정을 구성
> - ‘집체 교육’을 대신하는 수강생들의 집중력 향상을 위한, 실시간 강의와 실시간 퀴즈 도입
> (타사 서비스와의 차별점)

<br />

> 🔗 [Frontend repository 링크](https://github.com/AhHimMoYak/lms_fe)

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}2024년\ 부산디지털혁신아카데미\ 해커톤(Dev-ton)대회\ 아이디어상\ 수상}}$
  </summary>

<br />

![image](https://github.com/user-attachments/assets/5811df79-bd72-42e1-823d-e197ce8fa51b)

<br />

</details>

<br />

## 🚩 프로젝트 개요

### 📌 프로젝트 간략 소개

- **LMS & CMS 솔루션**:  
  기업을 대상으로 하는 학습 관리 시스템(LMS)과 콘텐츠 관리 시스템(CMS)을 통합하여 제공합니다.
  
- **비개발자 친화적 구성**:  
  비개발자도 손쉽게 학습 과정을 설계하고 관리할 수 있는 사용자 중심의 인터페이스를 제공합니다.
  
- **집중력 강화 솔루션**:  
  원격 훈련 환경에서 참여를 유도하고 학습자의 집중력을 높일 수 있는 다양한 기능과 도구를 지원합니다.

<br />

### 🎬 프로젝트 시연 영상

> 🔗 [기업대상 직무교육 LMS, CMS 플랫폼 시연영상 링크](https://youtu.be/5h6VI5sSYKE)

<br />

### 🗓️ 개발 기간
 2024.10.16 ~ 2024.12.12 (1개월 27일)

<br />

### 👪 팀원 소개

<br />

**Megazone Cloud Java 기반 SaaS개발자 양성 과정 2조**

| **프로필** | **직책**| **주역할** |
| --- |:---: | --- |
| <img width="150" alt="현규" src="https://github.com/user-attachments/assets/dfb727b4-80b4-4b7f-9db3-1d24ae7c9143" /> | 팀장 | <ul> <li> 프로젝트 기획, 설계 </li> <li> 콘텐츠 업로드 서비스 (멀티파트 업로드, Serverless) </li> <li> AWS ECS 배포 </li> </ul> |
| <img width="150" alt="미루" src="https://github.com/user-attachments/assets/833e8490-825d-464d-ba8d-64b9bb479714" /> | 부팀장 | <ul> <li> 미디어 서비스 (AWS IVS, Serverless) </li> <li> View UI 설계 </li> <li> RDBMS 관리 </li> </ul> |
| <img width="150" alt="소정" src="https://github.com/user-attachments/assets/6576da79-2fb0-461d-916b-d45fd6f21131" /> | 부팀장 | <ul> <li> 인증인가 서비스 (AWS Cognito) </li> <li> 사용자 관리 서비스 (AWS Congito, Serverless) </li><li> View UI 설계 </li> </ul> |
| <img width="150" alt="지영" src="https://github.com/user-attachments/assets/444b6103-39fe-43bb-94bb-6b3af8363502" /> | 팀원 | <ul> <li> 데이터 시각화 (Ag Grid, E-Chart, Serverless) </li> <li> 게시판 서비스 (Serverless) </li> </ul> |
| <img width="150" alt="형진" src="https://github.com/user-attachments/assets/6d69e980-6e1d-464f-85aa-7c5e9871ae39" /> | 팀원 | <ul> <li> 훈련기관 서비스 (Spring boot) </li> <li> CI/CD (Github Actions, AWS ECR) </li> </ul> |
| <img width="150" alt="도원" src="https://github.com/user-attachments/assets/6c323c2e-2896-45b1-9a79-c66196624767" /> | 팀원 | <ul> <li> 회사 서비스 (Spring boot) </li> <li> AWS ECS 배포 </li> </ul> |
| <img width="150" alt="동원" src="https://github.com/user-attachments/assets/07d42960-20f2-481d-bd31-1906ab36abe3" /> | 팀원 | <ul> <li> 콘텐츠 업로드 서비스 (멀티파트 업로드, Serverless) </li> </ul> |

<br />

## 🛠️ 인프라 아키텍처

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}인프라\ 아키텍처}}$
  </summary>
  
  ![image](https://github.com/user-attachments/assets/d9dd639b-4f01-4322-98a6-0637f63d50ba)

우리 프로젝트는 **"One Time One Set(한 번에 세팅)"** 을 목표로 하였습니다. 이를 실현하기 위하여 **IaC(Infrastructure as Code)** 를 기반으로하는 **`Serverless Framework`** 를 활용해 인프라를 효율적으로 구성하였습니다.

**`Serverless Framework`** 로 부터 작성한 **`IaC`** 는 **`AWS Cloud Formation`** 의 스택으로 단계적으로 인프라가 구성되게 됩니다.

 처음에는 **`SpringBoot`** 로 시작하였지만, **MSA** 를 실현하기 위해 **`이벤트기반 아키텍처(EDA, Event - driven Architecture)`** 를 선택하였습니다.
 
 <br />
 
</details>


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

 <br />

## 💡 서비스 아키텍처

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}서비스\ 아키텍처}}$
  </summary>

![image](https://github.com/user-attachments/assets/04beb06c-81e1-49ae-bc8d-5ddce4b22a88)

**`사용자 관리 서비스`** , **`게시판 서비스`** , **`데이터 시각화`** , **`미디어 서비스`** , **`콘텐츠 업로드`** 등은 **Serverless 환경**

으로 분리하였습니다.

다만, **`훈련기관`** , **`회사`** , **`사원(훈련생)서비스`** 와 같이 엔터티 간 강한 연관관계로 인해 **즉시 분리하기 어려운 기능** 은

**`ECS Fargate`** 를 활용해 배포하였으며, 이를 통해 **One Time One Set** 목표를 실현할 수 있었습니다.

</details>

<br />

 ## ⚙️ 기술 스택 & Tools

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

## 🤝 기술적 의사결정

| **주요 선택 기술** | **선택 이유 및 근거** |
| --- | --- |
| **Serverless </br> (이벤트 기반 아키텍처)** | <ul> <li> **기술적 이유** <br /> 다양한 서비스를 독립적인 기능으로 분리하기 위해 AWS API Gateway와 AWS Lambda를 활용한 이벤트 기반 아키텍처를 도입하였습니다. 이를 통해 각 서비스의 독립성을 높이고 유지보수를 용이하게 만들었습니다. </li> <li> **비용적 이점** <br /> AWS의 완전 관리형 서비스를 활용하여 서버 부하에 대한 걱정을 줄였고, 사용량에 따라 비용을 지불하는 온디맨드 서비스 모델을 채택하여 초기 비용을 절감할 수 있었습니다. </li></ul>|
| **ECS Fargate** | <ul> <li> **기술적 이유** <br /> 분리되지 않은 나머지 기능들은 엔터티 간 강한 연관 관계로 신중한 접근이 필요했기에, IaC(Infrastructure as Code)를 도입하여 효율적으로 관리하고자 했습니다. </li> <li> **배포 및 관리** <br /> AWS ECS Fargate를 활용해 서버리스 환경에서 컨테이너를 효율적으로 운영하고, 자동 확장과 관리가 가능하도록 배포 환경을 구성하였습니다. </li> <li> **비용적 이점** <br /> Fargate는 EC2 인스턴스를 직접 관리하지 않아 서버 인프라를 프로비저닝하거나 관리하는 데 드는 비용과 시간을 절감할 수 있습니다. 또한, **사용한 만큼만 비용을 지불**하는 모델을 적용하여 운영 비용을 최적화할 수 있었습니다. </li> </ul> |
| **AWS S3** | <ul> <li> **기술적 이유** <br /> S3는 이론상 무제한에 가까운 저장 용량을 제공하므로, 서비스의 성장에 따라 유연하게 확장할 수 있습니다. </li> <li> **업로드 효율성** <br /> 사전 서명된 URL(Presigned URL)을 활용하면 클라이언트가 서버를 거치지 않고 직접 파일을 업로드할 수 있어, 서버 부하를 줄이는 데 유리합니다. </li> <li> **비용적 이점** <br /> S3는 서울 리전 기준으로 첫 50TB까지 1GB당 33원으로 제공되어, 비용 면에서도 매우 효율적입니다. </li> </ul> |
| **AWS IVS** | <ul> <li> **기술적 이유** <br /> 실시간 강의와 퀴즈를 구현하기 위해 다양한 기술을 검토한 결과, WEBRTC와 RTMP 방식을 고려했습니다. </li> <li> **실시간 강의 구현** <br /> ‘집체 교육’을 대체하는 실시간 강의를 목표로 했으며, 이를 위해 강사와 수강생 간의 ‘1:n’ 관계를 바탕으로 참여도와 집중도를 유도하고자 했습니다. </li> <li> **비용적 이점** <br /> 실시간 스트리밍에 최적화된 AWS IVS를 활용하여, 높은 품질의 실시간 강의 환경을 제공할 수 있다고 판단했습니다. </li> </ul> |
| **AWS COGNITO** |  <ul> <li> **기술적 이유** <br /> 사용자 인증 및 권한 관리 기능을 효율적으로 제공하기 위해 AWS Cognito를 선택하였습니다. 이 서비스를 활용하여 로그인, 회원가입, 비밀번호 복구 등 인증 관련 기능을 구현하였으며, OAuth 2.0, SAML, OpenID Connect와 같은 표준 프로토콜을 지원하여 다양한 인증 요구 사항을 충족할 수 있었습니다. </li> <li> **확장성** <br /> AWS Cognito는 수백만 사용자를 지원할 수 있는 확장성을 제공하므로, 사용자가 증가하더라도 안정적인 서비스를 유지할 수 있었습니다. </li> <li> **보안 강화** <br /> 멀티팩터 인증(MFA)과 Amazon Web Services WAF(Web Application Firewall) 통합을 통해 보안 수준을 강화하여 사용자 데이터를 안전하게 보호할 수 있었습니다. </li> <li> **비용적 이점** <br /> 인증 요청 수에 따라 비용이 산정되는 모델을 제공하여 초기 운영 비용을 낮출 수 있었고, 서비스 성장에 따른 비용 증가를 효율적으로 관리할 수 있었습니다. </li> </ul> |

<br />

## 📢 서비스 핵심기능

<details>
<summary>
  $\rm{\normalsize{\color{#6580DD}사용자 관리 서비스}}$
</summary>

### 기능

- **Lambda + API Gateway + DynamoDB, S3**
    - 회사 매니저가 사원 관리 서비스
    - 전체 회원 관리 서비스
- **Cognito**
    - 모든 사용자의 회원가입 및 로그인 인증 서버 구현
    - 사용자 그룹 별로 접근 권한 지정
    - Ouath 2.0 을 이용한 사용자 인증 지원 구현

### 서비스 아키텍처

- **일반 회원가입**
    ![회원가입](https://github.com/user-attachments/assets/d81c55b1-1a07-47ad-aeb7-058793877f9d)
    
- **google Ouath + Cognito 회원가입**

    ![Ouath2 0 ](https://github.com/user-attachments/assets/f5403318-5385-4409-bbc1-61bfa988ae46)  

    - **google Ouath 발급 방식**

    <br><br><br> <!-- 여기에 공백 추가 -->

    ![Ouath2 0_Cognito](https://github.com/user-attachments/assets/4fef8b5d-f608-46cf-9daf-783a921aabf6)

     - **Ouath를 적용한 Cognito 토큰 발급 방식**

    <br><br><br> <!-- 여기에 공백 추가 -->

    ![CUSTOM_AUTH ](https://github.com/user-attachments/assets/6295876a-30f8-4366-87e4-e5ad6f6e8d0a)

     - **Custom Auth의 토큰 발급 흐름**

<br><br>

### 고민한 점

1. **Ouath2.0 사용자의 토큰 관리 방식**
    - 이유 : Ouath2.0을 사용해서 인증 서버로 로그인 시 클라이언트 쪽에서 로그인을 하는데 그 결과 인증 토큰을 클라이언트가 가지고 있게 되고, 그것을 다시 백엔드 서버로 전송을 해야 합니다 이때 클라이언트 측에서 토큰을 관리하기 때문에 탈취의 위험성이 있고, 또한 백엔드 서버로 전송 시에도 탈취의 위험성이 있다는 것을 알게 되었습니다
    
    - 해결법 : 기본적으로 Ouath2.0에서 redirect url의 설정을 클라이언트 측 프론트 서버가 아니라 백엔드 서버로 설정하였습니다. 이렇게 수정하여서 클라이언트가 해야 하는  grant type(authorization code) 를 수행을 백엔드 서버가 하도록 하였습니다
        
        이렇게 수정하여서 인증 서버가 Ouath2.0 인증 토큰들을 백엔드 서버에 전송하도록 수정하여 클라이언트 측에서 토큰을 관리하지 못하도록 하였습니다
        

1. **Ouath2.0 통합 인증 방식**
    - 이유 : Cognito에서 기본적으로 제공하는 Ouath2.0 방식들은 google, facebook만 소셜 로그인으로 제공하고 네이버, 카카오 같은 것은 따로 Ouath2.0 방식으로 구현해야 되었습니다
        
        그로 인해 따로따로 회원가입 및 로그인을 관리해야 하는 불편함이 발생하였습니다
        
    
    - 해결법 : 어떤 Ouath2.0 방식이 오더라도 다 통합적으로 회원가입과 로그인을 할 수 있도록 custom auth flow를 사용하여서 Ouath2.0의 토큰에 저장되어 있는 sub(id)을 회원가입 시 저장하게 하고, 그것을 로그인 시에 사용자가 제공하는 토큰의 sub(id)과 저장되어 있는 sub(id) 값을 비교하여 같으면 로그인이 되도록 하였습니다

1. **다중 하위 도메인간 토큰 공유**
    - 문제점 : 현재 서비스에서는 1개의 상위 도메인과 3개의 하위 도메인이 존재하고 있습니다. 그러나 각 도메인별로 서비스를 사용할 때 사용자의 토큰이 요구되고 있지만 로그인을 하는 페이지는 상위 페이지에만 존재하여 일반 response body로 전달 시 공유가 되지 않는다는 문제점이 발생하였습니다
    
    - 해결법 : 다중 도메인간 토큰들을 공유하기 위해 쿠키로 전송을 하는 방식을 선택하였습니다.쿠키 옵션으로 상위 도메인 값을 넣어서 하위 도메인에서도 공유를 할 수 있도록 하였고, 토큰들 중 refreshToken의 경우 사용자에게 브라우저에서 보이지 않고 전송할 수 있도록 path 설정을 따로 하여 지정된 path에서만 쿠키가 전송될 수 있도록 설정하였습니다

<br />

</details>

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}CI/CD와 AWS ECR 설정}}$
  </summary>

<br />

본 프로젝트는 교육기관, 회사, 그리고 사용자를 대상으로 하는 서비스 플랫폼 개발입니다.
세 개의 독립적인 서비스를 Spring Boot와 Java를 사용해 개발했으며, CICD 파이프라인을 통해 코드의 확장성과 효율성을 극대화했습니다.

### CICD 플랫폼 중 GithubActions 선택 이유

![image](https://github.com/user-attachments/assets/a902a957-653d-48d7-a3ac-71b6834d1cd2)

> <ol>
>   <li> 프로젝트 개발 협력 툴인 Github와 완벽한 통합 </li>
>   <li> GithubActions는 서버리스 기반으로 동작하기 때문에 서버 리소스와 유지 보수가 필요하지 않다는 점.</li>
>   <li> 초보자도 쉽게 사용이 가능하고 비교적 간단한 설명 </li>
>   <li> Github와 종속성 </li>
> </ol>

<br />

### CICD 파이프라인 단계

![image](https://github.com/user-attachments/assets/2844742c-1529-4518-bfa8-a21d0b20aed6)

> 
> 1. **코드 체크아웃 (Checkout)**
>    - GitHub Actions에서 `actions/checkout`을 사용하여 최신 코드를 가져옵니다.
> 2. **환경설정 (Environment Setup)**
>     - Java 17과 Maven 등의 빌드 도구를 설정합니다.
>     - 프로젝트별 의존성 설치.
> 3. **테스트 및 빌드 (Test & Build)**
>     - `mvn test`를 통해 유닛 테스트를 실행하여 코드 품질 확인.
>     - 테스트 통과 후 `mvn package`로 JAR 파일 생성.s
> 4. **도커 이미지 생성 및 태깅 (Docker Build & Tag)**
>     - 서비스별로 Dockerfile을 작성하여 이미지를 빌드.
>     - GitHub Actions Workflow에서 최신 커밋 SHA 또는 태그를 기준으로 Docker 이미지를 태깅.
> 5. **AWS ECR로 이미지 푸쉬 (Push to AWS ECR)**
>     - AWS CLI를 통해 ECR에 로그인.
>     - 태깅된 Docker 이미지를 ECR 리포지토리에 푸쉬.

<br />

### AWS ECR 선택 이유

![image](https://github.com/user-attachments/assets/655a9874-0727-4ca9-9f9d-78048e931a63)


> 1. **AWS 생태계와의 높은 호환성**
>     - AWS의 다른 서비스 (ECS, EKS, Lambda 등)와의 통합이 간편.
> 2. **보안**
>     - 이미지 스캔과 IAM 권한 관리를 통해 보안 강화.
> 3. 자동화 지원
>     - AWS CLI, SDK, Github Actions와 자연스러운 연동
> 4. **편리한 배포**
>     - ECR에 저장된 이미지를 기반으로 ECS에서 쉽게 확장 가능.
> 5. **비용 효율성**
>     - 사용한 저장 공간과 데이터 전송량에 비례한 과금으로 비용 관리 가능.

> **비교: Docker Hub**
> 
> - Docker Hub는 무료 플랜에서의 속도 제한과 제한된 보안 옵션으로 인해 AWS ECR 대비 불리.
> - ECR은 AWS 계정 내에서 통합 인증을 제공, Docker Hub보다 높은 보안성.

> **비교: Google Container Registry (GCR)**
> 
> - GCR 역시 클라우드 네이티브 통합을 제공하나, 프로젝트에서 AWS를 기반으로 한 서비스이므로 AWS ECR이 더 적합.

<br />

## **추가할 점 및 개선 아이디어**

> 1. **다른 서비스로의 확장 가능성**
>     - 현재는 ECR에 이미지를 푸쉬하지만, 향후 멀티 클라우드 환경에서 GCR 또는 Azure Container Registry와도 통합할 수 있도록 설계 가능.
> 2. **테스트 단계 고도화**
>     - 정적 분석 도구 (e.g., SonarQube)를 추가해 코드 품질과 보안 취약점 검토 강화.
> 3. **자동화 최적화**
>     - `Matrix Strategy`를 사용해 병렬로 여러 서비스의 빌드를 처리.
> 4. **모니터링**
>     - ECR 푸쉬 후 배포된 이미지의 상태를 실시간으로 모니터링하는 기능 추가.
> 5. **비용 최적화**
>     - 미사용 이미지 정리 및 ECR 이미지 정책 관리.

### Github Actions
<details>
  <summary>
        $\rm{\normalsize{\color{#6580DD}docker\ 이미지\ 빌드}}$
  </summary>
  
  <br />
  
```
### multi stage build ###

### stage 1 : builder stage ###

# 베이스 이미지
FROM openjdk:21-jdk-slim AS builder

# 필수 패키지 설치 (xargs 포함)
RUN apt-get update && apt-get install -y dos2unix findutils

# 프로젝트 파일 복사
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# application.yml 파일 복사
COPY src/main/resources/application.yml /app/application.yml

# gradlew 실행 권한 부여 및 줄바꿈 변환
RUN chmod +x ./gradlew
RUN dos2unix ./gradlew

# Gradle 캐시를 활용하기 위해 dependencies를 먼저 빌드
RUN ./gradlew dependencies || true

# 실행 가능한 jar 파일로 패키징
RUN ./gradlew bootJar --no-daemon
### stage 2 : executable stage ###

# 두 번째 FROM 에 해당하는 실행 단계
FROM openjdk:21-jdk-slim

# 컨테이너의 홈 경로 지정
WORKDIR /app

# stage 1 에서 빌드된 패키지를 컨테이너의 홈 경로로 복사
COPY --from=builder /app/build/libs/*.jar /app/app.jar


# 사용 가능한 port no 지정
EXPOSE 8080

# spring boot 기반의 application 을 컨테이너가 실행이 될 때 실행이 되도록 설정
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]

```
  
</details>

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}institution-deploy}}$
  </summary>

<br />

```
name: institution-service deploy

on:
  pull_request:
    branches:
      - microservice
    types:
      - closed
    paths:
      - 'institution-service/**'

jobs:
  deploy:
    if: ${{github.event.pull_request.merged}}
    runs-on: ubuntu-latest
    steps:
      - name: Github Repository 파일 불러오기
        uses: actions/checkout@v4

      - name: JDK21 설치
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 21

      - name: Create necessary directories
        run: mkdir -p ./institution-service/src/main/resources

      - name: Base64로 인코딩된 application.yml 디코딩 및 생성
        run: |
          echo "${{ secrets.INSTITUTION_APPLICATION_PROPERTIES }}" | base64 -d > ./institution-service/src/main/resources/application.yml

      - name: 테스트 및 빌드하기
        working-directory: ./institution-service
        run: |
          chmod +x ./gradlew
          ./gradlew clean build

      - name: AWS Resource 에 접근할 수 있게 AWS credentials 설정
        uses: aws-actions/configure-aws-credentials@v4
        with:
          aws-region: ap-northeast-2
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}

      - name: ECR에 로그인 하기.
        id: login-ecr
        uses: aws-actions/amazon-ecr-login@v2

      - name: ECR에 로그인 한 결과 확인
        run: echo ${{ steps.login-ecr.outputs.registry }}

      - name: Docker 이미지 생성
        working-directory: ./institution-service
        run: docker build -t institution-cicd .

      - name: Dokcer 이미지 생성 확인
        run: |
          ls
          pwd

      - name: Docker 이미지에 Tag 붙이기
        run: docker tag institution-cicd ${{ steps.login-ecr.outputs.registry }}/institution-service:latest

      - name: ECR에 docker image push 하기
        run: docker push ${{ steps.login-ecr.outputs.registry }}/institution-service:latest

```

<br />
  
</details>

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}student-deploy}}$
  </summary>

<br />

```
name: student-service deploy

on:
  pull_request:
    branches:
      - microservice
    types:
      - closed
    paths:
      - 'student-service/**'

jobs:
  deploy:
    if: ${{github.event.pull_request.merged}}
    runs-on: ubuntu-latest
    steps:
      - name: Github Repository 파일 불러오기
        uses: actions/checkout@v4

      - name: JDK21 설치
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 21

      - name: Create necessary directories
        run: mkdir -p ./student-service/src/main/resources

      - name: Base64로 인코딩된 application.yml 디코딩 및 생성
        run: |
          echo "${{ secrets.STUDENT_APPLICATION_PROPERTIES }}" | base64 -d > ./student-service/src/main/resources/application.yml

      - name: 테스트 및 빌드하기
        working-directory: ./student-service
        run: |
          chmod +x ./gradlew
          ./gradlew clean build

      - name: AWS Resource 에 접근할 수 있게 AWS credentials 설정
        uses: aws-actions/configure-aws-credentials@v4
        with:
          aws-region: ap-northeast-2
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}

      - name: ECR에 로그인 하기.
        id: login-ecr
        uses: aws-actions/amazon-ecr-login@v2

      - name: ECR에 로그인 한 결과 확인
        run: echo ${{ steps.login-ecr.outputs.registry }}

      - name: Docker 이미지 생성
        working-directory: ./student-service
        run: docker build -t student-cicd .

      - name: Dokcer 이미지 생성 확인
        run: |
          ls
          pwd

      - name: Docker 이미지에 Tag 붙이기
        run: docker tag student-cicd ${{ steps.login-ecr.outputs.registry }}/student-service:latest

      - name: ECR에 docker image push 하기
        run: docker push ${{ steps.login-ecr.outputs.registry }}/student-service:latest

```

<br />
  
</details>

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}company-deploy}}$
  </summary>

<br />

```

name: company-service deploy

on:
  pull_request:
    branches:
      - microservice
    types:
      - closed
    paths:
      - 'company-service/**'

jobs:
  deploy:
    if: ${{github.event.pull_request.merged}}
    runs-on: ubuntu-latest
    steps:
      - name: Github Repository 파일 불러오기
        uses: actions/checkout@v4

      - name: JDK21 설치$
        uses: actions/setup-java@v4
        with:c
          distribution: temurin
          java-version: 21

      - name: Create necessary directories$
        run: mkdir -p ./company-service/src/main/resources

      - name: Base64로 인코딩된 application.yml 디코딩 및 생성
        run: |
          echo "${{ secrets.COMPANY_APPLICATION_PROPERTIES }}" | base64 -d > ./company-service/src/main/resources/application.yml

      - name: 테스트 및 빌드하기
        working-directory: ./company-service
        run: |
          chmod +x ./gradlew
          ./gradlew clean build

      - name: AWS Resource 에 접근할 수 있게 AWS credentials 설정
        uses: aws-actions/configure-aws-credentials@v4
        with:
          aws-region: ap-northeast-2
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}

      - name: ECR에 로그인 하기.
        id: login-ecr
        uses: aws-actions/amazon-ecr-login@v2

      - name: ECR에 로그인 한 결과 확인
        run: echo ${{ steps.login-ecr.outputs.registry }}

      - name: Docker 이미지 생성
        working-directory: ./company-service
        run: docker build -t company-cicd .

      - name: Dokcer 이미지 생성 확인
        run: |
          ls
          pwd

      - name: Docker 이미지에 Tag 붙이기
        run: docker tag company-cicd ${{ steps.login-ecr.outputs.registry }}/company-service:latest

      - name: ECR에 docker image push 하기
        run: docker push ${{ steps.login-ecr.outputs.registry }}/company-service:latest

```

<br />
  
</details>

### **추가 문서 필요 사항**

- **AWS IAM Role 및 권한 설정 가이드**
- **Troubleshooting 섹션**: ECR 푸쉬 실패 또는 GitHub Actions 에러 처리 방법.

### **향후 계획**

- 성능 모니터링 툴 (e.g., CloudWatch)을 활용한 실시간 상태 추적.


</details>

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}콘텐츠 서비스(멀티파트 업로드)}}$
  </summary>

<br />

### 서비스 아키텍처

![image](https://github.com/user-attachments/assets/6d31e719-a7dd-4390-8581-3f5c6a60360b)

![image](https://github.com/user-attachments/assets/ccc0721f-248a-48d9-a2e6-edc919e10029)

> 
> 1. **`filesize`** 와 함께 **`/files/upload`** 에 **`multipartupload`** 요청을 합니다.
> 2. **`Lambda`** 에서 **`filesize`** 를 연산하여 **`presignedURL리스트`** 를 요청 후 반환 합니다.
> 3. **`Client`** 에서 반환받은 **`presignedURL리스트`** 에 파일을 쪼개어 **각각의 URL로 업로드 요청** 을 합니다.
> 4. 업로드가 완료되면 각각의 **`presignedURL`** 의 **`header`** 에 **`etag`** 와 함께 응답합니다.
> 5. **`Client`** 에서 **`etag`** 를 순서대로 배열의 형태로 수집하고 검증을 위해 **`/files/complete-multipart`** 를 호출합니다.
> 6. 순서대로 수집된 **`etag`**를 **`S3`**에 **검증 요청**을 합니다.
> 7. **“멀티파트업로드가 완료되었음”** 을 알리는 완료 응답을 받고 **`Client`** 에게 전달합니다.
> 

<br />

### 실행 결과

<br />

컨텐츠를 업로드를 진행하면 각각의 chunkfile(쪼개진 파일)마다 완료응답을 받고 있습니다.

![image](https://github.com/user-attachments/assets/061cab77-2e9d-4642-99a8-baaf0e0f91f7)

<br />

로그를 찍어보면 S3에서 업로드가 완료될때 각각의 고유한 etag를 헤더에 담아 응답하는 것을 확인할 수 있습니다. 이것을 수집하여 검증 요청을 보낼수 있게 됩니다.

![image](https://github.com/user-attachments/assets/cc238e55-1fb9-4820-b3cd-a3059dd0c4bc)


</details>

<details>

<summary>
  $\rm{\normalsize{\color{#6580DD}실시간 스트리밍 강의 서비스}}$
</summary>

### 실시간 스트리밍 강의 서비스 도입 배경 ###
본 프로젝트인 기업대상 직무교육 플랫폼 서비스에선 기본적으로 학습관리 시스템 서비스를 제공하지만 학습자료, 녹화된 강의영상, 시험 및 퀴즈의 형태로만 제공되는 교육방식에선 학생들의 집중도와 참여도를 높이기 어렵다는 한계가 있었고, 이러한 한계를 보완하기 위해서 LMS 서비스와 통합되어 제공될 수 있는 실시간 스트리밍 강의 서비스를 도입하게 되었습니다.

### 기능 ###
- 실시간 영상 스트리밍 (IVS Low-Latency Streaming)
- 실시간 채팅 (IVS Chat)
- 실시간 퀴즈 (TimedMetadata + Lambda)

### 서비스 아키텍처 ###
>![실시간 스트리밍 서비스 아키텍처 이미지](https://github.com/user-attachments/assets/8560c071-7677-4dc5-b378-b5b8324fd0d9)


### 서비스 구성 ###

#### 1. IVS Low-Latency Streaming ####
AWS 완전관리형 서비스로서 실시간 스트리밍과 관련된 모든 동작을 담당합니다. <br/>
실시간 스트리밍은 Channel 이라는 리소스로 구성 및 관리되며 각 Channel 은 다음과 같은 요소를 제공합니다.
- IngestEndpoint: 스트리머가 방송을 송출하는 URL이며 RTMP프롵토콜을 사용합니다.
- StreamKey: 방송 송출 시 StreamKey 소유자만 송출을 허용해줍니다.
- PlaybackUrl: 방송을 시청할 수 있는 URL 입니다. HLS 형식으로 영상을 스트리밍 합니다.

추가로 IVS Low-Latency Streaming 에선 TimedMetadata 라는 기능을 제공합니다. <br/>
이를 통해 실시간 스트리밍 중 해당 시간 정보가 포함된 텍스트나 JSON 등의 데이터를 추가로 전송할 수 있으며, 실시간 스트리밍 강의 중 실시간 퀴즈를 전송하고 응답하는데 이용할 수 있습니다.

#### 2. IVS Chat ####
AWS 완전관리형 서비스로서 실시간 채팅에 대한 기능을 제공합니다. <br/>
채팅은 Room 이라는 리소스로 구성 및 관리됩니다. <br/>
Room 에서 제공되는 Messaging Endpoint 에 WebSocket 통신을 통하여 채팅을 전송, 수신 받을 수 있습니다. <br/>

#### 3. 실시간 강의 API ####
IVS Low-Latency Streaming의 경우 Channel 리소스 하나당 하나의 방송만 송출할 수 있기 때문에 Channel 리소스를 동적으로 관리할 방법이 필요합니다. <br/>
마찬가지의 이유로 IVS Chat 의 Room 리소스도 동적으로 관리하여야 합니다. <br/>
또한 Channel에 포함된 정보 뿐만아니라 실시간 강의에 필요한 데이터(제목, 강사명, 코스명 등)을 실시간 강의라는 하나의 단위로 관리할 필요가 있습니다.

실시간 강의 API 의 대표적인 동작은 다음과 같습니다.
- 실시간 강의 생성
    - 강의자가 실시간 강의를 생성합니다.
    - 실시간 강의 생성시 Lambda 에서 AWS SDK 를 통해 IVS의 Channel 리소스와 Room 리소스가 자동으로 생성됩니다.
    - 생성된 리소스의 정보(ARN, Name, StreamKey, IngestEndpoint, PlaybackUrl 등)을 실시간 강의의 정보와 함께 DynamoDB 에 저장합니다.
- 실시간 강의 목록 조회
    - 실시간 강의의 목록을 조회합니다.
    - DynamoDB 에 저장된 실시간 강의 정보 중 검색하고자 하는 코스제공에 해당하는 모든 실시간 강의정보를 불러옵니다.
- 실시간 강의 채널 조회
    - 실시간 강의 정보를 자세하게 조회합니다.
    - 실시간 강의 id 에 맞는 실시간 강의 정보를 DynamoDB 에서 불러옵니다.
    - 강의자의 경우 IVS Channel 에 방송을 송출 할 수 있도록  StreamKey 와 IngestUrl 정보가 포함되며, 시청자의 경우 방송을 시청할 수 있도록 PlaybackUrl 이 각각 제공됩니다.

### 서비스 구성 및 구현 과정 ###
AWS 의 IVS 를 사용하기 전 자체적으로 실시간 스트리밍과 채팅, 라이브 퀴즈를 구현한 과정에 대한 간단한 설명입니다.
#### 기존 서비스 아키텍처 ####
> ![기존 라이브 스트리밍 서비스 아키텍처 이미지](https://github.com/user-attachments/assets/7fa08b64-c0f7-4c46-b83b-7f764892c687)


실시간 스트리밍은 NGINX 를 이용하여 RTMP 프로토콜로 영상을 수신받고 FFMPEG 를 이용해 영상을 변환하고 HLS형태로 영상을 송출해주도록 하였습니다. <br/>
실시간 채팅과 퀴즈의 경우 Spring Boot 를 이용해 WebSocket 통신으로 채팅과 퀴즈를 처리하도록 구현하였습니다.

이러한 방법엔 다음과 같은 한계와 문제점들이 있었습니다.
- 동영상 스트리밍은 부하가 매우 큰 작업임에도 하나의 서버만 실행되기 때문에 부하처리가 힘들고 서버가 자주 다운되는 문제
- 실행되는 서버를 여러개로 나누었을 경우 특정 방송이 송출중인 서버를 찾지 못해 시청할 수 없는 문제
- 지연시간이 최소 15초에서 최대 1분까지로 매우 높은 문제

이런 문제들을 직접 해결하기엔 높은 난이도와 많은 시간이 들어가는 것을 고려하여 AWS 에서 제공하는 관리형 서비스를 이용해 구현하기로 하였습니다.

### 추가 자료 ###
<details>
<summary>
  $\rm{\normalsize{\color{#6580DD}실시간 스트리밍 강의 서비스 화면}}$
</summary>

#### 송출 화면 ####
> ![송출화면 이미지](https://github.com/user-attachments/assets/b75bb5a5-e8f7-4f2f-a889-c9de8ecada5f)


#### 시청 화면 ####
> ![시청화면 이미지](https://github.com/user-attachments/assets/e1a7715e-9c68-4730-bd1f-6c4eb0cfd8a7)


</details>

<details>
<summary>
  $\rm{\normalsize{\color{#6580DD}DynamoDB 스키마}}$
</summary>

```JSON
{
    "id": "id",
    "createdAt": "생성시간",
    "updatedAt": "수정시간",
    "arn": "channel 리소스 arn",
    "streamKey": "스트림키",
    "ingestEndpoint": "송출용 엔드포인트",
    "playbackUrl": "재생 url",
    "title": "제목",
    "chatArn": "Room 리소스 arn",
    "messagingEndpoint": "채팅 메시징 엔드포인트",
    "instructor": "강사이름",
    "courseProvideId": "코스제공 id",
    "course": "코스이름",
    "startTime": "시작시간",
    "endTime": "종료시간",
    "status": "상태 [CREATED | ON | CANCELED | END]"
  }
```


</details>
</details>

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}데이터\ 시각화}}$
  </summary>

  <br />

> - ECharts와 AG Grid를 활용해 훈련기관 데이터를 시각적으로 표시.
> - 직관적인 차트와 표로 데이터를 분석 및 관리 가능.
> - 실적을 파악하기 쉽게 시각화.
> - 체결된 회사들의 정보를 한눈에 파악.

<img width="628" alt="Image" src="https://github.com/user-attachments/assets/9a0a2e1b-1ae1-4ecb-90fd-055caba8cf83" />
<img width="633" alt="Image" src="https://github.com/user-attachments/assets/981ca776-b2aa-4d72-86dc-72c254ccbe8d" />
<img width="635" alt="Image" src="https://github.com/user-attachments/assets/a9968205-6793-4018-a623-3aeb2500a2c5" />
<img width="629" alt="Image" src="https://github.com/user-attachments/assets/8eb65a07-1693-442f-87b7-ea42df448eae" />
<img width="631" alt="Image" src="https://github.com/user-attachments/assets/67f8261d-f61c-4fdb-9a17-928db2a70d0e" />
<img width="631" alt="Image" src="https://github.com/user-attachments/assets/695231b5-ef13-4d8a-979a-6f1142cb1509" />
<img width="628" alt="Image" src="https://github.com/user-attachments/assets/0dd2419b-2fe9-49da-9fac-9617fdab1bce" />
</details>

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}게시판\ (Notice,\ QnA)}}$
  </summary>
  
#### 공지 기능
> - 훈련기관이 공지사항을 작성하여 수강생에게 전달.
> - 수강생은 공지사항을 열람만 가능.
> - 조회수 확인 가능.

#### 질문 및 답변 기능
> - 수강생이 질문을 작성하고, 훈련기관 또는 다른 수강생이 답변 가능.
> - 훈련기관의 답변 완료 시, 상태가 "답변 완료"로 표시.
> - 질문과 답변 조회수 확인 가능.
> - CRUD 기능: 공지사항 및 질문/답변 관리 (생성, 조회, 수정, 삭제).


</details>

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}AWS ECS Fargate}}$
  </summary>
  
####  인프라 설계 및 구축

> - ECS Fargate 를 기반으로 컨테이너화된 마이크로 서비스 배포 및 관리
> - ALB 를 통한 트래픽을 효율적으로 분산
> - RDS 로 DB 관리하여 안정적이 데이터 저장 및 조회 지원
> - API Gateway 를 통한 경로기반 라우팅 및 인증 처리
> - 관련 인프라는 IaC 를 위해 Serverless Framework 를 활용하여 자동화 관리

#### 1. 2세대와 3세대가 합쳐진 서비스 아키텍처 구축
  - AWS ECS Fargate 를 사용하여 컨테이너화된 서비스를 배포하고 관리할수 있는 인프라를 구축하여 서비스의 확장성 및 유지보수성을 향상시켰습니다.
  - AWS ALB 를 통해 트래픽을 효율적으로 분산하여 고가용성과 부하 분산을 보장했습니다.
#### 2. 안정적이고 확장 가능한 데이터 관리
  - AWS RDS 를 활용한 DB 관리로 대규모의 데이터를 효율적으로 저장 및 조회할수 있게 구성하였습니다.


</details>
<br />

## 📃 Endpoint & API 설계 명세

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}Endpoint}}$
  </summary>

<br />

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

> 🔗 <a href="https://docs.google.com/spreadsheets/d/1FbEZzaVYbarLD038AXRxwZf7xKk-NhfY8tzmMPsJt2Y/edit?gid=1512902059#gid=1512902059">API 설계 명세 링크</a>

<br />

## 📃 ERD & DynamoDB 테이블 명세
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

<details>
  <summary>
    $\rm{\normalsize{\color{#6580DD}DynamoDb 테이블 명세}}$
  </summary>

<br />

### `CourseBoardTable`

| **필드명** | **키** | **설명** |
| --- | --- | --- |
| **id** | **PK (Primary Key)**   | 각 게시물의 고유 식별자. 테이블의 기본 키로 사용.         |
| **createdAt** | **SK (GSI)**             | 게시물이 생성된 날짜와 시간. 보조 인덱스 정렬 키로 사용됨.  |
| **userName** | **GSI PK (UserIndex)** | 사용자 이름. UserIndex의 파티션 키로 사용.             |
| **courseId** | **GSI PK (CourseIndex)** | 강의 ID. CourseIndex의 파티션 키로 사용.               |


#### 글로벌 보조 인덱스 (Global Secondary Index, GSI)

| **인덱스 이름** | **PK (파티션 키)** | **SK (정렬 키)** | **Projection** | **설명** |
| --- | --- | --- | --- | --- |
| **UserIndex** | `userName` | `createdAt` | ALL (모든 컬럼 포함) | 사용자 이름으로 데이터를 조회하고 생성일로 정렬. |
| **CourseIndex** | `courseId` | `createdAt` | ALL (모든 컬럼 포함) | 강의 ID로 데이터를 조회하고 생성일로 정렬.   |

---

### `CommentsTable`

| **필드명** | **키** | **설명** |
| --- | --- | --- |
| **id** | **PK (Primary Key)** | 각 댓글의 고유 식별자. 테이블의 기본 키로 사용됩니다. |
| **boardId** | **GSI PK (BoardIdIndex)** | 게시물 ID. BoardIdIndex의 파티션 키로 사용됩니다. |
| **createdAt** | **SK (GSI)** | 댓글이 작성된 날짜와 시간. 보조 인덱스의 정렬 키로 사용됩니다. |


#### 글로벌 보조 인덱스 (Global Secondary Index, GSI)

| **인덱스 이름**  | **PK (파티션 키)** | **SK (정렬 키)** | **Projection** | **설명** |
| --- | --- | --- | --- | --- |
| **BoardIdIndex** | `boardId` | `createdAt` | ALL (모든 컬럼 포함) | 게시물 ID로 댓글을 조회하고 생성일로 정렬. |
| **IdIndex** | `id` | `createdAt` | ALL (모든 컬럼 포함) | 댓글 ID로 데이터를 조회하고 생성일로 정렬. |


---

### `LiveTable`

| **필드명** | **키** | **설명** |
| --- | --- | --- |
| **id** | **PK (Primary Key)** | 각 항목의 고유 식별자. 테이블의 기본 키로 사용. |
| **courseProvideId** | **GSI PK (CourseProvideIndex)** | 강의 제공 ID. CourseProvideIndex의 파티션 키로 사용. |
| **startTime** | **GSI SK (StartTimeIndex)** | 항목의 시작 시간. StartTimeIndex의 정렬 키로 사용. |
| **arn** | **GSI PK (ArnIndex)** | ARN (Amazon Resource Name). ArnIndex의 파티션 키로 사용. |


#### 글로벌 보조 인덱스 (Global Secondary Index, GSI)

| **인덱스 이름** | **PK (파티션 키)** | **SK (정렬 키)** | **Projection** | **설명** |
| --- | --- | --- | --- | --- |
| **CourseProvideIndex** | `courseProvideId` | - | ALL (모든 컬럼 포함) | 강의 제공 ID로 데이터를 조회. |
| **IdIndex** | `id` | `startTime` | ALL (모든 컬럼 포함) | 항목 ID로 데이터를 조회하고 시작 시간으로 정렬. |
| **CourseProvideIndex-Sort** | `courseProvideId` | `startTime` | ALL (모든 컬럼 포함) | 강의 제공 ID로 데이터를 조회하고 시작 시간으로 정렬. |
| **ArnIndex** | `arn` | - | ALL (모든 컬럼 포함) | ARN으로 데이터를 조회. |


</details>

> 🔗 <a href="https://dbdiagram.io/d/ahmy-devton-6719cb9997a66db9a314d728">ERD 링크(db Diagram.io)</a>

<br />

## 🗝️ 피드백

1. 정부와 관련된 서비스는 수익성을 기대하기 어렵기 때문에 BEP(Break - evne Point)를 기대하기 어려움
2. 통합 LMS의 실시간 강의는 BEP를 기대하기 어렵기 때문에, 단순히 LMS를 만들어서 판매하는 것이 더 이득이 될 것
3. 직무교육 LMS는 집중력 유도 보다는 수료가 더 중요한 것, 라이브 스트리밍이 중요하지 않음
4. 하지만, 현실적인 문제에 대하여 솔루션을 내놓고 다른 시각으로 접근하여 도전해본것이 매우 인상적임
5. 완전한 LMS보다는 일부의 특정 문제점에 대하여 선택과 집중을 하여 구현해보는 것도 좋은 방법
6. 이벤트 기반의 서버리스 아키텍처와 ECS를 같이 운영 배포하는 것은 잘못 되었음. ECS의 경우 매우 많이 복잡하게 얽혀 있는 경우 어쩔수 없이 한 번에 올려 운영하는 경우는 있지만, 완전한 서버리스로 운영하는 것이 더 좋음
7. GitHub Actions에서 ECR에 제대로 업로드 되었는지 단계를 추가하지 않은 것이 아쉬움
