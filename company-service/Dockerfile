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

# application.yml 파일 복사
COPY src/main/resources/application.yml /app/application.yml

# 사용 가능한 port no 지정
EXPOSE 8080

# spring boot 기반의 application 을 컨테이너가 실행이 될 때 실행이 되도록 설정
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/application.yml"]


