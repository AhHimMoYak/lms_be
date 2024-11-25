### multi stage build ###

### stage 1 : builder stage ###

# 베이스 이미지
FROM openjdk:21-jdk AS builder

# 생성될 docker image 안에서 gradle 빌드를 하기 위해서 필요한 파일(gradle, java 소스) 를 docker image 내부로 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# gradlew 실행 권한 부여 및 줄바꿈 변환
RUN chmod +x ./gradlew
RUN sed -i 's/\r$//' ./gradlew

# 실행 가능한 jar 파일로 패키징
RUN ./gradlew bootJar

### stage 2 : executable stage ###

# 두 번째 FROM 에 해당하는 실행 단계
FROM openjdk:21-jdk

# 컨테이너의 홈 경로 지정
WORKDIR /app

# stage 1 에서 빌드된 패키지를 컨테이너의 홈 경로로 복사
COPY --from=builder build/libs/*.jar /app/app.jar

# 사용 가능한 port no 지정
EXPOSE 8080

# spring boot 기반의 application 을 컨테이너가 실행이 될 때 실행이 되도록 설정
ENTRYPOINT ["java", "-jar", "app.jar"]
