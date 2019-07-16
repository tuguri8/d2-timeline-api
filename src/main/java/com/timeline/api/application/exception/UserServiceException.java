package com.timeline.api.application.exception;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }

    public static UserIdIsAlreadyInUseException userIdIsAlreadyInUse() {
        return new UserIdIsAlreadyInUseException();
    }

    public static class UserIdIsAlreadyInUseException extends UserServiceException {
        public UserIdIsAlreadyInUseException() {
            super("이미 사용중인 아이디 입니다.");
        }
    }
}
