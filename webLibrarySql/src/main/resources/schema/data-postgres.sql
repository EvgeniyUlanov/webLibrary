INSERT INTO authors(author_name) VALUES ('Jack London');
INSERT INTO authors(author_name) VALUES ('Lev Tolstoy');
INSERT INTO authors(author_name) VALUES ('Aleksandr Pushkin');
INSERT INTO authors(author_name) VALUES ('Ilya Ilf');
INSERT INTO authors(author_name) VALUES ('Evgeniy Petrov');

INSERT INTO genres(genre_name) VALUES ('Comedy');
INSERT INTO genres(genre_name) VALUES ('Drama');
INSERT INTO genres(genre_name) VALUES ('Poem');

INSERT INTO books(book_name, genre_id)
  VALUES ('Sea Wolf', (SELECT genre_id FROM genres WHERE genre_name = 'Drama'));
INSERT INTO books(book_name, genre_id)
  VALUES ('The hearts of three', (SELECT genre_id FROM genres WHERE genre_name = 'Drama'));
INSERT INTO books(book_name, genre_id)
  VALUES ('War and peace', (SELECT genre_id FROM genres WHERE genre_name = 'Drama'));
INSERT INTO books(book_name, genre_id)
  VALUES ('Anna Karenina', (SELECT genre_id FROM genres WHERE genre_name = 'Drama'));
INSERT INTO books(book_name, genre_id)
  VALUES ('Evgeniy Onegin', (SELECT genre_id FROM genres WHERE genre_name = 'Poem'));
INSERT INTO books(book_name, genre_id)
  VALUES ('12 chairs', (SELECT genre_id FROM genres WHERE genre_name = 'Comedy'));
INSERT INTO books(book_name, genre_id)
  VALUES ('Golden calf', (SELECT genre_id FROM genres WHERE genre_name = 'Comedy'));

INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT author_id FROM authors WHERE author_name = 'Jack London'),
  (SELECT book_id FROM books WHERE book_name = 'Sea Wolf')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT author_id FROM authors WHERE author_name = 'Jack London'),
  (SELECT book_id FROM books WHERE book_name = 'The hearts of three')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT author_id FROM authors WHERE author_name = 'Lev Tolstoy'),
  (SELECT book_id FROM books WHERE book_name = 'War and peace')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT author_id FROM authors WHERE author_name = 'Lev Tolstoy'),
  (SELECT book_id FROM books WHERE book_name = 'Anna Karenina')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT author_id FROM authors WHERE author_name = 'Aleksandr Pushkin'),
  (SELECT book_id FROM books WHERE book_name = 'Evgeniy Onegin')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT author_id FROM authors WHERE author_name = 'Evgeniy Petrov'),
  (SELECT book_id FROM books WHERE book_name = '12 chairs')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT author_id FROM authors WHERE author_name = 'Ilya Ilf'),
  (SELECT book_id FROM books WHERE book_name = '12 chairs')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT author_id FROM authors WHERE author_name = 'Ilya Ilf'),
  (SELECT book_id FROM books WHERE book_name = 'Golden calf')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT author_id FROM authors WHERE author_name = 'Evgeniy Petrov'),
  (SELECT book_id FROM books WHERE book_name = 'Golden calf')
);

INSERT INTO users(user_name, password) VALUES (
  'admin', '$2a$10$xZuLs1qEyJe6s6VIa9yFyu8EUvWYJlJ.JP8Hn7F4I1UhVIC/iGrqe'  -- admin
);
INSERT INTO users(user_name, password) VALUES (
  'user', '$2a$10$zz14WvXPtq11ReCTFMR5u.9byVq6kuAhqVSyeOS6lavWBHcYxGzwy'  -- user
);

INSERT INTO roles(role) VALUES ('ROLE_ADMIN');
INSERT INTO roles(role) VALUES ('ROLE_USER');
INSERT INTO roles(role) VALUES ('ROLE_EDITOR');

INSERT INTO users_roles(user_id, role_id) VALUES (
  (SELECT user_id FROM users WHERE user_name = 'admin'),
  (SELECT role_id FROM roles WHERE role = 'ROLE_ADMIN')
);

INSERT INTO users_roles(user_id, role_id) VALUES (
  (SELECT user_id FROM users WHERE user_name = 'admin'),
  (SELECT role_id FROM roles WHERE role = 'ROLE_USER')
);

INSERT INTO users_roles(user_id, role_id) VALUES (
  (SELECT user_id FROM users WHERE user_name = 'user'),
  (SELECT role_id FROM roles WHERE role = 'ROLE_USER')
);

-- acl
INSERT INTO acl_class (id, class) VALUES
(1, 'ru.otus.domain.Book'),
(2, 'ru.otus.domain.Author'),
(3, 'ru.otus.domain.Genre');

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, true, 'admin'),
(2, true, 'user'),
(3, false, 'ROLE_ADMIN'),
(4, false, 'ROLE_USER');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, (SELECT book_id FROM books WHERE book_name = 'Sea Wolf'), NULL, 4, FALSE),
(2, 1, (SELECT book_id FROM books WHERE book_name = 'The hearts of three'), NULL, 4, FALSE),
(3, 1, (SELECT book_id FROM books WHERE book_name = 'War and peace'), NULL, 4, FALSE),
(4, 1, (SELECT book_id FROM books WHERE book_name = 'Anna Karenina'), NULL, 4, FALSE),
(5, 1, (SELECT book_id FROM books WHERE book_name = 'Evgeniy Onegin'), NULL, 4, FALSE),
(6, 1, (SELECT book_id FROM books WHERE book_name = '12 chairs'), NULL, 4, FALSE),
(7, 1, (SELECT book_id FROM books WHERE book_name = 'Golden calf'), NULL, 4, FALSE);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 1, 4, 1, TRUE, TRUE, TRUE),
(2, 2, 1, 4, 1, TRUE, TRUE, TRUE),
(3, 3, 1, 4, 1, TRUE, TRUE, TRUE),
(4, 4, 1, 4, 1, TRUE, TRUE, TRUE),
(5, 5, 1, 4, 1, TRUE, TRUE, TRUE),
(6, 6, 1, 4, 1, TRUE, TRUE, TRUE),
(7, 7, 1, 4, 1, TRUE, TRUE, TRUE);