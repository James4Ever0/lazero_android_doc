package ai.lazero.lazero;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
// for commandline it is a bit of easier to deal with. just use custom buffer reader to do the task.
// but for this shit, the screen parser will just crash.
import java.io.ByteArrayOutputStream;

public class MyService2 extends Service {
    public Bitmap bmp = ScreenShotFb.getScreenShotBitmap();
    private Handler handler = new Handler();
    public boolean threadDisable = false;
    public ByteClass byteClass=new ByteClass(false);
    public HttpPostBytes httpPostBytes;
    public MyThread myThread;
    ////    public MyService2() {
    // allow app running in background.
//    private HttpPostBytes httpPostBytes;
    // receive buttons.
    public static MyService2 instance;
    ////    }
    public PowerManager.WakeLock mWakeLock = null;
    public Notification note(){
        String CHANNEL_ID = "ai.lazero.lazero.Screencap";
        String CHANNEL_NAME = "SCREENSHOT";
        NotificationChannel notificationChannel = null;
        // you've got some foreground service. shit then.
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(this, MyService2.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, 0);

        Notification notification = new Notification.Builder(this,CHANNEL_ID).
                setContentTitle("Lazero").
                setContentText("Screencap service running.").
                setWhen(System.currentTimeMillis()).
                setSmallIcon(R.drawable.icon).
                setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)).
                setContentIntent(pendingIntent).build();
        return notification;
    }
//public void persistant(Class k,String t0,String t1, String t2,int t3){
//    Notification notification = new Notification(R.drawable.icon, t0,System.currentTimeMillis());
//    Intent notificationIntent = new Intent(this, k);
//    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
////    notification.setLatestEventInfo(this,t1,t2, pendingIntent);
////    notification.notify(t1,t2);
//    startForeground(t3, notification);
//}
public void antivirus(){
    handler.removeCallbacks(task);
    handler.removeCallbacksAndMessages(null);
}
public void mainLoop(){

    System.out.println("new screenshot captured");
//                    serviceLogger("new screenshot captured",-50,50);
    Log.v("SERVICE", "screenshot captured");
//                    Bitmap bmp=((BitmapDrawable)order_con_pic.getDrawable()).getBitmap();
//                    Intent intent=new Intent();
//                    intent.setAction("ai.lazero
    Intent intent = new Intent();
// .");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
    // is it because of this intent?
    intent.setAction("ai.lazero.lazero.MyService2");
//                    Intent intent=getIntent();
    //  check what the fuck is going on?
    //or the quality of picture?
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bmp.compress(Bitmap.CompressFormat.WEBP, 45, baos);
    byte[] bitmapByte = baos.toByteArray();
//    }
    // we need this back.
//    try{
////    HttpPostBytes
//}
    httpPostBytes.payload_self=bitmapByte;
    byteClass.screenshot_update=true;

    bmp = null;
    bmp = ScreenShotFb.getScreenShotBitmap();
    // here is the thing.
//                    Intent intent=new Intent();
    intent.putExtra("count", bitmapByte);
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
    //killed just in place.
    // don't know why.
    // and here's the problem.
//                   is it here?
    // does not make sense.
    System.gc();
}
    private Runnable task = new Runnable(){
        @Override
        public void run(){
            mainLoop();
            handler.postDelayed(this,3000);
        }
    };

// fucking shit. get the command? or reinit the whole thing?
@Override
public int onStartCommand(Intent intent, int flags, int startId) {
    threadDisable = true;
    try{Thread.sleep(3000);}catch (Exception e){e.printStackTrace();}
    flags = START_STICKY;
    handler.removeCallbacks(task);
    handler.removeCallbacksAndMessages(null);
    handler.postDelayed(task,5000);
    handler.post(task);
    return super.onStartCommand(intent, flags, startId);
}

    public void serviceLoggerX(String a, int b, int c) {
//    // TODO Auto-generated method stub
//    try{Thread.sleep(1000);}catch(Exception e){serviceLoggerX("SOMETHING HERE!\n"+e.toString(),100,100);}
        Toast toast = Toast.makeText(this, a, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, b, c);
        toast.show();
    }

    //    public void serviceLogger(String a){Snackbar.make(view, "Failed to acquire root", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show()}
// this one is the screenshot thing.
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
//        throw new UnsupportedOperationException("Not yet implemented");
    }
// check it out?
    // use some logic here?
//    @Override
//    public void run() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
////            serviceLogger(e.toString(),50,100);
//        }
////        serviceLogger("线程执行结果：第三种线程结果！",100,50);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = note();
        startForeground(1349, notification);
        Log.v("SERVICE", "START_SUCCESS");
//        serviceLogger("On create",0,0);
        httpPostBytes= new HttpPostBytes("http://localhost:4999/sample",null);
        myThread=new MyThread(httpPostBytes,byteClass,"type","screenshot");
        myThread.start();
        instance=this;
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
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ScreenCap");
//        KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        mWakeLock.acquire();
//        mKeyguardManager.
        // that's interesting.
//        main();
    }

    //    @Override
//    public IBinder onBind(Intent intent) { return null; }
    @Override
    public void onDestroy() {
//        boolean d = ((Apl) getApplication()).getMyPublicData();
        boolean d = false;

        // maybe that's the reason it cannot survive.
        // keep it alive.
        if (!d) {
            Intent intent = new Intent("ai.lazero.lazero.recreate");
            sendBroadcast(intent);
// cannot send this shit.
//            Intent intentZ = new Intent("ai.lazero.lazero.r");
//            sendBroadcast(intentZ);
        }
        // this is making it undead.
        // it might lead to unwanted result.
//        myThread.
//        myThread.destroy();
        byteClass.screenshot_update=false;
        myThread.interrupt();
        myThread=null;
        // if proceed.
        handler.removeCallbacks(task);
        handler.removeCallbacksAndMessages(null);
        threadDisable = true;
        serviceLoggerX("service destroyed", 0, 100);
        Log.v("ScreenshotService", "on destroy");
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        super.onDestroy();
    }

//    }
    // do some broadcast over normal things.
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
