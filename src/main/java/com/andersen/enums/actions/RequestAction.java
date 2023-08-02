package com.andersen.enums.actions;

public enum RequestAction implements CommandAction {
    
    LIST(1, 1);

    private int minArgsAmount;
    private int maxArgsAmount;

    RequestAction(int minArgsAmount, int maxArgsAmount) {
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
