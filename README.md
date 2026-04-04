# team02_todolist

Todolist   
Spring Boot 기반의 TodoList 애플리케이션   
Docker와 VM 환경을 활용하여 Build 방식으로 배포를 수행   
클라우드 아키텍처 설계 및 기획   

<기술스택>  
Language : Java 17   
Framework : Spring Boot   
Database : MySQL 8.0 (RDS)   
Container : Docker, Docker Compose   
AWS (아키텍처 설계)  

<주요 기능>  
사용자 회원가입 및 로그인  
todo 생성 / 수정 / 삭제 /조회  
todo 상태관리 (완료/미완료/진행률)  
사용자별 todo 데이터 분리  
로그아웃 및 회원탈퇴  

<배포 흐름>  
코드 수정  
   ↓  
maven Build (JAR 생성)  
   ↓  
Dockerfile 기반 이미지 생성  
   ↓  
docker-compose up --build 실행  
   ↓  
Spring Boot + MySQL 컨테이너 실행  
   ↓  
서비스 배포 완료  

<사전 설정>  
Java 17  
maven  
Docker  
Docker compose  
Git  

<실행>
1. 프로젝트 클론   
git clone https://github.com/CLD-05/team02_todolist.git  
cd team02_todolist/todolist

2. 환경변수 설정
cat > .env << 'EOF'
MYSQL_ROOT_PASSWORD=root1234
MYSQL_DATABASE=todo_app
MYSQL_USER=todo
MYSQL_PASSWORD=todo1234

SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/todo_app?serverTimezone=Asia/Seoul
SPRING_DATASOURCE_USERNAME=todo
SPRING_DATASOURCE_PASSWORD=todo1234
EOF

3.application.properties 설정
cat > src/main/resources/application.properties << 'EOF'
# DB 설정 
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

# JPA
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true

# Thymeleaf
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false
spring.thymeleaf.check-template-location=true
EOF
   
4. maven 빌드  
mvn clean package -DskipTests

6. Docker 실행(build)  
docker-compose up -d -build

7. 실행 확인  
docker ps

8. 서비스 접속  
http://localhost:8080
