package ai.lazero.lazero.m6;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;

public class BubbleService extends Service {

    private WindowManager windowManager;
    private basic_overlay button;
    //    private WindowManager windowManager0;
    private basic_overlay_v2 button0;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        showOver();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(button0);
        windowManager.removeView(button);
    }

    private void showOver() {
        button = new basic_overlay(this,"blue");
//        button = new Button(this);
        button.setBackgroundColor(Color.BLUE);
        button.setAlpha((float)0.5);
//        button.setBackgroundColor(Color.BLACK);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
// what is accessibility_overlay anyway?
        layoutParams.width = 100;
        layoutParams.height = 100;
        layoutParams.x = 0 ;
        layoutParams.y = 0 ;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP ;
//        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
//        layoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.format = PixelFormat.RGBA_8888 | PixelFormat.TRANSLUCENT;
// check if works.

        button0 = new basic_overlay_v2(this,"red",layoutParams,button,windowManager);
//        button = new Button(this);
//        getWin
        button0.setBackgroundColor(Color.RED);
        button0.setAlpha((float)0.2);
//        button.setBackgroundColor(Color.BLACK);
//        windowManager0 = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParam = new WindowManager.LayoutParams();
        layoutParam.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
// what is accessibility_overlay anyway?
        layoutParam.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParam.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParam.x = 0 ;
        layoutParam.y = 0 ;
        layoutParam.gravity = Gravity.RIGHT | Gravity.TOP ;
//        layoutParams.gravity = Gravity.BOTTOM;
        layoutParam.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
//        layoutParam.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
//        layoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        // WE'LL SET A FLOATING BUBBLE.
        layoutParam.format = PixelFormat.RGBA_8888 | PixelFormat.TRANSLUCENT;


        /*
        spliter?
        */

// check if works.
//windowManager.
        windowManager.addView(button, layoutParams);
        windowManager.addView(button0, layoutParam);


    }
}
