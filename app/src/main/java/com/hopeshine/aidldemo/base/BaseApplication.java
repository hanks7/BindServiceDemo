package com.hopeshine.aidldemo.base;

import android.app.Application;

/**
 * @author 侯建军 47466436@qq.com
 * @class com.easyway.mdhdatasyncc.base.BaseApplication
 * @time 2018/10/23 16:59
 * @description 请填写描述
 */
public class BaseApplication extends Application {
    public static BaseApplication APP;


    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;
    }

    public static BaseApplication getIntance() {
        return APP;
    }


}
