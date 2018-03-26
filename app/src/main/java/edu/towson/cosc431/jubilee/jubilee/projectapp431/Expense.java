package edu.towson.cosc431.jubilee.jubilee.projectapp431;

/**
 * Created by Rachael on 3/24/2018.
 */

public class Expense {
    private String name;
    private String category;
    private String amount;
    private String dateSpent;

    public String toString(){
        return "Expense:" + name + ", " + amount + ", " + category + ", " + dateSpent + ".";
    }

    public Expense(){}

    public Expense(String name, String category, String amount, String dateSpent){
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.dateSpent = dateSpent;
    }

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
