package project.delivery.exception;

/**
 * 데이터 중복 예외 처리
 */
public class DuplicateException extends IllegalArgumentException {

    public DuplicateException() {
        super();
    }

    public DuplicateException(String s) {
        super(s);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateException(Throwable cause) {
        super(cause);
    }
}
