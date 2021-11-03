INSERT INTO users(userid, filenumber, name, surname, username, email, password, isadmin)
VALUES (1337, 1, 'Matias', 'Pavan', 'mpavan', 'mpavan@itba.edu.ar', 'top_secret', true);

INSERT INTO roles(roleid, rolename)
VALUES (1, 'Yoda');

INSERT INTO subjects(subjectid, code, subjectname)
VALUES (1, 'A22', 'Star Wars');

INSERT INTO courses(courseid, subjectid, quarter, board, year)
VALUES (1, 1, 2, 'S', 2021);

INSERT INTO user_to_course(courseid, userid, roleid)
VALUES (1, 1337, 1);