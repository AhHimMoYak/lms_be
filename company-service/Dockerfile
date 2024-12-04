### multi-stage build ###

### Stage 1: Builder Stage ###
FROM openjdk:21-jdk-slim AS builder

# 필수 패키지 설치
RUN apt-get update && apt-get install -y dos2unix findutils base64

# 작업 디렉토리 설정
WORKDIR /app

# 프로젝트 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# gradlew 실행 권한 부여 및 줄바꿈 변환
RUN chmod +x ./gradlew
RUN dos2unix ./gradlew

# 실행 가능한 jar 파일로 패키징
RUN ./gradlew bootJar --no-daemon

### Stage 2: Executable Stage ###
FROM openjdk:21-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# Stage 1에서 빌드된 jar 파일 복사
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# 포트 노출
EXPOSE 8080

# Spring Boot 실행 명령 및 Base64 디코딩 처리
ENTRYPOINT ["/bin/bash", "-c", "base64 -d /app/application.yml.base64 > /app/application.yml && java -jar /app/app.jar --spring.profiles.active=prod"]
