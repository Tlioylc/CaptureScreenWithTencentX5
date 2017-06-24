package com.tlioylc.capturescreen;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by Administrator on 2017/6/23.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //加载X5内核
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " X5加载结果 " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }
}
