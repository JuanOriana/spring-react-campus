CREATE TABLE IF NOT EXISTS  teachers (
                id SERIAL PRIMARY KEY,
                name varchar(50),
                surname varchar (50),
                email varchar (50),
                username varchar(50),
                password varchar (50) );

CREATE TABLE IF NOT EXISTS  courses (
                courseId SERIAL PRIMARY KEY,
                name varchar (50),
                code varchar(50),
                quarter INTEGER ,
                board varchar(50),
                year INTEGER,
                UNIQUE(code,quarter,board,year));

        CREATE TABLE IF NOT EXISTS  announcement (
                announcementId SERIAL,
                teacherId INTEGER,
                courseId INTEGER,
                title varchar (50), 
                content TEXT ,
                date DATE,
                FOREIGN KEY (teacherId) REFERENCES teachers ON DELETE CASCADE,
                FOREIGN KEY (courseId) REFERENCES courses ON DELETE CASCADE ,
                PRIMARY KEY(announcementId, teacherId, courseId) );