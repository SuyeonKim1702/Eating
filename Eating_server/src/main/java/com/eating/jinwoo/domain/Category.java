package com.eating.jinwoo.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    boolean Korean;
    boolean Japanese;
    boolean SchoolFood;
    boolean Dessert;
    boolean Chicken;
    boolean Pizza;
    boolean Western;
    boolean Chinese;
    boolean NightFood;
    boolean FastFood;

    public Category() {
        Korean = true;
        Japanese = true;
        SchoolFood = true;
        Dessert = true;
        Chicken = true;
        Pizza = true;
        Western = true;
        Chinese = true;
        NightFood = true;
        FastFood = true;
    }
    public List<Boolean> getCategories() {
        return Arrays.asList(Korean, Japanese, SchoolFood, Dessert, Chicken, Pizza, Western, Chinese, NightFood, FastFood);
    }
}
