package com.andersen.enums.actions;

public enum RequestAction implements CommandAction {
    
    LIST("list", 1, 1);

    private String strValue;
    private int minArgsAmount;
    private int maxArgsAmount;

    RequestAction(String strValue, int minArgsAmount, int maxArgsAmount) {
        this.strValue = strValue;
        this.minArgsAmount = minArgsAmount;
        this.maxArgsAmount = maxArgsAmount;
    }

    @Override
    public String getStrValue() {
        return strValue;
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
