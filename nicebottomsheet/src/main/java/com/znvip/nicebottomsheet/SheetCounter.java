package com.znvip.nicebottomsheet;

public class SheetCounter {

    private int count = 0;

    private SheetCounter() {
    }

    private static class SingleSheetCounter {
        private static SheetCounter INSTANCE = new SheetCounter();
    }

    public static SheetCounter getInstance() {
        return SingleSheetCounter.INSTANCE;
    }

    public int increase() {
        return count++;
    }
}