INSERT INTO users(userid, filenumber, name, surname, username, email, password, isadmin)
VALUES (1, 41205221, 'Paw', '2021', 'paw2021', 'paw2021@itba.edu.ar', 'asd123', true);

INSERT INTO roles(roleid, rolename)
VALUES (1, 'Yoda');

INSERT INTO subjects(subjectid, code, subjectname)
VALUES (1, 'A22', 'Star Wars');

INSERT INTO courses(courseid, subjectid, quarter, board, year)
VALUES (1, 1, 2, 'S', 2021);

INSERT INTO user_to_course(courseid, userid, roleid)
VALUES (1, 1, 1);