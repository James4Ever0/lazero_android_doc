package ai.lazero.lazero;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

//import com.gyf.cactus.Cactus;

import java.io.DataOutputStream;

import me.weishu.reflection.Reflection;
//import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static String TAG= "MAJOR_PROCESS";
    // check many ways to crack all these shits.
    // not as good as the start command?
//    private NewMessageNotification manager;
    public void revive(){
        Intent intent = new Intent(this, SimpleService.class);
        PendingIntent pendingIntent = PendingIntent.getForegroundService(getApplicationContext(),
                0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
// not foreground service?
        long period = 1000;
        // 15 seconds for checking?
        try {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + period, pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // a kernel is actually something running over and over again.
//Reflection.unseal(MainActivity.this)
// get the thing.
    // isn't it taking too long to save the file?
    // say it.
    public static boolean upgradeRootPermission(String pkgCodePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + pkgCodePath;
            process = Runtime.getRuntime().exec("su"); //切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
            // not doing shit.
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Reflection.unseal(MainActivity.this);
        super.onCreate(savedInstanceState);
//        mJobScheduler = (JobScheduler) getSystemService( Context.JOB_SCHEDULER_SERVICE );
        setContentView(R.layout.activity_main);
        // not using toolbar.
        Button fac0 = (Button) findViewById(R.id.fab0);
        fac0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here's the shit.
                Snackbar.make(view, "This is the main lazero program.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MainXActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
//        setSupportActionBar(toolbar);
        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here's the shit.
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        Button fabs = (Button) findViewById(R.id.fabs);
        fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here's the shit.
                Snackbar.make(view, "This is the main lazero program.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main2Activity.class);
                MainActivity.this.startActivity(intent);
            }
        });
//        getWin
        Button fabc = (Button) findViewById(R.id.fabc);
        fabc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Starting service", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Intent intent = new Intent(MainActivity.this, MyService.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startService(intent);
                    }
                }
        );
        Button fabm = (Button) findViewById(R.id.fabm);
        fabm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Killing service", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Intent intent = new Intent(MainActivity.this, MyService.class);
                        stopService(intent);
                        //cannot start the service.
                        // cause you don't bind it.
                    }
                }
        );
        Button fabk = (Button) findViewById(R.id.fabk);
        fabk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean k = upgradeRootPermission(getPackageCodePath());
                        if (k == true) {
                            Snackbar.make(view, "Root acquire success", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
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
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           boolean k = upgradeRootPermission(getPackageCodePath());
                                           if (k == true) {
                                               Snackbar.make(view, "Root acquire success & Starting Screencap Service", Snackbar.LENGTH_LONG)
                                                       .setAction("Action", null).show();
                                               startService(new Intent(MainActivity.this, MyService2.class)); // 注册广播接收器
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
        //do these later.
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           boolean k = upgradeRootPermission(getPackageCodePath());
                                           if (k == true) {
                                               Snackbar.make(view, "Jumping to screencap viewing action.", Snackbar.LENGTH_LONG)
                                                       .setAction("Action", null).show();
                                               Intent intent = new Intent();
                                               intent.setClass(MainActivity.this, Main3Activity.class);
                                               MainActivity.this.startActivity(intent);
//                                                   startService(new Intent(MainActivity.this, Main3Activity.class)
                                               // 注册广播接收器
                                           } else {
                                               Snackbar.make(view, "Failed to jump.", Snackbar.LENGTH_LONG)
                                                       .setAction("Action", null).show();
                                           }
//                            Intent intent = new Intent(MainActivity.this, MyService.class);
//                            stopService(intent);
                                           //cannot start the service.
                                           // cause you don't bind it.
                                       }
                                   }
        );
        Button buttonX = (Button) findViewById(R.id.buttonX);
        buttonX.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {//boolean k=upgradeRootPermission(getPackageCodePath());
//            if (k==true){
                                           Snackbar.make(view, "Jumping to Clipboard Manager Viewer.", Snackbar.LENGTH_LONG)
                                                   .setAction("Action", null).show();
                                           Intent intent = new Intent();
                                           intent.setClass(MainActivity.this, MainClipActivity.class);
                                           MainActivity.this.startActivity(intent);
                                           // start the service later?
                                       }
                                   }
        );
        Button button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Snackbar.make(view, "Starting Audio Recoding Service", Snackbar.LENGTH_LONG)
                                                   .setAction("Action", null).show();
                                           Intent mIntent = new Intent(MainActivity.this,RecordAudio.class);
                                           mIntent.putExtra("command","INIT_SERVICE");
                                           startService(mIntent);
                                       }
                                   }
        );
        Button button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Snackbar.make(view, "Starting Audio Recoding Service", Snackbar.LENGTH_LONG)
                                                   .setAction("Action", null).show();
                                           Intent mIntent = new Intent(MainActivity.this,RecordAudio.class);
                                           mIntent.putExtra("command","START");
                                           mIntent.putExtra("channel","1");
                                           mIntent.putExtra("rates","1");
                                           startService(mIntent);
                                       }
                                   }
        );
        Button button10 = (Button) findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Snackbar.make(view, "Starting Audio Recoding Service", Snackbar.LENGTH_LONG)
                                                   .setAction("Action", null).show();
                                           Intent mIntent = new Intent(MainActivity.this,RecordAudio.class);
                                           mIntent.putExtra("command","START");
                                           mIntent.putExtra("channel","1");
                                           mIntent.putExtra("rates","5");
                                           startService(mIntent);
                                       }
                                   }
        );
        Button button11 = (Button) findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "Starting Audio Recoding Service", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent mIntent = new Intent(MainActivity.this,RecordAudio.class);
                                            mIntent.putExtra("command","START");
                                            mIntent.putExtra("channel","1");
                                            mIntent.putExtra("rates","25");
                                            startService(mIntent);
                                        }
                                    }
        );
        Button button13 = (Button) findViewById(R.id.button13);
        button13.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "STOP", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent mIntent = new Intent(MainActivity.this,RecordAudio.class);
                                            mIntent.putExtra("command","STOP");
                                            startService(mIntent);
                                        }
                                    }
        );
        Button button14 = (Button) findViewById(R.id.button14);
        button14.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "PAUSE", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent mIntent = new Intent(MainActivity.this,RecordAudio.class);
                                            mIntent.putExtra("command","PAUSE");
                                            startService(mIntent);
                                        }
                                    }
        );
        Button button15 = (Button) findViewById(R.id.button15);
        button15.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "CANCEL", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent mIntent = new Intent(MainActivity.this,RecordAudio.class);
                                            mIntent.putExtra("command","CANCEL");
                                            startService(mIntent);
                                        }
                                    }
        );
        Button button16 = (Button) findViewById(R.id.button16);
        button16.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "Stopping Audio Recording Service", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent mIntent = new Intent(MainActivity.this,RecordAudio.class);
//                                            mIntent.putExtra("command","CANCEL");
                                            stopService(mIntent);
                                        }
                                    }
        );
        Button button17 = (Button) findViewById(R.id.button17);
        button17.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "Starting Audio Recoding Service", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent mIntent = new Intent(MainActivity.this,RecordAudio.class);
                                            mIntent.putExtra("command","START");
                                            mIntent.putExtra("channel","0");
                                            mIntent.putExtra("rates","25");
                                            startService(mIntent);
                                        }
                                    }
        );
        Button button18 = (Button) findViewById(R.id.button18);
        button18.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "Starting Audio Recoding Service", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent mIntent = new Intent(MainActivity.this,RecordAudio.class);
                                            mIntent.putExtra("command","START");
                                            mIntent.putExtra("channel","2");
                                            mIntent.putExtra("rates","25");
                                            startService(mIntent);
                                        }
                                    }
        );
        Button button19 = (Button) findViewById(R.id.button19);
        button19.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "Starting Web Scraping Service", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent mIntent = new Intent(MainActivity.this,ScreenshotService.class);
                                            startService(mIntent);
                                        }
                                    }
        );
        Button button20 = (Button) findViewById(R.id.button20);
        button20.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "Webshot", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent intent = new Intent("ai.lazero.lazero.webshot");
                                            sendBroadcast(intent);
                                        }
                                    }
        );
        Button button21 = (Button) findViewById(R.id.button21);
        button21.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "Webdump", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent intent = new Intent("ai.lazero.lazero.webdump");
                                            sendBroadcast(intent);
                                        }
                                    }
        );
        Button button22 = (Button) findViewById(R.id.button22);
        button22.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "Trying to stop Webservice", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent intent = new Intent(MainActivity.this, ScreenshotService.class);
                                            stopService(intent);
                                        }
                                    }
        );
        Button button23 = (Button) findViewById(R.id.button23);
        button23.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "Trying to revive Webservice", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent intent = new Intent("ai.lazero.lazero.r");
                                            sendBroadcast(intent);
                                        }
                                    }
        );
        Button button24 = (Button) findViewById(R.id.button24);
        button24.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "JobIntentService Launcher", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Intent workIntent = new Intent();
//                                            num++;
                                            Log.d("houson", "onClick: JOBINTENTSERVICE");
                                            workIntent.putExtra("work","work num: 666");
                                            JobSchedulerService.enqueueWork(getApplicationContext(),workIntent);
//                                            JobInfo.Builder builder = new JobInfo.Builder( 1,
//                                                    new ComponentName( getPackageName(), JobSchedulerService.class.getName() ) );
//                                            builder.setPeriodic( 1000 );
//                                            // use this shit to communicate to my webbrowser.
//                                            if( mJobScheduler.schedule( builder.build() ) <= 0 ) {
//                                                //If something goes wrong
//                                                Log.e(TAG,"FAILED TO BUILD JOB");
//                                            }
                                        }
                                    }
        );

        // cactus is not working as expected.
        Button button25 = (Button) findViewById(R.id.button25);
        button25.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "SimpleService Launcher", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            revive();
                                            Log.e(TAG,"SIMPLE SERVICE STARTED");
//                                            Cactus.getInstance().unregister(MainActivity.this);
//                                            mJobScheduler.cancelAll();
////                                            Intent intent = new Intent("ai.lazero.lazero.r");
////                                            sendBroadcast(intent);
                                        }
                                    }
        );
        Button button26 = (Button) findViewById(R.id.button26);
        button26.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // this could be self_explanatory?
                                            // nothing here.
                                            Snackbar.make(view, "KeepAlive Webservice Restart", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
//                                            Cactus.getInstance().restart(MainActivity.this);
////                                            Intent intent = new Intent("ai.lazero.lazero.r");
//                                            sendBroadcast(intent);
                                        }
                                    }
        );
        Button button27 = (Button) findViewById(R.id.button27);
        button27.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // this could be self_explanatory?
                                            Snackbar.make(view, "M6 launched.", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
//                                            Cactus.getInstance().restart(MainActivity.this);
                                            Intent intent = new Intent(MainActivity.this,Main6Activity.class);
                                            startActivity(intent);
                                        }
                                    }
        );

//        NewMessageNotification nm=new NewMessageNotification();
//        manager.notifyAll();
    }
}



