package com.itheima.domain;

import com.itheima.inter.Blockable;
import com.itheima.inter.Direction;

public class Tank extends Element implements Blockable{


    public Direction direction = Direction.UP;
    public Direction badDirection;
    public int badSpeed;
    public long lastTime;
    public int blood = 3;

    public Tank() {
    }

    public Tank(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw() {

    }
}
