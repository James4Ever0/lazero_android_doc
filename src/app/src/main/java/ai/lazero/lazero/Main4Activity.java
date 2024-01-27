package ai.lazero.lazero;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.ByteArrayOutputStream;
//import android.support.v7.app.AppCompatActivity;
// never mind we just send that shit.
public class Main4Activity extends Activity {
    public Bitmap bmp = ScreenShotFb.getScreenShotBitmap();
    private Handler handler = new Handler();
    public static Main4Activity instance;
// fucking shit.
private Runnable task = new Runnable(){
    @Override
    public void run(){
        mainLoop();
        handler.postDelayed(this,3000);
    }
};
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
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bitmapByte = baos.toByteArray();
        // no more. what's the problem?
        // should we do this function outside?
//                    if(bitmapByte == null || bitmapByte.length == 0){intent.putExtra("bitmap", "this is shit");
//                        sendBroadcast(intent);serviceLoggerX("NOW IT IS EMPTY HERE",0,0);}else{intent.putExtra("bitmap", "not shit");
//                        sendBroadcast(intent);
//                        serviceLoggerX("HERE IT IS FULL",0,0);}
//                        return true;
//                    startActivity(intent);
        // can't get shit here.
        // service will kill itself.
        // the thing is good. but no bitmap received.
        bmp = null;
        bmp = ScreenShotFb.getScreenShotBitmap();
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
    public void main() {
//        threadDisable = false;
//        Thread k = new Thread(new Runnable() {
//            @Override
//            public void run() {
        while (true) {
            try {
//                        serviceLogger("Trying to sleep",50,50);
//                Thread.sleep(3000);
                // i guess it is this sleep cause entire shit to lag.
                System.out.println("sleeping");
//                    serviceLogger("sleeping",50,-50);
                Log.v("SERVICE", "SLEEPING");
            } catch (Exception e) {
                e.printStackTrace();
//                        serviceLogger("EXCEPTION FOUND",-50,-50);
//                        serviceLogger(e.toString(),100,0);
            }

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
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bitmapByte = baos.toByteArray();
            // this is my way to learn stuff.
            // no more. what's the problem?
            // should we do this function outside?
//                    if(bitmapByte == null || bitmapByte.length == 0){intent.putExtra("bitmap", "this is shit");
//                        sendBroadcast(intent);serviceLoggerX("NOW IT IS EMPTY HERE",0,0);}else{intent.putExtra("bitmap", "not shit");
//                        sendBroadcast(intent);
//                        serviceLoggerX("HERE IT IS FULL",0,0);}
//                        return true;
//                    startActivity(intent);
            // can't get shit here.
            // service will kill itself.
            // the thing is good. but no bitmap received.
            // this is the visible service.
            bmp = null;
            bmp = ScreenShotFb.getScreenShotBitmap();
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
            // it keeps doing stupid things.
            // so why it is killed?
        }
//            }
//        });
//        // i mean, it is in a thread. not inside the thing.
//        k.run();
    }
    public void antivirus(){
        handler.removeCallbacks(task);
        handler.removeCallbacksAndMessages(null);
    }
@Override
public void finish(){
        super.finish();
    handler.removeCallbacks(task);
    handler.removeCallbacksAndMessages(null);
//    handler.postDelayed(task,5000);
//    handler.post(task);
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
        handler.removeCallbacks(task);
        handler.removeCallbacksAndMessages(null);
        setContentView(R.layout.activity_main4);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        main();
        handler.postDelayed(task,5000);
        handler.post(task);
        // but what the heck?
        // do not trigger any shit.
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(task);
        handler.removeCallbacksAndMessages(null);
    }
}