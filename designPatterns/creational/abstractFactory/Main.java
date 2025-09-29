package designPatterns.creational.abstractFactory;

// ------------------ NOTES & EXPLANATION ------------------
/*
Abstract Factory Pattern

The Abstract Factory Pattern is a creational design pattern that provides an interface
for creating families of related or dependent objects without specifying their concrete classes.

In simpler terms:
- Instead of the client deciding which class to instantiate (tight coupling),
  we delegate that responsibility to a "Factory" that knows which objects to create.
- The client (e.g., CheckoutService) only depends on abstractions (interfaces).

Why Abstract Factory?
- Avoids hardcoding of specific product classes in client code.
- Maintains consistency among products (e.g., Indian gateways must always use GST invoices).
- Makes it easier to scale: adding a new region requires a new factory only.

Real-life analogy:
- Think of different regions having their own financial systems.
  - India → RazorPay/PayU gateways + GSTInvoice
  - US → PayPal/Stripe gateways + USInvoice
- The CheckoutService doesn’t know the details. It just says:
  “Give me a payment gateway and invoice for this region.”

Key Points:
- Client works only with interfaces (PaymentGateway, Invoice, RegionFactory).
- Concrete factories (IndianFactory, USFactory) decide the actual product instances.
- SOLID Principles:
  - Open/Closed Principle → Add new factories for new countries without modifying client code.
  - Dependency Inversion → Client depends on abstractions, not concrete implementations.

Pros:
- Scalable, clean, consistent product families.
- Easy to test and maintain.
- Extensible for new regions/payment systems.

Cons:
- More boilerplate (extra interfaces + factories).
- Can be overkill for simple use cases.

*/

// ------------------ PRODUCT INTERFACES ------------------
interface PaymentGateway {
    void processPayment(double amount);
}

interface Invoice {
    void generateInvoice();
}

// ------------------ INDIA PRODUCTS ------------------
class RazorPay implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("RazorPay payment successful of Rs: " + amount);
    }
}

class PayU implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("PayU payment successful of Rs: " + amount);
    }
}

class GSTInvoice implements Invoice {
    public void generateInvoice() {
        System.out.println("GST Invoice generated for the transaction");
    }
}

// ------------------ US PRODUCTS ------------------
class PayPal implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("PayPal payment successful of USD: " + amount);
    }
}

class Stripe implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Stripe payment successful of USD: " + amount);
    }
}

class USInvoice implements Invoice {
    public void generateInvoice() {
        System.out.println("Tax Invoice for US generated");
    }
}

// ------------------ ABSTRACT FACTORY ------------------
interface RegionFactory {
    PaymentGateway createPaymentGateway(String gatewayType);
    Invoice createInvoice();
}

// ------------------ INDIA FACTORY ------------------
class IndianFactory implements RegionFactory {
    public PaymentGateway createPaymentGateway(String gatewayType) {
        if (gatewayType.equalsIgnoreCase("RazorPay")) {
            return new RazorPay();
        } else if (gatewayType.equalsIgnoreCase("PayU")) {
            return new PayU();
        } else {
            throw new IllegalArgumentException("Invalid Payment Gateway: " + gatewayType);
        }
    }

    public Invoice createInvoice() {
        return new GSTInvoice();
    }
}

// ------------------ US FACTORY ------------------
class USFactory implements RegionFactory {
    public PaymentGateway createPaymentGateway(String gatewayType) {
        if (gatewayType.equalsIgnoreCase("PayPal")) {
            return new PayPal();
        } else if (gatewayType.equalsIgnoreCase("Stripe")) {
            return new Stripe();
        } else {
            throw new IllegalArgumentException("Invalid Payment Gateway: " + gatewayType);
        }
    }

    public Invoice createInvoice() {
        return new USInvoice();
    }
}

// ------------------ CLIENT (USES FACTORY) ------------------
class CheckOutService {
    private final PaymentGateway paymentGateway;
    private final Invoice invoice;

    public CheckOutService(String gatewayType, RegionFactory factory) {
        this.paymentGateway = factory.createPaymentGateway(gatewayType);
        this.invoice = factory.createInvoice();
    }

    public void completeOrder(double amount) {
        this.paymentGateway.processPayment(amount);
        this.invoice.generateInvoice();
    }
}

// ------------------ MAIN ------------------
public class Main {
    public static void main(String[] args) {
        // India checkout using RazorPay
        CheckOutService indiaCheckout = new CheckOutService("RazorPay", new IndianFactory());
        indiaCheckout.completeOrder(1000);

        // US checkout using PayPal
        CheckOutService usCheckout = new CheckOutService("PayPal", new USFactory());
        usCheckout.completeOrder(500);
    }
}

/*
------------------ FLOW DIAGRAM ------------------

Main
  |
  |---> new CheckOutService("RazorPay", new IndianFactory())
              |
              |---> IndianFactory.createPaymentGateway("RazorPay") --> RazorPay object
              |---> IndianFactory.createInvoice() ------------------> GSTInvoice object
              |
              ---> CheckoutService holds (RazorPay + GSTInvoice)

  |---> indiaCheckout.completeOrder(1000)
              |
              |---> RazorPay.processPayment(1000)
              |---> GSTInvoice.generateInvoice()

  |---> new CheckOutService("PayPal", new USFactory())
              |
              |---> USFactory.createPaymentGateway("PayPal") -------> PayPal object
              |---> USFactory.createInvoice() ----------------------> USInvoice object
              |
              ---> CheckoutService holds (PayPal + USInvoice)

  |---> usCheckout.completeOrder(500)
              |
              |---> PayPal.processPayment(500)
              |---> USInvoice.generateInvoice()

--------------------------------------------------

This design ensures:
- CheckoutService doesn’t care about which gateway or invoice is used.
- Factories decide which concrete products to create based on region.
- Adding a new country = just add a new Factory + Products. Client code unchanged!
*/
