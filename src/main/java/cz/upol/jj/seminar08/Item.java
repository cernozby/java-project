package cz.upol.jj.seminar08;

import java.lang.constant.Constable;

public class Item {
    private String name;
    private Type type;
    private int count;
    private double price;


    Item(String name, Type type, int count, double price) {
        this.name = name;
        this.type = type;
        this.count = count;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    //Překryto, nepobral jsem výynam překrytí.
    public String toString() {
        return "";
    }
}
