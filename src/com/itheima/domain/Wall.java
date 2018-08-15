package com.itheima.domain;

import com.itheima.inter.Blockable;
import com.itheima.inter.Destroy;
import com.itheima.inter.Hitable;
import org.itheima.game.DrawUtils;

import java.io.IOException;

public class Wall extends Element implements Blockable,Hitable,Destroy{


    private String srcPath = "TankWar2/res/img/wall.gif";

    private int blood = 3;

    public Wall() {
    }

    public Wall(int x, int y) {
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
    public Blast showBlast() {
        blood--;
        return new Blast(x,y,width,height,blood);
    }

    @Override
    public boolean isDestroy() {
        if(blood < 1){
            return true;
        }
        return false;
    }
}
