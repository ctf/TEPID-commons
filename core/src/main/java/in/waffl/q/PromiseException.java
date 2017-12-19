package in.waffl.q;

public class PromiseException extends RuntimeException {

    public PromiseException() {
    }

    public PromiseException(String msg) {
        super(msg);
    }

    public PromiseException(Throwable parent) {
        super(parent);
    }

    public PromiseException(String msg, Throwable parent) {
        super(msg, parent);
    }

}
