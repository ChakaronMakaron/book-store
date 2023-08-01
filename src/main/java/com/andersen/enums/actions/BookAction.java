package com.andersen.enums.actions;

public enum BookAction implements CommandAction {
    
    LIST("list", 1, 1),
    ADD("add", 2, 2);

    private String strValue;
    private int minArgsAmount;
    private int maxArgsAmount;

    BookAction(String strValue, int minArgsAmount, int maxArgsAmount) {
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
