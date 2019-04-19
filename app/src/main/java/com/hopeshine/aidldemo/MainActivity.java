package com.hopeshine.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hopeshine.aidldemo.utils.MyRunnable;

public class MainActivity extends AppCompatActivity {
    ServiceConnection myServiceConn;
    Intent intent;
    MusicBinderInterface musicInfBinder;

    //用于设置音乐播放器的播放进度
    private SeekBar sb;

    private TextView tv_progress;
    private TextView tv_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_total = (TextView) findViewById(R.id.tv_total);

        bindService();

        intView();

    }


    /**
     * 初始化服务
     */
    private void bindService() {
        intent = new Intent(this, MusicService.class);//创建意图对象
//        startService(intent);//启动服务
        myServiceConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //获得中间人对象
                musicInfBinder = (MusicBinderInterface) service;
                musicInfBinder.getProgressIndex(new OnProgressListener() {
                    @Override
                    public void onProgress(int duration, int currentPosition) {
                        handleTask(duration, currentPosition);
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };//创建服务连接对象
        bindService(intent, myServiceConn, BIND_AUTO_CREATE);//绑定服务
    }

    /**
     * 初始化控件
     */
    private void intView() {
        sb = (SeekBar) findViewById(R.id.sb);//获得布局文件上的滑动条

        //为滑动条添加事件监听
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //当滑动条中的进度改变后,此方法被调用
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            //滑动条刚开始滑动,此方法被调用
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //当滑动条停止滑动,此方法被调用
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                //根据拖动的进度改变音乐播放进度
                int progress = seekBar.getProgress();

                //改变播放进度
                musicInfBinder.seekTo(progress);
            }
        });
    }


    /**
     * hanlder处理的业务
     */
    private void handleTask(int duration, int currentPostition) {

        //刷新滑块的进度
        sb.setMax(duration);
        sb.setProgress(currentPostition);

        //歌曲的总时长
        int minute = duration / 1000 / 60;
        int second = duration / 1000 % 60;

        String strMinute = null;
        String strSecond = null;

        //如果歌曲的时间中的分钟小于10
        if (minute < 10) {

            //在分钟的前面加一个0
            strMinute = "0" + minute;
        } else {

            strMinute = minute + "";
        }

        //如果歌曲的时间中的秒钟小于10
        if (second < 10) {
            //在秒钟前面加一个0
            strSecond = "0" + second;
        } else {

            strSecond = second + "";
        }

        runOnUiThread(new MyRunnable(strMinute, strSecond) {
            @Override
            public void myRun(String finalStrMinute, String finalStrSecond) {
                tv_total.setText(finalStrMinute + ":" + finalStrSecond);
            }
        });


        //歌曲当前播放时长
        minute = currentPostition / 1000 / 60;
        second = currentPostition / 1000 % 60;

        //如果歌曲的时间中的分钟小于10
        if (minute < 10) {

            //在分钟的前面加一个0
            strMinute = "0" + minute;
        } else {

            strMinute = minute + "";
        }

        //如果歌曲的时间中的秒钟小于10
        if (second < 10) {

            //在秒钟前面加一个0
            strSecond = "0" + second;
        } else {

            strSecond = second + "";
        }
        runOnUiThread(new MyRunnable(strMinute, strSecond) {
            @Override
            public void myRun(String finalStrMinute, String finalStrSecond) {
                tv_progress.setText(finalStrMinute + ":" + finalStrSecond);
            }
        });
    }

    //播放音乐按钮响应函数
    public void play(View view) {

        //播放音乐
        musicInfBinder.play();
    }

    //暂停播放音乐按钮响应函数
    public void pausePlay(View view) {

        //暂停播放音乐
        musicInfBinder.pausePlay();
    }

    //继续播放音乐按钮响应函数
    public void continuePlay(View view) {

        //继续播放音乐
        musicInfBinder.continuePlay();
    }

    //退出音乐播放按钮响应函数
    public void exit(View view) {

        //解绑服务
        unbindService(myServiceConn);

        //停止服务
        stopService(intent);

        //结束这个activity
        finish();
    }

}
