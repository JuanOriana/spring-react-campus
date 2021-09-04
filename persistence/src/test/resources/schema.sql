CREATE TABLE IF NOT EXISTS  teachers (
                id IDENTITY PRIMARY KEY,
                name varchar(50),
                surname varchar (50),
                email varchar (50),
                username varchar(50),
                password varchar (50) );

CREATE TABLE IF NOT EXISTS subjects
(
    subjectId IDENTITY PRIMARY KEY,
    code     varchar(50),
    name     varchar(50)
);

CREATE TABLE IF NOT EXISTS courses
(
    courseId IDENTITY PRIMARY KEY,
    subjectId INTEGER,
    quarter  INTEGER,
    board    varchar(50),
    year     INTEGER,
    UNIQUE (subjectId, quarter, board, year),
    FOREIGN KEY (subjectId) REFERENCES subjects ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS  announcements (
                      announcementId IDENTITY PRIMARY KEY ,
                      teacherId INTEGER,
                      courseId INTEGER,
                      title varchar (50),
                      content varchar(50) ,
                      date DATE,
                      FOREIGN KEY (teacherId) references teachers ON DELETE CASCADE ,
                      FOREIGN KEY (courseId) references courses ON DELETE CASCADE);
