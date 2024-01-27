package ai.lazero.lazero;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
//import android.support.design.widget.Snackbar;

public class MainXActivity extends Activity {
    // we are under UTF!
//    toast.show();
    private MyReceiver receiver = null;
    // maybe we should not do this?
    public TextView imageview = null;
    public StringBuilder str = new StringBuilder();

    //    public static Main2Activity activity = null;
    // you can register random receiver anyway.
    // check it out?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainx);
//        try{

        str.append("sample_text_");
// is this how debug works?
        imageview = (TextView) findViewById(R.id.sample);
//        startService(new Intent(MainActivity.this, CountDataService.class)); // 注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("ai.lazero.lazero.MyService");
        MainXActivity.this.registerReceiver(receiver, filter);
        System.out.println(str.toString());
        System.out.println("\nregistration done");
        imageview.setText(str.toString());
    }
// this time we do not receive shit.
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
        MainXActivity.this.unregisterReceiver(receiver);
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
            if (intent != null) {
                try {
                    //String format=intent.getStringExtra("bitmap");
//                int f=(int);
                    // should not do it here.
                    // use another thread to do the setting?
                    System.out.println("BROADCAST FROM SERVICE:" + String.valueOf(System.currentTimeMillis()));
//                Log.v("sample",format);
                    //Intent intent = Main3Activity.this.getIntent();
                    String bis = intent.getStringExtra("xml");
                    // not really that thing.
                    System.out.println(bis);
                    str.append("\n"+bis);
////                nothing in it.
//                 name is not changed anyway.
                    // but what is the problem anyway?
                    // no one will receive things?
//                    // do it in another receiver?
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
                    // shit. better change your mind?
                    // no zero argument constructor?
                    MainXActivity.this.imageview.setText(str.toString());
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
                // get a looper like this? write it out?
                // nonstuck version.
                // or as a service.
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
