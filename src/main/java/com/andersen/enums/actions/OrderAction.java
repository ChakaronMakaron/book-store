package com.andersen.enums.actions;

public enum OrderAction implements CommandAction {

    LIST(0, 1),
    COMPLETE(1, 1),
    CREATE(0, 0),
    CANCEL(1, 1),
    TOTAL_INCOME (2 , 2);

    private final int minArgsAmount;
    private final int maxArgsAmount;

    OrderAction(int minArgsAmount, int maxArgsAmount) {
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
