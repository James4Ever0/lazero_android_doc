package ai.lazero.lazero;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
//import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;

public class SimpleService extends Service {
    public String tsgen() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        return ts;
    }
//
//    public final BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String a = intent.getAction();
//            if (a.equals("")){}
//        }
//    }

    private String TAG = "simple_service";
    private WebSocketClient webSocketClient;

    public void javascriptRelay(String script) {
        Intent iit = new Intent("ai.lazero.lazero.javascript");
        iit.putExtra("script", script);
        sendBroadcast(iit);
        System.out.println("simple_service_log: Sending Javascript Execution Request");
    }

    private void createWebSocketClient(String link) {
        URI uri;
        try {
            uri = new URI(link);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        // we're gonna send something to the place.
        // this is auto gc. seems fun.
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                System.out.println("onOpen");
                webSocketClient.send("Hello, World!");
            }

            @Override
            public void onTextReceived(String message) {
                System.out.println("onTextReceived");
                String ts = tsgen();
//                String filename = "simple_service_" + ts + "_error";
                System.out.println("simple_service_string_recv" + ts + message);
                if (message != null) {
                    javascriptRelay(message);
                }
                // pretend it is valid javascript.
                // register another broadcast receiver? send it back to the central.
            }

            @Override
            public void onBinaryReceived(byte[] data) {
                System.out.println("onBinaryReceived");
                int length;
                try {
                    length = data.length;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("simple_service_log: <MAY HAVE NULL BYTE ARRAY>");
                    length = 0;
                }
                String ts = tsgen();
                System.out.println("simple_service_bytes_recv: length " + ts + " " + String.valueOf(length));
            }

            @Override
            public void onPingReceived(byte[] data) {
                System.out.println("onPingReceived");
                // what is this ping thing?
            }

            @Override
            public void onPongReceived(byte[] data) {
                System.out.println("onPongReceived");
                // what is pong?
            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
                // this will be called.
                String ts = tsgen();
                String filename = "simple_service_" + ts + "_error";
                System.out.println(filename);
            }

            @Override
            public void onCloseReceived() {
                System.out.println("onCloseReceived");
            }
        };

        webSocketClient.setConnectTimeout(1000);
        webSocketClient.setReadTimeout(2000);
//        webSocketClient.addHeader("Origin", "http://developer.example.com");
        webSocketClient.enableAutomaticReconnection(1000);
        webSocketClient.connect();
        // do this over and over.
        // sending intent to the service? the webshot service.
    }

    public boolean thread_start = false;

    public static String urlFetch(String url) {
        return JobSchedulerService.loginByGet(url);
    }
    // what is this anyway?

    public PowerManager.WakeLock mWakeLock = null;

    public Notification note() {
        String CHANNEL_ID = "ai.lazero.lazero.SimpleService";
        String CHANNEL_NAME = "SIMPLE";
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
                setContentText("Simple Service running.").
                setWhen(System.currentTimeMillis()).
                setSmallIcon(R.drawable.icon).
                setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).
                setContentIntent(pendingIntent).build();
        return notification;
    }

    public void forth() {
        Notification notification = note();
        startForeground(2759, notification);
        Log.v("SIMPLE_SERVICE", "START_SUCCESS");
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

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
//        throw new UnsupportedOperationException("Not yet implemented");
        // get a new alarm.
    }

    public void feedback(String op) {
        if (webSocketClient != null) {
            if (op != null) {
                webSocketClient.send("javascript_output: " + op);
                System.out.println("simple_service_log: output sent.");
            }
        }
    }

    public void revive() {
        Intent intent = new Intent(this, SimpleService.class);
        PendingIntent pendingIntent = PendingIntent.getForegroundService(getApplicationContext(),
                0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
// not foreground service?
        long period = 1000 * 15;
        // 15 seconds for checking?
        try {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + period, pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do it in separate thread. and set an alarm after that.
        // get intents. or check the content of the intent.
        String tag;
        try {
            tag = intent.getStringExtra("tag");
            if (tag == null) {
                tag = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("simple_service_log: may have empty tag?");
            tag = null;
        }
        if (tag == null) {
            if (!thread_start) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // better not to start sending useless package but to establish a socket connection.
                        // loop [ connect -> share info async -> break ]
//                while (true) {
//                    try {
                        // one single run? get some suppression?
                        // do not know yet. waiting for sync?
                        createWebSocketClient("ws://localhost:5000/socket");
                        while (true) {
                            try {
                                String ts = tsgen();
                                // just use that thing.
                                webSocketClient.send("some random message after connection " + ts);
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("simple_service_log: <FAILED TO SEND MESSAGE>");
                            } finally {
                                try {
                                    Thread.sleep(1000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("simple_service_log: <THREAD IS PROBABLY DEAD>");
                                    break;
                                }
                            }
                        }
                        // this will not work? cause we have too much of it. we've sucked it up.
//                        try {
//                            Thread.sleep(1000);
//                        } catch (Exception e) {
//                            Log.e(TAG, "sleep failed");
//                            break;
//                        }
//                        Long tsLong = System.currentTimeMillis() / 1000;
//                        String ts = "packet_" + tsLong.toString();
//                        Log.e(TAG, "SENDING SIMPLE PACKET: " + ts);
//                        String a = urlFetch("http://localhost:7777/" + ts);
//                        if (a != null) {
//                            Log.i(TAG, a);
//                        } else {
//                            Log.i(TAG, "ERROR");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                    }
                }).start();
                thread_start = true;
            }
            this.revive();
        } else {
            if (tag.equals("script_output")) {
                // never get this shit.
                String output = intent.getStringExtra("output");
                // not null.
                String ts = tsgen();
                System.out.println("simple_service_exec: [javascript output " + ts + " ]\n" + output);
                feedback(output);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.forth();
    }

    @Override
    public void onDestroy() {
        this.revive();
        super.onDestroy();
    }
}
