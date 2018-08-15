package com.itheima.domain;

import org.itheima.game.DrawUtils;

import java.io.IOException;

public class Grass extends Element{

    private String srcPath = "TankWar2/res/img/grass.gif";

    public Grass() {
    }

    public Grass(int x, int y) {
       this.x = x;
       this.y = y;
        try {
            int[] arrSize = DrawUtils.getSize(srcPath);
            this.width = arrSize[0];
            this.height = arrSize[1];
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void draw(){
        try {
            DrawUtils.draw(srcPath,x,y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
