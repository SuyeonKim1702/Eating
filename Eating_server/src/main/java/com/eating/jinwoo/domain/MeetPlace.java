package com.eating.jinwoo.domain;

public enum MeetPlace {
    HOST, MID, GUEST;
    public static MeetPlace getEnumByValue(int value) {
        if (value == 0) {
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
        if (name == "HOST"){
            return 0;
        } else if (name == "MID") {
            return 1;
        } else {
            return 2;
        }
    }
}
