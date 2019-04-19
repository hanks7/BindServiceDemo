// AIDLtest.aidl
package com.hopeshine.aidldemo;


interface AIDLtest {  // 这个简单类型以 String 为例
    //播放音乐
       void play();

       //暂停播放音乐
       void pausePlay();

       //继续播放音乐
       void continuePlay();

       //修改音乐的播放位置
       void seekTo(int progress);
}
