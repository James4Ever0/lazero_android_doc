package ai.lazero.lazero;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.WindowManager;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

//import android.support.widget.Toolbar;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import java.io.DataOutputStream;
// so we are way advanced?
// it will crash. but first, use the bloody root method.
// then we can talk about some permissions.
public class Main5Activity extends Activity {
    final double sampler = 0.99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getActionBar().hide();
        setContentView(R.layout.activity_main5);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setActionBar(toolbar);
//        FileDescriptor fd =R.drawable.cai;
        // should register background network service.
        // just check if that works.
        AssetFileDescriptor afd = null;
        GifDrawable dv=null;
        try{afd = getAssets().openFd("cai.gif");}catch (Exception e){e.printStackTrace();System.out.println("Shit happened");}
        try{dv= new GifDrawable(afd);}catch(Exception e){e.printStackTrace();System.out.println("Shit happened");}
        GifImageView iv = (GifImageView) findViewById(R.id.sample);
        final int dur = (int) ((double) dv.getDuration() * sampler);
        // so this is not working.
        dv.setLoopCount(1);
        iv.setImageDrawable(dv);
        dv.start();
//        iv.
        // wtf is going on?
        // fuck them all.
//        iv.setImage
//        GifDrawable gifFromPath = new GifDrawable("/storage/emulated/0/Android/data/com.zhangqie.gif/cache/thumb/SpiderDressUp.gif");
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });//all bullshit.
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(dur);
                    Intent it = new Intent(Main5Activity.this,MainActivity.class);
                    startActivity(it);
                    finish();
                }catch (Exception e){
                 e.printStackTrace();
                 // all idiot malware detections.
                }
            }
        };
        thread.start();
    }

}
