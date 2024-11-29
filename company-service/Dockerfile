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


# gradlew 를 이용해서 빌드를 진행 및 jar 파일로 패키징
# gradle 빌드 시스템을 사용할 때 application 에서 필요한 gradle 버전을 자동으로 다운로드 하고 설치하는 도구
# gradle 명령어는  gradlew 를 기반임, 실행이 가능하도록 실행 권한을 설정
RUN chmod +x ./gradlew
# 윈도우 환경에서 docker 를 사용해 gradlew 를 하는 경우 필요함
# 사용하지 않게 되면, gradlew 명령어가 리눅스가 아닌 윈도우 명령어로 인식되어 다음 명령어인 gradlew bootJar 명령어가 실행되지 않을 수 있음
# dos2unix 명령어를 통해 gradlew 명령어를 리눅스 명령어로 인식되게 해줌
RUN dos2unix ./gradlew
# 실행 가능한 jar 파일로 패키징
# 실제 프로젝트가 docker image 안에서 build 되면서 배포될 실행 가능한 jar 파일을 생성
RUN ./gradlew bootJar

### stage 2 : executable stage ###

# 두 번째 FROM 에 해당하는 실행 단계
# openjdk:17-jdk-alpine 를 이용해서 item-service 마이크로 서비스를 실행
FROM openjdk:21-jdk

# 컨테이너의 홈 경로 지정
WORKDIR /app

# stage 1 에서 빌드된 패키지를 컨테이너의 홈 경로로 복사
# jdk17 버전으로 생성된 jar 파일들을 컨테이너 홈 경로인 /app 폴더안으로 복사
# 이전 builder stage 에서 생성된 빌드 결과물을 홈 경로로 복사
COPY --from=builder build/libs/*.jar /app/app.jar

# 사용 가능한 port no 지정
EXPOSE 8080

# spring boot 기반의 application 을 컨테이너가 실행이 될 때 실행이 되도록 설정
# java 명령어로 패키징된 jar 파일을 실행
# 즉, spring boot 기반의 item-service 이미지로 서비스를 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
