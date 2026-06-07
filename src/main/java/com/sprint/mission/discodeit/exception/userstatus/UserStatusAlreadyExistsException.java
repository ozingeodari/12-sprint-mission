package com.sprint.mission.discodeit.exception.userstatus;

import com.sprint.mission.discodeit.exception.ErrorCode;

import java.util.UUID;

public class UserStatusAlreadyExistsException extends UserStatusException {
    public UserStatusAlreadyExistsException() {
        super(ErrorCode.DUPLICATE_USER_STATUS);
    }

    public static UserStatusAlreadyExistsException withUserId(UUID userId) {
        UserStatusAlreadyExistsException exception = new UserStatusAlreadyExistsException();
        exception.addDetail("userId", userId);
        return exception;
    }
}
