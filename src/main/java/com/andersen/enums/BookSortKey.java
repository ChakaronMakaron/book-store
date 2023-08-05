package com.andersen.enums;

public enum BookSortKey {
    NAME,
    PRICE,
    AMOUNT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
