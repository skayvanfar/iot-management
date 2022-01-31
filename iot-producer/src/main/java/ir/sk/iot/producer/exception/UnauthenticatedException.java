package ir.sk.iot.producer.exception;

import ir.sk.iot.producer.model.exception.BaseErrorMessages;

public class UnauthenticatedException extends BaseException {

    public UnauthenticatedException(Throwable cause) {
        super(BaseErrorMessages.GENERIC_UNAUTHENTICATED_EXCEPTION, cause);
    }
}
