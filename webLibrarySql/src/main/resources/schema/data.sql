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
  'admin', 'admin'
);
INSERT INTO users(user_name, password) VALUES (
  'user', 'user'
);

INSERT INTO roles(role) VALUES ('ROLE_ADMIN');
INSERT INTO roles(role) VALUES ('ROLE_USER');

INSERT INTO users_roles(user_id, role_id) VALUES (
  (SELECT user_id FROM users WHERE user_name = 'admin'),
  (SELECT role_id FROM roles WHERE role = 'ROLE_ADMIN')
);

INSERT INTO users_roles(user_id, role_id) VALUES (
  (SELECT user_id FROM users WHERE user_name = 'user'),
  (SELECT role_id FROM roles WHERE role = 'ROLE_USER')
);
