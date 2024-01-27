package ai.lazero.lazero.m6;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import ai.lazero.lazero.MyService2;
import ai.lazero.lazero.R;
import ai.lazero.lazero.Status;

public class RecPlayService extends Service {
    public RecPlayService() {
    }

    // why we have this shit?
    private final static int SYSTEM_INPUT = MediaRecorder.AudioSource.REMOTE_SUBMIX;
    private final static int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;
    private final static int BOTH_INPUT = MediaRecorder.AudioSource.DEFAULT;
    public int status = Status.STATUS_NO_READY;
    public int bufferSize_common = 0;
    public ByteBuffer fileName;
    private List<byte[]> filesName = new ArrayList<>();
    public AudioRecord audioRecord;
    public PowerManager.WakeLock mWakeLock = null;
    public ConfigClass configClass = null;
    public boolean isPlaying = false;
    public boolean isStop = true;
    public boolean endloop = true;
    public String TAG = "FUCK";
    public Thread openThread = null;
//    public byte[] concatenated_byte_array = null;

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
    public AudioRecord findAudioRecord(int audioSource, int rateX) {
        for (int rate : new int[]{44100}) {
            for (short audioFormat : new short[]{AudioFormat.ENCODING_PCM_8BIT}) {
                for (short channelConfig : new short[]{AudioFormat.CHANNEL_IN_STEREO}) {
                    try {
                        Log.d("TEST CONFIG", "Attempting rate " + rate + "Hz, bits: " + audioFormat + ", channel: "
                                + channelConfig);
                        int bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat) * rateX;

                        if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                            // check if we can instantiate and have a success
                            AudioRecord recorder = new AudioRecord(audioSource, rate, channelConfig, audioFormat, bufferSize);
                            if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
                                // here is the presevation.
                                configClass = new ConfigClass(rate, channelConfig, audioFormat);
                            bufferSize_common = bufferSize;
                            // it did return somehow.
                            // what the fuck? if error is here?
                            Log.d("TESTER", "INITIALIZED");
                            return recorder;
                        }
                    } catch (Exception e) {
                        Log.e("TEST CONFIG", rate + "Exception, keep trying." + e.toString());
                    }
                }
            }
        }
        return null;
    }

    public void playback(byte[] audio) {
        if (configClass != null) {
//            AudioTrack.getMinBufferSize()
            Log.d(TAG, "PLAYING BACK.");
            Log.d(TAG, "audio length" + String.valueOf(audio.length));
            int bufferSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_8BIT);
            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_8BIT, bufferSize, AudioTrack.MODE_STREAM);
            try {
                audioTrack.play();
//                fis = new FileInputStream(path);
//                ByteArrayInputStream fis = new ByteArrayInputStream(audio);
//                byte[] buffer = new byte[bufferSize];
//                int len = 0;
//                int offset = 0;
                isPlaying = true;
                isStop = false;
//                audioTrack.
//                while ((len = fis.read(buffer)) != -1 && !isStop) {
//                    Log.e(TAG, "PLAYBACK ARRAY HASH: " + java.util.Arrays.hashCode(buffer));
//                    Log.d(TAG, "playPCMRecord: len " + len + " remaining: " + fis.available());
                    audioTrack.write(audio, 0, audio.length);
//                    offset += len;
//                    audioTrack.wr
//                }
                audioTrack.stop();
                audioTrack.release();
            } catch (Exception e) {
                Log.e(TAG, "playPCMRecord: e : " + e);
            }
        }

    }

    public Notification note() {
        String CHANNEL_ID = "ai.lazero.lazero.m6.RecPlayService";
        String CHANNEL_NAME = "TEST_V2";
        NotificationChannel notificationChannel = null;
        // you've got some foreground service. shit then.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(this, MyService2.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new Notification.Builder(this, CHANNEL_ID).
                setContentTitle("Lazero").
                setContentText("RecPlay service running.").
                setWhen(System.currentTimeMillis()).
                setSmallIcon(R.drawable.icon).
                setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).
                setContentIntent(pendingIntent).build();
        return notification;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = note();
        startForeground(2749, notification);
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
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "RecPlay");
//        KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        mWakeLock.acquire();
    }

    public void xstop(String userID) {
        stopRecord();
        cancel();
        Log.v("AUDIO SERVICE", userID);
    }

    public void xstart(Intent intent, String userID) {
        Log.v("AUDIO SERVICE", userID);
        int rates = Integer.parseInt(intent.getStringExtra("rates"));
        int channel = Integer.parseInt(intent.getStringExtra("channel"));
//            System.out.println(rates);
//            System.out.println(channel);
        // put the intent with string extras.
        bufferSize_common = init_set(channel, rates);
        Log.e("AUDIO BUFFER SIZE", String.valueOf(bufferSize_common));
        // it is not zero ones. but how comes?
        if (bufferSize_common != 0) {
            init_buffer(bufferSize_common);
            // we can start here.
            status = Status.STATUS_READY;
            startRecord();
            // offer another option here.
        } else {
            status = Status.STATUS_NO_READY;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // fucking shit?
        // first send an empty intent? or special intent.
        String userID = intent.getStringExtra("command");
        // you can get more things.
        if (userID.equals("INIT_SERVICE")) {
            Log.v("AUDIO SERVICE", userID);
        } else if (userID.equals("START")) {
            xstart(intent, userID);
        } else if (userID.equals("STOP")) {
            xstop(userID);
        } else if (userID.equals("PAUSE")) {
            Log.v("AUDIO SERVICE", userID);
            if (!audioRecord.equals(null)) {
                status = Status.STATUS_PAUSE;
            } else {
                Log.e("AUDIO REC", "ALREADY STOPPED");
            }
        } else if (userID.equals("CANCEL")) {
            Log.v("AUDIO SERVICE", userID);
            cancel();
        } else if (userID.equals("LOOP")) {
            openThread = null;
            endloop = true;
            openThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    endloop = false;
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    while (!endloop) {
//                        init_buffer(bufferSize_common);
                        xstart(intent, userID);
                        try {
                            Thread.sleep(4500);
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                        isPlaying = false;
                        isStop = true;
                        try {
                            Thread.sleep(500);
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                        isPlaying = true;
                        isStop = false;
//                        byte[] arr = new byte[fileNames.remaining()];
//                        fileName.get(arr);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                        for (int i = 0; i < filesName.size(); i++) {
                            try {
                                byte[] a = filesName.get(i);
                                Log.e(TAG, "WRITE ARRAY HASH: " + java.util.Arrays.hashCode(a));
                                outputStream.write(a);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        byte[] arr = outputStream.toByteArray();
//                        outputStream.write( b );
//                        byte c[] = outputStream.toByteArray();
                        Log.d(TAG, "BUFFER LENGTH" + String.valueOf(arr.length));
                        xstop(userID);
                        playback(arr);
                        try {
                            Thread.sleep(4500);
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                        filesName = null;
                        filesName = new ArrayList<>();
                    }
                }
            });
            openThread.start();
        } else if (userID.equals("ENDLOOP")) {
            endloop = true;
            openThread = null;
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
            Intent intent = new Intent("ai.lazero.lazero.m6.recreate");
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
    public int init_set(int a, int bufferRate) {
//        recorder = findAudioRecord();
//        int AUDIO_SAMPLE_RATE = 44100;
////        int AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_STEREO;
//        // shit. must use some universal setting.
//        int AUDIO_CHANNEL=AudioFormat.CHANNEL_IN_MONO;
//        int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
//        int bufferSizeInBytes = calcBufferSize(AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING, bufferRate);
        // we will not be able to record direct audio. and the time for playing shall be measured in a separate thread.
        // do a timestamp check.
        if (a == 0) {
            audioRecord = findAudioRecord(BOTH_INPUT, bufferRate);
            if (!audioRecord.equals(null)) {
//            bufferSize_common = bufferSize_common;
                return bufferSize_common;
            } else {
                return 0;
            }
        } else if (a == 1) {
            // without permission.
            audioRecord = findAudioRecord(SYSTEM_INPUT, bufferRate);
            if (!audioRecord.equals(null)) {
//            bufferSize_common = bufferSize_common;
                return bufferSize_common;
            } else {
                return 0;
            }
        } else if (a == 2) {
            audioRecord = findAudioRecord(AUDIO_INPUT, bufferRate);
            if (!audioRecord.equals(null)) {
//            bufferSize_common = bufferSize_common;
                return bufferSize_common;
            } else {
                return 0;
            }
        } else {
            Log.e("NO AUDIO", "Audio path Not yet implemented");
            bufferSize_common = 0;
            return 0;
        }
//        return 0;
    }

    public void startRecord() {
        if (status == Status.STATUS_NO_READY) {
            Log.e("NO AUDIO", "NOT READY");
            return;
        }
        if (status == Status.STATUS_START) {
//            throw new IllegalStateException("正在录音");
            Log.e("AUDIO", "RECORDING");
            return;
        }
        // can be STOP, READY, PAUSE.
        // set to ready please?
        int checkState = audioRecord.getState();
        Log.d("AudioRecorder", "startRecord===" + String.valueOf(checkState));
        if (checkState == AudioRecord.STATE_INITIALIZED) {
            audioRecord.startRecording();
//        audioRecord.
// really?
            new Thread(new Runnable() {
                @Override
                public void run() {
//                writeDataTOFile(listener);
                    if (bufferSize_common != 0) {
                        writeDataTOFile(bufferSize_common);
                        Log.d("AUDIO DAEMON BUFFERSIZE", String.valueOf(bufferSize_common));
                    } else {
                        Log.d("AUDIO DAEMON", "START FAILED");
                    }
                    // how to get it?
                    // cannot do nothing.
                }
            }).start();
        } else {
            Log.e("AUDIO CHANNEL", "NOT INITIALIZED -> CHECK FOR PERMISSIONS LIKE SYSTEM APPS");
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        Log.d("AudioRecorder", "===stopRecord===");
        if (status == Status.STATUS_NO_READY || status == Status.STATUS_READY) {
            Log.e("ERROR", "录音尚未开始");
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
    // {TAG: FINAL_DRILL}
    // find the system mic and play it out.
    public void release() {
        Log.d("AudioRecorder", "===release===");
        //假如有暂停录音
        // shall use byte[] array.
        try {
            if (filesName.size() > 0) {
//                List<byte[]> filePaths = new ArrayList<>();
//                ByteArrayOutputStream my_stream = new ByteArrayOutputStream();
//                my_stream.write(my_first_byte_array);
//                my_stream.write(my_another_byte_array);
//                for (byte[] fileNamex : filesName) {
//                    try {
//                        my_stream.write(fileNamex);
//                        Log.d(TAG, "ARRAY LENGTH: " + String.valueOf(fileNamex.length));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    Log.e("AUDIO FILE CAPTURED", String.valueOf(fileNamex.length));
//                }
                //清除
//                concatenated_byte_array = my_stream.toByteArray();     // Byte arrays are concatenated now
//                try {
//                    playback(concatenated_byte_array);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
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
    public void init_buffer(int bufferSize_common) {
        fileName = null;
        Log.d(TAG, "DIFFERENCE: " + String.valueOf(bufferSize_common) + " 640");
        byte[] allByteArray = new byte[bufferSize_common * 3000];
        fileName = ByteBuffer.wrap(allByteArray);
    }

    /**
     * 将音频信息写入文件
     *
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
//            filesName.add(currentFileName);
        } catch (Exception e) {
            Log.e("AudioRecorder", e.getMessage());
//            throw new IllegalStateException(e.getMessage());
        } /*catch (Exception e) {
            Log.e("AudioRecorder", e.getMessage());
        }*/
        // send you some bitches.
        //将录音状态设置成正在录音状态
        status = Status.STATUS_START;
        // a virtual reset function?
        while (status == Status.STATUS_START) {
            Log.e("AUDIO WORKER", "RECORD LOOP");
            readsize = audioRecord.read(audiodata, 0, bufferSizeInBytes);
            // from .. to ..
            if (AudioRecord.ERROR_INVALID_OPERATION != readsize && fileName != null) {
                try {
//                    fos.write(audiodata);
                    Log.e("AUDIO WORKER", "RECORD LENGTH" + audiodata.length);
//                    fileName.put(audiodata);
                    Log.e(TAG, "RECORD ARRAY HASH: " + java.util.Arrays.hashCode(audiodata));
                    filesName.add(audiodata.clone());
                    // this is the idea. fucking shit.
                    // this is not right. all data are the same. do the copy first.
//                    filesName.
                    // is it here?
                } catch (Exception e) {
                    Log.e("AudioRecorder", e.toString());
                }
            }
        }
        // now everything is done.
        try {
//            if (fos != null) {
            Log.d("AUDIO RECORD", "DONE");
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
