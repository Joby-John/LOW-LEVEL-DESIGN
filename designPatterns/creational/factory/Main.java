package designPatterns.creational.factory;

/**
 * ---------------------------
 * 🟢 Factory Pattern
 * ---------------------------
 * A **Creational Design Pattern** that provides an interface
 * for creating objects, but lets the FACTORY decide which
 * concrete class to instantiate.
 *
 * 👉 Why?
 * - To decouple client code from object creation.
 * - To centralize and control instantiation.
 * - To follow Open/Closed Principle (easily add new products).
 *
 * 👉 Real-world Analogy:
 * - Pizza Shop: You order "Veg Pizza" or "Cheese Pizza".
 *   You don’t cook it yourself. The kitchen (factory) creates
 *   the right pizza and gives it to you.
 *
 * ---------------------------
 * 🔑 Key Components
 * ---------------------------
 * 1. Product → Interface / Abstract class (e.g., Logistics)
 * 2. Concrete Products → Implementations (Road, Air, Rail)
 * 3. Factory → Returns the correct product based on input
 * 4. Client → Uses the product without worrying about its creation
 *
 * ---------------------------
 * ❌ Bad Practice (Without Factory)
 * ---------------------------
 * - Directly create objects in client code using `new`.
 * - Business logic tightly coupled with object creation.
 * - Hard to extend (adding a new type requires changing client code).
 *
 * ---------------------------
 * ✅ Good Practice (With Factory)
 * ---------------------------
 * - Factory handles object creation.
 * - Client depends only on interface (Logistics), not classes.
 * - Adding a new type → change only the Factory.
 *
 * ---------------------------
 * ✅ Pros
 * ---------------------------
 * - Loose Coupling (client talks only to interface).
 * - Open/Closed Principle (easily extend).
 * - Centralized object creation (Single Responsibility).
 * - Easier Testing & Maintenance.
 *
 * ---------------------------
 * ❌ Cons
 * ---------------------------
 * - Extra abstraction (factory class) → more boilerplate.
 * - Overkill for very small/simple apps.
 */

// ------------------------------------------------------
// 1. Product (Interface)
// ------------------------------------------------------
interface Logistics {
    void send();
}

// ------------------------------------------------------
// 2. Concrete Products
// ------------------------------------------------------
class Road implements Logistics {
    public void send() { System.out.println("Send via Road"); }
}
class Air implements Logistics {
    public void send() { System.out.println("Send via Air"); }
}
class Rail implements Logistics {
    public void send() { System.out.println("Send via Rail"); }
}

// ------------------------------------------------------
// 3. Factory
// ------------------------------------------------------
class LogisticsFactory {
    public static Logistics getLogistics(String mode) {
        if (mode.equalsIgnoreCase("Air")) return new Air();
        else if (mode.equalsIgnoreCase("Rail")) return new Rail();
        else return new Road(); // default
    }
}

// ------------------------------------------------------
// 4. Client (Uses factory instead of `new`)
// ------------------------------------------------------
class LogisticService {
    public void send(String mode) {
        Logistics logistics = LogisticsFactory.getLogistics(mode);
        logistics.send();
    }
}

// ------------------------------------------------------
// 5. Main Class - Demo
// ------------------------------------------------------
public class Main {
    public static void main(String[] args) {
        LogisticService service = new LogisticService();

        service.send("Air");   // Output: Send via Air
        service.send("Road");  // Output: Send via Road
        service.send("Rail");  // Output: Send via Rail
    }
}
