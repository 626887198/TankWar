package com.itheima.domain;

import com.itheima.inter.Destroy;
import org.itheima.game.DrawUtils;

import java.io.IOException;

public class Blast extends Element implements Destroy{

    String[] arr = {"TankWar2/res/img/blast_1.gif","TankWar2/res/img/blast_2.gif","TankWar2/res/img/blast_3.gif",
            "TankWar2/res/img/blast_4.gif","TankWar2/res/img/blast_5.gif","TankWar2/res/img/blast_6.gif",
            "TankWar2/res/img/blast_7.gif",
            "TankWar2/res/img/blast_8.gif"};
    int len = arr.length;
    int index = 0;
    boolean flag = false;
    public Blast(){

    }

    public Blast(int qx,int qy,int qw,int qh,int blood){
        try {
            int[] arr = DrawUtils.getSize("TankWar2\\res\\img\\blast_1.gif");
            this.width = arr[0];
            this.height = arr[1];
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

        this.x = qx - (this.width - qw) / 2;
        this.y = qy - (this.height - qh) / 2;

        if(blood > 0){
            len = 4;
        }
    }

    @Override
    public void draw() {
        if(index > len-1){
            index = 0;
            flag = true;
        }
        try {
            DrawUtils.draw(arr[index],x,y);
        } catch (IOException e) {
            e.printStackTrace();
        }
        index++;
    }

   public boolean isDestroy() {
        return flag;
    }
}
