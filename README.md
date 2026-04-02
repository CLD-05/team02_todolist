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

<Prerequistites>    
Java 17  
Docker   
Docker Compose  
Git    

<실행>
1. 프로젝트 클론 
git clone 
cd team02_todolist/todolist
2. maven 빌드
mvn clean package
3. Docker 실행(build)
docker-compose up -d -build
4. 실행 확인
docker ps
5. 서비스 접속
http://localhost:
