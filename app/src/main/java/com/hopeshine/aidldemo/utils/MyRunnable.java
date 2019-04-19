package com.hopeshine.aidldemo.utils;

/**
 * @author 侯建军 47466436@qq.com
 * @class com.hopeshine.aidldemo.utils.MyRunnable
 * @time 2019/4/19 13:24
 * @description 请填写描述
 */
public abstract class MyRunnable implements Runnable {
    String finalStrMinute;
    String finalStrSecond;

    public MyRunnable(String finalStrMinute, String finalStrSecond) {
        this.finalStrMinute = finalStrMinute;
        this.finalStrSecond = finalStrSecond;
    }

    private MyRunnable() {
    }

    @Override
    public void run() {
        myRun(finalStrMinute, finalStrSecond);
    }

    public abstract void myRun(String finalStrMinute, String finalStrSecond);
}
