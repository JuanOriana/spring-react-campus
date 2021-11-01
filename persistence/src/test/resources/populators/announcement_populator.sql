INSERT INTO subjects(subjectid, code, subjectname)
VALUES (1, 'A22', 'Star Wars');

INSERT INTO courses(courseid, subjectid, quarter, board, year)
VALUES (1, 1, 1, 'S1', 2021);

INSERT INTO users(userid, filenumber, name, surname, username, email, password, isadmin)
VALUES (1, 1, 'Matias', 'Pavan', 'mpavan', 'mpavan@itba.edu.ar', 'top_secret', true);

INSERT INTO announcements(announcementid, userid, courseid, title, content, date)
VALUES (2, 1, 1, 'test_title', 'test_content', '2021-09-26 21:14:35.138');

INSERT INTO roles(roleid, rolename)
VALUES (1, 'Yoda');

INSERT INTO user_to_course(courseid, userid, roleid)
VALUES (1, 1, 1);