package com.hopeshine.aidldemo;

/**
 * @author 侯建军 47466436@qq.com
 * @class com.hopeshine.aidldemo.OnProgressListener
 * @time 2019/4/19 11:13
 * @description 请填写描述
 */
public interface OnProgressListener {


    /**
     * @param duration        歌曲的总时长
     * @param currentPosition 歌曲的当前进度
     */
    void onProgress(int duration, int currentPosition);
}
