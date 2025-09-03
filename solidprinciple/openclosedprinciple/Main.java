// it basically means that new chnages should be extended than actually adjusting all to same class
// this reduces complexity and is easy to maintain and also to add new functionalities
 


package solidprinciple.openclosedprinciple;

interface TaxCalculator {

    double amountAfterTax(double amount);
}

class IndianTax implements TaxCalculator {
    @Override
    public double amountAfterTax(double amount) {
        return (amount + 0.18 * amount);
    }
}

class UsTax implements TaxCalculator {
    @Override
    public double amountAfterTax(double amount) {
        return (amount + 0.10 * amount);
    }
}

class InvoiceGenerator {
    private TaxCalculator taxCalculator;

    public InvoiceGenerator(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
    }

    public double generateAmount(double amount) {

        return taxCalculator.amountAfterTax(amount);
    }
}

public class Main {
    public static void main(String[] args) {
        InvoiceGenerator indianInvoice = new InvoiceGenerator(new IndianTax());
        InvoiceGenerator usInvoice = new InvoiceGenerator(new UsTax());

        System.out.println("Amount after Indian Tax: " + indianInvoice.generateAmount(100));
        System.out.println("Amount after US Tax: " + usInvoice.generateAmount(100));
    }
}
