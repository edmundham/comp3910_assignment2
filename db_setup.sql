drop database if exists schedule;
CREATE DATABASE schedule;
drop user if exists 'admin_user'@'localhost';
drop user if exists 'admin_user'@'%';
CREATE USER 'admin_user'@'localhost' IDENTIFIED BY 'admin_user';
CREATE USER 'admin_user'@'%' IDENTIFIED BY 'admin_user';
GRANT ALL ON schedule.* TO 'admin_user'@'localhost';
GRANT ALL ON schedule.* TO 'admin_user'@'%';
USE schedule;

DROP TABLE IF EXISTS Employee;
CREATE TABLE Employee(
    employeeid integer auto_increment primary key,
    name varchar(256),
    username varchar(256),
    password varchar(256),
    createddate timestamp,
    isadmin boolean
);
INSERT INTO Employee (name, username, password, createddate, isadmin) 
    VALUES('Test Admin', 'admin', 'admin', now(), 1);
INSERT INTO Employee (name, username, password, createddate, isadmin) 
    VALUES('Cameron Lay', 'Cam.Lay123', 'Cam.Lay123', now(), 0);
INSERT INTO Employee (name, username, password, createddate, isadmin) 
    VALUES('Edmund Ham', 'Edmund.Ham123', 'Edmund.Ham123', now(), 0);
INSERT INTO Employee (name, username, password, createddate, isadmin) 
    VALUES('Danny Di Iorio', 'Danny.Di.Iorio123', 'Danny.Di.Iorio123', now(), 0);
INSERT INTO Employee (name, username, password, createddate, isadmin) 
    VALUES('Tony Pacheco', 'Tony.Pacheco123', 'Tony.Pacheco123', now(), 0);
INSERT INTO Employee (name, username, password, createddate, isadmin) 
    VALUES('Bruce Link', 'brucelink', 'brucelink', now(), 0);
INSERT INTO Employee (name, username, password, createddate, isadmin) 
    VALUES('Trevor Lord', 'Trevor.Lord123', 'Trevor.Lord123', now(), 0);

drop table if exists timesheet;
create table timesheet(
    timesheetid integer auto_increment primary key,
    employeeid integer,
    endweek timestamp,
    startweek timestamp
);
insert into timesheet (employeeid, endweek, startweek)
    values(1,
           (SELECT TIMESTAMP('2018-11-02')),
           (select timestamp('2018-11-08'))
           );
insert into timesheet (employeeid, endweek, startweek)
    values(1,
		   (SELECT TIMESTAMP('2018-11-09')),
           (select timestamp('2018-11-15'))
           );
insert into timesheet (employeeid, endweek, startweek)
    values(2,
		   (SELECT TIMESTAMP('2018-11-02')),
           (select timestamp('2018-11-08'))
           );
insert into timesheet (employeeid, endweek, startweek)
    values(2,
		   (SELECT TIMESTAMP('2018-11-09')),
           (select timestamp('2018-11-15'))
           );
insert into timesheet (employeeid, endweek, startweek)
    values(3,
           (SELECT TIMESTAMP('2018-11-02')),
           (select timestamp('2018-11-08'))
           );
insert into timesheet (employeeid, endweek, startweek)
    values(3,
           (SELECT TIMESTAMP('2018-11-09')),
           (select timestamp('2018-11-15'))
           );
insert into timesheet (employeeid, endweek, startweek)
    values(4,
		   (SELECT TIMESTAMP('2018-11-02')),
           (select timestamp('2018-11-08'))
           );
insert into timesheet (employeeid, endweek, startweek)
    values(4,
		   (SELECT TIMESTAMP('2018-11-09')),
           (select timestamp('2018-11-15'))
           );
insert into timesheet (employeeid, endweek, startweek)
    values(5,
		   (SELECT TIMESTAMP('2018-11-02')),
           (select timestamp('2018-11-08'))
           );
insert into timesheet (employeeid, endweek, startweek)
    values(5,
		   (SELECT TIMESTAMP('2018-11-09')),
           (select timestamp('2018-11-15'))
           );

DROP TABLE IF EXISTS TimesheetRow;
CREATE TABLE timesheetrow(
    timesheetrowid integer auto_increment primary key,
    timesheetid integer,
    projectid integer,
    workpackage varchar(256),
    monday integer,
    tuesday integer,
    wednesday integer,
    thursday integer,
    friday integer,
    saturday integer,
    sunday integer,
    notes varchar(256)
);
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(1, 1, 'wp1', 5, 2, 3, 7, 9, 0, 0, 'Made database working');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(1, 2, 'wp1', 8, 8, 8, 7, 9, 0, 0, 'UX/UI design');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(1, 3, 'wp1', 8, 8, 8, 10, 6, 0, 0, 'Set up model classes');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(1, 1, 'wp2', 7, 9, 8, 8, 8, 0, 0, 'Set up controllers');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(1, 2, 'wp2', 9, 9, 9, 9, 9, 0, 0, 'Final presentation');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(2, 1, 'wp1', 5, 2, 3, 7, 9, 0, 0, 'Made database working');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(2, 2, 'wp1', 8, 8, 8, 7, 9, 0, 0, 'UX/UI design');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(2, 3, 'wp1', 8, 8, 8, 10, 6, 0, 0, 'Set up model classes');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(2, 1, 'wp2', 7, 9, 8, 8, 8, 0, 0, 'Set up controllers');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(2, 2, 'wp2', 9, 9, 9, 9, 9, 0, 0, 'Final presentation');

insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(3, 1, 'wp1', 5, 2, 3, 7, 9, 0, 0, 'UX/UI design');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(3, 2, 'wp1', 7, 7, 7, 7, 9, 0, 0, 'Knowledge transfer to team in London');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(3, 3, 'wp1', 8, 6, 6, 7, 9, 0, 0, 'Set up development environment');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(3, 1, 'wp2', 9, 7, 5, 4, 4, 0, 0, 'Final presentation');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(3, 2, 'wp2', 5, 5, 5, 4, 4, 0, 0, 'Selling');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(4, 1, 'wp1', 5, 2, 3, 7, 9, 0, 0, 'UX/UI design');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(4, 2, 'wp1', 7, 7, 7, 7, 9, 0, 0, 'Knowledge transfer to team in London');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(4, 3, 'wp1', 8, 6, 6, 7, 9, 0, 0, 'Set up development environment');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(4, 1, 'wp2', 9, 7, 5, 4, 4, 0, 0, 'Final presentation');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(4, 2, 'wp2', 5, 5, 5, 4, 4, 0, 0, 'Selling');

insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(5, 1, 'wp1', 5, 2, 3, 7, 9, 0, 0, 'UX/UI design');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(5, 2, 'wp1', 7, 7, 7, 7, 9, 0, 0, 'Knowledge transfer to team in London');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(5, 3, 'wp1', 8, 6, 6, 7, 9, 0, 0, 'Set up development environment');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(5, 1, 'wp2', 9, 7, 5, 4, 4, 0, 0, 'Final presentation');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(5, 2, 'wp2', 5, 5, 5, 4, 4, 0, 0, 'Selling');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(6, 1, 'wp1', 5, 2, 3, 7, 9, 0, 0, 'UX/UI design');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(6, 2, 'wp1', 7, 7, 7, 7, 9, 0, 0, 'Knowledge transfer to team in London');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(6, 3, 'wp1', 8, 6, 6, 7, 9, 0, 0, 'Set up development environment');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(6, 1, 'wp2', 9, 7, 5, 4, 4, 0, 0, 'Final presentation');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(6, 2, 'wp2', 5, 5, 5, 4, 4, 0, 0, 'Selling');

insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(7, 1, 'wp1', 5, 2, 3, 7, 9, 0, 0, 'UX/UI design');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(7, 2, 'wp1', 7, 7, 7, 7, 9, 0, 0, 'Knowledge transfer to team in London');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(7, 3, 'wp1', 8, 6, 6, 7, 9, 0, 0, 'Set up development environment');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(7, 1, 'wp2', 9, 7, 5, 4, 4, 0, 0, 'Final presentation');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(7, 2, 'wp2', 5, 5, 5, 4, 4, 0, 0, 'Selling');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(8, 1, 'wp1', 5, 2, 3, 7, 9, 0, 0, 'UX/UI design');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(8, 2, 'wp1', 7, 7, 7, 7, 9, 0, 0, 'Knowledge transfer to team in London');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(8, 3, 'wp1', 8, 6, 6, 7, 9, 0, 0, 'Set up development environment');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(8, 1, 'wp2', 9, 7, 5, 4, 4, 0, 0, 'Final presentation');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(8, 2, 'wp2', 5, 5, 5, 4, 4, 0, 0, 'Selling');

insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(9, 1, 'wp1', 5, 2, 3, 7, 9, 0, 0, 'UX/UI design');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(9, 2, 'wp1', 7, 7, 7, 7, 9, 0, 0, 'Knowledge transfer to team in London');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(9, 3, 'wp1', 8, 6, 6, 7, 9, 0, 0, 'Set up development environment');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(9, 1, 'wp2', 9, 7, 5, 4, 4, 0, 0, 'Final presentation');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(9, 2, 'wp2', 5, 5, 5, 4, 4, 0, 0, 'Selling');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(10, 1, 'wp1', 5, 2, 3, 7, 9, 0, 0, 'UX/UI design');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(10, 2, 'wp1', 7, 7, 7, 7, 9, 0, 0, 'Knowledge transfer to team in London');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(10, 3, 'wp1', 8, 6, 6, 7, 9, 0, 0, 'Set up development environment');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(10, 1, 'wp2', 9, 7, 5, 4, 4, 0, 0, 'Final presentation');
insert into timesheetrow (timesheetid, projectid, workpackage, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes)
    values(10, 2, 'wp2', 5, 5, 5, 4, 4, 0, 0, 'Selling');