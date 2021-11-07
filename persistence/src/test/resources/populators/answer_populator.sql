INSERT INTO subjects(subjectid, code, subjectname)
VALUES (1, 'A22', 'Star Wars');

INSERT INTO courses(courseid, subjectid, quarter, board, year)
VALUES (1, 1, 2, 'S', 2021);

INSERT INTO users(userid, filenumber, name, surname, username, email, password, isadmin)
VALUES (1337, 1, 'Santiago', 'Garcia', 'sangarcia', 'sangarcia@itba.edu.ar', 'top_secret', true);

INSERT INTO users(userid, filenumber, name, surname, username, email, password, isadmin)
VALUES (1338, 2, 'Profesor', 'Garcia', 'profegarcia', 'profegarcia@itba.edu.ar', 'top_secret', true);

INSERT INTO roles(roleid, rolename)
VALUES (1, 'Student');

INSERT INTO roles(roleid, rolename)
VALUES (2, 'Teacher');


INSERT INTO user_to_course(courseid, userid, roleid)
VALUES (1, 1338, 2);

INSERT INTO user_to_course(courseid, userid, roleid)
VALUES (1, 1337, 1);

INSERT INTO file_categories(categoryid, categoryname)
VALUES (2, 'exam');

INSERT INTO file_extensions(fileextensionid, fileextension)
VALUES (2, 'other');

INSERT INTO file_extensions(fileextensionid, fileextension)
VALUES (1, 'pdf');

INSERT INTO files(fileid, filesize, filename, filedate, file, fileextensionid, courseid, downloads, hidden)
VALUES (1337, 1000, 'test.png', '2021-09-26 21:14:35.138', null, 1, 1, 1, false);

INSERT INTO files(fileid, filesize, filename, filedate, file, fileextensionid, courseid, downloads, hidden)
VALUES (1338, 1000, 'test2.png', '2021-09-26 21:14:35.138', null, 1, 1, 1, false);

INSERT INTO category_file_relationship(categoryid, fileid)
VALUES (2, 1337);

INSERT INTO category_file_relationship(categoryid, fileid)
VALUES (2, 1338);
--
INSERT INTO exams(examId,courseId,startTime,endTime,title,description)
VALUES (1,1,'2021-09-26 21:14:35.138','2021-09-26 22:14:35.138','Exam title','Exam description');

INSERT INTO exams(examId,courseId,startTime,endTime,title,description)
VALUES (2,1,'2021-09-26 21:15:35.138','2021-09-26 23:14:35.138','Exam title','Exam description');


INSERT INTO answers(answerId,examId,deliveredDate,studentId)
VALUES (1,1,'2021-09-26 21:14:35.138',1337);

INSERT INTO answers(answerId,examId,deliveredDate,studentId,teacherId,score)
VALUES (2,2,'2021-09-26 21:14:35.138',1337,1338,10);

