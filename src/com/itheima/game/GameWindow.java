package com.itheima.game;

import com.itheima.domain.*;
import com.itheima.inter.*;
import org.itheima.game.DrawUtils;
import org.itheima.game.SoundUtils;
import org.itheima.game.Window;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWindow extends Window {
    CopyOnWriteArrayList<Element> list = new CopyOnWriteArrayList<>();
    MyTank mt;

    public GameWindow(String title, int width, int height, int fps) {
        super(title, width, height, fps);
    }

    @Override
    protected void onCreate() {
        for(int i = 0;i< Config.WIDTH/64-1;i++){
            Wall wall = new Wall(i*64,64);
            list.add(wall);
        }

        for(int i = 1;i< Config.WIDTH/64;i++){
            Water water = new Water(i*64,64*3);
            list.add(water);
        }

        for(int i = 0;i< Config.WIDTH/64-1;i++){
            Steel steel = new Steel(i*64,64*5);
            list.add(steel);
        }

        for(int i = 1;i< Config.WIDTH/64;i++){
            Grass grass = new Grass(i*64,64*7);
            list.add(grass);
        }

        mt = new MyTank(Config.WIDTH/2-32,Config.HEIGHT-64);
        list.add(mt);
        EnemyTank et1 = new EnemyTank(0,0);
        EnemyTank et2= new EnemyTank(Config.WIDTH-64,0);
        EnemyTank et3= new EnemyTank(Config.WIDTH-64,128);

        list.add(et1);
        list.add(et2);
        list.add(et3);


        /*
        根据渲染级别对集合进行排序,渲染级别越高的元素,就排在最后,那么也就意味着越晚绘制
         */
        Collections.sort(list, new Comparator<Element>() {
            @Override
            public int compare(Element o1, Element o2) {
                return o1.getLevel() - o2.getLevel();
            }
        });

    }

    @Override
    protected void onMouseEvent(int key, int x, int y) {

    }

    @Override
    protected void onKeyEvent(int key) {
        if(key == Keyboard.KEY_UP){
            mt.move(Direction.UP);
        }else if(key == Keyboard.KEY_DOWN){
            mt.move(Direction.DOWN);
        }else if(key == Keyboard.KEY_LEFT){
            mt.move(Direction.LEFT);
        }else if(key == Keyboard.KEY_RIGHT){
            mt.move(Direction.RIGHT);
        }else if(key == Keyboard.KEY_SPACE){
            Bullet bullet = mt.shot();
            if(bullet != null){
                list.add(bullet);
            }
        }
    }

    @Override
    protected void onDisplayUpdate() {
        if(mt.isDestroy()){
            list.clear();
            try {
                DrawUtils.draw("TankWar3/res/img/01.jpg",0,0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Element e : list) {
            e.draw();
            if(e instanceof Bullet){
                ((Bullet) e).move();
            }

            if(e instanceof EnemyTank){
                ((EnemyTank) e).move();
                int num = new Random().nextInt(33);
                if(num == 6){
                    Bullet bullet = ((EnemyTank) e).shot();
                    if(bullet != null){
                        list.add(bullet);
                    }
                }
            }
        }
        for (Element e : list) {
            if(e instanceof Destroy){
                boolean res = ((Destroy) e).isDestroy();
                if(res == true){
                    list.remove(e);
                }
            }
        }

        for (Element e1 : list) {
            if(e1 instanceof Moveable){
                for (Element e2 : list) {
                    if(e2 instanceof Blockable){
                        if(e1 == e2){
                           continue;
                        }
                        Blockable blockable = (Blockable)e2;
                        Moveable moveable = (Moveable)e1;
                        boolean res = moveable.checkHit(blockable);
                        if(res){
                            System.out.println("已经碰撞上了");
                            break;
                        }
                    }
                }
            }
        }

        for(Element e1 : list){
            if(e1 instanceof Attackable){
                for (Element e2 : list) {
                    if(e2 instanceof Hitable){

                        if(e1 == e2){
                            continue;
                        }

                        Bullet bullet = (Bullet)e1;
                        if(bullet.tank.getClass() == e2.getClass()){
                            continue;
                        }

                        if(e2 instanceof Bullet){
                            Bullet bullet1 = (Bullet) e2;
                            if(bullet.tank.getClass() == bullet1.tank.getClass()){
                                continue;
                            }
                        }

                        Attackable attackable = (Attackable)e1;
                        Hitable hitable = (Hitable)e2;
                        boolean res = attackable.checkAttack(hitable);
                        if(res){
                            // 爆炸物遗留问题: 爆炸物的位置,爆炸物图片绘制个数,爆炸物绘制次数
                            Blast blast = hitable.showBlast();
                            list.add(blast);
                            list.remove(e1);
                            break;
                        }
                    }
                }
            }
        }

//        System.out.println(list.size());
    }
}
