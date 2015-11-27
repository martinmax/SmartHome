package javaskop.demo.homeautomation.core.exception;

/**
 * Created by martin.ilievski on 11/27/2015.
 */
public class SmartHomeException extends RuntimeException {
    public SmartHomeException() {
    }

    public SmartHomeException(String message) {
        super(message);
    }

    public SmartHomeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmartHomeException(Throwable cause) {
        super(cause);
    }

    public SmartHomeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
