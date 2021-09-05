CREATE TABLE IF NOT EXISTS teachers
(
    id       SERIAL PRIMARY KEY,
    name     varchar(50),
    surname  varchar(50),
    email    varchar(50),
    username varchar(50),
    password varchar(50),
    UNIQUE (email, username)
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
   startTime TIME,
   endTime TIME,
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

