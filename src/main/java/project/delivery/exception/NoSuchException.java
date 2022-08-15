package project.delivery.exception;

public class NoSuchException extends IllegalArgumentException {

    public NoSuchException() {
        super();
    }

    public NoSuchException(String s) {
        super(s);
    }

    public NoSuchException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchException(Throwable cause) {
        super(cause);
    }
}
