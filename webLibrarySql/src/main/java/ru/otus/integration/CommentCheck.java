package ru.otus.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.dto.CommentDto;

@MessagingGateway
public interface CommentCheck {

    @Gateway(requestChannel = "commentInputChannel", replyChannel = "commentOutputChannel")
    CommentDto check(CommentDto commentDto);
}
