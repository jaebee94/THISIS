

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



<img src="https://ifh.cc/g/eEQC6V.png" height = "350px" width="800px"></img>



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
drop table IF EXISTS `THISIS`.`Userinfo`;
CREATE TABLE IF NOT EXISTS `THISIS`.`UserInfo` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `nickname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `introduction` VARCHAR(300) NULL,
  `userimage` VARCHAR(300) NULL,
  `role` VARCHAR(10) DEFAULT "user",
  `disabled` INT DEFAULT 0,
  `salt` VARCHAR(100) NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC) VISIBLE)
ENGINE = InnoDB;

```

##### 인증 테이블
```sql
drop table IF EXISTS `THISIS`.`auth`;
CREATE TABLE IF NOT EXISTS `THISIS`.`auth` (
  `user_id` INT NOT NULL,
  `refresh_token` VARCHAR(700) NULL,
  `access_token` VARCHAR(700) NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_auth_userinfo`
    FOREIGN KEY (`user_id`)
    REFERENCES `THISIS`.`UserInfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
```

##### 알람/요청 테이블
``` sql
drop table IF EXISTS `THISIS`.`notification`;
CREATE TABLE IF NOT EXISTS `THISIS`.`notification` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `newtofollower` INT NOT NULL,
  `newtofollowee` INT NOT NULL,
  `approval` INT NOT NULL,
  `time` TIMESTAMP NOT NULL,
  `follower_id` INT NOT NULL,
  `followee_id` INT NOT NULL,
  PRIMARY KEY (`id`, `follower_id`, `followee_id`),
  CONSTRAINT `fk_notification_userinfo1`
    FOREIGN KEY (`follower_id`)
    REFERENCES `THISIS`.`UserInfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_notification_userinfo2`
    FOREIGN KEY (`followee_id`)
    REFERENCES `THISIS`.`UserInfo` ( `user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    )
ENGINE = InnoDB;
```
##### 게시글 테이블
``` sql
drop table IF EXISTS `THISIS`.`post`;
CREATE TABLE IF NOT EXISTS `THISIS`.`posts` (
  `posts_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `posts_title` TEXT NULL,
  `posts_main` TEXT NULL,
  `post_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `category` INT NULL,
  `diseasecode` VARCHAR(45) NULL,
  `imgsrc` VARCHAR(300) NULL,
  `hidden` INT DEFAULT 0,
  PRIMARY KEY (`posts_id`),
  CONSTRAINT `fk_posts_userinfo1`
    FOREIGN KEY (`user_id`)
    REFERENCES `THISIS`.`UserInfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
ENGINE = InnoDB;
```

##### 댓글 테이블
``` sql
drop table IF EXISTS `THISIS`.`comment`;
CREATE TABLE IF NOT EXISTS `THISIS`.`comment` (
  `posts_id` INT NOT NULL,
  `comment_main` TEXT NULL,
  `hide` TINYINT(1) NULL,
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `comment_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`comment_id`),
  CONSTRAINT `fk_comment_posts`
    FOREIGN KEY (`posts_id`)
    REFERENCES `THISIS`.`posts` (`posts_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_userinfo1`
    FOREIGN KEY (`user_id`)
    REFERENCES `THISIS`.`UserInfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = '   ';
```

##### 팔로워/팔로잉 테이블
``` sql
drop table IF EXISTS `THISIS`.`followers_following`;
CREATE TABLE IF NOT EXISTS `THISIS`.`followers_following` (
  `follower` INT NOT NULL,
  `followee` INT NOT NULL,
  PRIMARY KEY (`follower`, `followee`),
  CONSTRAINT `fk_follower_followering_userinfo1`
    FOREIGN KEY (`follower`)
    REFERENCES `THISIS`.`UserInfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
     CONSTRAINT `fk_follower_followering_userinfo2`
    FOREIGN KEY (`followee`)
    REFERENCES `THISIS`.`UserInfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    )
ENGINE = InnoDB;
```

##### 건강해요 테이블
```sql
drop table IF EXISTS `THISIS`.`health`;
CREATE TABLE IF NOT EXISTS `THISIS`.`health` (
  `posts_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`posts_id`, `user_id`),
  CONSTRAINT `fk_health_posts1`
    FOREIGN KEY (`posts_id`)
    REFERENCES `THISIS`.`posts` (`posts_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_health_userinfo1`
    FOREIGN KEY (`user_id`)
    REFERENCES `THISIS`.`UserInfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
```

#### 스크랩 테이블    
``` sql
drop table IF EXISTS `THISIS`.`scrap`;
CREATE TABLE IF NOT EXISTS `THISIS`.`scrap` (
  `user_id` INT NOT NULL,
  `posts_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `posts_id`),
  CONSTRAINT `fk_scrap_userinfo1`
    FOREIGN KEY (`user_id`)
    REFERENCES `THISIS`.`UserInfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_scrap_posts`
    FOREIGN KEY (`posts_id`)
    REFERENCES `THISIS`.`posts` (`posts_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
```

####  질병 테이블    
``` sql
drop table IF EXISTS `THISIS`.`subscribe`;
CREATE TABLE IF NOT EXISTS `THISIS`.`subscribe` (
  `diseasecode` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `diseasecode`),
  CONSTRAINT `fk_subscribe_userinfo1`
    FOREIGN KEY (`user_id`)
    REFERENCES `THISIS`.`UserInfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
```

####  구독 테이블    
``` sql
drop table IF EXISTS `THISIS`.`subscribe`;
CREATE TABLE  `THISIS`.`subscribe` (
  `user_id` INT NOT NULL,
  `diseasecode` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`, `diseasecode`))
ENGINE = InnoDB;
```

####  태그 테이블    
``` sql
drop table IF EXISTS `THISIS`.`tag`;
CREATE TABLE IF NOT EXISTS `THISIS`.`tag` (
  `tagid` INT NOT NULL AUTO_INCREMENT,
  `tagname` VARCHAR(45) NULL,
  PRIMARY KEY (`tagid`))
ENGINE = InnoDB;
```

####  태그 관계 테이블    
``` sql
drop table IF EXISTS `THISIS`.`tag_relation`;
CREATE TABLE IF NOT EXISTS `THISIS`.`tag_relation` (
  `tagid` INT NOT NULL,
  `posts_id` INT NOT NULL,
  PRIMARY KEY (`tagid`, `posts_id`),
  CONSTRAINT `fk_tag_has_posts_tag1`
    FOREIGN KEY (`tagid`)
    REFERENCES `THISIS`.`tag` (`tagid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tag_has_posts_posts1`
    FOREIGN KEY (`posts_id`)
    REFERENCES `THISIS`.`posts` (`posts_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
```

####  댓글 건강해요 테이블    
``` sql
drop table IF EXISTS `THISIS`.`comment_health`;
CREATE TABLE IF NOT EXISTS `THISIS`.`comment_health` (
  `comment_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`comment_id`, `user_id`),
  CONSTRAINT `fk_comment_health_comment1`
    FOREIGN KEY (`comment_id`)
    REFERENCES `THISIS`.`comment` (`comment_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_health_userinfo1`
    FOREIGN KEY (`user_id`)
    REFERENCES `THISIS`.`UserInfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
```

#### 신고해요 테이블
``` sql
drop table IF EXISTS `THISIS`.`police`;
CREATE TABLE IF NOT EXISTS `THISIS`.`police` (
  `posts_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `reason` VARCHAR(100) NULL,
  `police_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`police_id`),
  CONSTRAINT `fk_police_posts1`
    FOREIGN KEY (`posts_id`)
    REFERENCES `THISIS`.`posts` (`posts_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_police_userinfo1`
    FOREIGN KEY (`user_id`)
    REFERENCES `THISIS`.`UserInfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
```

#### 의사 인증 테이블
``` sql
drop table IF EXISTS `THISIS`.`doctor`;
CREATE TABLE IF NOT EXISTS `THISIS`.`doctor` (
  `user_id` INT NOT NULL,
  `doctorimg` VARCHAR(45) NULL,
  `doctorauth` VARCHAR(45) NULL,
  `doctor_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`doctor_id`),
  CONSTRAINT `fk_doctor_userinfo1`
    FOREIGN KEY (`user_id`)
    REFERENCES `THISIS`.`UserInfo` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
```



## Rest API



|Category|Task|Method|Path|
|---|--------------|--|--------|
|게시물|모든 게시글 조회|GET|/articles|
|게시물|게시글 생성|POST|/articles|
|게시물|게시글 수정|PUT|/articles/{posts_id}|
|게시물|게시글 삭제|DELETE|	/articles/{posts_id}|
|게시물|유저에 해당하는 모든 게시글 반환|GET	|/articles/{user_id}|
|게시물|user_id 인자에 해당하는 게시판 목록를 반환한다.|GET|	/articles/new|
|게시물|게시글 생성 : 태그포함|	POST|/articles/new|
|게시물|파일을 입력한다.|	POST|/articles/upload|
|이메일|이메일 찾기|GET|/email|
|팔로우|팔로우 생성|POST|/follow|
|팔로우|팔로우 삭제|DELETE|	/follow|
|팔로우|팔로잉 정보 반환|	GET|/follow/followee/{user_id}|
|팔로우|팔로잉 집계 정보 반환|GET|	/follow/followee/sum/{user_id}|
|팔로우|팔로워 정보 반환|	GET|/follow/follwer/{user_id}|
|팔로우|팔로워 집계 정보 반환|GET|/follow/follower/sum/{user_id}|
|스크랩|해당하는 스크랩 유무|GET|/scrap|
|스크랩|스크랩 추가|POST|/scrap|
|스크랩|스크랩 삭제|DELETE|/scrap/{posts_id}/{user_id}|
|스크랩|스크랩 불러오기|GET|/scrap/{user_id}|
|건강해요|건강해요 추가|POST|/health/{posts_id}|
|건강해요|사람에 해당하는 건강해요 반환|	GET|/health/{user_id}|
|건강해요|게시글에 해당하는 건강해요 수 반환|GET|/health/post/{posts_id}|
|건강해요|사람에 해당하는 건강해요 수 반환|GET|/health/user/{user_id}|
|알람/요청|	모든 알람/요청을 반환한다|	GET|/notification|
|알람/요청|	팔로우를 요청했을 때 레코드를 저장한다|POST|	/notification|
|알람/요청|	해당 id에 해당하는 알림/요청 정보 수정|PUT|/notification|
|알람/요청|	팔로우 요청 취소 시 레코드 삭제|	DELETE|	/notification|
|알람/요청|	해당 user_id가 followee인 레코드 목록 조회|GET|	/notification/followee/{user_id}|
|알람/요청|	해당 user_id가 follower인 레코드 목록 조회|GET|	/notification/follower/{user_id}|
|댓글|댓글 생성|POST|/comment|
|댓글|댓글 수정|PUT|/comment/{comment_id}|
|댓글|댓글 삭제|DELETE|/comment/{comment_id}|
|댓글|게시판에 해당하는 댓글들을 반환|GET|/comment/{posts_id}|
|댓글|숨김 설정|PUT|/comment/hidden/{comment_id}|
|회원관리|모든 회원 정보를 반환한다|GET|/account|
|회원관리|유저 ID에 해당하는 회원 정보를 반환한다|GET|/account/{user_id}|
|회원관리|유저 ID에 해당하는 회원 정보를 수정한다|PUT|/account/{user_id}|
|회원관리|회원 탈퇴 시 회원 정보를 삭제한다|DELETE|/account/{user_id}|
|회원관리|이메일 중복 테스트|GET|/account/email|
|회원관리|회원 email과 password로 로그인 가능 여부| 	GET|	/account/login|
|회원관리|닉네임 중복 테스트|GET|/account/nickname|
|회원관리|유저 id에 해당하는 프로필 정보를 반환|GET|	/account/profile/{user_id}|
|회원관리|accesstoken 재생성 테스트|POST|	/account/refreshAccessToken|
|회원관리|검색에어 해당하는 회원 정보 반환|GET|/account/search|
|회원관리|유저 회원가입 시 회원 정보 등록|POST|/account/signup|
|회원관리|이미지 주소 입력|POST|/account/upload|
|태그|태그 추가|POST|/tag|
|태그|태그 불러오기|GET|/tag/{tagid}|
|태그|태그 삭제|DELETE|/tag/{tagid}|
|태그 릴레이션|태그 릴레이션 추가|POST|/tag-relation|
|태그 릴레이션|태그 릴레이션 삭제|DELETE|/tag-relation/{tagid}/{posts_id}|
|태그 릴레이션|태그 릴레이션 불러오기(게시글)|	GET|/tag-relation/post/{posts_id}|
|태그 릴레이션|태그 릴레이션 불러오기(태그)|GET|/tag-relation/tag/{tagid}|
|댓글 건강해요|댓글 건강해요 추가|POST|/comment-health/{comment_id}|
|댓글 건강해요|사람에 해당하는 댓글 건강해요 반환|GET|/comment-health/{user_id}|
|댓글 건강해요|게시글에 해당하는 댓글 건강해요 수 반환|GET|/comment-health/comment/{comment_id}|
|댓글 건강해요|사람에 해당하는 댓글 건강해요 수 반환|	GET|/comment-health/user/{user_id}|
|질병 태그|	모든 질병 정보를 반환한다|	GET|/disease|
|질병 태그|	질병 정보를 등록한다|POST|/disease|
|질병 태그|	질병 정보를 삭제한다|DELETE|	/disease|
|질병 태그|	질병코드에 해당하는 질병 정보를 반환한다|	GET	|/disease/diseasecode|
|구독하기|모든 구독 정보를 반환한다|GET|/subscribe|
|구독하기|유저의 질병 구독 여부를 저장한다|POST|/subscribe|
|구독하기|유저가 질병 구독을 취소한다| 	DELETE|	/subscribe|
|구독하기|유저 아이디에 해당하는 구독 정보를 반환한다|	GET|/subscribe/user|
|신고하기|신고한다 생성|POST|/subscribe/user|
|관리자 기능|신고 많이 받은 순으로 게시글 반환|POST|/admin/post|
|관리자 기능|게시글 숨김|	POST|/admin/post/{posts_id}|
|관리자 기능|게시글 삭제|	DELETE|/admin/post/{posts_id}|
|관리자 기능|신고 많이 받은 순으로 유저 반환|POST|/admin/user|
|관리자 기능|계정사용정지|POST|/admin/user/disable|
|관리자 기능|유저가 신고받은 수를 반환한다.|GET|/admin/police/user/{user_id}|
|관리자 기능|게시글에 해당하는 신고한다 수를 반환한다|GET|/admin/police/post/{posts_id}|
|관리자 기능|게시글에 해당하는 모든 신고한다를 반환|GET|	/admin/police/post/{posts_id}|
|관리자 기능|유저에 해당하는 모든 신고 자료를 반환한다|GET|	/admin/police/users/{user_id}|
|관리자 기능|모든 의사를 반환한다|GET|/admin/doctor-auth|
|관리자 기능|체크하지 않은 사람들을 반환한다|GET|/admin/doctor-auth/check|
|관리자 기능|의사 결정|PUT|/admin/doctor-auth|
|의사신청|의사 정보를 신청한다|POST|/doctor/register|
|의사신청|내 신청 결과를 반환한다|GET|/doctor/result|



## Browser Support



|<img src="https://is2-ssl.mzstatic.com/image/thumb/Purple114/v4/bb/72/49/bb72493a-6ede-8369-5513-f1e9f930b642/AppIcon-0-1x_U007emarketing-0-6-0-0-85-220.png/246x0w.png" width="80px"> |<img src = "https://zdnet4.cbsistatic.com/hub/i/2019/01/29/f4eba7da-10cd-4b45-b974-464c08e145be/microsoft-edge.png" width="80px"></img>|<img src = "https://t1.daumcdn.net/cfile/tistory/253EC1495495846E1C" width = "80px"></img>|
|--|--|--|
|latest✔|latest✔|latest✔|



# 👀 서비스 설명 

<img src = "https://ifh.cc/g/7toxt5.png"> </img>

<img src = "https://ifh.cc/g/YgIern.png" width=200></img>



​															로딩 화면 - 로그인으로 넘어가기 전에 처음으로 보여주는 페이지 

<img src = "https://ifh.cc/g/3trgSh.png" width=200></img>



​														로그인 화면 - 저장된 계정을 가지고 이메일과 패스워드를 입력받는 페이지

<img src = "https://ifh.cc/g/0Dwy2q.png" width=200></img>



​														회원가입 화면 - 계정을 회원가입 할 수 있는 탭으로 중복여부도 알려준다 

<img src = "https://ifh.cc/g/krQNkD.png" width=200></img>



​														비밀번호 찾기 탭 - 임의로 재생성된 비밀번호를 이메일로 보내주고 프로필 변경에서                                                           																									비밀번호 수정이 가능하다 



<img src = "https://ifh.cc/g/wyYLNK.png" width=200></img>



​															검색 화면 - 사용자를 검색할 수 있는 화면으로 누를 시 상대방 프로필에 들어가짐



<img src = "https://ifh.cc/g/N1KFV2.png" width=200></img>



​														검색 화면 - 질병을 검색할 수 있는 화면으로 질병 팔로우가 가능하다



<img src = "https://ifh.cc/g/CXjvzh.png" width=200></img>



​										메인 피드 - 메인 화면으로 팔로우한 사람들의 게시글들을 보여주는 화면으로 건강해요, 댓글, 스크랩,                                        																							(자신의 게시글일 경우) 수정 가능



<img src = "https://ifh.cc/g/KkAj2q.png" width=200></img>



​														 Q&A 게시판 -  Q&A 게시판으로 질문하고 답변할 수 있는 게시판



<img src = "https://ifh.cc/g/jXQjyX.png" width=200></img>



​																뉴스 검색 -  키워드로 검색을 하면 뉴스를 검색해줌



<img src = "https://ifh.cc/g/3XiLyR.png" width=200></img>



​																	게시글 작성 - 게시글을 작성할 수 있는 화면으로 이미지 업로드가 가능하고 태그들을 게시글에                       																								            지정할 수 있다. 



<img src = "https://ifh.cc/g/5jyv4i.png" width=200></img>



​														알림 요청 - 팔로잉을 했을 경우 & 팔로잉 결과를 알려줄 때 알람에서 팔로잉을 받았을 때는 요청탭으로                       																								요청이 와서 승낙 혹은 거절을 결정할 수 있다



<img src = "https://ifh.cc/g/qslUUj.png" width=200></img>



​    								메인 프로필 화면 - 메인 프로필 화면으로 내 정보를 보여주는 화면으로 내가 쓴 게시글, 나의 스크랩 등도 볼 수 있다.



<img src = "https://ifh.cc/g/4Aqdco.png" width=200></img>



​										프로필 변경 - 자신의 프로필을 변경하는 탭으로 오른쪽 키 버튼을 누르면 비밀번호 변경 역시 가능하다 



<img src = "https://ifh.cc/g/6O3kxc.png" width=200></img>



​										비밀번호 변경 - 자신의 비밀번호를 변경하는 탭으로 의사인증과 회원 탈퇴가 가능한 탭이다 



<img src = "https://ifh.cc/g/eS4lLU.png" width=200></img>



​										의사 인증 - 의사를 인증받을 수 있는 탭으로 사진을 통해 의사 인증 신청이 가능하다. 


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



본 가이드는 [MIT](https://choosealicense.com/licenses/mit/) 라이선스 하에 공개합니다 <img src = "https://img.shields.io/badge/license-MIT-pink" width=80></img>

* 공유 - 복제, 배포, 포맷 변경, 전송, 전시, 공연, 방송할 수 있습니다.
* 변경 - 리믹스, 변형, 2차적 저작물 작성 및 영리목적의 이용이 가능합니다. 


