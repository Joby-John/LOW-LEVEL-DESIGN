package designPatterns.creational.singleton;

/**
 * Example: JudgeAnalytics tracks the number of "Run" and "Submit" actions
 * on an online judge system.
 *
 * This file shows 3 implementations:
 *  1. Normal class (no singleton)
 *  2. Eager Singleton (thread-safe but created at class loading time)
 *  3. Lazy Singleton (created only when needed, but not thread-safe)
 */

// ------------------------------------------------------
// 1. Normal Class - NO SINGLETON
// ------------------------------------------------------
// Issue: Each object has its own run/submit counters.
// ------------------------------------------------------
class JudgeAnalyticsNormal {
    private int run = 0;
    private int submit = 0;

    public void countRun() {
        run++;
    }

    public void countSubmit() {
        submit++;
    }

    public int getRunCount() {
        return run;
    }

    public int getSubmitCount() {
        return submit;
    }
}

// ------------------------------------------------------
// 2. Eager Singleton (Thread Safe)
// ------------------------------------------------------
// Pros: Instance is created at class load time → safe in multithreaded env.
// Cons: Instance is created even if never used.
// ------------------------------------------------------
class JudgeAnalyticsEager {
    // Instance created eagerly when class is loaded
    private static final JudgeAnalyticsEager INSTANCE = new JudgeAnalyticsEager();

    private int run = 0;
    private int submit = 0;

    // Private constructor prevents direct instantiation
    private JudgeAnalyticsEager() { }

    // Global point of access
    public static JudgeAnalyticsEager getInstance() {
        return INSTANCE;
    }

    public void countRun() {
        run++;
    }

    public void countSubmit() {
        submit++;
    }

    public int getRunCount() {
        return run;
    }

    public int getSubmitCount() {
        return submit;
    }
}

// ------------------------------------------------------
// 3. Lazy Singleton (NOT Thread Safe)
// ------------------------------------------------------
// Pros: Instance is created only when needed → saves memory.
// Cons: Not safe if multiple threads call getInstance() simultaneously.
// ------------------------------------------------------
class JudgeAnalyticsLazy {
    private static JudgeAnalyticsLazy INSTANCE;

    private int run = 0;
    private int submit = 0;

    private JudgeAnalyticsLazy() { }

    public static JudgeAnalyticsLazy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JudgeAnalyticsLazy();
        }
        return INSTANCE;
    }

    public void countRun() {
        run++;
    }

    public void countSubmit() {
        submit++;
    }

    public int getRunCount() {
        return run;
    }

    public int getSubmitCount() {
        return submit;
    }
}

// ------------------------------------------------------
// Main Class to demonstrate usage
// ------------------------------------------------------
public class Main {
    public static void main(String[] args) {

        // ---------- Normal Class (No Singleton) ----------
        JudgeAnalyticsNormal normal1 = new JudgeAnalyticsNormal();
        normal1.countRun();

        JudgeAnalyticsNormal normal2 = new JudgeAnalyticsNormal();
        normal2.countRun();

        // Each object has its own counter (not shared!)
        System.out.println("Normal (no singleton): " + normal1.getRunCount()); // 1
        System.out.println("Normal (no singleton): " + normal2.getRunCount()); // 1


        // ---------- Eager Singleton ----------
        JudgeAnalyticsEager eager1 = JudgeAnalyticsEager.getInstance();
        eager1.countRun();

        JudgeAnalyticsEager eager2 = JudgeAnalyticsEager.getInstance();
        eager2.countRun();

        // Shared counters (same instance used)
        System.out.println("Eager Singleton Run Count: " + eager2.getRunCount()); // 2


        // ---------- Lazy Singleton ----------
        JudgeAnalyticsLazy lazy1 = JudgeAnalyticsLazy.getInstance();
        lazy1.countRun();

        JudgeAnalyticsLazy lazy2 = JudgeAnalyticsLazy.getInstance();
        lazy2.countRun();

        // Shared counters (same instance used)
        System.out.println("Lazy Singleton Run Count: " + lazy2.getRunCount()); // 2
    }
}
