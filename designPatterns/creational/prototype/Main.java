package designPatterns.creational.prototype;

import java.util.*;

// ------------------ NOTES & EXPLANATION ------------------
/*
Prototype Pattern

The Prototype Pattern is a creational design pattern used to clone existing objects instead of constructing them from scratch. 
It enables efficient object creation, especially when the initialization process is complex or costly.

Formal Definition:
"Prototype pattern creates duplicate objects while keeping performance in mind. 
It provides a mechanism to copy the original object to a new one without making the code dependent on their classes."

In simpler terms:
Imagine you already have a perfectly set-up object — like a pre-defined email template or a configured game character. 
Instead of building a new one every time (which is repetitive and expensive), you just copy the existing one and make small adjustments. 

Real-life Analogy (Photocopy Machine):
Think of preparing ten offer letters. Instead of typing the same letter ten times, you write it once, photocopy it, 
and change just the name on each copy. This is exactly what Prototype Pattern does.

Deep Cloning VS Shallow Cloning:
- Shallow Clone: Only the main object is copied; internal objects (like nested lists or helper objects) are shared.
- Deep Clone: Both the main object and all referenced objects are copied, ensuring clones are fully independent.

Use Cases:
- Object creation is resource-intensive.
- Many similar objects are needed with slight variations.
- Avoid repetitive initialization logic.
- Runtime object creation without tight class coupling.
*/

// ------------------ PROTOTYPE INTERFACE ------------------
interface EmailTemplate extends Cloneable {
    EmailTemplate clone();          // Prototype clone method
    void setContent(String content); // Allows modifying the clone
    void send(String to);           // Action method for the template
}

// ------------------ HELPER CLASS (Nested object to show deep copy) ------------------
class SenderInfo implements Cloneable {
    private String name;
    private String email;

    public SenderInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected SenderInfo clone() {
        try {
            return (SenderInfo) super.clone(); // deep copy of SenderInfo
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Sender{ name='" + name + "', email='" + email + "'}";
    }
}

// ------------------ CONCRETE PROTOTYPE ------------------
class WelcomeEmail implements EmailTemplate {
    private String subject;
    private String content;
    private SenderInfo sender; // Nested object demonstrating deep copy

    public WelcomeEmail() {
        this.subject = "Welcome to TUF+";
        this.content = "Hi there, Thanks for joining us";
        this.sender = new SenderInfo("TUF Support", "support@tuf.com");
    }

    @Override
    public WelcomeEmail clone() {
        try {
            WelcomeEmail copy = (WelcomeEmail) super.clone(); // Shallow copy first
            copy.sender = this.sender.clone();                // Deep copy nested object
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    public SenderInfo getSender() {
        return sender;
    }

    @Override
    public void send(String to) {
        System.out.println("Sending to " + to + ": [" + subject + "] " + content + " - From " + sender);
    }
}

// ------------------ REGISTRY ------------------
/*
The registry stores pre-configured prototypes. 
Whenever a clone is needed, the registry provides a deep copy.
This decouples the client from the creation logic.
*/
class EmailTemplateRegistry {
    private static final Map<String, EmailTemplate> templates = new HashMap<>();

    static {
        templates.put("welcome", new WelcomeEmail());
        // Add other templates as needed
    }

    public static EmailTemplate getTemplate(String type) {
        return templates.get(type).clone(); // return deep copy, not original
    }
}

// ------------------ CLIENT / MAIN ------------------
public class Main {
    public static void main(String args[]) {
        // First cloned email
        WelcomeEmail email1 = (WelcomeEmail) EmailTemplateRegistry.getTemplate("welcome");
        email1.setContent("Hi Alice! Welcome aboard");
        email1.getSender().setName("Alice's Manager");
        email1.send("alice@gmail.com");

        // Second cloned email
        WelcomeEmail email2 = (WelcomeEmail) EmailTemplateRegistry.getTemplate("welcome");
        email2.setContent("Hi Bob, Happy to have you.");
        email2.send("bob@gmail.com");

        // Demonstrating deep copy: nested objects are independent
        System.out.println("\nAre sender objects same? " + (email1.getSender() == email2.getSender()));
    }
}

/*
OUTPUT:
Sending to alice@gmail.com: [Welcome to TUF+] Hi Alice! Welcome aboard - From Sender{ name='Alice's Manager', email='support@tuf.com'}
Sending to bob@gmail.com: [Welcome to TUF+] Hi Bob, Happy to have you. - From Sender{ name='TUF Support', email='support@tuf.com'}

Are sender objects same? false
*/
