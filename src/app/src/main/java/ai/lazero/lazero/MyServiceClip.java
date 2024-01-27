package ai.lazero.lazero;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
//import android.content.ClipDescription;

// the heck.
//import in.srain.cube.clipboardcompat.ClipboardManagerCompat;
//import in.srain.cube.clipboardcompat.ClipboardManagerCompatFactory;
//import in.srain.cube.clipboardcompat.OnPrimaryClipChangedListener;

public class MyServiceClip extends Service {
    //    public MyServiceClip() {
//    }
    public static MyServiceClip instance;
    public ByteClass byteClass=new ByteClass(false);
    public HttpPostBytes httpPostBytes;
    public MyThread myThread;
    ////    }
    // come on!
    // fucking hell.
    public PowerManager.WakeLock mWakeLock = null;

    public Notification note() {
        String CHANNEL_ID = "ai.lazero.lazero.ServiceClip";
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
                setContentText("Clipboard service running.").
                setWhen(System.currentTimeMillis()).
                setSmallIcon(R.drawable.icon).
                setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).
                setContentIntent(pendingIntent).build();
        return notification;
    }// fuck these people?
// this is the public domain, and it is (so fucking) huge.
    // do the overall shits?
    // receive things.
    // fuck that.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        final ClipboardManagerCompat clipboardManager = ClipboardManagerCompatFactory.create(this);
        final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                // is it because of this intent?
                intent.setAction("ai.lazero.lazero.MyServiceClip");
                StringBuilder sb = new StringBuilder();
                try {
                    ClipData clipData = clipboardManager.getPrimaryClip();
                    // starts here.
                    // get descriptions here.
                    ClipDescription cld = clipboardManager.getPrimaryClipDescription();
                    // what is this one?
                    String mimeType = cld.getMimeType(0);
                    // it works somehow.
                    sb.append("mimeType: " + mimeType + "; ");
//                String patten = getString(R.string.data_changed_tip);
//                    check this out.

                    // shit man.
                    // check it anyway.
                    // better use some BYTES?
                    // trying to copy something different.
//                String msg = String.format("pattern", String.valueOf(clipboardManager.getText()));
//                clipboardManager.getClass();
                    // not sending the real shit.
                    // or you can just send the message, for others to register?
                    // you shall process it.
                    // shall I?
                    // defend yourself? cause someone will be hacking in?
                    if (clipData != null && clipData.getItemCount() > 0) {
                        // hell shit!
                        // as you get more keyloggers than usual, you can prevent from invasion?
                        // 从数据集中获取（粘贴）第一条文本数据
                        // or get something else?
                        // getHTML. getURI. getTEXT. getINTENT.
                        int clipcount = clipData.getItemCount();
                        ClipData.Item cit = clipData.getItemAt(0);
//                        cit.coerceToHtmlText()
                        // getting the length. -> error.
                        // even put object into clipboard?
                        // parcel?
                        String htmltext = cit.getHtmlText();
                        if (htmltext != null && htmltext.length() > 0) {
                            sb.append(" [htmltext] :: " + htmltext + "; ");
                        }
                        Intent intent1 = cit.getIntent();
                        if (intent1 != null) {
                            sb.append(" [intent] :: " + intent1.toString() + "; ");
                        }
                        CharSequence charsequence = cit.getText();
                        if (charsequence != null) {
                            String charString = charsequence.toString();
                            if (charString != null && charString.length() > 0) {
                                sb.append(" [charString] :: " + charString + "; ");
                            }
                        }
                        Uri uri = cit.getUri();
//                        uri. so many goddamn shits. just like python.
                        // fucking hell.
                        // get last one.
                        if (uri != null) {
                            String charString = uri.toString();
                            if (charString != null && charString.length() > 0) {
                                sb.append(" [uri] :: " + charString + "; ");
                            }
                        }
                        // is that html pricks?
                        // how to determine the type???
//                        CharSequence text = clipData.getItemAt(0).getText();
//                        System.out.println("text: " + text);
                    }
                    httpPostBytes.payload_self=sb.toString().getBytes();
                    byteClass.screenshot_update=true;
                    intent.putExtra("count", sb.toString());
                    sendBroadcast(intent);// whatever.
                    // so fuck that.
//                System.out.println(msg);
                    // somehow working?
//                resultInfoTextView.setText(msg);
                    // fuck that.
                } catch (Exception e) {
                    String msg = e.toString();
                    sb.append("SHIT HAPPENED. ");
                    intent.putExtra("count", sb.toString() + " <split> " + msg);
                    sendBroadcast(intent);
                }
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = note();
        startForeground(1339, notification);
        Log.v("SERVICE", "START_SUCCESS");
//        serviceLogger("On create",0,0);
        instance = this;
        httpPostBytes= new HttpPostBytes("http://localhost:4999/sample",null);
        myThread=new MyThread(httpPostBytes,byteClass,"type","clipboard");
        myThread.start();
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
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ClipServer");
//        KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        mWakeLock.acquire();
    }

    @Override
    public void onDestroy() {
//        boolean d = ((Apl) getApplication()).getMyPublicData();
        boolean d = false;
        // maybe that's the reason it cannot survive.
        byteClass.screenshot_update=false;
        myThread.interrupt();
        myThread=null;
        // keep it alive.
        if (!d) {
            Intent intent = new Intent("ai.lazero.lazero.recreateX");
            sendBroadcast(intent);
        }
        // this is making it undead.
        // it might lead to unwanted result.
        // if proceed.
//        handler.removeCallbacks(task);
//        handler.removeCallbacksAndMessages(null);
//        threadDisable = true;
//        serviceLoggerX("service destroyed", 0, 100);
        Log.v("ClipService", "on destroy");
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        super.onDestroy();
    }
}
