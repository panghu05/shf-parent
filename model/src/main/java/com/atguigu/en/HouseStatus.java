package com.atguigu.en;

/**
 * 包名:com.atguigu.en
 *
 */
public enum HouseStatus {
    REMOVED(2,"已下架"),
    PUBLISHED(1,"已发布"),
    UNPUBLISHED(0,"未发布");
    public int code;
    public String message;

    HouseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
