
DROP TABLE IF EXISTS colleges CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS subjects CASCADE;
DROP TABLE IF EXISTS college_subject CASCADE;

CREATE TABLE colleges
(
    college_id   SERIAL NOT NULL PRIMARY KEY,
    college_name VARCHAR(255) NOT NULL,
    rating      double precision check ( rating >= 0 AND rating <= 10.0)
);

CREATE TABLE students
(
    student_id   SERIAL NOT NULL PRIMARY KEY,
    college_id   int    not null REFERENCES colleges (college_id) ON DELETE CASCADE,
    student_name VARCHAR(255)NOT NULL ,
    gpa         DOUBLE PRECISION not null check (gpa >= 0 AND gpa <= 10.0 )
);

CREATE TABLE subjects
(
    subject_id   SERIAL NOT NULL PRIMARY KEY,
    subject_name VARCHAR(255) not null
);

CREATE TABLE college_subject
(
    college_id int not null REFERENCES colleges (college_id) ON DELETE CASCADE,
    subject_id int not null REFERENCES subjects (subject_id) ON DELETE CASCADE,
    primary key (college_id,subject_id)
);

insert into colleges(college_name, rating)
values ('Technical College of Innovations', 10.0);
insert into colleges(college_name, rating)
values ('Humanitarian College of Progress', 9.8);
insert into colleges(college_name, rating)
values ('Engineering College of Perspectives', 8.0);
insert into colleges(college_name, rating)
values ('Artistic College of Creativity', 8.8);

insert into students(college_id, student_name, gpa)
values (1,'Alexander Ivanov', 10.0);
insert into students(college_id,student_name, gpa)
values (2,'Ekaterina Petrova', 9.9);
insert into students(college_id,student_name, gpa)
values (3,'Maxim Smirnov', 8.0);
insert into students(college_id,student_name, gpa)
values (4,'Anna Kozlova', 8.7);
insert into students(college_id,student_name, gpa)
values (4,'Dmitry Volkov', 4.0);
insert into students(college_id,student_name, gpa)
values (3,'Olga Morozova', 5.9);
insert into students(college_id,student_name, gpa)
values (1,'Pavel Vasilyev', 8.8);
insert into students(college_id,student_name, gpa)
values (2,'Natalia Kuznetsova', 7.6);
insert into students(college_id,student_name, gpa)
values (1,'Igor Belyakov', 1.0);
insert into students(college_id,student_name, gpa)
values (2,'Maria Solovyova', 0.0);
insert into students(college_id,student_name, gpa)
values (2,'Vladimir Kozlov', 7.9);
insert into students(college_id,student_name, gpa)
values (3,'Svetlana Kim', 9.9);
insert into students(college_id,student_name, gpa)
values (4,'Doja Cat', 9.8);
insert into students(college_id,student_name, gpa)
values (4,'Alisher Morgenshtern', 10.0);
insert into students(college_id,student_name, gpa)
values (4,'Gone Fladd', 10.0);

insert into subjects(subject_name)
values ('Mathematics');
insert into subjects(subject_name)
values ('Science');
insert into subjects(subject_name)
values ('History');
insert into subjects(subject_name)
values ('Geography');
insert into subjects(subject_name)
values ('English');
insert into subjects(subject_name)
values ('Art');
insert into subjects(subject_name)
values ('French');
insert into subjects(subject_name)
values ('Music');
insert into subjects(subject_name)
values ('Social studies');
insert into subjects(subject_name)
values ('Economics');
insert into subjects(subject_name)
values ('Computer science');
insert into subjects(subject_name)
values ('Psychology');

insert into college_subject(college_id, subject_id)
values (1, 1);
insert into college_subject(college_id, subject_id)
values (1, 11);
insert into college_subject(college_id, subject_id)
values (1, 2);
insert into college_subject(college_id, subject_id)
values (1, 5);
insert into college_subject(college_id, subject_id)
values (1, 10);

insert into college_subject(college_id, subject_id)
values (2, 2);
insert into college_subject(college_id, subject_id)
values (2, 3);
insert into college_subject(college_id, subject_id)
values (2, 4);
insert into college_subject(college_id, subject_id)
values (2, 9);
insert into college_subject(college_id, subject_id)
values (2, 12);

insert into college_subject(college_id, subject_id)
values (3, 1);
insert into college_subject(college_id, subject_id)
values (3, 2);
insert into college_subject(college_id, subject_id)
values (3, 4);
insert into college_subject(college_id, subject_id)
values (3, 5);
insert into college_subject(college_id, subject_id)
values (3, 10);
insert into college_subject(college_id, subject_id)
values (3, 11);

insert into college_subject(college_id, subject_id)
values (4, 2);
insert into college_subject(college_id, subject_id)
values (4, 3);
insert into college_subject(college_id, subject_id)
values (4, 5);
insert into college_subject(college_id, subject_id)
values (4, 6);
insert into college_subject(college_id, subject_id)
values (4, 8);




