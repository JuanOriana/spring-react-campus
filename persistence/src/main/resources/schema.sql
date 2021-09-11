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

CREATE TABLE IF NOT EXISTS  announcements (
                                              announcementId SERIAL PRIMARY KEY ,
                                              userId INTEGER,
                                              courseId INTEGER,
                                              title varchar (50),
    content TEXT ,
    date TIMESTAMP,
    FOREIGN KEY (userId) references users ON DELETE CASCADE,
    FOREIGN KEY (courseId) references courses ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS  timetables (
                                           courseId INTEGER,
                                           dayOfWeek INTEGER,
                                           startTime TIME,
                                           endTime TIME,
                                           FOREIGN KEY (courseId) REFERENCES courses ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS user_to_course
(
    courseId INTEGER NOT NULL,
    userId INTEGER NOT NULL,
    roleId INTEGER NOT NULL,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (courseId) references courses ON DELETE CASCADE,
    FOREIGN KEY (roleId) references roles ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS file_categories
(
    categoryId SERIAL PRIMARY KEY,
    categoryName varchar (50) NOT NULL,
    UNIQUE (categoryName)
);

CREATE TABLE IF NOT EXISTS file_extensions (
    fileExtensionId SERIAL PRIMARY KEY,
    fileExtension varchar (3),
    UNIQUE (fileExtension)
);

CREATE TABLE IF NOT EXISTS files (
    fileId SERIAL PRIMARY KEY,
    fileSize INTEGER,
    categoryId INTEGER NOT NULL,
    fileName varchar(50),
    fileDate DATE,
    file BYTEA,
    fileExtensionId INTEGER,
    FOREIGN KEY (categoryId) references file_categories ON DELETE SET NULL,
    FOREIGN KEY (fileExtensionId) references file_extensions ON DELETE SET NULL
);



