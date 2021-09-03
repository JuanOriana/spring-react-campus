CREATE TABLE IF NOT EXISTS  teachers (
                id IDENTITY PRIMARY KEY,
                name varchar(50),
                surname varchar (50),
                email varchar (50),
                username varchar(50),
                password varchar (50) );

CREATE TABLE IF NOT EXISTS  courses (
    subjectId IDENTITY PRIMARY KEY,
    name varchar (50),
    code varchar(50),
    quarter INTEGER ,
    board varchar(50),
    year INTEGER,
     UNIQUE(code,quarter,board,year));

CREATE TABLE IF NOT EXISTS  announcements (
                      announcementId IDENTITY PRIMARY KEY ,
                      teacherId INTEGER,
                      courseId INTEGER,
                      title varchar (50),
                      content varchar(50) ,
                      date DATE,
                      FOREIGN KEY (teacherId) references teachers,
                      FOREIGN KEY (courseId) references courses ON DELETE CASCADE);
