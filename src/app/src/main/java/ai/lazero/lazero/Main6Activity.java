package ai.lazero.lazero;


import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.app.AlertDialog;

import ai.lazero.lazero.m6.AcService;
import ai.lazero.lazero.m6.AdService;
import ai.lazero.lazero.m6.AeService;
import ai.lazero.lazero.m6.BubbleService;
import ai.lazero.lazero.m6.Main7Activity;
import ai.lazero.lazero.m6.Main8Activity;
import ai.lazero.lazero.m6.PowerButtonService;
import ai.lazero.lazero.m6.RecPlayService;
import ai.lazero.lazero.m6.ViewService;
import ai.lazero.lazero.m6.basic_overlay;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class Main6Activity extends AppCompatActivity {
    public String TAG = "LUCKY";
    public static int OVERLAY_PERMISSION_CODE = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
    public static String[] launcher = {"su -c \"CLASSPATH=/data/app/ai.lazero.lazero/base.apk /system/bin/app_process32 /system/bin ai.lazero.lazero.m6.Main7Activity optionalArgs\""};

    public void diaglock() {
        try {
            View view = new View(getApplicationContext());
//        view
            Dialog dialog = new AlertDialog.Builder(getApplicationContext(), R.style.TransparentWindowBg)
                    .setView(view)
                    .create();

            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
//        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
            window.setType(OVERLAY_PERMISSION_CODE);
//        TYPE_APPLICATION_OVERLAY
            // use this instead?
            // make a transparent overlay view, and control actions.
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            System.out.print("EXCEPTION OVERLAY PERMISSION");
            addOverlay();
        }
    }

    //public class MView extends View{
//
//}
    public void diagblock() {
        try {
            View view = new basic_overlay(getApplicationContext(), "nonsense");
//            view
            Dialog dialog = new AlertDialog.Builder(getApplicationContext(), R.style.TransparentWindowBg)
                    .setView(view)
                    .create();
// how to create transparent overlay clickthrough?
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
//        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
            window.setType(OVERLAY_PERMISSION_CODE);
//        TYPE_APPLICATION_OVERLAY
            // use this instead?
            // make a transparent overlay view, and control actions.
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            System.out.print("EXCEPTION OVERLAY PERMISSION");
            addOverlay();
        }
    }

    public void addOverlay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
//                askedForOverlayPermission = true;
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_CODE);
            }
        }
    }

    // check how to get system permissions?
    // launch this app as root. use a custom launcher.
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_POWER) {
//            // Do something here...
//            System.out.print("{M6} Short power press");
//            event.startTracking(); // Needed to track long presses
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
////        KeyEvent.Po
//        if (keyCode == KeyEvent.KEYCODE_POWER) {
//            // Do something here...
//            System.out.print("{M6} Long power press");
//            return true;
//        }
//        return super.onKeyLongPress(keyCode, event);
//    }
//    public void keepUp(){
//        getWindow().addFlags(WindowManager.LayoutParams.);
//////        try{
//////        int val=android.provider.Settings.System.getInt(getContentResolver(), SCREEN_OFF_TIMEOUT);
//////
//////        android.provider.Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, -1);
//////        Toast.makeText(this, "Disabled Screen Timeout", Toast.LENGTH_LONG).show();
//////        SharedPreferences.Editor editor = settings.edit();
//////        editor.putInt("ScreenTimeout",val);
//////        editor.commit();
//////    }
//////} catch(Throwable er) {
//////        Toast.makeText(this, "Error "+er.getMessage(), Toast.LENGTH_LONG).show();
//////        }
//////        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE);
//////        KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
//////        lock.disableKeyguard();
//        }
    public void hide() {
        try {
            View decorView = getWindow().getDecorView();
// Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
            ActionBar actionBar = getActionBar();
            actionBar.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void keyPrevent() {
        if (Settings.canDrawOverlays(this)) {
            startService(new Intent(this, PowerButtonService.class));
        } else {
            addOverlay();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        Button fab0 = (Button) findViewById(R.id.fab0);
        fab0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here's the shit.
                Snackbar.make(view, "Alert Screen Blocker", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                diaglock();
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, SettingsActivity.class);
//                MainActivity.this.startActivity(intent);
            }
        });
        Button fab1 = (Button) findViewById(R.id.buttonX);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here's the shit.
                Snackbar.make(view, "Power Button Blocker", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                diaglock();
                keyPrevent();
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, SettingsActivity.class);
//                MainActivity.this.startActivity(intent);
            }
        });
        Button fab2 = (Button) findViewById(R.id.button1);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here's the shit.
                Snackbar.make(view, "Power Button Blocker", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                diagblock();
//                keyPrevent();
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, SettingsActivity.class);
//                MainActivity.this.startActivity(intent);
            }
        });
        Button fab3 = (Button) findViewById(R.id.button2);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here's the shit.
                Snackbar.make(view, "Demo Overlay", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                diagblock();
//                keyPrevent();
                Intent intent = new Intent();
                intent.setClass(Main6Activity.this, ViewService.class);
                Main6Activity.this.startService(intent);
            }
        });
        Button fab4 = (Button) findViewById(R.id.fab);
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here's the shit.
                Snackbar.make(view, "Overlay II", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                diagblock();
//                keyPrevent();
                Intent intent = new Intent();
                intent.setClass(Main6Activity.this, BubbleService.class);
                Main6Activity.this.startService(intent);
            }
        });
        Button fab5 = (Button) findViewById(R.id.fabs);
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here's the shit.
                Snackbar.make(view, "Accessibility Overlay", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                diagblock();
//                keyPrevent();
                Intent intent = new Intent();
                intent.setClass(Main6Activity.this, AcService.class);
                try {
                    Main6Activity.this.startService(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Button fab6 = (Button) findViewById(R.id.fabc);
        fab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here's the shit.
                Snackbar.make(view, "In App Hide", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                hide();
//                diagblock();
////                keyPrevent();
//                Intent intent = new Intent();
//                intent.setClass(Main6Activity.this, AcService.class);
//                try{Main6Activity.this.startService(intent);}catch(Exception e){e.printStackTrace();}
            }
        });
        Button fab7 = (Button) findViewById(R.id.fabm);
        fab7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here's the shit.
                Snackbar.make(view, "Gesture Accessibility Service", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                hide();
//                diagblock();
////                keyPrevent();
                Intent intent = new Intent();
                intent.setClass(Main6Activity.this, AdService.class);
                try {
                    Main6Activity.this.startService(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Button fab8 = (Button) findViewById(R.id.fabk);
        fab8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here's the shit.
                Snackbar.make(view, "Blocker Accessibility Service", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                hide();
//                diagblock();
////                keyPrevent();
                Intent intent = new Intent();
                intent.setClass(Main6Activity.this, AeService.class);
                try {
                    Main6Activity.this.startService(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Button button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Snackbar.make(view, "Starting RECPLAY Service", Snackbar.LENGTH_LONG)
                                                   .setAction("Action", null).show();
                                           Intent mIntent = new Intent(Main6Activity.this, RecPlayService.class);
                                           mIntent.putExtra("command", "INIT_SERVICE");
                                           startService(mIntent);
                                           // and more.
                                           new Thread(new Runnable() {
                                               @Override
                                               public void run() {
                                                   try {
                                                       Thread.sleep(500);
                                                   } catch (Exception e) {
                                                       e.printStackTrace();
                                                   }
                                                   Snackbar.make(view, "Starting RECPLAY Service", Snackbar.LENGTH_LONG)
                                                           .setAction("Action", null).show();
                                                   Intent mIntent = new Intent(Main6Activity.this, RecPlayService.class);
                                                   mIntent.putExtra("command", "LOOP");
                                                   mIntent.putExtra("channel", "2");
                                                   mIntent.putExtra("rates", "1");
                                                   startService(mIntent);
                                               }
                                           }).start();
                                       }
                                   }
        );
        Button button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Snackbar.make(view, "M7", Snackbar.LENGTH_LONG)
                                                   .setAction("Action", null).show();
                                           Intent mIntent = new Intent(Main6Activity.this, Main7Activity.class);
//                                           mIntent.putExtra("command","INIT_SERVICE");
                                           startActivity(mIntent);
                                       }
                                   }
        );
        Button button10 = (Button) findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "RM7", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            boolean root = MainActivity.upgradeRootPermission(getPackageCodePath());
                                            if (root) {
                                                Log.e(TAG, "with root");

                                                boolean result = AeService.RootPermission(launcher);
                                                if (result) {
                                                    Log.e(TAG, "lucky");
                                                } else {
                                                    Log.e(TAG, "no luck");
                                                }
                                            } else {
                                                Log.e(TAG, "no root");
                                            }
//                                           Intent mIntent = new Intent(Main6Activity.this, Main7Activity.class);
////                                           mIntent.putExtra("command","INIT_SERVICE");
//                                           startActivity(mIntent);
                                        }
                                    }
        );
        Button button11 = (Button) findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "M8", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                           Intent mIntent = new Intent(Main6Activity.this, Main8Activity.class);
//                                           mIntent.putExtra("command","INIT_SERVICE");
                                           startActivity(mIntent);
                                        }
                                    }
        );
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//        getWindow().clearFlags(WindowManager.LayoutParams.PREVENT_POWER_KEY);
//        su -c "CLASSPATH=/data/app/ai.lazero.lazero/base.apk /system/bin/app_process32 /system/bin in.omerjerk.remotedroid.m6.Main7Activity optionalArgs"
// really want to kill it?
    }


}
