package com.andersen.enums;

public enum RequestSortKey {
    NAME,
    PRICE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
