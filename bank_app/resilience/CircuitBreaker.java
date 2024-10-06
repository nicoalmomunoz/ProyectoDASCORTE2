package resilience;

public class CircuitBreaker {
    private final int failureThreshold;
    private int failureCount = 0;
    private long lastFailureTime = 0;
    private final long timeout;

    public CircuitBreaker(int failureThreshold, long timeout) {
        this.failureThreshold = failureThreshold;
        this.timeout = timeout;
    }

    public boolean allowRequest() {
        if (failureCount >= failureThreshold) {
            if (System.currentTimeMillis() - lastFailureTime < timeout) {
                System.out.println("Circuit breaker activated. Please try again later.");
                return false;
            } else {
                // Reset the circuit breaker
                failureCount = 0;
            }
        }
        return true;
    }

    public void recordFailure() {
        failureCount++;
        lastFailureTime = System.currentTimeMillis();
    }
}
