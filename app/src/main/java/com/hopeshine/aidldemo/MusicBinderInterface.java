package com.hopeshine.aidldemo;

/**
 * Created by User on 2017/10/9.
 */

public interface MusicBinderInterface {

    //播放音乐
    void play();

    //暂停播放音乐
    void pausePlay();

    //继续播放音乐
    void continuePlay();

    //修改音乐的播放位置
    void seekTo(int progress);

    //返回播放进度
    void getProgressIndex(OnProgressListener listener);
}
