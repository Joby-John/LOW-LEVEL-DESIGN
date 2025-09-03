package solidprinciple.liskovsubstitutionprinciple;
class Notification
{
    void sendNotification()
    {
        System.out.println("Email notified");
    }
}

class Sms extends Notification
{
    @Override
    void sendNotification()
    {
        System.out.println("SMS notified");
    }
}

class Whatsapp extends Notification
{
    @Override
    void sendNotification()
    {
        System.out.println("WhatsApp notified");
    }
}
public class Main {
    public static void main(String args[])
    {
        //initial email
        // Notification notification = new Notification();
        // notification.sendNotification();

        //when migrating to sms
        // Notification notification = new Sms();
        // notification.sendNotification();

        //when migrating to whatsapp
        Notification notification = new Whatsapp();
        notification.sendNotification();


        // see how this can be achieved with minimal changes
    }
}
