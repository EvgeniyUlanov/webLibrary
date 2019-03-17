package ru.otus.dto;

public class BookContainerDto {

    private String bookId;
    private String bookName;
    private String authorName;
    private String genreName;

    public BookContainerDto() {
    }

    public BookContainerDto(String bookId, String bookName, String authorName, String genreName) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.genreName = genreName;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
