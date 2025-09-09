package designPatterns.creational.singleton;

/**
 * ---------------------------
 * 🟢 Singleton Pattern
 * ---------------------------
 * Ensures only ONE instance of a class exists
 * and provides a GLOBAL point of access.
 *
 * 👉 Why? 
 * - To manage shared resources (logging, DB connection, configs).
 * - Prevent multiple inconsistent objects.
 * - Save memory & control access.
 *
 * 👉 Real-world analogy:
 * - Print Spooler: All print jobs go through a single queue.
 * - Logger: Only one instance writes to a log file.
 * - Operating System kernel: only one running instance.
 *
 * ---------------------------
 * 🔑 Key Components
 * ---------------------------
 * 1. Private constructor → restricts external instantiation.
 * 2. Static variable → stores the sole instance.
 * 3. Public static accessor → returns instance (new or existing).
 *
 * ---------------------------
 * ⚡ Common Implementations
 * ---------------------------
 * 1. Normal class (no singleton)
 * 2. Eager Singleton (early init, thread-safe)
 * 3. Lazy Singleton (on-demand init, not thread-safe)
 * 4. Thread-Safe Singleton (synchronized)
 * 5. Double-Checked Locking (efficient + thread-safe)
 * 6. Bill Pugh (best practice)
 *
 * ---------------------------
 * ❌ Cons / Pitfalls
 * ---------------------------
 * - Violates SRP (handles instance mgmt + business logic).
 * - Difficult to unit test (global state, hard to mock).
 * - Tight coupling (all clients depend on the singleton directly).
 * - Multi-threading race conditions if not handled properly.
 * - Overuse → becomes a "global variable" anti-pattern.
 */

// ------------------------------------------------------
// 1. Normal Class (NO SINGLETON)
// ------------------------------------------------------
// ❌ Problem: Every object has its own counter.
// Not suitable when one global state is needed.
// ------------------------------------------------------
class JudgeAnalyticsNormal {
    private int run = 0;
    private int submit = 0;

    public void countRun() { run++; }
    public void countSubmit() { submit++; }

    public int getRunCount() { return run; }
    public int getSubmitCount() { return submit; }
}

// ------------------------------------------------------
// 2. Eager Singleton
// ------------------------------------------------------
// ✅ Pros: Thread-safe, simple
// ❌ Cons: Wastes memory if never used
// Real-life analogy: Fire extinguisher (always there, even if no fire).
// ------------------------------------------------------
class JudgeAnalyticsEager {
    private static final JudgeAnalyticsEager INSTANCE = new JudgeAnalyticsEager();
    private int run = 0;
    private int submit = 0;

    private JudgeAnalyticsEager() { }
    public static JudgeAnalyticsEager getInstance() { return INSTANCE; }

    public void countRun() { run++; }
    public void countSubmit() { submit++; }
    public int getRunCount() { return run; }
    public int getSubmitCount() { return submit; }
}

// ------------------------------------------------------
// 3. Lazy Singleton (NOT Thread-Safe)
// ------------------------------------------------------
// ✅ Pros: Saves memory if never used
// ❌ Cons: Race conditions in multi-threaded apps
// Real-life analogy: Coffee machine (brews only when needed).
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

    public void countRun() { run++; }
    public void countSubmit() { submit++; }
    public int getRunCount() { return run; }
    public int getSubmitCount() { return submit; }
}

// ------------------------------------------------------
// 4. Thread-Safe Singleton (Synchronized Method)
// ------------------------------------------------------
// ✅ Pros: Safe in multi-threaded env
// ❌ Cons: SLOW → every call synchronized
// Interview Tip: Good to mention, but not optimal.
// ------------------------------------------------------
class JudgeAnalyticsSynchronized {
    private static JudgeAnalyticsSynchronized INSTANCE;
    private int run = 0;
    private int submit = 0;

    private JudgeAnalyticsSynchronized() { }
    public static synchronized JudgeAnalyticsSynchronized getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JudgeAnalyticsSynchronized();
        }
        return INSTANCE;
    }

    public void countRun() { run++; }
    public void countSubmit() { submit++; }
    public int getRunCount() { return run; }
    public int getSubmitCount() { return submit; }
}

// ------------------------------------------------------
// 5. Double-Checked Locking (DCL)
// ------------------------------------------------------
// ✅ Pros: Fast after init, safe in multi-threaded apps
// Uses volatile to avoid caching issues
// ❌ Cons: Slightly complex
// ------------------------------------------------------
class JudgeAnalyticsDCL {
    private static volatile JudgeAnalyticsDCL INSTANCE;
    private int run = 0;
    private int submit = 0;

    private JudgeAnalyticsDCL() { }
    public static JudgeAnalyticsDCL getInstance() {
        if (INSTANCE == null) {
            synchronized (JudgeAnalyticsDCL.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JudgeAnalyticsDCL();
                }
            }
        }
        return INSTANCE;
    }

    public void countRun() { run++; }
    public void countSubmit() { submit++; }
    public int getRunCount() { return run; }
    public int getSubmitCount() { return submit; }
}

// ------------------------------------------------------
// 6. Bill Pugh Singleton (BEST PRACTICE)
// ------------------------------------------------------
// ✅ Pros: Lazy + Thread-safe + No sync overhead
// Uses static inner class for initialization
// ❌ Cons: Slightly tricky for beginners
// ------------------------------------------------------
class JudgeAnalyticsBillPugh {
    private int run = 0;
    private int submit = 0;

    private JudgeAnalyticsBillPugh() { }
    private static class Holder {
        private static final JudgeAnalyticsBillPugh INSTANCE = new JudgeAnalyticsBillPugh();
    }
    public static JudgeAnalyticsBillPugh getInstance() { return Holder.INSTANCE; }

    public void countRun() { run++; }
    public void countSubmit() { submit++; }
    public int getRunCount() { return run; }
    public int getSubmitCount() { return submit; }
}

// ------------------------------------------------------
// Main class demonstrating all approaches
// ------------------------------------------------------
public class Main {
    public static void main(String[] args) {

        // 1. Normal Class
        JudgeAnalyticsNormal n1 = new JudgeAnalyticsNormal();
        JudgeAnalyticsNormal n2 = new JudgeAnalyticsNormal();
        n1.countRun(); n2.countRun();
        System.out.println("Normal Class → " + n1.getRunCount() + " & " + n2.getRunCount()); // 1 & 1

        // 2. Eager Singleton
        JudgeAnalyticsEager e1 = JudgeAnalyticsEager.getInstance();
        JudgeAnalyticsEager e2 = JudgeAnalyticsEager.getInstance();
        e1.countRun(); e2.countRun();
        System.out.println("Eager Singleton → " + e1.getRunCount()); // 2

        // 3. Lazy Singleton
        JudgeAnalyticsLazy l1 = JudgeAnalyticsLazy.getInstance();
        JudgeAnalyticsLazy l2 = JudgeAnalyticsLazy.getInstance();
        l1.countRun(); l2.countRun();
        System.out.println("Lazy Singleton → " + l1.getRunCount()); // 2

        // 4. Synchronized Singleton
        JudgeAnalyticsSynchronized s1 = JudgeAnalyticsSynchronized.getInstance();
        JudgeAnalyticsSynchronized s2 = JudgeAnalyticsSynchronized.getInstance();
        s1.countRun(); s2.countRun();
        System.out.println("Synchronized Singleton → " + s1.getRunCount()); // 2

        // 5. Double-Checked Locking
        JudgeAnalyticsDCL d1 = JudgeAnalyticsDCL.getInstance();
        JudgeAnalyticsDCL d2 = JudgeAnalyticsDCL.getInstance();
        d1.countRun(); d2.countRun();
        System.out.println("DCL Singleton → " + d1.getRunCount()); // 2

        // 6. Bill Pugh
        JudgeAnalyticsBillPugh b1 = JudgeAnalyticsBillPugh.getInstance();
        JudgeAnalyticsBillPugh b2 = JudgeAnalyticsBillPugh.getInstance();
        b1.countRun(); b2.countRun();
        System.out.println("Bill Pugh Singleton → " + b1.getRunCount()); // 2
    }
}
