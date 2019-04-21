package ru.otus.exeptions;

public class CreateEntityException extends RuntimeException {

    public CreateEntityException(String message) {
        super(message);
    }
}
