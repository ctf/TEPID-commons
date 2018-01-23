package in.waffl.q;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Base implementation of a promise
 * <p>
 * Most of the interface is hidden, and can be accessed by the parent holder
 * {@link Q}
 *
 * @param <T>
 */
public class Promise<T> {
    private T result;
    private Semaphore lock = new Semaphore(0);
    private String reason;
    private Throwable cause;
    private boolean resolved;
    private Queue<PromiseCallback<T>> listeners = new ConcurrentLinkedQueue<>();

    Promise() {
    }

    public T getResult() {
        try {
            lock.acquire();
        } catch (InterruptedException ignored) {
        }
        if (reason != null) {
            throw new PromiseRejectionException(reason, cause);
        }
        return result;
    }

    public T getResult(long ms) {
        try {
            if (!lock.tryAcquire(1, ms, TimeUnit.MILLISECONDS)) return null;
        } catch (InterruptedException ignored) {
        }
        if (reason != null) {
            throw new PromiseRejectionException(reason, cause);
        }
        return result;
    }

    void resolve(T result) {
        if (resolved) throw new PromiseException("Promise already resolved");
        resolved = true;
        this.result = result;
        lock.release();
        while (!listeners.isEmpty()) {
            listeners.poll().resolved(result);
        }
    }

    void reject(String reason) {
        if (resolved) throw new PromiseException("Promise already resolved");
        resolved = true;
        this.reason = reason;
        lock.release();
        while (!listeners.isEmpty()) {
            listeners.poll().rejected(reason, null);
        }
    }

    void reject(String reason, Throwable cause) {
        if (resolved) throw new PromiseException("Promise already resolved");
        resolved = true;
        this.reason = reason;
        this.cause = cause;
        lock.release();
        while (!listeners.isEmpty()) {
            listeners.poll().rejected(reason, cause);
        }
    }

    boolean isResolved() {
        return this.resolved;
    }

    public void then(PromiseCallback<T> cb) {
        if (!this.resolved) {
            listeners.add(cb);
        } else {
            if (this.reason != null) {
                cb.rejected(reason, cause);
            } else {
                cb.resolved(result);
            }
        }
    }
}
