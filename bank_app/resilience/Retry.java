package resilience;

import java.util.concurrent.Callable;

public class Retry {
    private final int maxAttempts;

    public Retry(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public <T> T call(Callable<T> callable) throws Exception {
        Exception lastException = null;
        for (int i = 0; i < maxAttempts; i++) {
            try {
                return callable.call();
            } catch (Exception e) {
                lastException = e;
                System.out.println("Retry attempt " + (i + 1) + " failed.");
            }
        }
        throw lastException;
    }
}
