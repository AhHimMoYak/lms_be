### multi stage build ###

### stage 1 : builder stage ###

# 베이스 이미지
FROM openjdk:21-jdk AS builder

# 도구 설치
RUN apt-get update && apt-get install -y dos2unix

# 생성될 docker image 안에서 gradle 빌드를 하기 위해서 필요한 파일(gradle, java 소스) 를 docker image 내부로 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# gradlew 를 이용해서 빌드를 진행 및 jar 파일로 패키징
# gradle 빌드 시스템을 사용할 때 application 에서 필요한 gradle 버전을 자동으로 다운로드 하고 설치하는 도구
# gradle 명령어는 gradlew 를 기반임, 실행이 가능하도록 실행 권한을 설정
RUN chmod +x ./gradlew

# 윈도우 환경에서 docker 를 사용해 gradlew 를 하는 경우 필요함
RUN dos2unix ./gradlew

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
