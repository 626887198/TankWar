package com.itheima.domain;

import com.itheima.inter.*;
import org.itheima.game.CollsionUtils;
import org.itheima.game.DrawUtils;

import java.io.IOException;
import java.util.Collections;

public class MyTank extends Tank implements Moveable,Hitable,Destroy{
    public int speed = 32;
    private String srcPath1 = "TankWar2/res//img//tank_u.gif";
    private String srcPath2 = "TankWar2/res//img//tank_d.gif";
    private String srcPath3 = "TankWar2/res//img//tank_l.gif";
    private String srcPath4 = "TankWar2/res//img//tank_r.gif";


    public MyTank() {
    }

    public MyTank(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            int[] arrSize = DrawUtils.getSize(srcPath1);
            this.width = arrSize[0];
            this.height = arrSize[1];
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void draw() {
        String srcPath = "";
        switch (this.direction) {
            case UP:
                srcPath = srcPath1;
                break;
            case DOWN:
                srcPath = srcPath2;
                break;
            case LEFT:
                srcPath = srcPath3;
                break;
            case RIGHT:
                srcPath = srcPath4;
                break;
        }
        try {
            DrawUtils.draw(srcPath, x, y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(Direction direction) {
        if(this.badDirection == direction && this.badDirection != null){
            switch (direction) {
                case UP:
                    y -= badSpeed;
                    break;
                case DOWN:
                    y += badSpeed;
                    break;
                case LEFT:
                    x -= badSpeed;
                    break;
                case RIGHT:
                    x += badSpeed;
                    break;
            }
            return;
        }

        if (this.direction != direction) {
            this.direction = direction;// 当传进来的方向和坦克原有的方向不一致的时候,需要记录坦克的方向,并且不移动
            return;
        }
        switch (direction) {
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
        }

        if (x < 0) {
            x = 0;
        }

        if (x > Config.WIDTH - 64) {
            x = Config.WIDTH - 64;
        }

        if (y < 0) {
            y = 0;
        }

        if (y > Config.HEIGHT - 64) {
            y = Config.HEIGHT - 64;
        }
    }

    public Bullet shot() {// 发射子弹
        long nowTime = System.currentTimeMillis();
        if (nowTime - lastTime < 300) {
            return null;
        } else {
            lastTime = nowTime;
            return new Bullet(this);
        }
    }


    public boolean checkHit(Blockable blockable) {
        Element e = (Element)blockable;
        int x1 = this.x;
        int y1 = this.y;
        int w1 = this.width;
        int h1 = this.height;

        switch (this.direction){
            case UP:
                y1 -= speed;
                break;
            case DOWN:
                y1 += speed;
                break;
            case LEFT:
                x1 -= speed;
                break;
            case RIGHT:
                x1 += speed;
                break;
        }

        int x2 = e.x;
        int y2 = e.y;
        int w2 = e.width;
        int h2 = e.height;

        boolean res = CollsionUtils.isCollsionWithRect(x1, y1, w1, h1, x2, y2, w2, h2);

        if(res){
            // 说明已经碰撞上了
            this.badDirection = direction;
            switch (this.direction){
                case UP:
                    badSpeed = this.y - y2 - h2;
                    break;
                case DOWN:
                    badSpeed = y2 - this.y - this.height;
                    break;
                case LEFT:
                    badSpeed = this.x - x2 - w2;
                    break;
                case RIGHT:
                    badSpeed = x2 - this.x - this.width;
                    break;
            }
        }else{
            // 说明没有碰撞上
            this.badDirection = null;
        }
        return res;
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
        }else{
            return false;
        }
    }
}
