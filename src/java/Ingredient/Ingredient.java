/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ingredient;

/**
 *
 * @author Haba
 */
public class Ingredient {
    String rName;
    double amount;
    String unit;

    public Ingredient () {}
    public Ingredient (String rn, double a, String u) {
        this.rName = rn;
        this.amount = a;
        this.unit = u;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    
}
