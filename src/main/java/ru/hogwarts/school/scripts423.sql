-- SQL-console 1
SELECT student.name, student.age, faculty.name
from student
         INNER JOIN faculty on student.faculty_id = faculty.id;
-- SQL-console 2
SELECT student.name, student.age
FROM avatar
         INNER JOIN student ON student.id = avatar.student_id;