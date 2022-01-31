package ir.sk.iot.producer.exception;

import ir.sk.iot.producer.model.exception.BaseErrorMessages;

public class BaseException extends Exception {

    public BaseException(BaseErrorMessages baseErrorMessages) {
        super(baseErrorMessages.getMessage());
    }

    public BaseException(BaseErrorMessages baseErrorMessages, Throwable cause) {
        super(baseErrorMessages.getMessage(), cause);
    }
}
