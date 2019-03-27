package ru.otus.dto;

public class CommentContainerDto {

    private String bookId;
    private String comment;

    public CommentContainerDto() {
    }

    public CommentContainerDto(String bookId, String comment) {
        this.bookId = bookId;
        this.comment = comment;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
