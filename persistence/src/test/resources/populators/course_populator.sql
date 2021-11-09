INSERT INTO subjects(subjectid, code, subjectname)
VALUES (1, 'A22', 'Star Wars');

INSERT INTO courses(courseid, subjectid, quarter, board, year)
VALUES (1, 1, 1, 'S1', 2021);

INSERT INTO users(userid, filenumber, name, surname, username, email, password, isadmin)
VALUES (1, 1, 'Matias', 'Pavan', 'mpavan', 'mpavan@itba.edu.ar', 'top_secret', false);

INSERT INTO users(userid, filenumber, name, surname, username, email, password, isadmin)
VALUES (2, 2, 'Matias', 'Pavan', 'mpavan2', 'mpavan2@itba.edu.ar', 'top_secret', false);

INSERT INTO users(userid, filenumber, name, surname, username, email, password, isadmin)
VALUES (3, 3, 'Santiago', 'Pavan', 'spavan', 'spavan@itba.edu.ar', 'top_secret', false);

INSERT INTO roles(roleid, rolename)
VALUES (1, 'Student');

INSERT INTO roles(roleid, rolename)
VALUES (3, 'Teacher');

INSERT INTO user_to_course(courseid, userid, roleid)
VALUES (1, 2, 1);

INSERT INTO user_to_course(courseid, userid, roleid)
VALUES (1, 1, 3);