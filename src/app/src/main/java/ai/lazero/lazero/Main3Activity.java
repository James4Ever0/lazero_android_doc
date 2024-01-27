package ai.lazero.lazero;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;

public class Main3Activity extends Activity {
    // we are under UTF!
//    private static final int REQUEST_MEDIA_PROJECTION = 201;
//private LogService logService;
//    public String str = "";
    // it is not about popularity. it's alive or dead.
    // what is the problem?
//public void serviceLogger(String a,int b,int c) {
//    // TODO Auto-generated method stub
//    try{Thread.sleep(1000);}catch(Exception e){serviceLogger("SOMETHING HERE!\n"+e.toString(),100,100);}
//    Toast toast=Toast.makeText(this,a,Toast.LENGTH_SHORT);
//    toast.setGravity(Gravity.CENTER, b, c);
//    toast.show();
    private MyReceiver receiver = null;
    // maybe we should not do this?
    public StringBuilder str = new StringBuilder();
    public ImageView imageview = null;

    //    public static Main2Activity activity = null;
    // you can register random receiver anyway.
    // check it out?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
//        try{
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "March on visible service", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setClass(Main3Activity.this, Main4Activity.class);
                Main3Activity.this.startActivity(intent);
            }
        });
        FloatingActionButton fac = (FloatingActionButton) findViewById(R.id.fac);
        fac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Killing visible service", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Intent intent = new Intent();
//                intent.setClass(Main3Activity.this, Main4Activity.class);
                if (Main4Activity.instance != null) {
                    Main4Activity.instance.antivirus();
                    Main4Activity.instance.finish();
                    System.out.println("KILLING VISIBLE SERVICE");
                } else {
                    System.out.println("VISIBLE SERVICE IS INVISIBLE");
                }
//                Main3Activity.thi
            }
        });
// run service even if visible application is killed.
        // register a service?
        // write a standalone module?
        // cause that will be great. using headless technology.
        // install subapk along the way?
        // using tutorials?
        // well, you can do that by using some modification. such as recording the screen.
        // will it consume too many resources?
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           boolean k = MainActivity.upgradeRootPermission(getPackageCodePath());
                                           if (k == true) {
                                               boolean d = false;
//                                               (Apl)this.getMyPublicData();
//                                               Apl myApplication=(Apl)getApplication();
                                               ((Apl) getApplication()).setMyPublicData(d);
                                               // not going to work. check the settings?
                                               // it will do good.
//                                               Apl.instance.screenShotService=d;
                                               Snackbar.make(view, "Root acquire success & Starting Screencap Service", Snackbar.LENGTH_LONG)
                                                       .setAction("Action", null).show();
                                               Intent intent = new Intent(Main3Activity.this, MyService2.class);
//                                               intent.setAction(ACTION_START)
                                               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                               startService(intent); // 注册广播接收器
                                           } else {
                                               Snackbar.make(view, "Failed to acquire root", Snackbar.LENGTH_LONG)
                                                       .setAction("Action", null).show();
                                           }
//                            Intent intent = new Intent(MainActivity.this, MyService.class);
//                            stopService(intent);
                                           //cannot start the service.
                                           // cause you don't bind it.
                                       }
                                   }
        );
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           boolean k = MainActivity.upgradeRootPermission(getPackageCodePath());
                                           if (k == true) {
                                               // use a variable to do the job.
                                               boolean d = true;
                                               ((Apl) getApplication()).setMyPublicData(d);
                                               // but the application could be killed.
                                               Snackbar.make(view, "Root acquire success & Stopping Screencap Service", Snackbar.LENGTH_LONG)
                                                       .setAction("Action", null).show();
                                               if (MyService2.instance != null) {
                                                   MyService2.instance.antivirus();
//                                                   MyService2.instance.finish();
                                                   System.out.println("KILLING VISIBLE SERVICE");
                                               } else {
                                                   System.out.println("VISIBLE SERVICE IS INVISIBLE");
                                               }
//                Main3Activity.thi
                                               stopService(new Intent(Main3Activity.this, MyService2.class)); // 注册广播接收器
                                           } else {
                                               Snackbar.make(view, "Failed to acquire root", Snackbar.LENGTH_LONG)
                                                       .setAction("Action", null).show();
                                           }
//                            Intent intent = new Intent(MainActivity.this, MyService.class);
//                            stopService(intent);
                                           //cannot start the service.
                                           // cause you don't bind it.
                                       }
                                   }
        );
        // it is alright.
        str.append("sample_text_");
// is this how debug works?
        imageview = (ImageView) findViewById(R.id.sample);
//        startService(new Intent(MainActivity.this, CountDataService.class)); // 注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("ai.lazero.lazero.MyService2");
        Main3Activity.this.registerReceiver(receiver, filter);
        System.out.println(str.toString());
        System.out.println("registration done");
    }
    /**
     * 获取广播数据 * * @author jiqinlin *
     */
    @Override
    protected void onDestroy() {
        System.out.println("ACTIVITY DESTROYED!");
        // that's the middle one.
        // see if we can do this in another app?
        // well, check the receiver.
        // just get the length of the bytearray. that's good for all.
        Main3Activity.this.unregisterReceiver(receiver);
        super.onDestroy();
    }
// whatever.
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Bundle bundle = intent.getExtras();
//            byte[] count = bundle.getByteArray("count");
            // just check out the role here.
//            serviceLogger("RECEIVED INTENT",0,0);
            // where the fuck is the thing?
            if (intent != null) {
                try {
                    //String format=intent.getStringExtra("bitmap");
//                int f=(int);
                    // should not do it here.
                    // use another thread to do the setting?
                    System.out.println("BROADCAST FROM SERVICE:" + String.valueOf(System.currentTimeMillis()));
//                Log.v("sample",format);
                    //Intent intent = Main3Activity.this.getIntent();
                    byte[] bis = intent.getByteArrayExtra("count");
////                nothing in it.
//                 name is not changed anyway.
                    // but what is the problem anyway?
                    // no one will receive things?
                    // do it in another receiver?
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
                    // shit. better change your mind?
                    Main3Activity.this.imageview.setImageBitmap(bitmap);
                    System.out.println("image capture service update success");
                } catch (Exception e) {
                    System.out.println("SOMETHING NASTY HAPPENED");
                    System.out.println(e.toString());
                    e.printStackTrace();
                }
            } else {
//            imageview.setImageBitmap(count);
                // it is the timeout.
//            editText.setText(count + "");
                //
                // check other's manifest.
                // is that not a service at all?
                // persistent banner?
                System.gc();
                System.out.println("failed to update image capture service");
            }
        }
    }
}
