drop table if exists Question cascade;
drop sequence if exists Question_SEQ;
drop extension if exists vector;

create extension vector;

create sequence Question_SEQ start with 1 increment by 50;

create table Question (
        id bigint not null,
        round varchar(256),
        question varchar(65535),
        embedding vector(384),
        primary key (id));
------


