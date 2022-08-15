package project.delivery.exception;

public class MaxException extends IllegalArgumentException {

    public MaxException() {
        super();
    }

    public MaxException(String s) {
        super(s);
    }

    public MaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaxException(Throwable cause) {
        super(cause);
    }
}
