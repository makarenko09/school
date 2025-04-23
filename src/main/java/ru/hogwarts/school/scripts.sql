select * from student;

select * from student as s
where s.age > 43 and s.age < 50;

select s.name from student as s;

select * from student as s
where name like '%P%';

select * from student as s
where s.age < s.id;

select * from student as s
order by age;