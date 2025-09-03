package solidprinciple.interfaceSegregationPrinciple;

interface Uber{
    void bookRide();
    void acceptRide();
    void startRide();
    void endRide();
    void pay();
} // this is wrong as if i try to implement this for rider class, 
  //i might need to implement 3 unwanted function for it , likewise for driver class

interface RiderInterface
{
    void bookRide();
    void pay();
}

interface  DriverInterface{
    void acceptRide();
    void startRide();
    void endRide();
}

class Driver implements DriverInterface
{
    public void acceptRide()
    {
        System.out.println("Accepted");
    }
    public void startRide()
    {
    System.out.println("Started ride");
    }
    
    public void endRide()
    {
        System.out.println("Ride Ended");
    }
    
}

class Rider implements RiderInterface
{
    public void bookRide()
    {
        System.out.println("Ride Booking");
    }
    
    public void pay()
    {
        System.out.println("Payment initiated");
    }
    
}



public class Main {
    
   public static void main(String args[])
   {
        Rider rider = new Rider();
        Driver driver = new Driver();

        rider.bookRide();
        driver.acceptRide();
        driver.startRide();
        driver.endRide();
        rider.pay();
   }
}
