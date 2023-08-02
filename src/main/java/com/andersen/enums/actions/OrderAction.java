package com.andersen.enums.actions;

public enum OrderAction implements CommandAction {
    
    LIST(1, 1),
    COMPLETE(1, 1),
    CREATE(2, 20),
    CANCEL(1, 1);

    private int minArgsAmount;
    private int maxArgsAmount;

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
