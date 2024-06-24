package com.sparta.realestatefeed.entity;

public enum ApartStatusEnum {

    AVAILABLE("매매가능"),
    SOLD("매매완료");

    private final String description;

    ApartStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
