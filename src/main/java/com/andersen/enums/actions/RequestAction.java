package com.andersen.enums.actions;

public enum RequestAction implements CommandAction {

    LIST(0, 1),
    SWITCH (1,1);


    private final int minArgsAmount;
    private final int maxArgsAmount;

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
