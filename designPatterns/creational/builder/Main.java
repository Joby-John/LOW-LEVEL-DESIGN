package designPatterns.creational.builder;

import java.util.*;

// ------------------ NOTES & EXPLANATION ------------------
/*
Builder Pattern:

- The Builder Pattern is a creational design pattern that separates the construction 
  of a complex object from its representation. 
  This allows you to build different types and representations of an object using the same construction process.

- Example: Building a custom burger.
    Required: Bun and Patty
    Optional: Cheese, Toppings, Side, Drink

Why Builder Pattern:
- Avoids multiple constructor overloads (Telescoping Constructor Anti-Pattern)
- Provides readable, maintainable, and safe object creation
- Handles optional parameters safely
- Enables immutability

Real-life analogy:
- Ordering a pizza online with size, crust, toppings, sauces — built step by step
- Amazon cart customization with quantity, size, color, delivery option, etc.

Static Nested Class Explanation:
- Static nested class does NOT require an instance of the outer class.
- It cannot access instance variables of the outer class directly.
- Outer class CAN access private variables of the static nested class if it has a reference.
- In the Builder Pattern, the static nested class (BurgerBuilder) can set up the outer class
  (BurgerMeal) because it passes its own reference to the BurgerMeal constructor.
- Using static ensures that the builder can exist independently of any BurgerMeal instance,
  which is exactly what we want for step-by-step object construction.
*/

// ------------------ BURGERMEAL CLASS ------------------
class BurgerMeal {
    // Required parameters
    private final String bunType;
    private final String patty;

    // Optional parameters
    private final boolean hasCheese;
    private final List<String> toppings;
    private final String side;
    private final String drink;

    // Private constructor: only Builder can create BurgerMeal
    private BurgerMeal(BurgerBuilder builder) {
        this.bunType = builder.bunType;
        this.patty = builder.patty;
        this.hasCheese = builder.hasCheese;
        this.toppings = builder.toppings;
        this.side = builder.side;
        this.drink = builder.drink;
    }

    // Method to get a description of the burger
    public String getBurger() {
        StringBuilder burgerType = new StringBuilder();
        burgerType.append(bunType).append(" ").append(patty);

        if(hasCheese) burgerType.append(" with cheese");

        if(toppings != null && !toppings.isEmpty()) {
            burgerType.append(", with added toppings: ");
            for(String topping : toppings) {
                burgerType.append(topping).append(" ");
            }
        }

        if(side != null) burgerType.append(", side: ").append(side);
        if(drink != null) burgerType.append(", drink: ").append(drink);

        return burgerType.toString();
    }

    // ------------------ STATIC NESTED BUILDER CLASS ------------------
    public static class BurgerBuilder {
        // Required fields
        private final String bunType;
        private final String patty;

        // Optional fields
        private boolean hasCheese;
        private List<String> toppings;
        private String side;
        private String drink;

        // Constructor ensures required parameters
        public BurgerBuilder(String bunType, String patty) {
            this.bunType = bunType;
            this.patty = patty;
        }

        // Optional setters (return 'this' to allow chaining)
        public BurgerBuilder withCheese(boolean hasCheese) {
            this.hasCheese = hasCheese;
            return this;
        }

        public BurgerBuilder withToppings(List<String> toppings) {
            this.toppings = toppings;
            return this;
        }

        public BurgerBuilder withSide(String side) {
            this.side = side;
            return this;
        }

        public BurgerBuilder withDrink(String drink) {
            this.drink = drink;
            return this;
        }

        // Build method returns the final immutable BurgerMeal object
        public BurgerMeal build() {
            return new BurgerMeal(this);
        }
    }
}

// ------------------ MAIN CLASS ------------------
public class Main {
    public static void main(String[] args) {
        // Required only
        BurgerMeal nonVegBurgerMeal = new BurgerMeal.BurgerBuilder("Italian", "Chicken").build();

        // With cheese and side
        BurgerMeal vegBurgerMealWithFries = new BurgerMeal.BurgerBuilder("Normal", "Veg")
                .withCheese(true)
                .withSide("Fries")
                .build();

        // With cheese and toppings
        BurgerMeal beefBurgerMealWithToppings = new BurgerMeal.BurgerBuilder("Italian", "Beef")
                .withCheese(true)
                .withToppings(Arrays.asList("pineapple", "cheddar"))
                .build();

        // Print burger details
        System.out.println("Dispatched burger: " + nonVegBurgerMeal.getBurger());
        System.out.println("Dispatched burger: " + vegBurgerMealWithFries.getBurger());
        System.out.println("Dispatched burger: " + beefBurgerMealWithToppings.getBurger());
    }
}

/*
------------------ FLOW DIAGRAM ------------------

Main
  |
  |---> BurgerBuilder("Italian", "Chicken") // required params
  |           .withCheese(true)             // optional
  |           .withToppings(...)            // optional
  |           .build()                       // returns BurgerMeal object
  |
  |---> BurgerMeal object created
             | bunType, patty, hasCheese, toppings, side, drink
             | getBurger() to print description

- Main interacts only with BurgerBuilder for construction.
- BurgerBuilder sets required + optional fields.
- build() hands over immutable BurgerMeal.
*/
