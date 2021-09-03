CREATE TABLE IF NOT EXISTS  teachers (
                id INTEGER PRIMARY KEY,
                name varchar(50),
                surname varchar (50),
                email varchar (50),
                username varchar(50),
                password varchar (50) );

CREATE TABLE IF NOT EXISTS  courses (
                subjectId SERIAL PRIMARY KEY,
                name varchar (50),
                code varchar(50),
                quarter INTEGER ,
                board varchar(50),
                year INTEGER,
                UNIQUE(code,quarter,board,year));

        CREATE TABLE IF NOT EXISTS  announcement (
                announcementId INTEGER,
                teacherId INTEGER,
                subjectId INTEGER, 
                title varchar (50), 
                content TEXT ,
                date DATE, 
                PRIMARY KEY(announcementId, teacherId, subjectId) );