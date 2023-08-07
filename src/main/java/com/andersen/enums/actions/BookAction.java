package com.andersen.enums.actions;

public enum BookAction implements CommandAction {

    LIST(1, 1),
    ADD(2, 2);

    private final int minArgsAmount;
    private final int maxArgsAmount;

    BookAction(int minArgsAmount, int maxArgsAmount) {
        this.minArgsAmount = minArgsAmount;
        this.maxArgsAmount = maxArgsAmount;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    @Override
    public int getMinArgsAmount() {
        return minArgsAmount;
    }

    @Override
    public int getMaxArgsAmount() {
        return maxArgsAmount;
    }
}
