package com.example.shoppinglistapp.Item;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Table(value = "shopping_list_entry")
public class Item  implements Serializable {

    @PrimaryKey
    private ItemPrimaryKey id;

    @Column("item_name")
    private String itemName;

    @Column("quantity")
    private Integer quantity;

    @Column("category")
    private String category;

    // Constructors, getters, and setters

    public Item() {
    }

    public Item(ItemPrimaryKey id, String itemName, Integer quantity, String category) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.category = category;
    }

    public ItemPrimaryKey getId() {
        return id;
    }

    public void setId(ItemPrimaryKey id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // toString method for easy debugging

    @Override
    public String toString() {
        return "ShoppingListEntry{" +
                "id=" + id.getId() +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", priority='" + id.getPriority() + '\'' +
                ", status='" + id.getStatus() + '\'' +
                '}';
    }
}

