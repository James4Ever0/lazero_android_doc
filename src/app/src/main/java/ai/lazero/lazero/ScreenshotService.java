package ai.lazero.lazero;
//package dev.protium.rest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.content.IntentFilter;
import android.graphics.Bitmap;
//import android.os.AsyncTask;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
//import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
//import android.os.Process;
// initialize and keep it up.
// another keep-up daemon set and ready.
import android.os.Environment;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.os.Binder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
//import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.net.URLDecoder;

import im.delight.android.webview.AdvancedWebView;
//import android.graphics.*;
//import java.util.*;
//import com.squareup.okhttp.*;

public class ScreenshotService extends Service {
    public static ScreenshotService instance;
    ////    }
    public boolean timelock;

    public boolean getTimelock() {
        if (instance != null) {
            if (!instance.timelock) {
                instance.timelock = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        instance.timelock = false;
                    }
                }).start();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Handler handler = new Handler();
    public PowerManager.WakeLock mWakeLock = null;

    public final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String a = intent.getAction();
            // whatever. must be not null.
            if (a.equals("ai.lazero.lazero.r")) {
                Log.e(TAG, "MASTURBATION");
            } else if (instance.webview != null && instance.mServiceHandler != null) {
                if (a.equals("ai.lazero.lazero.webdump")) {
                    Log.i(TAG, "dumping html");
                    instance.mServiceHandler.viewSource();
                } else if (a.equals("ai.lazero.lazero.webshot")) {
                    instance.mServiceHandler.takeWebviewScreenshot();
                } else if (a.equals("ai.lazero.lazero.javascript")) {
                    System.out.println("DO_HAVE_JAVASCRIPT");
                    if (instance.getTimelock()) {
                        if (instance.webview != null) {
                            String script = intent.getStringExtra("script");
                            if (script != null) {
                                // this api is somehow shitty. do we need to add comma after that thing?
                                // no exception shown.
                                // how to enable webview debug mode?
                                instance.webview.evaluateJavascript(script, new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String value) {
                                        // send to another intent?
                                        // do not put it back immediately.
                                        // quota. must have quota.
//                                        new Thread(new Runnable() {
//                                            @Override
//                                            public void run() {
                                        Intent iin = new Intent(ScreenshotService.this, SimpleService.class);
                                        iin.putExtra("tag", "script_output");
                                        if (value != null) {
                                            iin.putExtra("output", value);
                                        } else {
                                            iin.putExtra("output", "");
                                        }
//                                sendBroadcast(iin);
                                        startForegroundService(iin);
                                        // maybe not running?
//                                            }
//                                        }).start();
                                        // value is the result returned by the Javascript as JSON
                                    }
                                });
                            }
                        }
                    }
                }
            } else {
                Log.e(TAG, "currently not receiving broadcast. webview not started");
            }
        }
    };

    public Notification note() {
        String CHANNEL_ID = "ai.lazero.lazero.ScreenshotService";
        String CHANNEL_NAME = "DEBUG";
        NotificationChannel notificationChannel = null;
        // you've got some foreground service. shit then.
        // do it now?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(this, ScreenshotService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new Notification.Builder(this, CHANNEL_ID).
                setContentTitle("Lazero").
                setContentText("WebScreenshot running.").
                setWhen(System.currentTimeMillis()).
                setSmallIcon(R.drawable.icon).
                setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).
                setContentIntent(pendingIntent).build();
        return notification;
    }

    //}
    public void antivirus() {
//        handler.removeCallbacks(task);
        // what is this task?
        handler.removeCallbacksAndMessages(null);
    }

    //    public void viewSource(){};
//    public void takeWebviewScreenshot(){};
    public final String TAG = "WebpageScreenshotService";
    public ServiceHandler mServiceHandler;
    public StringBuilder inject_js_final = new StringBuilder();

    //    inject_js_final.append("");
// type into that tiny shit.
    public void revive() {
        Intent intentZ = new Intent("ai.lazero.lazero.r");
        sendBroadcast(intentZ);
        Intent intentSelf = new Intent(ScreenshotService.this, ScreenshotService.class);
//        startService(intentSelf);
        startForegroundService(intentSelf);
        // call it masturbation.
    }

    // or initialize a websocket service. execute random javascript code. catch exceptions.
    // not running at all?
    public Thread heartThread;
    //    public WebView webview;
    public AdvancedWebView webview;

    //    public class AppServiceBinder extends Binder {
//        AppService getService() {
//            return  AppService.this;
//        }
//    }
    // Handler that receives messages from the thread
    public final class ServiceHandler extends Handler {
        public int currentStartId;
        // this is within the servicehandler.
        public boolean webviewScreenshotTaken = false;
        public boolean websiteIconTaken = false;

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(final Message msg) {
            currentStartId = msg.arg1;
            Intent intent = (Intent) msg.obj;
            // not publicly registered.
            String command;
            if (intent != null) {
                command = intent.getStringExtra("intent");
            }
            webview = new AdvancedWebView(ScreenshotService.this);
            Log.i(TAG, "Creating WebView");

            //without this toast message, screenshot will be blank, dont ask me why...
            Toast.makeText(ScreenshotService.this, "Save completed.", Toast.LENGTH_SHORT).show();
// only one instance. no multiple webview?
            // This is important, so that the webview will render and we don't get blank screenshot
            webview.setDrawingCacheEnabled(true);

            //width and height of your webview and the resulting screenshot
            int width = 1920;
            int height = 1080;
            webview.measure(width, height);
            webview.layout(0, 0, width, height);


            boolean javaScriptEnabled = PreferenceManager.getDefaultSharedPreferences(ScreenshotService.this).getBoolean("enable_javascript", true);
            webview.getSettings().setJavaScriptEnabled(true);
// check for flutter code.
            webview.getSettings().setAllowFileAccessFromFileURLs(true);
            webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
// now let's implement the websocket. evaluate the thing.
            // thought you can do this in xposed framework.
            // and the worker.
//            webview.loadUrl(intent.getStringExtra(Database.FILE_LOCATION));
            // replace the call man.
            String baidu_url = "https://www.baidu.com";
            webview.loadUrl(baidu_url);
            // just pass it to the thing.
            Log.i(TAG, "Loading URL: " + baidu_url);

            webview.setWebViewClient(new MyWebViewClient() {
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Log.w(TAG, "Recieved error from WebView, description: " + description + ", Failing url: " + failingUrl);
                    //without this method, your app may crash...
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.i(TAG, "Page finished, getting thumbnail");
                    view.loadUrl("javascript:" + inject_js_final.toString());
                    // shall be running a ws client here.
//                    viewSource();
                    // this could be changed.
//                    takeWebviewScreenshot(intent.getStringExtra(Database.THUMBNAIL));
//                    takeWebviewScreenshot();
// just launch it by the way.
                }
            });
//            }else if (webview!=null){if(command.equals("screenshot")){takeWebviewScreenshot();}
//            else if(command.equals("dumpSource")){viewSource();}}else {//do something to restart the view.
//                Log.e(TAG, "webview not started.");
//                // use some broadcast mechanism. do not use normal intent sender. it will be destroyed.
//            }
//            // use broadcast receiver.
        }
        // do another message receiver.
        // disassemble the whole process.
        // not sending intent. receiving broadcast.

        public class MyWebViewClient extends WebViewClient {
            //            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("source://")) {
                    try {
                        String html = URLDecoder.decode(url, "UTF-8").substring(9);
                        Log.i(TAG, "TRYING TO SAVE HTML.");
                        saveHTMLToFile(html);
                    } catch (UnsupportedEncodingException e) {
                        Log.e("example", "failed to decode source", e);
                    }
                    webview.getSettings().setJavaScriptEnabled(true);
                    // this is shit.
                    return true;
                }
                // For all other links, let the WebView do it's normal thing
                return false;
            }
        }

        // start a websocket server.
        public void viewSource() {
            if (webview != null) {
//        webview.getSettings().setJavaScriptEnabled(true);
                webview.loadUrl(
                        "javascript:this.document.location.href = 'source://' + encodeURI(document.documentElement.outerHTML);");
            } else {
                Log.i(TAG, "WEBVIEW NULL CANNOT DUMP");
            }
        }

        // execute javascript and get result.
        public void takeWebviewScreenshot() {
//            final String outputFileLocation
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);  //allow webview to render, otherwise screenshot may be blank or partial
                    } catch (InterruptedException e) {
                        //should never happen
                        Log.e(TAG, "InterruptedException when taking webview screenshot ", e);
                    }
                    // no saving. just print the length instead. or send intent?
//                    saveBitmapToFile(webview.getDrawingCache(), new File(outputFileLocation));
                    saveBitmapToFile(webview.getDrawingCache());
                    // this is shit. maybe not as fluent as android p.
                    webviewScreenshotTaken = true;
                    // already dead by then.
                    Log.i(TAG, "SCREENSHOT SAVED.");
                    // do not stop.
                    // do it in another thread. the listener thread.
//                    stopService();
                }
            }).start();
        }

        public void saveHTMLToFile(String bitmap) {
            if (bitmap == null) {
                Log.i(TAG, "html is empty.");
                return;
            }
//            outputFile.getParentFile().mkdirs();
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath() + "/lazero/flutter/html");
//                dir.mkdirs();
                if (!directory.exists()) {
                    directory.mkdirs();
                }
//                Timestamp timestamp = TimeStamp(System.currentTimeMillis());
                // method reloaded.
                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                String filename = "baidu_html-" + ts + ".html";
//            File file = new File(directory, filename);
                // passing in a file object.
//            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                PrintWriter out = new PrintWriter(directory + "/" + filename);
                out.print(bitmap);
//            out.
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                // no logcat here.
                // make directory?
                // /sdcard/lazero/flutter/web
////                bitmap.
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
//                byte[] byteArray = stream.toByteArray();
//                String length="BITMAP SIZE: "+String.valueOf(byteArray.length);
//                Log.i(TAG,length);
//                Log.i(TAG,"hello world");
//print()
                out.flush();
                out.close();
                Log.i(TAG, "saved html to file");
                // not at this step.
            } catch (Exception e) {
                Log.e(TAG, "IoException while saving html to file", e);
            }
//            Log.i(TAG, "Saved Bitmap to file: " + outputFile.getPath());
        }

        public void saveBitmapToFile(Bitmap bitmap) {
            if (bitmap == null) {
                Log.i(TAG, "bitmap is empty.");
                return;
            }
//            outputFile.getParentFile().mkdirs();
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath() + "/lazero/flutter/web");
//                dir.mkdirs();
                if (!directory.exists()) {
                    directory.mkdirs();
                }
//                Timestamp timestamp = TimeStamp(System.currentTimeMillis());
                // method reloaded.
                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                String filename = "baidu_screenshot-" + ts + ".png";
                File file = new File(directory, filename);
                // passing in a file object.
                OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                // no logcat here.
                // make directory?
                // /sdcard/lazero/flutter/web
////                bitmap.
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
//                byte[] byteArray = stream.toByteArray();
//                String length="BITMAP SIZE: "+String.valueOf(byteArray.length);
//                Log.i(TAG,length);
//                Log.i(TAG,"hello world");
//print()
                Log.i(TAG, "web screenshot saved");
                // do not do extra things. null pointers.
                out.flush();
                out.close();
            } catch (Exception e) {
                Log.e(TAG, "IoException while saving bitmap to file", e);
            }
//            Log.i(TAG, "Saved Bitmap to file: " + outputFile.getPath());
        }

        // do not start that service. make it into a work instead of service.
        public void stopService() {
            if (heartThread != null) {
                heartThread = null;
            }
            if (websiteIconTaken && webviewScreenshotTaken) {
                Log.i(TAG, "Service stopped, with startId " + currentStartId + " completed");
                stopSelf(currentStartId);
            }
        }
    }

    // send to external things or simply save to sdcard. just generate a random number and make some folders.
    @Override
    public void onCreate() {
        this.timelock = false;
        super.onCreate();
        instance = this;
        instance.coreLogic();
        // shall get main looper?
    }

    public void forth() {
        Notification notification = note();
        startForeground(1759, notification);
        Log.v("SERVICE", "START_SUCCESS");
//        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
//        filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);
//        filter.addAction("your_action_strings"); //further more
//        filter.addAction("your_action_strings"); //further more
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        try {
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WebCap");
//        KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            mWakeLock.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void coreLogic() {
        this.forth();
        IntentFilter filter = new IntentFilter();
        filter.addAction("ai.lazero.lazero.webdump");
        filter.addAction("ai.lazero.lazero.webshot");
        filter.addAction("ai.lazero.lazero.javascript");
        filter.addAction("ai.lazero.lazero.r");
        registerReceiver(receiver, filter);
        heartThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        Log.e(TAG, "sleep falied");
                        break;
                    }
                    if (webview != null) {
                        Long tsLong = System.currentTimeMillis() / 1000;
                        String ts = tsLong.toString();
                        Log.e(TAG, "WEBVIEW PRESENT " + ts);
                    } else {
                        Log.e(TAG, "DEAD WEBVIEW");
                    }
                }
            }

            ;
        }
        );
//        heartThread.run();
        heartThread.start();

        // just not this thread.
//        HandlerThread thread = new HandlerThread("WebpageScreenshotService", Process.THREAD_PRIORITY_BACKGROUND);
//        thread.start();
//        thread.get
//        mServiceHandler = new ServiceHandler(thread.getMainLooper());
//        mServiceHandler = new ServiceHandler(thread.getLooper());
        mServiceHandler = new ServiceHandler(Looper.getMainLooper()); // working
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "WEB QUEST RECEIVED");
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);
        flags = START_STICKY;
//        handler.removeCallbacks(task);
//        handler.removeCallbacksAndMessages(null);
//        handler.postDelayed(task,5000);
//        handler.post(task);
        return super.onStartCommand(intent, flags, startId);
    }

    public class ScreenshotServiceBinder extends Binder {
        ScreenshotService getService() {
            return ScreenshotService.this;
        }
    }

    public final IBinder binder = new ScreenshotServiceBinder();

    // but without cookie manager and more. not a browser.
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        // do it at last.
        unregisterReceiver(receiver);
        antivirus();
        instance.revive();
        Log.i(TAG, "SENDING RESTART WEB SERVICE SIGNAL");
        // do nothing here.
        // skip self receiving.
        mServiceHandler.stopService();
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        // send that signal.
        super.onDestroy();
        // it will be dead anyway.
    }

}