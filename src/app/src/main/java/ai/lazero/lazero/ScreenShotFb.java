/**
 * ScreenShotFb.java
 * 版权所有(C) 2014
 * 创建者:cuiran 2014-4-3 下午4:55:23
 */
package ai.lazero.lazero;


import android.graphics.Bitmap;
import android.graphics.PixelFormat;
// do you like what you see?
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * FrameBuffer中获取Android屏幕截图
 * @author cuiran
 * @version 1.0.0
 */
public class ScreenShotFb {

    private static final String TAG="ScreenShotFb";

//    final static String FB0FILE1 = "/dev/graphics/fb0";
// no such thing.
//    static File fbFile;
    //程序入口
// problem of the colors.
// usually the end is 0.
    public static void init(int a, int b,int c){

        try {

//            DisplayMetrics dm = new DisplayMetrics();
//            Display display = activity.getWindowManager().getDefaultDisplay();
//            display.getMetrics(dm);
            screenWidth = a; // 屏幕宽（像素，如：480px）
            screenHeight = b; // 屏幕高（像素，如：800p）
            pixelformat = c;
            PixelFormat localPixelFormat1 = new PixelFormat();
            PixelFormat.getPixelFormatInfo(pixelformat, localPixelFormat1);
            int depth = localPixelFormat1.bytesPerPixel;// 位深
//            LogUtil.i(TAG, "deepth="+depth);
            // whatever.
            // get the thing?
            piex = new byte[screenHeight * screenWidth*depth] ;// 像素
            colors = new int[screenHeight * screenWidth];



        }catch(Exception e){
//            LogUtil.e(TAG, "Exception error",e);
            e.printStackTrace();
        }
    }
    static DataInputStream dStream=null;
    static byte[] piex=null;
    static int[] colors =null;
    static int screenWidth;
    static int screenHeight;
    static int pixelformat;
    public static int bytesToInt(byte[] b) {
        return   b[0] & 0xFF |
                (b[1] & 0xFF) << 8 |
                (b[2] & 0xFF) << 16 |
                (b[3] & 0xFF) << 24;
    }
    //    public static int bytesToInt(byte[] bs) {
//        int a = 0;
//        for (int i =0 ; i <= bs.length - 1; i++) {
//            a += bs[i] * Math.pow(255, bs.length - i - 1);
//        }
//        return a;
//    }
    public static synchronized Bitmap getScreenShotBitmap() {
        FileInputStream buf = null;
        try {
            Process p=Runtime.getRuntime().exec("su");
            DataOutputStream dos=new DataOutputStream(p.getOutputStream());
            dStream =new DataInputStream(p.getInputStream());
            String cmd="screencap";
            dos.writeBytes(cmd+"\n");
            dos.flush();
//            fbFile = new File(FB0FILE1);
//            buf = new FileInputStream(fbFile);// 读取文件内容
            //does that work?
//            dStream=new DataInputStream(buf);
            // separate thread. setting it on the go.
            int[] config=new int[4];
            // nu such byte?
            for(int k = 0;k<4;k++){byte[] xb=new byte[4]; dStream.read(xb);config[k]=bytesToInt(xb);
//                System.out.println(xb[0]);System.out.println(xb[1]);System.out.println(xb[2]);System.out.println(xb[3]);
//                System.out.println(String.valueOf(k)+":"+String.valueOf(config[k])+"|");
            }
                init(config[0],config[1],config[2]);
            dStream.readFully(piex);
            // it is closed!
            // what the heck is going on?
            dStream.close();
            dos.close();
            p.destroy();
            // 将rgb转为色值
            // permission denied.
            // EOF?
            // so what???
            // get root access.
//            System.out.println("sample_log");System.out.println("sample_log");System.out.println("sample_log");
//            System.out.println("_length_of_plex_"+String.valueOf(piex.length));
//            this is not good.
//            PixelFormat.getPixelFormatInfo(PixelFormat.RGBA_8888);
            // this is not intended.
//            for(int i=0;i<piex.length;i+=2)
//            {if (i%100000==0){System.out.println(String.valueOf(i)+"_");}
//                colors[i/2]= (int)0xff000000 +
//                        (int) (((piex[i+1]) << (16))&0x00f80000)+
//                        (int) (((piex[i+1]) << 13)&0x0000e000)+
//                        (int) (((piex[i]) << 5)&0x00001A00)+
//                        (int) (((piex[i]) << 3)&0x000000f8);
//            }
// this is the fucking awful data structure.
            // how to do it?
            // this config is not right.
            // shall we use EVAL?
            // 得到屏幕bitmap
//            // unimplemented.
            Bitmap stitchBmp = Bitmap.createBitmap(screenWidth,screenHeight,Bitmap.Config.ARGB_8888);
            stitchBmp.copyPixelsFromBuffer(ByteBuffer.wrap(piex));
            return stitchBmp;
//            BitmapFactory.Options op = new BitmapFactory.Options();
//            op.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            // unimplemented.
//            return BitmapFactory.decodeByteArray(piex,0,piex.length,op);
////            return BitmapFactory.decodeStream(dStream);
//            return Bitmap.createBitmap(colors, screenWidth, screenHeight,
//                    Bitmap.Config.RGB_565);
        } catch (FileNotFoundException e) {
//            LogUtil.e(TAG, "FileNotFoundException error",e);
            e.printStackTrace();
        } catch (IOException e) {
//            LogUtil.e(TAG, "IOException error",e);
            e.printStackTrace();
        }catch (Exception e) {
//            LogUtil.e(TAG, "Exception error",e);
            e.printStackTrace();
        }
        finally {
            if(buf!=null){
                try {
                    buf.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
