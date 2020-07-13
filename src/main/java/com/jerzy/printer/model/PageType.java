package com.jerzy.printer.model;

public class PageType {
    boolean front;
    boolean gridded;

    public PageType(boolean front, boolean gridded) {
        this.front = front;
        this.gridded = gridded;
    }

    public boolean isFront() {
        return front;
    }

    public boolean isGridded() {
        return gridded;
    }
}
