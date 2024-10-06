package resilience;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class Bulkhead {
    private final int maxConcurrentRequests;
    private final ExecutorService executor;
    private final AtomicInteger activeCount; // Contador de tareas activas

    public Bulkhead(int maxConcurrentRequests) {
        this.maxConcurrentRequests = maxConcurrentRequests;
        this.executor = Executors.newFixedThreadPool(maxConcurrentRequests);
        this.activeCount = new AtomicInteger(0); // Inicializa el contador
    }

    public void call(Runnable runnable) {
        if (activeCount.get() < maxConcurrentRequests) {
            activeCount.incrementAndGet(); // Incrementa el contador antes de ejecutar la tarea
            executor.execute(() -> {
                try {
                    runnable.run(); // Ejecuta la tarea
                } finally {
                    activeCount.decrementAndGet(); // Decrementa el contador al finalizar
                }
            });
        } else {
            System.out.println("Too many concurrent requests. Please try again later.");
        }
    }
}
