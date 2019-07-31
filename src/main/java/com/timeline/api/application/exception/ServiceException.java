package com.timeline.api.application.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }

    public static UserIdIsAlreadyInUseException userIdIsAlreadyInUse() {
        return new UserIdIsAlreadyInUseException();
    }

    public static UserIsNotExistException userIsNotExistException() {
        return new UserIsNotExistException();
    }
    public static NotFollowException notFollowException() {
        return new NotFollowException();
    }

    public static class UserIdIsAlreadyInUseException extends ServiceException {
        private static final long serialVersionUID = 5545065780904372621L;

        public UserIdIsAlreadyInUseException() {
            super("이미 사용중인 아이디 입니다.");
        }
    }

    public static class UserIsNotExistException extends ServiceException {
        private static final long serialVersionUID = -5169532409018886460L;

        public UserIsNotExistException() {
            super("존재하지 않는 아이디 입니다.");
        }
    }

    public static class NotFollowException extends ServiceException {
        private static final long serialVersionUID = -5169532409018886360L;

        public NotFollowException() {
            super("팔로우 하지 않은 회원입니다.");
        }
    }
}
