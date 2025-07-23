CREATE USER jdbc IDENTIFIED BY jdbc;

GRANT CONNECT, RESOURCE TO jdbc;


-- 드랍문은 항상 위에. 그래야 스크립트 돌릴때 초기화하기 용이
DROP TABLE MEMBER;
DROP SEQUENCE SEQ_USERNO;

CREATE TABLE MEMBER(
    USERNO NUMBER PRIMARY KEY,                -- 회원번호
    USERID VARCHAR2(15) NOT NULL UNIQUE,      -- 회원아이디
    USERPWD VARCHAR2(15) NOT NULL,            -- 회원비밀번호
    USERNAME VARCHAR2(20) NOT NULL,           -- 회원이름
    GENDER CHAR(1) CHECK(GENDER IN ('M','F')),-- 성별
    AGE NUMBER,                               -- 나이
    EMAIL VARCHAR(30),                        -- 이메일
    PHONE CHAR(11), --짝대기X                  --전화번호
    ADDRESS VARCHAR(100),                     -- 주소
    HOBBY VARCHAR2(50),                       -- 취미
    ENROLLDATE DATE DEFAULT SYSDATE NOT NULL -- 회원가입일
);

CREATE SEQUENCE SEQ_USERNO
NOCACHE;

INSERT INTO MEMBER
VALUES (SEQ_USERNO.NEXTVAL, 'admin', '1234', '관리자' , 'M', 45, 'admin@kh.or.kr', '01012345555','서울',NULL,'2025-07-01');

INSERT INTO MEMBER
VALUES (SEQ_USERNO.NEXTVAL, 'user01', 'pass01', '차은우' , NULL , 25, 'user01@kh.or.kr', '01022221111','부산', '등산,영화보기','2025-07-03');

SELECT * FROM MEMBER;
COMMIT;

CREATE TABLE TEST(
    TNO NUMBER,
    TNAME VARCHAR2(20),
    TDATE DATE
);
SELECT * FROM TEST;

SELECT * FROM MEMBER;-




