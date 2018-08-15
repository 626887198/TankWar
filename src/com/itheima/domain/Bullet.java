package com.itheima.domain;

import com.itheima.inter.*;
import org.itheima.game.CollsionUtils;
import org.itheima.game.DrawUtils;
import org.itheima.game.SoundUtils;

import java.io.IOException;

public class Bullet extends Element implements Attackable,Destroy,Hitable{

    private String srcPath1 = "TankWar2/res//img//bullet_d.gif";
    private String srcPath2 = "TankWar2/res//img//bullet_u.gif";
    private String srcPath3 = "TankWar2/res//img//bullet_l.gif";
    private String srcPath4 = "TankWar2/res//img//bullet_r.gif";
    private Direction direction = Direction.UP;
    private int speed = 6;
    private int blood = 1;
    public Tank tank;
    public Bullet() {
    }

    /*
	 	向上发射子弹
				zx = tx + (tw - zw)/2;
				zy = ty - zh;
		向下发射子弹:
				zx = tx + (tw - zw)/2;
				zy = ty + th;
		向左发射子弹:
				zx = tx - zw;
				zy = ty + (th - zh)/2;
		向右发射子弹:
				zx = tx + tw;
				zy = ty + (th - zh)/2;
	 */
    public Bullet(Tank mt) {
        this.tank = mt;
        int tx = mt.x;
        int ty = mt.y;
        int tw = mt.width;
        int th = mt.height;
        this.direction = mt.direction;
        switch (this.direction){
            case UP:
                try {
                    int[] size = DrawUtils.getSize(srcPath1);
                    this.width = size[0];
                    this.height = size[1];
                    this.x = tx + (tw - this.width)/2;
                    this.y = ty - this.height;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case DOWN:
                try {
                    int[] size = DrawUtils.getSize(srcPath2);
                    this.width = size[0];
                    this.height = size[1];
                    this.x = tx + (tw - this.width)/2;
                    this.y = ty + th;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case LEFT:
                try {
                    int[] size = DrawUtils.getSize(srcPath3);
                    this.width = size[0];
                    this.height = size[1];
                    this.x = tx - this.width;
                    this.y = ty + (th - this.height)/2;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case RIGHT:
                try {
                    int[] size = DrawUtils.getSize(srcPath4);
                    this.width = size[0];
                    this.height = size[1];
                    this.x = tx + tw;
                    this.y = ty + (th - this.height)/2;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        try {
            SoundUtils.play("TankWar2\\res\\snd\\fire.wav");
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    public void move(){
        switch (this.direction){
            case UP:
                this.y -= speed;
                break;
            case DOWN:
                this.y += speed;
                break;
            case LEFT:
                this.x -= speed;
                break;
            case RIGHT:
                this.x += speed;
                break;
        }
    }

    public boolean isDestroy(){
        if(x < 0 || y < 0 || x > Config.WIDTH || y > Config.HEIGHT || blood < 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void draw() {
        String srcPath = "";
        switch (this.direction){
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
            DrawUtils.draw(srcPath,x,y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkAttack(Hitable hitable) {
        Element e = (Element)hitable;
        int x1 = this.x;
        int y1 = this.y;
        int w1 = this.width;
        int h1 = this.height;

        int x2 = e.x;
        int y2 = e.y;
        int w2 = e.width;
        int h2 = e.height;

        boolean res = CollsionUtils.isCollsionWithRect(x1,y1,w1,h1,x2,y2,w2,h2);

        return res;
    }

    @Override
    public Blast showBlast() {
        blood--;
        return new Blast(x,y,width,height,blood);
    }
}
