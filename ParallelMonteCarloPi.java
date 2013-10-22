package lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ParallelMonteCarloPi {
    public static AtomicLong fullCount = new AtomicLong();

    public static void main(String[] args) {
        int threadCount = Integer.valueOf(args[0]);
        long dots = 1 * 1000 * 1000 * 1000;
        Runnable r = new MonteCarloTask(dots / threadCount);

        long before = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < threadCount; ++i) {
            Thread t = new Thread(r);
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long after = System.currentTimeMillis();

        double res = ((double)fullCount.get() / (double)dots) * 4;
        System.out.println("PI is " + res);
        System.out.println("THREADS " + threadCount);
        System.out.println("ITERATIONS " + dots);
        System.out.println("TIME " + (after - before) + "ms");
    }


}
