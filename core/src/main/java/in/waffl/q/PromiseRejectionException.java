package in.waffl.q;

public class PromiseRejectionException extends RuntimeException {

    public PromiseRejectionException() {
    }

    public PromiseRejectionException(String msg) {
        super(msg);
    }

    public PromiseRejectionException(Throwable parent) {
        super(parent);
    }

    public PromiseRejectionException(String msg, Throwable parent) {
        super(msg, parent);
    }


}
