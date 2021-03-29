package com.eating.jinwoo.domain;

import com.eating.jinwoo.common.EatingException;

public enum MeetPlace {
    HOST, MID, GUEST;

    public static MeetPlace getEnumByValue(int value) {
        if (value > 2 || value < 0) {
            throw new EatingException("올바르지 않은 번호");
        }
        else if (value == 0) {
            return HOST;
        }
        else if (value == 1) {
            return MID;
        }
        else {
            return GUEST;
        }
    }
    public static int getValueByString(String name) {
        if (name.equals("HOST")){
            return 0;
        } else if (name.equals("MID")) {
            return 1;
        } else if (name.equals("GUEST")){
            return 2;
        }
        else return -1;
    }
}
