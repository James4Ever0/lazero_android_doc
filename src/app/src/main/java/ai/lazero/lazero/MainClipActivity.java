package ai.lazero.lazero;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainClipActivity extends Activity {
// good at it? or major?
private MainClipActivity.MyReceiver receiver = null;
    // maybe we should not do this?
    public StringBuilder str = new StringBuilder();
    public TextView xv = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_clip);
        xv= (TextView) findViewById(R.id.textView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "SWITCHING ON CLIPBOARD MANAGER", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MainClipActivity.this, MyServiceClip.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startService(intent);
            }
        });
        str.append("sample_text_\n");
// is this how debug works?
//        imageview = (ImageView) findViewById(R.id.sample);
//        startService(new Intent(MainActivity.this, CountDataService.class)); // 注册广播接收器
        receiver = new MainClipActivity.MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("ai.lazero.lazero.MyServiceClip");
        MainClipActivity.this.registerReceiver(receiver, filter);
        System.out.println(str.toString());
        System.out.println("registration done");
    }
    @Override
    public void onDestroy() {
        MainClipActivity.this.unregisterReceiver(receiver);
        super.onDestroy();
    }
    // hashtag.
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Bundle bundle = intent.getExtras();
//            byte[] count = bundle.getByteArray("count");
            // just check out the role here.
//            serviceLogger("RECEIVED INTENT",0,0);
            if (intent != null) {
                try {
                    System.out.println("BROADCAST FROM CLIPBOARD SERVICE:" + String.valueOf(System.currentTimeMillis()));
//                Log.v("sample",format);
                    // how the hell you will have that?
                    //Intent intent = Main3Activity.this.getIntent();
//                    byte[] bis = intent.getByteArrayExtra("count");
////                nothing in it.
                    String bis=intent.getStringExtra("count");
                    System.out.println(bis);
                    str.append(bis);
                    str.append("\n");
//                 name is not changed anyway.
                    // are you sure there's no format inside?
                    MainClipActivity.this.xv.setText(str.toString());
                    System.out.println("clipboard capture service update success");
                } catch (Exception e) {
                    System.out.println("SOMETHING NASTY HAPPENED");
                    System.out.println(e.toString());
                    e.printStackTrace();
                }
            } else {
                System.gc();
                System.out.println("failed to update clipboard service");
            }
        }
    }
}
