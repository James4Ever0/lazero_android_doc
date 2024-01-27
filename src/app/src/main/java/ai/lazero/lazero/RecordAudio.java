package ai.lazero.lazero;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class RecordAudio extends Service {
    public RecordAudio() {
    }
    // why we have this shit?
    private final static int SYSTEM_INPUT = MediaRecorder.AudioSource.REMOTE_SUBMIX;
    private final static int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;
    private final static int BOTH_INPUT = MediaRecorder.AudioSource.DEFAULT;
    public int status = Status.STATUS_NO_READY;
    public int bufferSize_common=0;
    public ByteBuffer fileName;
    private List<byte[]> filesName = new ArrayList<>();
    public AudioRecord audioRecord;
    public PowerManager.WakeLock mWakeLock = null;
    // the try.
    // how to get it installed into system apps?
    // use lucky patcher. -> but are we signed?
    /*
    *
    *
    */
    // need reboot to apply the changes? really.
    // get more rights. fight more shits.
    // graranteed to work if given permission to record 0 and 2.
    // pass to web interface. nodejs for data comprehension, and flask is way too lightweight.
//    private static int[] mSampleRates = new int[] { 8000, 11025, 22050, 44100 };
    // it won't fucking boot! fucking pricks! do not do this please?
    // we need to reflash the kernel this time. holy shit.
    // hope this works? or delete the folder under system/...
public AudioRecord findAudioRecord(int audioSource,int rateX) {
    for (int rate :new int[] { 8000, 11025, 22050, 44100 }) {
        for (short audioFormat : new short[] { AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT }) {
            for (short channelConfig : new short[] { AudioFormat.CHANNEL_IN_MONO, AudioFormat.CHANNEL_IN_STEREO }) {
                try {
                    Log.d("TEST CONFIG", "Attempting rate " + rate + "Hz, bits: " + audioFormat + ", channel: "
                            + channelConfig);
                    int bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat)*rateX;

                    if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                        // check if we can instantiate and have a success
                        AudioRecord recorder = new AudioRecord(audioSource, rate, channelConfig, audioFormat, bufferSize);
                        if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
                            bufferSize_common=bufferSize;
                        // it did return somehow.
                        // what the fuck? if error is here?
                        Log.d("TESTER","INITIALIZED");
                            return recorder;
                    }
                } catch (Exception e) {
                    Log.e("TEST CONFIG", rate + "Exception, keep trying."+e.toString());
                }
            }
        }
    }
    return null;
}

//AudioRecord
//recorder.release();
/*
    *
    *
    * */
    // need to be a system app to do so. use magisk module systemize to elevate the right.
    // and you'll also have the right to grab screenbuffer, record video without permission prompt.
    // holy shit! consider finding similar things under Windows, Mac.

    public Notification note(){
        String CHANNEL_ID = "ai.lazero.lazero.AudioRec";
        String CHANNEL_NAME = "TEST";
        NotificationChannel notificationChannel = null;
        // you've got some foreground service. shit then.
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(this, MyService2.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, 0);

        Notification notification = new Notification.Builder(this,CHANNEL_ID).
                setContentTitle("Lazero").
                setContentText("AudioCap service running.").
                setWhen(System.currentTimeMillis()).
                setSmallIcon(R.drawable.icon).
                setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)).
                setContentIntent(pendingIntent).build();
        return notification;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Notification notification = note();
        startForeground(1349, notification);
        Log.v("AUDIO SERVICE", "START_SUCCESS");
//        serviceLogger("On create",0,0);
//        httpPostBytes= new HttpPostBytes("http://localhost:4999/sample",null);
//        myThread=new MyThread(httpPostBytes,byteClass,"type","screenshot");
//        myThread.start();
//        instance=this;
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        /**
         * PowerManager.PARTIAL_WAKE_LOCK:保持CPU运转，屏幕和键盘灯可能是关闭的
         * PowerManager.SCREEN_DIM_WAKE_LOCK:保持CPU运转,运行屏幕显示但是屏幕有可能是灰的，允许关闭键盘灯
         * PowerManager.SCREEN_BRIGHT_WAKE_LOCK：保持CPU运转，屏幕高亮显示，允许关闭键盘灯
         * PowerManager.FULL_WAKE_LOCK：保持CPU运转，屏幕高亮显示，键盘灯高亮显示
         * PowerManager.ON_AFTER_RELEASE：当锁被释放时，保持屏幕亮起一段时间
         * PowerManager.ACQUIRE_CAUSES_WAKEUP：强制屏幕亮起
         */
        // not allowed... background....
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AudioCap");
//        KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        mWakeLock.acquire();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // fucking shit?
        // first send an empty intent? or special intent.
        String userID = intent.getStringExtra("command");
        // you can get more things.
        if (userID.equals("INIT_SERVICE")) {
            Log.v("AUDIO SERVICE", userID);
        } else if(userID.equals("START")) {
            Log.v("AUDIO SERVICE", userID);
            int rates = Integer.parseInt(intent.getStringExtra("rates"));
            int channel = Integer.parseInt(intent.getStringExtra("channel"));
//            System.out.println(rates);
//            System.out.println(channel);
            bufferSize_common=init_set(channel,rates);
            Log.e("AUDIO BUFFER SIZE",String.valueOf(bufferSize_common));
            // it is not zero ones. but how comes?
            if (bufferSize_common!=0){
                init_buffer(bufferSize_common);
                // we can start here.
                status=Status.STATUS_READY;
                startRecord();
            }else{status=Status.STATUS_NO_READY;}
        } else if (userID.equals("STOP")) {stopRecord();cancel();
            Log.v("AUDIO SERVICE", userID);
        } else if (userID.equals("PAUSE")) {Log.v("AUDIO SERVICE", userID);if (!audioRecord.equals(null)){status=Status.STATUS_PAUSE;}else{Log.e("AUDIO REC","ALREADY STOPPED");}
        } else if (userID.equals("CANCEL")) {
            Log.v("AUDIO SERVICE", userID);
            cancel();
        } else {
            Log.v("AUDIO SERVICE", userID);
            Log.e("AUDIO REC CONTROL", "NOT IMPLEMENTED");
        }
//        threadDisable = true;
//        try{Thread.sleep(3000);}catch (Exception e){e.printStackTrace();}
//        flags = START_STICKY;
//        handler.removeCallbacks(task);
//        handler.removeCallbacksAndMessages(null);
//        handler.postDelayed(task,5000);
//        handler.post(task);
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
//        boolean d = ((Apl) getApplication()).getMyPublicData();
        boolean d = false;
        // maybe that's the reason it cannot survive.
        // keep it alive.
        if (!d) {
            Intent intent = new Intent("ai.lazero.lazero.recreateY");
            sendBroadcast(intent);
        }
        // get all things killed.
        stopRecord();
        cancel();
        // do it again?
        Log.v("AudioRecorderService", "on destroy");
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        super.onDestroy();
    }
    // how to return a service instance?
    /*
    public int calcBufferSize(int sampleRateInHz, int channelConfig, int audioFormat,int bufferRate){
        int bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
                channelConfig, audioFormat);
        return bufferSizeInBytes*bufferRate;
    }*/
    /*
    public AudioRecord createAudio(int audioSource, int sampleRateInHz, int channelConfig, int audioFormat,int bufferSizeInBytes) {
        // 获得缓冲区字节大小
        AudioRecord audioRecord = createDefaultAudio(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes);
//        this.fileName = fileName;
        return audioRecord;
    }

    /**
     * 创建默认的录音对象
     * @param fileName 文件名
     */
// use a separate thread to dump buffer?*/
/*
    public AudioRecord createDefaultAudio(int INPUT_SRC, int sampleRateInHz, int channelConfig, int audioFormat,int bufferSizeInBytes) {
//        mContext = ctx;
//        mHandler = handler;
        AudioRecord audioRecord = new AudioRecord(INPUT_SRC,sampleRateInHz,  channelConfig,  audioFormat,bufferSizeInBytes);
//        this.fileName = fileName;
        status = Status.STATUS_READY;
        return audioRecord;
    }*/
    // pass some parameter while starting the service.
    public int init_set(int a,int bufferRate) {
//        recorder = findAudioRecord();
//        int AUDIO_SAMPLE_RATE = 44100;
////        int AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_STEREO;
//        // shit. must use some universal setting.
//        int AUDIO_CHANNEL=AudioFormat.CHANNEL_IN_MONO;
//        int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
//        int bufferSizeInBytes = calcBufferSize(AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING, bufferRate);
        // we will not be able to record direct audio. and the time for playing shall be measured in a separate thread.
        // do a timestamp check.
        if ( a == 0 ) {
            audioRecord = findAudioRecord(BOTH_INPUT,bufferRate);
            if (!audioRecord.equals(null)){
//            bufferSize_common = bufferSize_common;
            return bufferSize_common;}else{return 0;}
        } else if (a == 1) {
            // without permission.
            audioRecord = findAudioRecord(SYSTEM_INPUT,bufferRate);
            if (!audioRecord.equals(null)){
//            bufferSize_common = bufferSize_common;
                return bufferSize_common;}else{return 0;}
        } else if (a == 2) {
            audioRecord = findAudioRecord(AUDIO_INPUT,bufferRate);
            if (!audioRecord.equals(null)){
//            bufferSize_common = bufferSize_common;
                return bufferSize_common;}else{return 0;}
        } else {
            Log.e("NO AUDIO", "Audio path Not yet implemented");
            bufferSize_common = 0;
            return 0;
        }
//        return 0;
    }
    public void startRecord() {
        if (status == Status.STATUS_NO_READY ) {
            Log.e("NO AUDIO","NOT READY");
            return;
        }
        if (status == Status.STATUS_START) {
//            throw new IllegalStateException("正在录音");
            Log.e("AUDIO","RECORDING");
            return;
        }
        // can be STOP, READY, PAUSE.
        // set to ready please?
        int checkState=audioRecord.getState();
        Log.d("AudioRecorder","startRecord==="+String.valueOf(checkState));
        if (checkState == AudioRecord.STATE_INITIALIZED){
        audioRecord.startRecording();
//        audioRecord.
// really?
        new Thread(new Runnable() {
            @Override
            public void run() {
//                writeDataTOFile(listener);
                if (bufferSize_common!=0)
                {writeDataTOFile(bufferSize_common);
                Log.d("AUDIO DAEMON BUFFERSIZE",String.valueOf(bufferSize_common));}
                else{ Log.d("AUDIO DAEMON","START FAILED");}
                // how to get it?
                // cannot do nothing.
            }
        }).start();}
        else{
            Log.e("AUDIO CHANNEL","NOT INITIALIZED -> CHECK FOR PERMISSIONS LIKE SYSTEM APPS");
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        Log.d("AudioRecorder","===stopRecord===");
        if (status == Status.STATUS_NO_READY || status == Status.STATUS_READY) {
            Log.e("ERROR","录音尚未开始");
        } else {
            audioRecord.stop();
            status = Status.STATUS_STOP;
            release();
        }
    }

    /**
     * 取消录音
     */
    public void cancel() {
        filesName.clear();
        fileName = null;
        // assign to null to clear all.
        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }
        status = Status.STATUS_NO_READY;
    }
    /**
     * 释放资源
     */
    public void release() {
        Log.d("AudioRecorder","===release===");
        //假如有暂停录音
        // shall use byte[] array.
        try {
            if (filesName.size() > 0) {
//                List<byte[]> filePaths = new ArrayList<>();
                for (byte[] fileNamex : filesName) {
//                    filePaths.add(fileNamex);
//                    System.out
                    Log.e("AUDIO FILE CAPTURED",String.valueOf(fileNamex.length));
                }
                //清除
                filesName.clear();
                // just check. not playing.
                //将多个pcm文件转化为wav文件
//                mergePCMFilesToWAVFile(filePaths);

            } else {
                //这里由于只要录音过filesName.size都会大于0,没录音时fileName为null
                //会报空指针 NullPointerException
                // 将单个pcm文件转化为wav文件
                //Log.d("AudioRecorder", "=====makePCMFileToWAVFile======");
                //makePCMFileToWAVFile();
                // nothing here.
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }
        status = Status.STATUS_NO_READY;
    }
// just a useless demo.
    public void init_buffer(int bufferSize_common){
        fileName=null;
        byte[] allByteArray = new byte[bufferSize_common*500];
        fileName = ByteBuffer.wrap(allByteArray);
    }
    /**
     * 将音频信息写入文件
     * @param listener 音频流的监听
     */
    private void writeDataTOFile(int bufferSizeInBytes) {
        // new一个byte数组用来存一些字节数据，大小为缓冲区大小
        byte[] audiodata = new byte[bufferSizeInBytes];
//        FileOutputStream fos = null;
        int readsize = 0;
        try {
            byte[] currentFileName = fileName.array();
            if (status == Status.STATUS_PAUSE) {
                init_buffer(bufferSizeInBytes);
//                currentFileName += filesName.size();
            }
            filesName.add(currentFileName);
        } catch (Exception e) {
            Log.e("AudioRecorder", e.getMessage());
//            throw new IllegalStateException(e.getMessage());
        } /*catch (Exception e) {
            Log.e("AudioRecorder", e.getMessage());
        }*/
        // send you some bitches.
        //将录音状态设置成正在录音状态
        status = Status.STATUS_START;
        while (status == Status.STATUS_START) {
            Log.e("AUDIO WORKER","ONE LOOP");
            readsize = audioRecord.read(audiodata, 0, bufferSizeInBytes);
            // from .. to ..
            if ( AudioRecord.ERROR_INVALID_OPERATION != readsize && fileName !=null) {
                try {
//                    fos.write(audiodata);
                    fileName.put(audiodata.clone());
                } catch (Exception e) {
                    Log.e("AudioRecorder", e.getMessage());
                }
            }
        }
        // now everything is done.
        // where is the noise?
        try {
//            if (fos != null) {
            Log.d("AUDIO RECORD","DONE");
//                fos.close();// 关闭写入流
//            }
        } catch (Exception e) {
            Log.e("AudioRecorder", e.getMessage());
        }
    }
// this is a service.
    // there are potential bugs under comments.
    // fuck them all.
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
        // why the first one won't work? need system rights?
    }
}
