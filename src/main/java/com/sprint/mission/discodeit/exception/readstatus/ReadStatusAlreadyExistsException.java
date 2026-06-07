package com.sprint.mission.discodeit.exception.readstatus;

import com.sprint.mission.discodeit.exception.ErrorCode;

import java.util.UUID;

public class ReadStatusAlreadyExistsException extends ReadStatusException {
    public ReadStatusAlreadyExistsException() {
        super(ErrorCode.DUPLICATE_READ_STATUS);
    }

    public static ReadStatusAlreadyExistsException withId(UUID readStatusId) {
        ReadStatusAlreadyExistsException exception = new ReadStatusAlreadyExistsException();
        exception.addDetail("readStatusId", readStatusId);
        return exception;
    }
}
