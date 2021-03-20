package com.eating.jinwoo.domain;

import com.eating.jinwoo.common.EatingException;

public enum Category {
    Korean,
    Japanese,
    SchoolFood,
    Dessert,
    Chicken,
    Pizza,
    Western,
    Chinese,
    NightFood,
    FastFood;

    public static Category getEnumByValue(int val){
        if (val < 0 || val > 10){
            throw new EatingException("옳지 않은 카테고리 번호 입력");
        }
        switch (val){
            case 0:
                return Korean;
            case 1:
                return Japanese;
            case 2:
                return SchoolFood;
            case 3:
                return Dessert;
            case 4:
                return Chicken;
            case 5:
                return Pizza;
            case 6:
                return Western;
            case 7:
                return Chinese;
            case 8:
                return NightFood;
            case 9:
                return FastFood;
            default:
                return null;
        }
    }
}
