CREATE TABLE IF NOT EXISTS teachers
(
    id       SERIAL PRIMARY KEY,
    name     varchar(50),
    surname  varchar(50),
    email    varchar(50),
    username varchar(50),
    password varchar(50),
    UNIQUE (email,username)
);

CREATE TABLE IF NOT EXISTS subjects
(
    subjectId SERIAL PRIMARY KEY,
    code     varchar(50),
    name     varchar(50)
);

CREATE TABLE IF NOT EXISTS courses
(
    courseId SERIAL PRIMARY KEY,
    subjectId INTEGER,
    quarter  INTEGER,
    board    varchar(50),
    year     INTEGER,
    UNIQUE (subjectId, quarter, board, year),
    FOREIGN KEY(subjectId) REFERENCES subjects ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS  students (
                id INTEGER PRIMARY KEY,
                name varchar(50),
                surname varchar (50),
                email varchar (50),
                username varchar(50),
                password varchar (50) );

CREATE TABLE IF NOT EXISTS  timetables (
                courseId INTEGER,
                dayOfWeek INTEGER,
                beginning INTEGER,
                duration INTEGER,
                FOREIGN KEY (courseId) REFERENCES courses ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS announcements
(
    announcementId SERIAL PRIMARY KEY,
    teacherId      INTEGER,
    courseId       INTEGER,
    title          varchar(50),
    content        TEXT,
    date           DATE,
    FOREIGN KEY (teacherId) REFERENCES teachers ON DELETE CASCADE,
    FOREIGN KEY (courseId) REFERENCES courses ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS coursesroles
(
    teacherId   INTEGER,
    courseId   INTEGER,
    rol         TEXT,
    FOREIGN KEY (teacherId) references teachers ON DELETE CASCADE ON UPDATE RESTRICT ,
    FOREIGN KEY (courseId) references courses ON DELETE CASCADE ON UPDATE RESTRICT,
    PRIMARY KEY (teacherId,courseId)
);
