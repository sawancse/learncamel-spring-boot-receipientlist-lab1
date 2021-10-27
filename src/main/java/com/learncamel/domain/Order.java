package com.learncamel.domain;

import java.util.List;

public class Order {
    
    private String id;
    
    private List<Item> items;
    
    private double totalPrice;
        
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }    

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order [id=");
        builder.append(id);
        builder.append(", items=");
        builder.append(items);
        builder.append(", totalPrice=");
        builder.append(totalPrice);
        builder.append("]");
        return builder.toString();
    }
}