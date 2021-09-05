CREATE TABLE IF NOT EXISTS subjects
(
    subjectId SERIAL PRIMARY KEY,
    code     varchar(50),
    subjectName     varchar(50)
    );

CREATE TABLE IF NOT EXISTS courses
(
    courseId SERIAL PRIMARY KEY,
    subjectId INTEGER,
    quarter  INTEGER,
    board    varchar(50),
    year     INTEGER,
    UNIQUE (subjectId, quarter, board, year),
    FOREIGN KEY (subjectId) REFERENCES subjects ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS users
(
    userId SERIAL PRIMARY KEY,
    fileNumber INTEGER UNIQUE,
    name VARCHAR(50),
    surname VARCHAR(50),
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50),
    isAdmin BOOLEAN
    );

CREATE TABLE IF NOT EXISTS roles
(
    roleId SERIAL NOT NULL PRIMARY KEY,
    roleName VARCHAR(50) UNIQUE
    );

CREATE TABLE IF NOT EXISTS user_to_role
(
    userId INTEGER NOT NULL,
    roleId INTEGER NOT NULL,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (roleId) REFERENCES roles ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS  announcements (
                                              announcementId SERIAL PRIMARY KEY ,
                                              userId INTEGER,
                                              courseId INTEGER,
                                              title varchar (50),
    content varchar(50) ,
    date DATE,
    FOREIGN KEY (userId) references users ON DELETE CASCADE,
    FOREIGN KEY (courseId) references courses ON DELETE CASCADE
    );



