package ru.otus.services;

import ru.otus.dto.CommentDto;

public interface CommentCheckService {

    CommentDto checkForbiddenWords(CommentDto comment);
}
