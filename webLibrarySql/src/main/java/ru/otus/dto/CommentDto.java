package ru.otus.dto;

public class CommentDto {

    private Long bookId;
    private String comment;

    public CommentDto() {
    }

    public CommentDto(Long bookId, String comment) {
        this.bookId = bookId;
        this.comment = comment;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
