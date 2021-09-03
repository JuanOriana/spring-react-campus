CREATE TABLE IF NOT EXISTS  teachers (
                id INTEGER PRIMARY KEY,
                name varchar(50),
                surname varchar (50),
                email varchar (50),
                username varchar(50),
                password varchar (50) );

CREATE TABLE IF NOT EXISTS  courses (
                subjectId INTEGER PRIMARY KEY,
                name varchar (50),
                code varchar(50),
                quarter INTEGER ,
                board varchar(50),
                year INTEGER );

CREATE TABLE IF NOT EXISTS  announcements (
               announcementId INTEGER,
               teacherId INTEGER,
               subjectId INTEGER,
               title varchar (50),
               content varchar,
               date DATE,
               PRIMARY KEY(announcementId, teacherId, subjectId) );

CREATE TABLE IF NOT EXISTS  students (
                id INTEGER PRIMARY KEY,
                name varchar(50),
                surname varchar (50),
                email varchar (50),
                username varchar(50),
                password varchar (50) );

CREATE TABLE IF NOT EXISTS  timetables (
                course_id INTEGER FOREIGN KEY,
                day_of_week INTEGER,
                beginning INTEGER,
                duration INTEGER );
