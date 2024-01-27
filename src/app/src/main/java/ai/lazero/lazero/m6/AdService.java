        package ai.lazero.lazero.m6;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import ai.lazero.lazero.AccessibilityUtil;
import ai.lazero.lazero.R;

//import android.app.Service;

public class AdService extends AccessibilityService {
//    private WindowManager windowManager;
    public boolean checker = false;
//    public FrameLayout mLayout;
    public Thread thread = null;
    //    private basic_overlay button;
//    //    private WindowManager windowManager0;
//    private basic_overlay_v2 button0;
    public AdService() {
    }
public String TAG="hover_accessibility";
    @Override
    public void onCreate() {
//        super.onCreate();
        boolean samplex = AccessibilityUtil.isSettingOpen(AdService.class, this);
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
            boolean samplex = AccessibilityUtil.isSettingOpen(AdService.class, this);
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
//        windowManager.removeView(mLayout);
        if (thread!=null){thread=null;}
//        windowManager.removeView(button);
    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}
public void dpGesture(){
    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

    int middleYValue = displayMetrics.heightPixels / 2;
    final int leftSideOfScreen = displayMetrics.widthPixels / 4;
    final int rightSizeOfScreen = leftSideOfScreen * 3;
    GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
    Path path = new Path();

//    if (event.getText() != null && event.getText().toString().contains("1")) {
        //Swipe left
        path.moveTo(rightSizeOfScreen, middleYValue);
//        path.lineTo(leftSideOfScreen, middleYValue);
//    } else {
//        //Swipe right
//        path.moveTo(leftSideOfScreen, middleYValue);
//        path.lineTo(rightSizeOfScreen, middleYValue);
//    }

    gestureBuilder.addStroke(new GestureDescription.StrokeDescription(path, 100, 50));
    dispatchGesture(gestureBuilder.build(), new GestureResultCallback() {
        @Override
        public void onCompleted(GestureDescription gestureDescription) {
            Log.w(TAG,"Gesture Completed");
            super.onCompleted(gestureDescription);
        }
    }, null);
}
public void startThread(){
    thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dpGesture();
            }
        }
    });
    thread.start();
}
    private void showOver() {
        if (!this.checker){
            this.checker = true;
            if (thread == null) {
startThread();
            }else{
                thread = null;
                startThread();
            }
        }else if (!thread.isAlive()){
            thread = null;
            startThread();
        }
// start a separate thread for dispatching gesture.

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
