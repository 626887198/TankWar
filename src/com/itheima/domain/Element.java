package com.itheima.domain;

public abstract class Element {
    protected int x;
    protected int width;
    protected int y;
    protected int height;

    public Element() {
    }

    public Element(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw();

    public int getLevel(){
        return 0;
    }
}
