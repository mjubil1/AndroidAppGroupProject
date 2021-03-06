package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import java.util.UUID;

/**
 * Created by Rachael on 3/24/2018.
 */

public class Expense {
    public String name;
    public String category;
    public String amount;
    public String dateSpent;
    public UUID id;

    public String toString(){
        return "Expense: " + name + ", $" + amount + ", " + category + ", " + dateSpent + ".";
    }

    public Expense(){
        id = UUID.randomUUID();
    }

    public Expense(String name, String category, String amount, String dateSpent){
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.dateSpent = dateSpent;
        this.id = UUID.randomUUID();
    }

    public String getId() { return id.toString(); }

    public void setId(String id) { this.id = UUID.fromString(id); }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String  getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDateSpent() {
        return dateSpent;
    }

    public void setDateSpent(String dateSpent) {
        this.dateSpent = dateSpent;
    }
}
