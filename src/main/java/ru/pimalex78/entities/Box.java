package ru.pimalex78.entities;

public class Box {

    private Integer id;

    public Box() {
    }

    public Box(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                '}';
    }
}
