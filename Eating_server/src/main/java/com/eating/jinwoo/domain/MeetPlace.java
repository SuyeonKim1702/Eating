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
}
