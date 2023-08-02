package com.andersen.enums.actions;

public enum OrderAction implements CommandAction {
    
    LIST("list", 1, 1),
    COMPLETE("complete", 1, 1),
    CREATE("create", 0, 0),
    CANCEL("cancel", 1, 1);

    private String strValue;
    private int minArgsAmount;
    private int maxArgsAmount;

    OrderAction(String strValue, int minArgsAmount, int maxArgsAmount) {
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
