package project.delivery.exception;

public class NotEnoughBalanceException extends IllegalArgumentException {

    public NotEnoughBalanceException() {
        super();
    }

    public NotEnoughBalanceException(String s) {
        super(s);
    }

    public NotEnoughBalanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughBalanceException(Throwable cause) {
        super(cause);
    }
}
