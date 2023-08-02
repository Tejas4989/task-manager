CREATE SCHEMA if not exists task_manager AUTHORIZATION sa;
-- cant' use user table as it's a reserved word in h2 hence using users
CREATE TABLE if not exists task_manager.users (
    id int generated always as identity,
    name varchar(255) not null,
    constraint pk_user_id primary key (id)
);
CREATE TABLE if not exists task_manager.task (
    id int generated always as identity,
    title varchar(255) not null,
    description varchar(255),
    due_date DATE not null,
    completed_date DATE,
    status varchar(50),
    priority varchar(50),
    progress_percentage int,
    user_id int,
    constraint pk_task_id primary key (id),
    constraint fk_user_id foreign key (user_id) references task_manager.users(id)
);
insert into task_manager.users (name)
values ('User 1'),
       ('User 2'),
       ('User 3'),
       ('User 4'),
       ('User 5');
insert into task_manager.task (title, description, due_date, completed_date, status, priority, progress_percentage, user_id)
values ('Task 1', 'Description 1', '2024-01-01', null, 'NEW', 'LOW', 0, null),
       ('Task 2', 'Description 2', '2024-01-01', null, 'NEW', 'HIGH', 0, null),
       ('Task 2.1', 'Description 2.1', '2024-01-01', null, 'NEW', 'HIGH', 0, null),
       ('Task 3', 'Description 3', '2024-01-03', null, 'NEW', 'LOW', 0, null),
       ('Task 4', 'Description 4', '2024-01-04', null, 'NEW', 'MEDIUM', 0, null),
       ('Task 5', 'Description 5', '2024-01-05', null, 'COMPLETED', 'LOW', 0, null);
