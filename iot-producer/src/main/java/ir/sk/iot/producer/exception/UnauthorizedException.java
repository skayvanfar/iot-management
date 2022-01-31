package ir.sk.iot.producer.exception;

import ir.sk.iot.producer.model.exception.BaseErrorMessages;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(Throwable cause) {
        super(BaseErrorMessages.GENERIC_UNAUTHORIZED_EXCEPTION, cause);
    }
}
