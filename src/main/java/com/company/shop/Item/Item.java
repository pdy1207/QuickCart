package com.company.shop.Item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    private Long id;

    private String title;
    private Integer price;
    private String username;

    public Item() {}

    public Item(String title, Integer price,String username) {
        this.title = title;
        this.price = price;
        this.username = username;
    }
}

