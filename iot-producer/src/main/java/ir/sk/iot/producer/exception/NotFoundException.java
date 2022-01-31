package ir.sk.iot.producer.exception;

import ir.sk.iot.producer.model.exception.BaseErrorMessages;

public class NotFoundException extends BaseException {

    public NotFoundException() {
        super(BaseErrorMessages.GENERIC_NOT_FOUND);
    }
}
