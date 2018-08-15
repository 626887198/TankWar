package com.itheima.game;

import com.itheima.inter.Config;

public class App1 {
    public static void main(String[] args) {
        GameWindow gw = new GameWindow(Config.TITLE,Config.WIDTH,Config.HEIGHT,Config.FPS);
        gw.start();
    }
}
