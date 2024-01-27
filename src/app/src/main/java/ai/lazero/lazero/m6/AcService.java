package ai.lazero.lazero.m6;

import android.accessibilityservice.AccessibilityService;
//import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import ai.lazero.lazero.AccessibilityUtil;
import ai.lazero.lazero.MyService;
import ai.lazero.lazero.R;

public class AcService extends AccessibilityService {
    private WindowManager windowManager;
    public FrameLayout mLayout;
    //    private basic_overlay button;
//    //    private WindowManager windowManager0;
//    private basic_overlay_v2 button0;
    public AcService() {
    }
public String TAG="hover_accessibility";
    @Override
    public void onCreate() {
//        super.onCreate();
        boolean samplex = AccessibilityUtil.isSettingOpen(AcService.class, this);
        if (samplex == true) {
            Log.e("XML_service", "onCreate!");
            super.onCreate();
            try{
            showOver();}catch (Exception e){e.printStackTrace();}
        } else {
            Log.i(TAG, "onDestroy: ");
            Toast.makeText(this, getString(R.string.aby_label) + "停止了，请重新开启", Toast.LENGTH_LONG).show();
            AccessibilityUtil.jumpToSetting(this);
        }}

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            boolean samplex = AccessibilityUtil.isSettingOpen(AcService.class, this);
            if (samplex == true) {
                Log.e("XML_service", "onCreate!");
//                super.onCreate();
                try{
                showOver();}catch (Exception e){e.printStackTrace();}
            } else {
                Log.i(TAG, "onDestroy: ");
                Toast.makeText(this, getString(R.string.aby_label) + "停止了，请重新开启", Toast.LENGTH_LONG).show();
                AccessibilityUtil.jumpToSetting(this);
            }
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(mLayout);
//        windowManager.removeView(button);
    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}

    private void showOver() {
//        this.getWindow().getDecorView();

        windowManager =  (WindowManager) getSystemService(WINDOW_SERVICE);
//        View decorView = windowManager.getDecorView();
//// Hide both the navigation bar and the status bar.
//// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
//// a general rule, you should design your app to hide the status bar whenever you
//// hide the navigation bar.
////        windowManager.getDefaultDisplay();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//        decorView.setSystemUiVisibility(uiOptions);

        mLayout = new FrameLayout(this);
        mLayout.setBackgroundColor(Color.GREEN);
        mLayout.setAlpha((float)0.2);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS | WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW;
//        lp.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.TOP;
        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Here I'm getting the touch events on the overlay I added
                // get the track!
                System.out.println("{ACCWINDOW} "+event.toString());
                return true;
            }
//            @Override
//            public boolean dispatchTouchEvent(MotionEvent event){
//
//            }
        });
//        mLayout.dispatchWindowSystemUiVisiblityChanged();
//        mLayout.
        windowManager.addView(mLayout, lp);
        // get a new tracker here.
        // check dispatch?
//        button = new basic_overlay(this,"blue");
////        button = new Button(this);
//        button.setBackgroundColor(Color.BLUE);
//        button.setAlpha((float)0.5);
////        button.setBackgroundColor(Color.BLACK);
//        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
//// what is accessibility_overlay anyway?
//        layoutParams.width = 100;
//        layoutParams.height = 100;
//        layoutParams.x = 0 ;
//        layoutParams.y = 0 ;
//        layoutParams.gravity = Gravity.LEFT | Gravity.TOP ;
////        layoutParams.gravity = Gravity.BOTTOM;
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
////        layoutParams.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
////        layoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//        layoutParams.format = PixelFormat.RGBA_8888 | PixelFormat.TRANSLUCENT;
//// check if works.
//
//        button0 = new basic_overlay_v2(this,"red",layoutParams,button,windowManager);
////        button = new Button(this);
//        button0.setBackgroundColor(Color.RED);
//        button0.setAlpha((float)0.2);
////        button.setBackgroundColor(Color.BLACK);
//        //this is really working? can adb work it out?
////        windowManager0 = (WindowManager) getSystemService(WINDOW_SERVICE);
//        WindowManager.LayoutParams layoutParam = new WindowManager.LayoutParams();
//        layoutParam.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
//// what is accessibility_overlay anyway?
//        layoutParam.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        layoutParam.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        layoutParam.x = 0 ;
//        layoutParam.y = 0 ;
//        layoutParam.gravity = Gravity.RIGHT | Gravity.TOP ;
////        layoutParams.gravity = Gravity.BOTTOM;
//        layoutParam.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
////        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
////        layoutParam.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
////        layoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//        // WE'LL SET A FLOATING BUBBLE.
//        layoutParam.format = PixelFormat.RGBA_8888 | PixelFormat.TRANSLUCENT;
//
//
//        /*
//        spliter?
//        */
//
//// check if works.
////windowManager.
//        windowManager.addView(button, layoutParams);
//        windowManager.addView(button0, layoutParam);


    }
    @Override
    public void onInterrupt() {
        // create accessibility view anyway?
    }
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}
