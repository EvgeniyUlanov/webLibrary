package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dto.CommentDto;
import ru.otus.services.CommentCheckService;

@Service
public class CommentCheckServiceImpl implements CommentCheckService {

    private static String[] FORBIDDEN_WORDS = {"word1", "word2"};

    @Override
    public CommentDto checkForbiddenWords(CommentDto commentDto) {
        if (checkForbiddenWords(commentDto.getComment())) {
            commentDto.setComment("comment contains forbidden words and was deleted");
        }
        return commentDto;
    }

    private boolean checkForbiddenWords(String comment) {
        for (String str : FORBIDDEN_WORDS) {
            if (comment.contains(str)) {
                return true;
            }
        }
        return false;
    }
}
