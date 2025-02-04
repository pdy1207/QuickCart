package com.company.shop;

import jakarta.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    public Long id;
    public String title;
    public Integer price;
}

