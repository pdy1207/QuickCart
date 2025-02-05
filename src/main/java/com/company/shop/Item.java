package com.company.shop;

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

    public Item() {}

    public Item(String title, Integer price) {
        this.title = title;
        this.price = price;
    }
}

