package javaQuiz.dto;

import javaQuiz.model.User;

public interface UserWithAnswerCountProjection {
    User getUser();
    int getAnswerCount();
}
