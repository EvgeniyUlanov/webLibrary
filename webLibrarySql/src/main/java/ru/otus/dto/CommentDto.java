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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentDto)) return false;

        CommentDto that = (CommentDto) o;

        if (bookId != null ? !bookId.equals(that.bookId) : that.bookId != null) return false;
        return comment != null ? comment.equals(that.comment) : that.comment == null;
    }

    @Override
    public int hashCode() {
        int result = bookId != null ? bookId.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
