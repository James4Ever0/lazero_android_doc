package ai.lazero.lazero.m6;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.util.Date;

import ai.lazero.lazero.AccessibilityUtil;
import ai.lazero.lazero.R;

import static ai.lazero.lazero.MainActivity.upgradeRootPermission;

//import android.app.Service;

public class AeService extends AccessibilityService {
    //    private WindowManager windowManager;
    public boolean checker = false;
    public int counter = 0;
    public boolean simplex = false;
    public Thread thread = null;
    public String TAG = "blocker_accessibility";
    public static String[] blocker = {"cd /dev/input && mkdir ../dummy", "cd /dev/input && ln -n event1 ../dummy/event1 ", "cd /dev/input && rm -rf event1"};
    public static String[] unblocker = {"cd /dev/input && ln -n ../dummy/event1 event1"};
    public long historical = 0;

    //    private basic_overlay button;
    //    //    private WindowManager windowManager0;
    //    private basic_overlay_v2 button0;
    public AeService() {
    }

    //    public FrameLayout mLayout;
    public static boolean RootPermission(String[] pkgCodePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
//                String cmd = pkgCodePath;
            process = Runtime.getRuntime().exec("su"); //切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            for (String cmd : pkgCodePath) {
                //Do your stuff here
                os.writeBytes(cmd + "\n");
                Thread.sleep(100);
                os.flush();
            }
            Thread.sleep(100);
            os.writeBytes("exit\n");
            Thread.sleep(100);
            os.flush();
            process.waitFor();
            // not doing shit.
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }


    @Override
    public void onCreate() {
        //        super.onCreate();
        boolean samplex = AccessibilityUtil.isSettingOpen(AeService.class, this);
        if (samplex == true) {
            Log.e("XML_service", "onCreate!");
            boolean k = upgradeRootPermission(getPackageCodePath());
            simplex = k;
            super.onCreate();
//                    if (k == true) {
//                        Snackbar.make(view, "Root acquire success", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                    } else {
//                        Snackbar.make(view, "Failed to acquire root", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                    }
//                    try{
//                    showOver();}catch (Exception e){e.printStackTrace();}
        } else {
            Log.i(TAG, "onDestroy: ");
            Toast.makeText(this, getString(R.string.aby_label) + "停止了，请重新开启", Toast.LENGTH_LONG).show();
            AccessibilityUtil.jumpToSetting(this);
        }
    }

    public boolean getStat(long limit) {
        Date date = new Date();
        long current = date.getTime();
        if (historical == 0) {
            historical = current;
            return true;
        } else {
            long subtract = current - historical;
            historical = current;
            if (subtract <= limit) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean onKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        if (action == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                if (simplex) {
                    if (counter >= 0) {
                        if (getStat(500)) {
                            if (counter < 3) {
                                counter++;
                            }
                        } else {
                            counter = 0;
                        }
                    } else {
                        counter = 0;
                    }
                    if (counter == 3) {
                        Log.d("Hello", "UNBLOCKER TRIGGERED");
                        if (thread == null) {
                            thread = null;
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    RootPermission(unblocker);
                                }
                            });
                            thread.start();
                            checker = true;
                        } else if (thread != null && !thread.isAlive()) {
                            thread = null;
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    RootPermission(unblocker);
                                }
                            });
                            thread.start();
                            checker = true;
                        } else {
                            Log.d("Hello", "FAILED TO TRIGGER");
                        }
                        Log.d("Hello", "KeyUp");
                    }
                } else {
                    Log.d("Hello", "No Root Permission");
                }
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                if (simplex) {
                    if (counter <= 0) {
                        if (getStat(500)) {
                            if (counter > -3) {
                                counter--;
                            }
                        } else {
                            counter = 0;
                        }
                    } else {
                        counter = 0;
                    }
                    if (counter == -3) {
                        Log.d("Hello", "BLOCKER TRIGGERED");
                        if (thread == null) {
                            thread = null;
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    RootPermission(blocker);
                                }
                            });
                            thread.start();
                            checker = true;
                        } else if (thread != null && !thread.isAlive()) {
                            thread = null;
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    RootPermission(blocker);
                                }
                            });
                            thread.start();
                            checker = true;
                        } else {
                            Log.d("Hello", "FAILED TO TRIGGER");
                        }
                    }
                    Log.d("Hello", "KeyDown");
                } else {
                    Log.d("Hello", "No Root Permission");
                }
            }
//            return true;
            return super.onKeyEvent(event);
        } else {
            // block the power key?
            return super.onKeyEvent(event);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean samplex = AccessibilityUtil.isSettingOpen(AeService.class, this);
        if (samplex == true) {
            Log.e("XML_service", "onCreate!");
            boolean k = upgradeRootPermission(getPackageCodePath());
            simplex = k;
            //                super.onCreate();
//                        try{
//                        showOver();}catch (Exception e){e.printStackTrace();}
        } else {
            Log.i(TAG, "onDestroy: ");
            Toast.makeText(this, getString(R.string.aby_label) + "停止了，请重新开启", Toast.LENGTH_LONG).show();
            AccessibilityUtil.jumpToSetting(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //        windowManager.removeView(mLayout);
//                if (thread!=null){thread=null;}
        //        windowManager.removeView(button);
    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }


    private void showOver() {
//                if (!this.checker){
//                    this.checker = true;
//                    if (thread == null) {
//        startThread();
//                    }else{
//                        thread = null;
//                        startThread();
//                    }
//                }else if (!thread.isAlive()){
//                    thread = null;
//                    startThread();
//                }
        // start a separate thread for dispatching gesture.

    }

    @Override
    public void onInterrupt() {
        // create accessibility view anyway?
    }

}
