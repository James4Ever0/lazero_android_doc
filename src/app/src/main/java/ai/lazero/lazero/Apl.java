package ai.lazero.lazero;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;

import me.weishu.reflection.Reflection;

//import java.lang.ref.WeakReference;

public class Apl extends Application {
    /*
    Application创建时被调用，可以在该方法里进行一些初始化操作
     */
    private static Apl instance;
    private MyReceiver myReceiver;
    private IntentFilter intentFilter;
    // does this work?
//    Type<T> = boolean;
    public boolean screenShotService=false;

    public void setMyPublicData(boolean _d){
//        screenShotService = null;
        screenShotService = _d;
    }

    public boolean getMyPublicData(){
        return screenShotService;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Reflection.unseal(base);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Reflection.unseal(this);
        instance=this;
        System.out.println("MAIN APP LAUNCHED!");
        myReceiver = new MyReceiver();
        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //当网络发生变化的时候，系统广播会发出值为android.net.conn.CONNECTIVITY_CHANGE这样的一条广播
        intentFilter.addAction("ai.lazero.lazero.recreate");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        registerReceiver(myReceiver,intentFilter);
//        myReceiver.onReceive();
        // the api has been modified.
        // no easy way?
        // check it out later on.
    }
    // 获取Application
    public static Context getApplication() {
        return instance;
    }
    // no zero argument constructor.
    /*
    系统配置发生变更 的时候被调用 如：屏幕方向更改 、系统语言更改
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /*
    系统内存吃紧时被调用，用于释放内存
     */
    @Override
    public void onTerminate(){
        unregisterReceiver(myReceiver);
        super.onTerminate();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.out.println("SYSTEM GARBAGE COLLECTION");
    }
}