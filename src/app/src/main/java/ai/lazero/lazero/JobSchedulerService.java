package ai.lazero.lazero;

//import android.app.job.JobParameters;
//import android.app.job.JobService;
//import android.os.Handler;
//import android.os.Message;
//import android.widget.Toast;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by paulruiz on 3/7/15.
 */
public class JobSchedulerService extends JobIntentService {
    public static String loginByGet(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            //获得结果码
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                //请求成功 获得返回的流
                InputStream inputStream = connection.getInputStream();
                StringBuilder sb = new StringBuilder();
                String line;

                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
//                String str = sb.toString();
                return sb.toString();
            } else {
                //请求失败
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static final int JOB_ID = 10111;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, JobSchedulerService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(Intent intent) {
        while (true) {
            try {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
                long tm= System.currentTimeMillis() / 1000;
                String ts = String.valueOf(tm);
                String rt = loginByGet("http://localhost:7777/"+ts);
                if (rt!=null){Log.d("house_return",rt);}else{Log.d("house_return","server not present.");}
                Log.d("houson", "onHandleWork: " + intent.getStringExtra("work"));
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
//    private Handler mJobHandler = new Handler( new Handler.Callback() {
//        @Override
//        public boolean handleMessage( Message msg ) {
//            Toast.makeText( getApplicationContext(), "JobService task running", Toast.LENGTH_SHORT ).show();
//            jobFinished( (JobParameters) msg.obj, false );
//            return true;
//        }
//    } );
//
//    @Override
//    public boolean onStartJob(JobParameters params ) {
//        // what job is starting?
//        mJobHandler.sendMessage( Message.obtain( mJobHandler, 1, params ) );
//        return true;
//    }
//
//    @Override
//    public boolean onStopJob( JobParameters params ) {
//        mJobHandler.removeMessages( 1 );
//        return false;
//    }

}