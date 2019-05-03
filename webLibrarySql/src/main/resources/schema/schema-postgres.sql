DROP TABLE IF EXISTS Authors CASCADE;
DROP TABLE IF EXISTS Books CASCADE;
DROP TABLE IF EXISTS Genres CASCADE;
DROP TABLE IF EXISTS Authors_books;
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Roles CASCADE;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS acl_sid CASCADE;
DROP TABLE IF EXISTS acl_class CASCADE;
DROP TABLE IF EXISTS acl_entry CASCADE;
DROP TABLE IF EXISTS acl_object_identity CASCADE;

CREATE TABLE IF NOT EXISTS Authors (
  author_id SERIAL PRIMARY KEY,
  author_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Genres (
  genre_id SERIAL PRIMARY KEY,
  genre_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Books (
  book_id SERIAL PRIMARY KEY,
  book_name VARCHAR(255) NOT NULL UNIQUE,
  genre_id INT REFERENCES Genres(genre_id)
);

CREATE TABLE IF NOT EXISTS Authors_books (
  author_id INT NOT NULL REFERENCES Authors(author_id) ON DELETE CASCADE,
  book_id INT NOT NULL REFERENCES Books(book_id) ON DELETE CASCADE,
  UNIQUE (author_id, book_id)
);

CREATE TABLE IF NOT EXISTS Comments (
  comment_id SERIAL PRIMARY KEY,
  comment VARCHAR(255),
  book_id INT NOT NULL REFERENCES Books(book_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Users (
  user_id SERIAL PRIMARY KEY,
  user_name VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Roles (
  role_id SERIAL PRIMARY KEY,
  role VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users_roles (
  user_id INT NOT NULL REFERENCES Users(user_id) ON DELETE CASCADE,
  role_id INT NOT NULL REFERENCES Roles(role_id) ON DELETE CASCADE,
  UNIQUE (user_id, role_id)
);


-- acl


create table acl_sid(
    id bigserial not null primary key,
    principal boolean not null,
    sid varchar(100) not null,
    constraint unique_uk_1 unique(sid,principal)
);

create table acl_class(
    id bigserial not null primary key,
    class varchar(100) not null,
    constraint unique_uk_2 unique(class)
);

create table acl_object_identity(
    id bigserial primary key,
    object_id_class bigint not null,
    object_id_identity varchar(36) not null,
    parent_object bigint,
    owner_sid bigint,
    entries_inheriting boolean not null,
    constraint unique_uk_3 unique(object_id_class,object_id_identity),
    constraint foreign_fk_1 foreign key(parent_object)references acl_object_identity(id),
    constraint foreign_fk_2 foreign key(object_id_class)references acl_class(id),
    constraint foreign_fk_3 foreign key(owner_sid)references acl_sid(id)
);

create table acl_entry(
    id bigserial primary key,
    acl_object_identity bigint not null,
    ace_order int not null,
    sid bigint not null,
    mask integer not null,
    granting boolean not null,
    audit_success boolean not null,
    audit_failure boolean not null,
    constraint unique_uk_4 unique(acl_object_identity,ace_order),
    constraint foreign_fk_4 foreign key(acl_object_identity) references acl_object_identity(id),
    constraint foreign_fk_5 foreign key(sid) references acl_sid(id)
);



-- spring batch
-- CREATE TABLE BATCH_JOB_INSTANCE  (
--   JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
--   VERSION BIGINT ,
--   JOB_NAME VARCHAR(100) NOT NULL,
--   JOB_KEY VARCHAR(32) NOT NULL,
--   constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
-- ) ;
--
-- CREATE TABLE BATCH_JOB_EXECUTION  (
--   JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
--   VERSION BIGINT  ,
--   JOB_INSTANCE_ID BIGINT NOT NULL,
--   CREATE_TIME TIMESTAMP NOT NULL,
--   START_TIME TIMESTAMP DEFAULT NULL ,
--   END_TIME TIMESTAMP DEFAULT NULL ,
--   STATUS VARCHAR(10) ,
--   EXIT_CODE VARCHAR(2500) ,
--   EXIT_MESSAGE VARCHAR(2500) ,
--   LAST_UPDATED TIMESTAMP,
--   JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
--   constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
--   references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
-- ) ;
--
-- CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
--   JOB_EXECUTION_ID BIGINT NOT NULL ,
--   TYPE_CD VARCHAR(6) NOT NULL ,
--   KEY_NAME VARCHAR(100) NOT NULL ,
--   STRING_VAL VARCHAR(250) ,
--   DATE_VAL TIMESTAMP DEFAULT NULL ,
--   LONG_VAL BIGINT ,
--   DOUBLE_VAL DOUBLE PRECISION ,
--   IDENTIFYING CHAR(1) NOT NULL ,
--   constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
--   references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
-- ) ;
--
-- CREATE TABLE BATCH_STEP_EXECUTION  (
--   STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
--   VERSION BIGINT NOT NULL,
--   STEP_NAME VARCHAR(100) NOT NULL,
--   JOB_EXECUTION_ID BIGINT NOT NULL,
--   START_TIME TIMESTAMP NOT NULL ,
--   END_TIME TIMESTAMP DEFAULT NULL ,
--   STATUS VARCHAR(10) ,
--   COMMIT_COUNT BIGINT ,
--   READ_COUNT BIGINT ,
--   FILTER_COUNT BIGINT ,
--   WRITE_COUNT BIGINT ,
--   READ_SKIP_COUNT BIGINT ,
--   WRITE_SKIP_COUNT BIGINT ,
--   PROCESS_SKIP_COUNT BIGINT ,
--   ROLLBACK_COUNT BIGINT ,
--   EXIT_CODE VARCHAR(2500) ,
--   EXIT_MESSAGE VARCHAR(2500) ,
--   LAST_UPDATED TIMESTAMP,
--   constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
--   references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
-- ) ;
--
-- CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
--   STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
--   SHORT_CONTEXT VARCHAR(2500) NOT NULL,
--   SERIALIZED_CONTEXT TEXT ,
--   constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
--   references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
-- ) ;
--
-- CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
--   JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
--   SHORT_CONTEXT VARCHAR(2500) NOT NULL,
--   SERIALIZED_CONTEXT TEXT ,
--   constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
--   references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
-- ) ;
--
-- CREATE SEQUENCE BATCH_STEP_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
-- CREATE SEQUENCE BATCH_JOB_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
-- CREATE SEQUENCE BATCH_JOB_SEQ MAXVALUE 9223372036854775807 NO CYCLE;