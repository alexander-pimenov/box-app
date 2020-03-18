package ru.pimalex78.entities;

public class Item {

    private Integer id;
    private String color;

    public Item() {
    }

    public Item(Integer id, String color) {
        this.id = id;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", color='" + color + '\'' +
                '}';
    }
}
