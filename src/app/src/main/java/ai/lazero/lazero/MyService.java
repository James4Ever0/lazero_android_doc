package ai.lazero.lazero;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//import android.hardware.display.DisplayManager;
// bullshit.
// get the fuck.
// talk later. I do not believe in that thing. that parcel thing.
// write a separate class to post data to server.
// this is the main service, and we are sending something to the server.
public class MyService extends AccessibilityService {
    public PowerManager.WakeLock mWakeLock = null;
    public static final String TAG = "XML_monitor";
    public boolean threadDisable = true;
    public static MyService instance;
    public ByteClass byteClass = new ByteClass(false);
    public HttpPostBytes httpPostBytes;
    public MyThread myThread;
    // do not shit.
    //private Handler handler = new Handler();
//    private android.os.Handler handler = new android.os.Handler();

    // all abstract shits.
// fucking idiots.
    public Notification note() {
        String CHANNEL_ID = "ai.lazero.lazero.AccessibilityService";
        String CHANNEL_NAME = "TEST";
        NotificationChannel notificationChannel = null;
        // you've got some foreground service. shit then.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(this, MyService2.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new Notification.Builder(this, CHANNEL_ID).
                setContentTitle("Lazero").
                setContentText("Accessibility service running.").
                setWhen(System.currentTimeMillis()).
                setSmallIcon(R.drawable.icon).
                setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).
                setContentIntent(pendingIntent).build();
        return notification;
    }// fuck these people?
//
//    private Runnable task = new Runnable() {
//        @Override
//        public void run() {
//
//            String a = util();
////    String a = disaster();
//            // either do automation or else.
////            Log.d("disaster",a);
//            // man find another way to get the content of termux or something?
//            sending(a);
//            // send what? print it out first.
////            handler.postDelayed(this,3000);
////            sending("sample_text_from_task");
//            // don't you flash too damn often.
//            handler.postDelayed(this, 3000);
//            System.gc();
//        }
//    };

    //    }
    public static ArrayList<Integer> getMetrics(Context context) {
//    List<int> n=new ArrayList<>();
        ArrayList<Integer> n = new ArrayList<Integer>();
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
//    manager.get
        n.add((Integer) manager.getDefaultDisplay().getRotation());
        n.add((Integer) metrics.widthPixels);
        n.add((Integer) metrics.heightPixels);
        return n;
    }
/*
    public void sending(String a) {
        Intent intent = new Intent();
// .");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setAction("ai.lazero.lazero.MyService");
        intent.putExtra("xml", a);
        int v = 0;
        while (v < 3) {
            try {
                sendBroadcast(intent);
                Log.v("VISIBLE SERVICE", "SENDING BMP");
                v = 3;
                break;
            } catch (Exception e) {
                e.printStackTrace();
                v = v + 1;
            }
        }
    }
*/
    // get them into a json or something?
    // print them all.
    /*
    public List<AccessibilityNodeInfo> walkNode(AccessibilityNodeInfo a) {
        List<AccessibilityNodeInfo> b = new ArrayList<>();
        try {
            int sample = a.getChildCount();
            for (int i = 0; i < sample; i++) {
                try {
                    AccessibilityNodeInfo child = a.getChild(i);
                    b.add(child);
                    String resName = child.getViewIdResourceName();
                    System.out.println("THE NODE NUM:" + String.valueOf(i));
                    sending("THE NODE NUM:" + String.valueOf(i));
                    Log.e(TAG, "THE NODE NUM:" + String.valueOf(i));
                    System.out.println("resName: " + resName);
                    sending("resName: " + resName);
                    Log.e(TAG, "resName: " + resName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
*/
        /*
    public List<AccessibilityNodeInfo> walkNodes(List<AccessibilityNodeInfo> a) {
        List<AccessibilityNodeInfo> b = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            List<AccessibilityNodeInfo> c = walkNode(a.get(i));
            for (int j = 0; j < c.size(); j++) {
                b.add(c.get(j));
            }
        }
        if (b.size() > 0) {
            return walkNodes(b);
        } else {
            return b;
        }
        // doing recursive things till it is done.
        // check how to walk through directories.
        // similar code and so on.
        // how the fuck does that work?
        // does it really use the thing?
    }*/

    @Override
    public void onCreate() {
        Notification notification = note();
        startForeground(3337, notification);
        Log.v("SERVICE", "START_SUCCESS");
//        serviceLogger("On create",0,0);
        // this is the destination.
        // need to open the port.
        httpPostBytes = new HttpPostBytes("http://192.168.43.131:4999/sample", null);
        myThread = new MyThread(httpPostBytes, byteClass, "type", "accessibilityNode");
        myThread.start();
        instance = this;
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        /**
         * PowerManager.PARTIAL_WAKE_LOCK:保持CPU运转，屏幕和键盘灯可能是关闭的
         * PowerManager.SCREEN_DIM_WAKE_LOCK:保持CPU运转,运行屏幕显示但是屏幕有可能是灰的，允许关闭键盘灯
         * PowerManager.SCREEN_BRIGHT_WAKE_LOCK：保持CPU运转，屏幕高亮显示，允许关闭键盘灯
         * PowerManager.FULL_WAKE_LOCK：保持CPU运转，屏幕高亮显示，键盘灯高亮显示
         * PowerManager.ON_AFTER_RELEASE：当锁被释放时，保持屏幕亮起一段时间
         * PowerManager.ACQUIRE_CAUSES_WAKEUP：强制屏幕亮起
         */
        // not allowed... background....
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AccessibilityServer");
//        KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        mWakeLock.acquire();
        // do that.
//        Context cxt= getBaseContext();
        // again, no fucking return. shall we use broadcast?
//        Class msv=MyService.class
        boolean samplex = AccessibilityUtil.isSettingOpen(MyService.class, this);
        if (samplex == true) {
            Log.e("XML_service", "onCreate!");
            super.onCreate();
        } else {
            Log.i(TAG, "onDestroy: ");
            Toast.makeText(this, getString(R.string.aby_label) + "停止了，请重新开启", Toast.LENGTH_LONG).show();
            AccessibilityUtil.jumpToSetting(this);
        }
        // there is no accessibilityEvent here.
    }

    // there is nothing to end.
    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        byteClass.screenshot_update = false;
        myThread.interrupt();
        myThread = null;
        Toast.makeText(this, getString(R.string.aby_label) + "停止了，请重新开启", Toast.LENGTH_LONG).show();
        // 服务停止，重新进入系统设置界面
//        AccessibilityUtil accessibilityUtil = new AccessibilityUtil();
        // root access button?
        AccessibilityUtil.jumpToSetting(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        threadDisable = true;
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        flags = START_STICKY;
//        handler.removeCallbacks(task);
//        handler.removeCallbacksAndMessages(null);
//        handler.postDelayed(task, 500);
//        handler.post(task);
        // maybe this task really works.
        // what is that task?
        // is this task really working?
        // get the message?
////        PendingIntent notificationIntent
//        Intent notificationIntentX = new Intent();
//        PendingIntent notificationIntent = PendingIntent.getActivity(this, 0, notificationIntentX, 0);
////        Intent notificationIntent = new Intent();
//        Notification noti = new Notification.Builder(this)
//                .setContentTitle("Title")
//                .setContentText("Message")
//                .setSmallIcon(R.drawable.icon)
//                .setContentIntent(notificationIntent)
//                .build();
//        startForeground(123456, noti);
//        main();
        // well, why restart every now and then?
        return super.onStartCommand(intent, flags, startId);
    }
/*
    public StringBuilder recycle(AccessibilityNodeInfo info, StringBuilder sb) {
        // use try blocks.
        try {
            sb.append("child widget----------------------------" + info.getClassName());
            sb.append("\n");
            sb.append("showDialog:" + info.canOpenPopup());
            sb.append("\n");
            sb.append("Text：" + info.getText().toString());
//        info.setTextSelection(0,1024*1024);
////        info.selec
//        info.
            // record the audio. play it back? as a service.
            sb.append("\n");
            sb.append("windowId:" + info.getWindowId());
            // what the fuck.
            sb.append("\n");
            if (info.getChildCount() != 0) {
                for (int i = 0; i < info.getChildCount(); i++) {
                    if (info.getChild(i) != null) {
                        recycle(info.getChild(i), sb);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("ACCESSIBILITY RECYCLE", e.toString());
        }
        return sb;
    }*/
/*
    public String util() {
        StringBuilder sb = new StringBuilder();
        try {
            AccessibilityNodeInfo rootNode = getRootInActiveWindow();
            System.out.println(rootNode.toString());
            sending(rootNode.toString());
            sb = recycle(rootNode, sb);
            System.gc();
            return sb.toString();
            // print it out first?
//        List<AccessibilityNodeInfo> cv = new ArrayList<>();
//        cv.add(rootNode);
//        walkNodes(cv);
        } catch (Exception e) {
            e.printStackTrace();
            System.gc();
        }
//        System.gc();
        return "SHIT HERE.";
    }*/
// it is of some use?
/*
    public String disaster() {
        // use reflection.
        // not to print anything please.
//        Display.D
//        Reflection.unseal(base);
        try {
            AccessibilityNodeInfo rootNode = getRootInActiveWindow();
            // this one?
            if (rootNode != null) {
//            rootNode.
                System.out.println(rootNode.toString());
//            sending(rootNode.toString());
                Context context = getApplicationContext();
                ArrayList<Integer> k = getMetrics(context);
//            int width = getMetrics(context).widthPixels;
//            int height = getMetrics(context).heightPixels;
//            int rotation=get
                // either use a better dumper or else?
                // not at this step.
//        Class cls= Class.forName("android.hardware.display.DisplayManagerGlobal");
//        Constructor<?> constructor = cls.getConstructor(Context.class);
//        Object obj = constructor.newInstance(this);
//        Method method = cls.getMethod("getRealDisplay", new Class[]{int.class});
//        Display display =
//                (Display) method.invoke(cls.newInstance(),Display.DEFAULT_DISPLAY);
//        int rotation = display.getRotation();
//        Point size = new Point();
                // always no zero argument constructor.
//        display.getSize(size);
                String formal = AccessibilityNodeInfoDumper.dumpWindowToString(rootNode, (int) k.get(0), (int) k.get(1), (int) k.get(2));
//
                return formal;
            } else {
//                Log.e("NORMAL DUMP", e.toString());
                return "";
            }
            // there must be something wrong with it.
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("NORMAL DUMP", e.toString());
            return "";
//        return "";
            // there will be much problems. should not only logging the rootnode.
        }
    }*/

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //another thread.
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.e(TAG, "event type printed!");
//                // or whatever fucking shit is going on. just start another thread instead.
//                // it is working somehow.
//                // not at all.
//                int eventType = event.getEventType();
//                System.out.println(eventType);
//                Log.e(TAG, "event type printed!");
////        sending("ACCESSIBILITY EVENTS!");
//                // there is no such thing.
//                try {
//                    Log.e(TAG, "ACCESSING ROOT NODE");
//                    //获取根节点
//                    // shit. it is dynamic.
//                    // think about a walker somehow.
////            AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//                    // this one shall be replaced.
//                    // but how do we call this thing?
//                    List<AccessibilityWindowInfo> windows = getWindows();
//                    List<AccessibilityNodeInfo> result = new ArrayList<>();
//                    // do a loop here.
//                    // does this really works?
//                    StringBuilder nodeBuff = new StringBuilder();
//                    for (AccessibilityWindowInfo window : windows) {
//                        AccessibilityNodeInfo root = window.getRoot();
//                        if (root == null) {
//                            Log.e(TAG, String.format("Skipping null root node for window: %s", window.toString()));
//                            continue;
//                        }
//                        result.add(root);
//                    }
//                    for (AccessibilityNodeInfo rootNode : result) {
//                        if (rootNode != null) {
//                            String rootByte = null;
//                            try {
//                                Context context = getApplicationContext();
//                                ArrayList<Integer> k = getMetrics(context);
//                                rootByte = AccessibilityNodeInfoDumper.dumpWindowToString(rootNode, k.get(0), k.get(1), k.get(2));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            if (rootByte != null) {
//                                nodeBuff.append(rootByte);
//                            }
//                        }
//                    }
//                    if (nodeBuff != null) {
////        getResources().getConfiguration().orientation,
////        getWindows();
//                        // windows -> root -> finally get the thing.
//                        httpPostBytes.payload_self = nodeBuff.toString().getBytes();
//                        byteClass.screenshot_update = true;
//                    } else {
//                        Log.e(TAG, "NULL ROOT NODE. CONSIDER ALTERNATIVE?");
////                return "";
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.e(TAG, "ERROR WHEN ACCESSING ROOT NODE. CONSIDER ALTERNATIVE?");
//                }
//                switch (eventType) {
//                    case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:  //收到通知栏消息
//                        Log.e(TAG, "TYPE_NOTIFICATION_STATE_CHANGED");
//                        break;
//                    case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:    //界面状态改变
////                util();
//                        Log.e(TAG, "TYPE_WINDOW_STATE_CHANGED");
//                        break;
//                    case AccessibilityEvent.TYPE_VIEW_CLICKED:   //点击事件
//                        Log.e(TAG, "TYPE_VIEW_CLICKED");
//                        break;
//                    case AccessibilityEvent.CONTENT_CHANGE_TYPE_TEXT: //文本改变
//                        Log.e(TAG, "CONTENT_CHANGE_TYPE_TEXT");
//                        break;
//                    //省略其他的一堆可以监听的事件
//                }//what is the deal?
//            }
//        }).start();
    }

    @Override
    public void onInterrupt() {
    }

}
