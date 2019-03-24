$(document).ready(function () {
    $('#bookInfo_buttonAddComment').click(addComment);
    $('#bookInfo_buttonAddAuthor').click(addAuthorToBook);
    $('#buttonAddBook').click(addBook);
    fillBooksTable();
    fillAuthorsTable();
    fillGenresTable();
    $('.content').hide();
    $('#bookContainer').show();
});

function fillBooksTable() {
    fillBookTableWithRequest('book/all', {});
}

function fillBookTableWithRequest(url, data) {
    var bookTable = $('#bookTable');
    bookTable.find('.removable').remove();
    $.ajax({
        type: 'GET',
        url: url,
        data: data,
        success: function (response) {
            $.each(response, function (id, book) {
                var newTr = $('<tr>').addClass('removable').appendTo(bookTable);
                $('<td>').text(book.name).appendTo(newTr);
                $('<td>').text(book.genre.name).appendTo(newTr);
                $('<td>').text(book.authors[0].name).appendTo(newTr);
                var buttonDelete = $('<button>')
                    .text('delete')
                    .val(book.id)
                    .addClass('btn btn-primary deleteButton')
                    .click(buttonDeleteHandler);
                $('<td>').append(buttonDelete).appendTo(newTr);
                var buttonInfo = $('<button>')
                    .text('info')
                    .val(book.id)
                    .addClass('btn btn-primary infoButton')
                    .click(buttonInfoHandler);
                $('<td>').append(buttonInfo).appendTo(newTr);
            })
        },
        error: function () {
            alert('error');
        }
    });
}

function fillAuthorsTable() {
    fillAuthorsByRequest('author/getAll', {});
}

function fillAuthorsByRequest(url, data) {
    var authorTable = $('#authorContainer_authorTable');
    authorTable.find('.removable').remove();
    $('#authorContainer_message').find('h2').remove();
    $('#bookInfo_selectAuthor').find('.removable').remove();
    $('#bookPage_selectAuthor').find('.removable').remove();
    $.ajax({
        type: 'GET',
        url: url,
        data: data,
        success: function (response) {
            $.each(response, function (index, author) {
                $('#bookInfo_selectAuthor').append($('<option>').addClass('removable').val(author.name).text(author.name));
                $('#bookPage_selectAuthor').append($('<option>').addClass('removable').val(author.name).text(author.name));
                var tr = $('<tr>').addClass('removable').appendTo(authorTable);
                $('<td>').text(author.name).appendTo(tr);
            })
        },
        error: function () {
            $('#authorContainer_message').addClass('errorAction').append($('<h2>').text('author not found'));
        }
    })
}

function fillGenresTable() {
    var genreTable = $('#genreTable');
    genreTable.find('.removable').remove();
    $('#bookPage_selectGenre').find('.removable').remove();
    $.ajax({
        type: 'GET',
        url: 'genre/getAll',
        success: function (response) {
            $.each(response, function (index, genre) {
                $('#bookPage_selectGenre').append($('<option>').addClass('removable').val(genre.name).text(genre.name));
                var tr = $('<tr>').addClass('removable').appendTo(genreTable);
                $('<td>').text(genre.name).appendTo(tr);
            })
        }
    })
}

function buttonInfoHandler() {
    var bookId = $(this).val();
    getBookInfo(bookId);
}

function buttonDeleteHandler() {
    var bookId = $(this).val();
    $.ajax({
        type: 'DELETE',
        url: 'book/delete/' + bookId,
        success: function () {
            fillBooksTable();
        }
    })
}

function getBookInfo(bookId) {
    $.ajax({
        type: 'GET',
        url: 'book/info',
        data: {'book_id': bookId},
        success: function (book) {
            $('#bookInfo_bookName').text(book.name);
            $('#bookInfo_genre').text(book.genre.name);
            $('#bookInfo_authors').find('p').remove();
            $.each(book.authors, function (id, author) {
                $('#bookInfo_authors').append($('<p>').text(author.name));
            });
            $('#bookInfo_comments').find('p').remove();
            $.each(book.comments, function (id, comment) {
                $('#bookInfo_comments').append($('<p>').text(comment.comment));
            });
            $('#bookInfo_buttonAddComment').val(book.id);
            $('#bookInfo_buttonAddAuthor').val(book.id);
            $('.content').hide();
            $('#bookInfo').show();
        },
        error: function () {
            alert('error');
        }
    })
}

function addComment() {
    var bookId = $(this).val();
    var comment = $('#bookInfo_comment').val();
    if (bookId !== '') {
        $.ajax({
            type: 'POST',
            url: 'book/addComment',
            data: JSON.stringify({'bookId': bookId, 'comment': comment}),
            contentType: 'application/json',
            success: function () {
                getBookInfo(bookId);
            },
            error: function () {
                alert("error");
            }
        })
    }
}

function addAuthorToBook() {
    var bookId = $(this).val();
    var authorName = $('#bookInfo_selectAuthor').val();
    if (bookId !== '') {
        $.ajax({
            type: 'POST',
            url: 'book/addAuthorToBook',
            data: JSON.stringify({'bookId': bookId, 'authorName': authorName}),
            contentType: 'application/json',
            success: function () {
                getBookInfo(bookId);
            },
            error: function () {
                alert("error");
            }
        })
    }
}

function addBook() {
    var bookName = $('#bookPage_newBookName').val();
    var authorName = $('#bookPage_selectAuthor').val();
    var genre = $('#bookPage_selectGenre').val();
    $.ajax({
        type: 'POST',
        url: 'book/add',
        data: JSON.stringify({'bookName': bookName, 'authorName': authorName, 'genreName': genre}),
        contentType: 'application/json',
        async: false,
        success: function () {
            fillBooksTable();
        }
    })
}

function addGenre() {
    var genreName = $('#genreContainer_newGenreName').val();
    $('#genreContainer_message').find('span').remove();
    if (genreName !== '') {
        $.ajax({
            type: 'POST',
            url: 'genre/add',
            data: JSON.stringify({"name": genreName}),
            contentType: 'application/json',
            success: function () {
                fillGenresTable();
                $('#genreContainer_message').addClass('successAction').append($('<span>').text('genre was added'));
            },
            error: function () {
                $('#genreContainer_message').addClass('errorAction').append($('<span>').text('error'));
            }
        })
    }
}

function addAuthor() {
    var authorName = $('#authorContainer_newAuthorName').val();
    $('#authorContainer_message').find('span').remove();
    if (authorName !== '') {
        $.ajax({
            type: 'POST',
            url: 'author/add',
            data: JSON.stringify({'name': authorName}),
            contentType: 'application/json',
            success: function () {
                fillAuthorsTable();
                $('#authorContainer_message').addClass('successAction').append($('<span>').text('author was added'));
            },
            error: function () {
                $('#authorContainer_message').addClass('errorAction').append($('<span>').text('author was not added'));
            }
        })
    }
}

function findBook() {
    var inputValue = $('#inputValue').val();
    var typeOfSearching = $('#typeOfSearching').val();
    var url;
    var data;
    switch (typeOfSearching) {
        case 'byName':
            url = 'book/findByName';
            data = {'bookName': inputValue};
            break;
        case 'byAuthor':
            url = 'book/findByAuthor';
            data = {'authorName': inputValue};
            break;
        case 'byGenre':
            url = 'book/findByGenre';
            data = {'genreName': inputValue};
            break;
        default:
            url = 'book/all';
            data = {};
            break;
    }
    fillBookTableWithRequest(url, data);
    showBookContainer();
}

function findAuthor() {
    var authorName = $('#authorName').val();
    fillAuthorsByRequest('author/findByName', {'authorName': authorName});
    showAuthorContainer();
}

function showBookContainer() {
    $('.content').hide();
    $('#bookContainer').show();
}

function showGenreContainer() {
    $('.content').hide();
    $('#genreContainer').show();
}

function showAuthorContainer() {
    $('.content').hide();
    $('#authorContainer').show();
}

function showAllAuthors() {
    fillAuthorsTable();
    showAuthorContainer();
}

function showAllGenres() {
    fillGenresTable();
    showGenreContainer();
}

function showAllBooks() {
    fillBooksTable();
    showBookContainer();
}