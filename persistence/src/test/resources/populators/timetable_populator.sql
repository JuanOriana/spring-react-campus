INSERT INTO subjects(subjectid, code, subjectname)
VALUES (1, 'A22', 'Protos');

INSERT INTO courses(courseid, subjectid, quarter, board, year)
VALUES (1, 1, 1, 'S1', 2021);

INSERT INTO timetables(courseid,dayofweek,starttime,endtime)
VALUES(1,1,'12:00:00','20:00:00')