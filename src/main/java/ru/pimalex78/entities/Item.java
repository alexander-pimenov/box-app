package ru.pimalex78.entities;

public class Item {

    private Integer id;
    private Integer parentId;
    private String color;

    public Item(Integer id, Integer parentId, String color ) {
        this.id = id;
        this.parentId = parentId;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return String.format("Item id=%d, parentId=%d, color=%s",
                id, parentId, color);
    }
}
