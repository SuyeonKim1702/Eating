package com.eating.jinwoo.domain;


import com.eating.jinwoo.common.EatingException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
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

    public Category(int value) {
        Korean = false;
        Japanese = false;
        SchoolFood = false;
        Dessert = false;
        Chicken = false;
        Pizza = false;
        Western = false;
        Chinese = false;
        NightFood = false;
        FastFood = false;
        if (value < 0 || value > 9) throw new EatingException("옳지 않은 번호 입력");
        if (value == 0) Korean = true;
        if (value == 1) Japanese = true;
        if (value == 2) SchoolFood = true;
        if (value == 3) Dessert = true;
        if (value == 4) Chicken = true;
        if (value == 5) Pizza = true;
        if (value == 6) Western = true;
        if (value == 7) Chinese = true;
        if (value == 8) NightFood = true;
        if (value == 9) FastFood = true;


    }

    public List<Boolean> getCategories() {
        return Arrays.asList(Korean, Japanese, SchoolFood, Dessert, Chicken, Pizza, Western, Chinese, NightFood, FastFood);
    }

}
