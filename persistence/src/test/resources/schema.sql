CREATE TABLE IF NOT EXISTS teachers
(
    id IDENTITY PRIMARY KEY,
    name     varchar(50),
    surname  varchar(50),
    email    varchar(50),
    username varchar(50),
    password varchar(50),
    UNIQUE (email,username)
);

CREATE TABLE IF NOT EXISTS courses
(
    courseId IDENTITY PRIMARY KEY,
    name    varchar(50),
    code    varchar(50),
    quarter INTEGER,
    board   varchar(50),
    year    INTEGER,
    UNIQUE (code, quarter, board, year)
);

CREATE TABLE IF NOT EXISTS announcements
(
    announcementId IDENTITY PRIMARY KEY,
    teacherId INTEGER,
    courseId  INTEGER,
    title     varchar(50),
    content   varchar(50),
    date      DATE,
    FOREIGN KEY (teacherId) references teachers ON DELETE CASCADE,
    FOREIGN KEY (courseId) references courses ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS coursesroles
(
    teacherId   INTEGER,
    courseId   INTEGER,
    rol         varchar(50),
    FOREIGN KEY (teacherId) references teachers ON DELETE CASCADE ON UPDATE RESTRICT ,
    FOREIGN KEY (courseId) references courses ON DELETE CASCADE ON UPDATE RESTRICT,
    PRIMARY KEY (teacherId,courseId)
);