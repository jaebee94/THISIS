<img src="https://ifh.cc/g/0wcaFR.png"></img>

# 📢프로젝트 소개

질병을 의미하는 Disease에서 비슷한 발음인 **THISIS**로 프로젝트 이름을 정하였습니다.
환자들끼리 부족한 정보들을 얻기 위한 목적에 있는 질병 SNS로써 역할을 하기 위해 프로젝트를 진행하게 되었습니다. 
회원가입, 로그인, 유저 팔로우 등 명세서 기반 기본 기능을 바탕으로 해 실질적으로 유용한 질병 관리 SNS를 추구합니다. 

<img src ="https://img.shields.io/badge/platform-Web-red"></img>
<img src ="https://img.shields.io/badge/library-Vue-green"></img>
<img src ="https://img.shields.io/badge/framework-Spring-skyblue"></img>
<img src ="https://img.shields.io/badge/database-MySQL-silver"></img> 
<img src ="https://img.shields.io/badge/server-AWS-gold"></img>
<img src ="https://img.shields.io/badge/language-Java%2C%20JavaScript-purple"></img>

# 😁 팀원 소개

<img src="https://ifh.cc/g/kWHKzY.jpg" width="80px"></img>
팀장 : **강세응** 

<img src="https://ifh.cc/g/HPZDs9.jpg" width="80px"></img>
CTO : 김지현

<img src="https://ifh.cc/g/WW8w5c.jpg" width="80px"></img>
Frontend 장 : 조재빈

<img src="https://ifh.cc/g/e1R0hS.jpg" width="80px"></img>
Backend 장: 김재현

<img src="https://ifh.cc/g/U0AkZo.jpg" width="80px"></img>
Backend Ace : 채지은

# ⌛ 프로젝트 개발 일정

<img src="https://ifh.cc/g/KJw2qH.png" height = "350px" width="600px"></img>

# 🤝 Branch Rule

* Git Branch 전략
   * master -> develop -> feature/(기능 이름)
        
     * 기능 단위로 feature 생성 후 작업 -> develop으로 병합
     * 완성된 기능에 대한 feature 브랜치는 작업 완료 후 삭제 
   
   * Commit Rule : [브랜치명]커밋 내용 | JIRA이슈 ID
      ex)[develop] : [브랜치명] 커밋 내용 | JIRA이슈 ID

     * JIRA번호 마지막에
     
     * 마침표 X
     
     * 영어만 사용
     
     * 브랜치명: 모두 소문자
     
     * 커밋내용 : 첫 글자만 대문자, 명령문 형식으로 작성

# ✍ Ground Rule

* 1일 1 commit

* 1주 1 merge request

* merge 후 브랜치 지우기


# 🛠 프로젝트 개발 환경 설정


## Frontend 개발환경

##### NPM 사용을 위한 Node.js 설치

>Node.js 는 오픈소스 자바스크립트 런타임 엔진(컴파일러 + 인터프리터 등)

>브라우저가 아닌 환경에서도 자바스크립트를 사용할 수 있게 해준다.

- [node.js 설치하기](https://nodejs.org/en/_) (LTS 버전 권장)

- 명령 프롬프트 창에서 `node -v`, `npm -v` 입력하여 설치 확인


##### Yarn 설치

>빌드 속도가 보다 빠르고 최근 활용도가 높다.

- [yarn 설치하기](https://yarnpkg.com/en/docs/install#windows-stable)

- 명령 프롬프트 창에서 `yarn -v`입력하여 설치 확인


##### Vue.js, Vue-cli 설치

> Vue  프로젝트의 Vue-cli 를 이용할 경우 Webpack, Eslint, 개발 서버 등의 설정이 자동으로 구성되어 손쉽게 프로젝트를 시작할 수 있는 장점이 있다.

- 명령 프롬프트 창에서 `yarn global add @vue/cli` 입력

- `vue --version` 입력하여 설치 확인


(참고) `vue create example` 를 입력하여 새로운 프로젝트를 생성할 수 있다.


##### Sass 설정

> Webpack에서 Sass 를 Css로 컴파일하기 위해 node-sass 와 sass-loader 를 설치한다.

- `yarn add node-sass sass-loader`


##### Vue-route, Vuex 설치

- `yarn add vue-router vuex`


## Backend 개발환경

##### Spring Boot 설치

> 간단한 구성을 위해 Spriong Boot 를 이용한다.

- Spring 사용을 위한 JDK 설치

  OpenJDK 공식 웹사이트에서 압축 파일을 다운로드 받고 압축 해제 후 해당 경로를 기억한다.

- 환경 변수 설정

  [내 컴퓨터 - 속성] -> [고급 시스템 설정] -> [환경 변수] -> [새로 만들기]

  - 변수 이름:  `JAVA_HOME`
  - 변수 값: `{해당 경로}`

  Path 편집 -> 변수 값에 `%JAVA_HOME%\bin` 추가

- 명령 프롬프트 -> `java -version` 설치 확인

- [Maven 설치](http://maven.apache.org/download.cgi)

- 환경 변수 설정

  - 변수 이름: `MAVEN_HOME`
  - 변수 값: `{해당 경로}`

  Path 편집 -> 변수 값에 `%MAVEN_HOME%\bin` 추가

- 명령 프롬프트 -> `mvn -v` 설치 확인

- VScode 설치
  - Spring Boot Extension Pack 설치
  - ctrl + shift + P -> Spring Initializer:Generate Maven Project 실행
    - Project Language: Java
    - Spring Boot Version: 2.2.2
    - Dependency: Lombok, Spring Web 선택


##### Docker 기반의 MariaDB 구성

- Docker 설치
- 명령 프롬프트 `Docker -v` 설치 확인

- 인스턴스 생성: `docker run --name {db이름} -p {port}:{port} -e MYSQL_ROOT_PASSWORD={password} -d mariadb`
- 인스턴스 실행: `docker exec -it {db이름} mysql -u root -p`
- `docker exec -i {db이름} mysql -uroot -p{password} --database={schema 이름} < {OOO.sql}`

- MariaDB 연결

  - pom.xml 에 아래 구문 추가

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
    ```

  - application.properties 에 아래 구문 추가

    ```properties
    spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
    spring.datasource.url=jdbc:mysql://localhost:3306/{DB명}?autoReconnect=true&useUnicode=true&characterEncoding=utf8
    spring.datasource.username=root
    spring.datasource.password={비밀번호}
    ```
    
## 개발 환경 및 IDE

<img src="https://subicura.com/assets/article_images/2017-01-19-docker-guide-for-beginners-1/docker-logo.png" width="80px"></img>
도커를 사용하여 MariaDB 인스턴스 생성

<img src="https://t1.daumcdn.net/cfile/tistory/213F23475902E2B619" width="80px"></img>
STS를 사용해 BackEnd 작업

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Visual_Studio_Code_1.18_icon.svg/1028px-Visual_Studio_Code_1.18_icon.svg.png" width="80px"></img>
VS CODE 사용하여 FrontEnd 작업

<img src="https://a0.awsstatic.com/libra-css/images/logos/aws_logo_smile_1200x630.png" height = "80px" width="80px"></img>
AWS 활용한 서버 연동

## Docker 명령어 정리

|명령어|내용|
|------|----|
|버전 확인|Docker -v|
|인스턴스 생성|docker run --name {db이름} -p {port}:{port} -e MYSQL_ROOT_PASSWORD={password} -d mariadb|
|인스턴스 실행|docker exec -it {db이름} mysql -u root -p|

## FrontEnd 명령어 정리

* 명령어는 Node.js와 Vue.js가 설치되어 있다고 가정합니다

|명령어|내용|
|-----|---|
|yarn install|Node Modules에 있는 내용들 install|
|yarn serve --port 3000|port번호 3000에서 로컬 서버 실행|

## AWS 서버 명령어 정리 

|명령어|내용|
|-----|---|
|ssh -i I3A301T.pem ubuntu@i3a301.p.ssafy.io|키 I3A301T.pem을 가지고 AWS 서버에 접속|
|sudo java -jar test/THISIS-0.0.3.jar|AWS 서버 내부에서 스프링 서버 실행|
|(backend 폴더에서) mvn package | 백엔드 스프링 서버 빌드 |
| pm2 restart War2 | 서버에 백엔드 빌드 파일 반영 |
|(frontend 폴더에서) npm run buile | 프론트엔드 뷰 배포 |

## DB sql문 설정

##### 회원 테이블
``` sql
create table UserInfo(
  user_id      int primary key auto_increment,   
  username      varchar(45),      
  nickname        varchar(45) ,         
  email         varchar(45) ,         
  password         varchar(45),      
  introduction      varchar(200)         
);

```

##### 인증 테이블
```sql
drop table IF EXISTS `THISIS`.`auth`;
CREATE TABLE  `THISIS`.`auth` (
  `user_id` INT NOT NULL,
  `refresh_token` VARCHAR(500) NULL,
  `access_token` VARCHAR(500) NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;
```    

##### 알람/요청 테이블
``` sql
create table notification(
  `id` int primary key auto_increment,
  `follower_id` int,
  `followee_id` int,
  `newtofollower` int,
  `newtofollowee` int,
  `approval` int,
  `time`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```
##### 게시글 테이블
``` sql
CREATE TABLE IF NOT EXISTS `THISIS`.`posts` (
  `posts_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `posts_title` TEXT NULL,
  `posts_main` TEXT NULL,
  `health_count` INT NULL,
  `notification_id` VARCHAR(45) NULL,
  `post_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `nickname` VARCHAR(20) NULL,   
  PRIMARY KEY (`posts_id`))
ENGINE = InnoDB;
```

##### 댓글 테이블
``` sql
CREATE TABLE IF NOT EXISTS `THISIS`.`comment` (
  `user_id` INT NOT NULL,
  `posts_id` INT NOT NULL,
  `user_nickname` VARCHAR(45) NULL,
  `comment_main` TEXT NULL,
  `hide` TINYINT(1) NULL,
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `comment_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`))
ENGINE = InnoDB
COMMENT = '   ';
```

##### 팔로워/팔로잉 테이블
``` sql
drop table IF EXISTS `THISIS`.`followers_following` ;
CREATE TABLE IF NOT EXISTS `THISIS`.`followers_following` (
  `follower` INT NOT NULL,
  `followee` INT NOT NULL,
  PRIMARY KEY (`follower`, `followee`))
ENGINE = InnoDB;
```

##### 건강해요 테이블
```sql
drop table IF EXISTS `THISIS`.`health` ;
CREATE TABLE IF NOT EXISTS `THISIS`.`health` (
  `posts_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `nickname` VARCHAR(20) NULL,
  PRIMARY KEY (`posts_id`, `user_id`))
ENGINE = InnoDB;
```

#### 스크랩 테이블    
``` sql
drop table IF EXISTS `THISIS`.`scrap` ;
CREATE TABLE IF NOT EXISTS `THISIS`.`scrap` (
  `user_id` INT NOT NULL,
  `posts_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `posts_id`),
  CONSTRAINT `fk_scrap_userinfo1`
    FOREIGN KEY (`user_id`)
    REFERENCES `THISIS`.`Userinfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_scrap_posts`
    FOREIGN KEY (`posts_id`)
    REFERENCES `THISIS`.`posts` (`posts_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
```

## Rest API
|Task|URL|Method|Action|
|---|------|--|--------|
|회원상세정보|/account/{user_id}|GET|해당 user_id 회원 정보 조회|
|회원가입|/account/signup|POST|	회원 가입|
|로그인|/account/login|POST|로그인|
|회원정보 변경|/account/{user_id}|PUT|회원정보 변경|
|회원 탈퇴|/account/{user_id}|DELETE|해당 user_id 회원 정보 삭제|
|이메일 중복 테스트|/account/email|GET|이메일 중복 여부를 테스트함|
|닉네임 중복 테스트|/account/nickname|GET|닉네임 중복 여부를 테스트함|
|회원 검색|/account/search|GET|검색어에 해당하는 회원 정보를 검색함|
|비밀번호찾기|/email|GET|이메일로 비밀번호를 찾아줌|
|전체 게시글 목록|/articles/|GET|전체 게시글 조회|
|게시글 목록|/articles/{user_id}|GET|그 유저에 대한 모든 게시글 목록|
|게시글 생성|/articles|POST|새로운 게시글 생성|
|게시글 수정|/articles/{posts_id}|PUT|게시글 내용 수정(게시글 제목, 내용만 수정 가능)|
|게시글 삭제|/articles/{posts_id}|DELETE|작성자가 자신의 게시글 삭제|
|댓글 목록|/comment/{post_id}|GET|게시글에 해당하는 댓글들 목록|
|댓글 생성|/comment|POST|댓글을 생성함|	
|댓글 수정|/comment/{comment_id}|PUT|해당 댓글을 수정함|	
|댓글 삭제|/comment/{comment_id}|DELETE|해당 댓글을 삭제함|	
|댓글 숨김|/comment/hidden/{comment_id}|PUT|게시글 작성자가 요청 시 댓글 숨김|
|팔로잉 리스트|/follow/followee/{user_id}|GET|유저가 팔로잉하는 리스트를 노출|
|팔로워 리스트|/follow/follower/{user_id}|GET|유저를 팔로워하는 리스트를 노출|
|팔로잉 수 집계|/follow/followee/sum/{user_id}|GET|해당 user_id가 팔로우하는 총 팔로잉 수 조회|
|팔로워 수 집계|/follow/follower/sum/{user_id}|GET|해당 user_id를 팔로우하는 총 팔로워 수 조회|
|팔로잉/팔로워 저장|/follow|POST|팔로우 팔로워 관계 저장|
|팔로잉/팔로워 삭제(언팔)|/follow|DELETE|팔로우 팔로워 관계 삭제|
|유저 총 건강해요 수 조회|health/user/{user_id}|GET|프로필에 나오는 user 총 좋아요 수 집계|
|건강해요 취소|health/{posts_id}/{user_id}|DELETE|유저가 건강해요를 취소했을때 record 삭제|
|스크랩 조회|scrap/{user_id}|GET|유저 아이디에 해당하는 스크랩 조회|	
|스크랩 생성|scrap|	POST|스크랩 생성|	깃 수정 규칙
|스크랩 삭제|scrap/{posts_id, user_id}|DELETE|해당 스크랩 삭제|	


## Browser Support
|<img src="https://is2-ssl.mzstatic.com/image/thumb/Purple114/v4/bb/72/49/bb72493a-6ede-8369-5513-f1e9f930b642/AppIcon-0-1x_U007emarketing-0-6-0-0-85-220.png/246x0w.png" width="80px"> |<img src = "https://zdnet4.cbsistatic.com/hub/i/2019/01/29/f4eba7da-10cd-4b45-b974-464c08e145be/microsoft-edge.png" width="80px"></img>|<img src = "https://t1.daumcdn.net/cfile/tistory/253EC1495495846E1C" width = "80px"></img>|
|--|--|--|
|latest✔|latest✔|latest✔|


# 👀 서비스 설명 

<img src = "https://ifh.cc/g/7toxt5.png"> </img>

<img src = "https://ifh.cc/g/YgIern.png" width=200></img>
  로딩 화면 - 로그인으로 넘어가기 전에 처음으로 보여주는 페이지 

<img src = "https://ifh.cc/g/3trgSh.png" width=200></img>
  로그인 화면 - 저장된 계정을 가지고 이메일과 패스워드를 입력받는 페이지

<img src = "https://ifh.cc/g/wjOPFi.png" width=200></img>
  검색 화면 - 사용자와 질병을 검색할 수 있는 화면으로 누를 시 상대방 프로필에 들어가짐

<img src = "https://ifh.cc/g/RBar5F.png" width=200></img>
  메인 피드 - 메인 화면으로 모든 게시글들을 보여주는 화면으로 건강해요, 댓글, 스크랩, (자신의 게시글일 경우) 수정 가능

<img src = "https://ifh.cc/g/30tWNI.png" width=200></img>
  게시글 작성 - 게시글을 작성할 수 있는 화면으로 자신의 계정으로 작성한다
  
<img src = "https://ifh.cc/g/5jyv4i.png" width=200></img>
   알림 요청 - 팔로잉을 했을 경우 & 팔로잉 결과를 알려줄 때 알람에서 팔로잉을 받았을 때는 요청탭으로 요청이 와서 승낙 혹은 거절을 결정할 수 있다
   
<img src = "https://ifh.cc/g/ougKek.png" width=200></img>
   메인 프로필 화면 - 메인 프로필 화면으로 내 정보를 보여주는 화면으로 내가 쓴 게시글, 나의 스크랩 등도 볼 수 있다.

# 💪기여 방법

## 고려 사항

* 프로젝트를 변경하려는 사람들은 시작하는 방법에 대한 문서를 작성하는 것이 좋습니다. 
* 실행해야하는 스크립트 나 설정해야 할 환경 변수가 있을 수 있습니다. 
* 이 단계를 명시적으로 작성하십시오.
* AWS 서버의 작동 유무를 확인하십시오.

## Git Rules 
* git pull origin develop를 통해 develop와 먼저 merge 한 상태로 진행합니다.
* git status를 통해 변경 된 점을 확인합니다
* git add 를 통해서 변경을 주고 싶은 점을 진행합니다.
* git commit -m "지라번호" 를 통해서 각 지라에 등록을 해서 커밋합니다.
* git push origin feature/"개발 기능"를 통해서 기능기리 겹치지 않게 수행합니다.
* git bash에서 merge request를 신청합니다. 

# 📜License

본 가이드는 MIT 라이선스 하에 공개합니다 <img src = "https://img.shields.io/badge/license-MIT-pink" width=80></img>

* 공유 - 복제, 배포, 포맷 변경, 전송, 전시, 공연, 방송할 수 있습니다.
* 변경 - 리믹스, 변형, 2차적 저작물 작성 및 영리목적의 이용이 가능합니다. 




