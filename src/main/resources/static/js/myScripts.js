$(document).ready(function () {
    $('#bookInfo_buttonAddComment').on('click', addComment);
    $('#bookInfo_buttonAddAuthor').on('click', addAuthor);
    $('#buttonAddBook').on('click', addBook);
    fillBooksTable();
    fillAuthorsTable();
    fillGenresTable();
    $('.content').hide();
    $('#bookContainer').show();
});

function fillBooksTable() {
    var bookTable = $('#bookTable');
    bookTable.find('.removable').remove();
    $.ajax({
        type: 'GET',
        url: 'book/all',
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
                    .on('click', buttonDeleteHandler);
                $('<td>').append(buttonDelete).appendTo(newTr);
                var buttonInfo = $('<button>')
                    .text('info')
                    .val(book.id)
                    .addClass('btn btn-primary infoButton')
                    .on('click', buttonInfoHandler);
                $('<td>').append(buttonInfo).appendTo(newTr);
            })
        },
        error: function () {
            alert('error');
        }
    });
}

function fillAuthorsTable() {
    $.ajax({
        type: 'GET',
        url: 'author/getAll',
        success: function (response) {
            $.each(response, function (index, author) {
                $('#bookInfo_selectAuthor').append($('<option>').val(author.name).text(author.name));
                $('#bookPage_selectAuthor').append($('<option>').val(author.name).text(author.name));
            })
        },
        error: function () {
            alert('error');
        }
    })
}

function fillGenresTable() {
    var genreTable = $('#genreTable');
    genreTable.find('.removable').remove();
    $.ajax({
        type: 'GET',
        url: 'genre/getAll',
        success: function (response) {
            $.each(response, function (index, genre) {
                $('#bookPage_selectGenre').append($('<option>').val(genre.name).text(genre.name));
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
        type: 'POST',
        url: 'book/delete',
        data: {'book_id': bookId},
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
            data: {'book_id': bookId, 'comment': comment},
            success: function () {
                getBookInfo(bookId);
            },
            error: function () {
                alert("error");
            }
        })
    }
}

function addAuthor() {
    var bookId = $(this).val();
    var authorName = $('#bookInfo_selectAuthor').val();
    if (bookId !== '') {
        $.ajax({
            type: 'POST',
            url: 'book/addAuthorToBook',
            data: {'book_id': bookId, 'authorName': authorName},
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
        data: {'bookName': bookName, 'authorName': authorName, 'genreName': genre},
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
            data: {'genreName': genreName},
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