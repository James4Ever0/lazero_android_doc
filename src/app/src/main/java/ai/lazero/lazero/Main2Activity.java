package ai.lazero.lazero;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
//import android.media.projection.MediaProjection;

public class Main2Activity extends AppCompatActivity {
// we are under UTF!
//    private static final int REQUEST_MEDIA_PROJECTION = 201;
//private LogService logService;
//    public String str = "";
    // I mean it. if you want to build something on top of this, you've got to write some services.
    // what is the problem?
    // maybe we should not do this?
    // only one shot done here?
    public StringBuilder str = new StringBuilder();
//    public static Main2Activity activity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        try{
        // that is a system package.
        // check it out.
        // sound, image and more?
        // what about settings? a great pivot point.
        // the context is this.
        // this is a toy project.
        // pretty much like it.
        // check how to record screen without root permission.
        // hacked version of Android, Windows or Linux?
//        Process p = Runtime.getRuntime().exec("su");
////然后，在向这个进程的写入要执行的命令，即可达到以root权限执行命令：
//        DataOutputStream dos = new DataOutputStream(p.getOutputStream());
//        String cmd="chmod +x /dev/graphics";
//        dos.writeBytes(cmd + "\n");//cmd命令可为空
//            str.append("|");
//            str.append(dos.toString());
//            str.append("|");
//        dos.flush();
//            str.append(dos.toString());
//            str.append("|");} catch (Exception e){str.append(e.toString());e.printStackTrace();}
        // shit.
        // what the heck?
        // get the name changed.
//        int cls=Reflection.unseal(Main2Activity.this);
//        str.append("result: ");
//        str.append(String.valueOf(cls));
//        str.append("|");
//        // what is this one?
////        Reflection.
        // must change the permission on that file.
        // or do some redirection?
        // so do it here??
        // it is alright.
        str.append("sample_text_");
// is this how debug works?
        ImageView imageview = (ImageView) findViewById(R.id.sample);
        try{
//            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
//        Activity activity= Main2Activity.this;
            str.append("0_");
//            activity=this;
            str.append("1_");
//            Display display = activity.getWindowManager().getDefaultDisplay();

            str.append("2_");
            str.append("3_");
            // but we need the whole screen.
            // really.
            int[] dims = { getResources().getDisplayMetrics().widthPixels,getResources().getDisplayMetrics().heightPixels};
            str.append("4_");
            ScreenShotFb sfb= new ScreenShotFb();
            str.append("5_");
//            sfb.init((int) dims[0],(int) dims[1], PixelFormat.RGBA_8888);
            str.append("6_");
            Bitmap btm=sfb.getScreenShotBitmap();
            str.append("7_");
            if (btm==null){str.append("null_picture_");}
            // why we do not have picture at all?
            // holy shit.
            imageview.setImageBitmap(btm);
            // does not have anything in return?
            // what is this one?
            // these two sucks.
            try{
                str.append(String.valueOf(dims[0]));
                str.append(" ");
                str.append(String.valueOf(dims[1]));
                str.append(" ");
                str.append(String.valueOf(PixelFormat.RGBA_8888));
                str.append(" [SHOT_END]");} catch (Exception e0){e0.printStackTrace();str.append("___[ERROR_SPLITER]___");str.append(e0.toString());str.append("___[ERROR_SPLITER]___");}
        } catch (Exception e){
            str.append(e.toString());
            e.printStackTrace();
        }
//        tv.setText(str.toString());
        try{TextView tv = (TextView) findViewById(R.id.txtOne);
        tv.setText(str.toString());}catch (Exception e){e.printStackTrace();}
//        i guess this is the problem.
//        (dims[0],dims[1],display.getPixelFormat());
//        sfb.init();
//        ScreenShotFb.class;
//        setContentView(R.layout.activity_main2);
//        Class<?> demo = null;
//        try {
//            demo = Class.forName("android.view.SurfaceControl");
////            great.
//            TextView tv = (TextView) findViewById(R.id.txtOne);
//            str.append("nothing here|");
//        } catch (Exception e) {
// expect too much?
//        }
//        try{
//            Method method=demo.getMethod("screenshot",new Class[]{Rect.class,int.class,int.class,int.class});
////            // not even this step.
//            // base context already set?
//////            demo.get
//////            Bitmap bitmap = (Bitmap) demo.screenshot((int) dims[0],(int) dims[1]);
//////            this is the step.
////            // so what is that thing?
//            Rect rect=new Rect(0,0,(int) dims[0],(int) dims[1]);
////            str.append("hello world|");
//            // write something over there.
//            Bitmap bitmap= (Bitmap) method.invoke(demo.newInstance(),rect,(int) dims[0],(int) dims[1],0);
////            str.append("hello world|");
////            Bitmap bitmap=demo.getMethod("screenshot")((int) dims[0],(int) dims[1]);
//            //这里其实可以直接用null替换demo.newInstance()，因为screenshot是静态方法，
////            TextView tv = (TextView) findViewById(R.id.txtOne);
////                        str.append(e.toString());
//
////                    Method[] md =demo.getMethods();
////
////                    for(int i=0;i<md.length;i++){
////                        String val=md[i].getName();
////                        if(val.equals("screenshot")){
////                            Method method=md[i];
////                    	str.append(val);
////                        str.append(" ");
////                        int count =method.getParameterCount();
////                        str.append(String.valueOf(count));
////                        Class<?>[] td=method.getParameterTypes();
////                        for(int j=0;j<td.length;j++){str.append(" ");str.append(td[j].getTypeName());
////                            str.append(" ");
////                        }
////                    	str.append("|");}
////                    }
////            tv.setText(str.toString());
//
//            	}
////        Class sc = Class.forName("android.view.Surface");
////        Method method =sc.getMethod("screenshot", new Class[] {int.class, int.class});
////        Object o = method.invoke(sc, new Object[]{(int) dims[0],(int) dims[1]});
////        Bitmap bitmap =(Bitmap)o;
//
//        catch (Exception e) {
////
//            TextView tv = (TextView) findViewById(R.id.txtOne);
//            str.append(e.toString());
//            tv.setText(str.toString());
////            logService.buildLogLine(e.toString());
//        }
//


//        Canvas canvas = new Canvas(bitmap);
//        canvas.drawColor(Color.TRANSPARENT);

//        MediaProjectionManager mMediaProjectionManager = (MediaProjectionManager)getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
//        startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
        //this is not background running.
    }
}
