package com.itheima.domain;

import com.itheima.inter.Blockable;
import org.itheima.game.DrawUtils;

import java.io.IOException;

public class Water extends Element implements Blockable{

    private String srcPath = "TankWar2/res/img/water.gif";

    public Water() {
    }

    public Water(int x, int y) {
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
}
