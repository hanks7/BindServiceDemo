package com.hopeshine.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.os.Environment.getExternalStorageDirectory;

public class MusicService extends Service {
    private MediaPlayer player;
    private Timer timer;
    OnProgressListener listener;
    public List<String> musicList;// 存放找到的所有mp3的绝对路径。


    //创建播放音乐的服务
    @Override
    public void onCreate() {
        super.onCreate();

        String MUSIC_PATH_String = getExternalStorageDirectory().getAbsolutePath() + "/abc/";
        File MUSIC_PATH = new File(MUSIC_PATH_String);//创建音乐播放器对象
        musicList = new ArrayList<>();
        player = new MediaPlayer();
        addMusicFiles(MUSIC_PATH);
    }

    /**
     * 和MainActivity使用同一个接口
     */
    class MusicBinderControl extends Binder implements MusicBinderInterface {
        @Override
        public void play() {

            MusicService.this.play();
        }

        @Override
        public void pausePlay() {

            MusicService.this.pausePlay();
        }

        @Override
        public void continuePlay() {

            MusicService.this.continuePlay();
        }

        @Override
        public void seekTo(int progress) {

            MusicService.this.seekTo(progress);
        }

        @Override
        public void setProgressListener(OnProgressListener listener) {
            MusicService.this.listener = listener;
        }
    }


    /**
     * 绑定服务时,返回了MusicInterface接口
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new MusicBinderControl();
    }



    //播放音乐
    public void play() {

        try {

            if (player == null) {
                player = new MediaPlayer();
            }

            //重置
            player.reset();

            //加载多媒体文件
            player.setDataSource(musicList.get(0));

            //准备播放音乐
            player.prepare();

            //播放音乐
            player.start();

            //添加计时器
            addTimer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //暂停播放音乐
    public void pausePlay() {
        player.pause();
    }

    //继续播放音乐
    public void continuePlay() {

        player.start();
    }

    //设置音乐的播放位置
    public void seekTo(int progress) {
        player.seekTo(progress);
    }

    //添加计时器用于设置音乐播放器中的播放进度
    public void addTimer() {

        //如果没有创建计时器对象
        if (timer == null) {

            //创建计时器对象
            timer = new Timer();

            timer.schedule(new TimerTask() {

                               //执行计时任务
                               @Override
                               public void run() {
                                   if (player == null) {
                                       timer.cancel();
                                       return;
                                   }
                                   //获得歌曲总时长
                                   int duration = player.getDuration();

                                   //获得歌曲的当前播放进度
                                   int currentPosition = player.getCurrentPosition();


                                   listener.onProgress(duration, currentPosition);

                               }
                           },

                    //开始计时任务后的5毫秒，第一次执行run方法，以后每500毫秒执行一次
                    5, 500);
        }
    }


    //销毁播放音乐服务
    @Override
    public void onDestroy() {
        super.onDestroy();

        //停止播放音乐
        player.stop();

        //释放占用的资源
        player.release();

        //将player置为空
        player = null;
    }

    /**
     * 添加播放音乐的文件
     *
     * @param MUSIC_PATH
     */
    private void addMusicFiles(File MUSIC_PATH) {
        if (MUSIC_PATH.listFiles(new MusicFilter()) != null && MUSIC_PATH.listFiles(new MusicFilter()).length > 0) {
            for (File file : MUSIC_PATH.listFiles(new MusicFilter())) {
                musicList.add(file.getAbsolutePath());
            }
        }
    }

    /**
     * 文件过滤器,只过滤.MP3的文件
     */
    class MusicFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3"));//返回当前目录所有以.mp3结尾的文件
        }
    }
}
