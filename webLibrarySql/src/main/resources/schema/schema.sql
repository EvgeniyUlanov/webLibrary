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
create table if not exists acl_sid(
  id bigserial not null primary key,
  principal boolean not null,
  sid varchar(100) not null,
  constraint unique_uk_1 unique(sid,principal)
);

create table if not exists acl_class(
  id bigserial not null primary key,
  class varchar(100) not null,
  constraint unique_uk_2 unique(class)
);

create table if not exists acl_object_identity(
  id bigserial primary key,
  object_id_class bigint not null,
  object_id_identity bigint not null,
  parent_object bigint,
  owner_sid bigint,
  entries_inheriting boolean not null,
  constraint unique_uk_3 unique(object_id_class,object_id_identity),
  constraint foreign_fk_1 foreign key(parent_object) references acl_object_identity(id),
  constraint foreign_fk_2 foreign key(object_id_class) references acl_class(id),
  constraint foreign_fk_3 foreign key(owner_sid) references acl_sid(id)
);

create table if not exists acl_entry(
  id bigserial primary key,
  acl_object_identity bigint not null,
  ace_order int not null,
  sid bigint not null,
  mask integer not null,
  granting boolean not null,
  audit_success boolean not null,
  audit_failure boolean not null,
  constraint unique_uk_4 unique(acl_object_identity,ace_order),
  constraint foreign_fk_4 foreign key(acl_object_identity)
  references acl_object_identity(id),
  constraint foreign_fk_5 foreign key(sid) references acl_sid(id)
);