package ai.lazero.lazero;

//import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ai.lazero.lazero.m6.RecPlayService;

public class MyReceiver extends BroadcastReceiver {
//    @Override
//    public vold onCreate(){
//
//    }
//
    @Override
    public void onReceive(Context context, Intent intent) {
        String a = intent.getAction();
        if (
                a.equals("android.intent.action.BOOT_COMPLETED") |
                a.equals("android.intent.action.USER_PRESENT")
                ) {
            Intent service = new Intent(context, MyService2.class);
//            context.startService(service);
//            String c = "method";
            context.startForegroundService(service);
            // so what the heck?
            Intent serviceX = new Intent(context, MyServiceClip.class);
//            context.startService(service);
//            String c = "method";
            context.startForegroundService(serviceX);
            Intent serviceZ = new Intent(context, ScreenshotService.class);
            context.startForegroundService(serviceZ);
            // so what the heck?
//            bool k=c.equals("method");
            // receiver never launched?
            Log.e("BROADCAST_RECEIVER","RESTARTING THE CLIPBOARD MANAGER SERVICE");
//            bool k=c.equals("method");
            // receiver never launched?
            Log.e("BROADCAST_RECEIVER","RESTARTING THE SCREENCAP SERVICE");
            Log.e("BROADCAST_RECEIVER","RESTARTING THE WEB SCRAPER SERVICE");
        }else
        if (a.equals("ai.lazero.lazero.recreate")){
            Intent service = new Intent(context, MyService2.class);
//            context.startService(service);
//            String c = "method";
            context.startForegroundService(service);
            // so what the heck?
//            bool k=c.equals("method");
            // receiver never launched?
            Log.e("BROADCAST_RECEIVER","RESTARTING THE SCREENCAP SERVICE");
        }else
        if (a.equals("ai.lazero.lazero.recreateX")){
            Intent serviceX = new Intent(context, MyServiceClip.class);
//            context.startService(service);
//            String c = "method";
            // the good old days?
            context.startForegroundService(serviceX);
            // so what the heck?
//            bool k=c.equals("method");
            // receiver never launched?
            Log.e("BROADCAST_RECEIVER","RESTARTING THE CLIPBOARD MANAGER SERVICE");
        }else
        if (a.equals("ai.lazero.lazero.recreateY")){
            Intent serviceX = new Intent(context, RecordAudio.class);
//            context.startService(service);
//            String c = "method";
            // the good old days?
            context.startForegroundService(serviceX);
            // so what the heck?
//            bool k=c.equals("method");
            // receiver never launched?
            Log.e("BROADCAST_RECEIVER","RESTARTING THE AUDIO RECORDER SERVICE");
        }else
        if (a.equals("ai.lazero.lazero.r")){
//            ai.lazero.lazero.recreateZ
            Intent serviceZ = new Intent(context, ScreenshotService.class);
            context.startForegroundService(serviceZ);
            // not a foreground service?
            Log.e("BROADCAST_RECEIVER","RESTARTING THE WEB SCRAPER SERVICE");
            // this is not right. use workmanager.
            //
        }else if(a.equals("ai.lazero.lazero.m6.recreate")){
            Intent serviceX = new Intent(context, RecPlayService.class);
//            context.startService(service);
//            String c = "method";
            // the good old days?
            context.startForegroundService(serviceX);
            // so what the heck?
//            bool k=c.equals("method");
            // receiver never launched?
            Log.e("BROADCAST_RECEIVER","RESTARTING THE RECPLAY SERVICE");
        }
        System.gc();
    }
}
