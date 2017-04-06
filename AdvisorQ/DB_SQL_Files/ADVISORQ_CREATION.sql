create database AdvisorQ;
use AdvisorQ;
create table ADVISOR_DETAILS(
ADVISOR_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
NAME VARCHAR(25) NOT NULL,
FROM_TIME time NOT NULL,
TO_TIME TIME NOT NULL,
DAYS VARCHAR(10) NOT NULL,
LOCATION VARCHAR(10) NOT NULL);

CREATE TABLE ADVISOR_LIST
(
ADVISOR_ID INTEGER,
CURRENT_TOKEN INTEGER NOT NULL,
foreign key (ADVISOR_ID) REFERENCES ADVISOR_DETAILS(ADVISOR_ID)
);

CREATE table ADVISOR_AUTHENTICATION(
_USERNAME VARCHAR(10) PRIMARY KEY,
_PASSWORD VARCHAR(10)
);

CREATE TABLE STUDENT_DETAIL(
NAME VARCHAR(25) NOT NULL,
UTA_ID VARCHAR(20) PRIMARY KEY,
EMAIL_ID VARCHAR(100) UNIQUE,
MOBILE_NUMBER varchar(25) UNIQUE,
GCM_REGISTRATION_ID VARCHAR(255)
);

CREATE TABLE SCHEDULE_LIST(
UTA_ID VARCHAR(20),
ADVISOR_ID INTEGER,
SCHEDULE_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
REASON VARCHAR(100) NOT NULL,
STATUS VARCHAR(10) NOT NULL,
TOKEN_NUMBER INTEGER NOT NULL,
FOREIGN KEY (UTA_ID) REFERENCES STUDENT_DETAIL(UTA_ID),
FOREIGN KEY (ADVISOR_ID) REFERENCES ADVISOR_DETAILS(ADVISOR_ID)
 );

CREATE TABLE MISSED_QUEUE(
UTA_ID VARCHAR(20),
SCHEDULE_ID INTEGER,
MISSED_ID INTEGER PRIMARY KEY,
FOREIGN KEY (UTA_ID) REFERENCES STUDENT_DETAIL(UTA_ID),
FOREIGN KEY (SCHEDULE_ID) REFERENCES SCHEDULE_LIST(SCHEDULE_ID)
);

CREATE TABLE COMPLETED(
UTA_ID VARCHAR(20),
ADVISOR_ID INTEGER,
REASON VARCHAR(100) NOT NULL,
FOREIGN KEY (UTA_ID) REFERENCES STUDENT_DETAIL(UTA_ID)
);

