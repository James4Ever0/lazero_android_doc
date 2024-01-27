package ai.lazero.lazero.m6;

//import android.app.Service;
import android.content.Context;
//import android.content.Intent;
//import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
//import android.view.WindowInsets;
import android.view.WindowManager;
//import android.widget.TextView;


public class basic_overlay_v2 extends View {
    public Context mContext;
    //private GestureDetector mGesture;
    public String tag;
    public basic_overlay v;
    public WindowManager.LayoutParams x;
public WindowManager w;
    public basic_overlay_v2(Context context,String tag,WindowManager.LayoutParams x,basic_overlay v,WindowManager w) {
//        this
//        this.x = x;
        this(context, null,tag,x,v,w);
    }

    public basic_overlay_v2(Context context, AttributeSet attrs, String tag, WindowManager.LayoutParams x,basic_overlay v,WindowManager w) {
        super(context, attrs);
//        Log.e(TAG, "MyView");
        mContext = context;
        this.tag=tag;
        this.x = x;
        this.v = v;
        this.w=w;
//        手势初始化
        // mGesture = new GestureDetector(mContext, mGestureListener);
    }
public void handler(MotionEvent e,basic_overlay v,WindowManager w){
    float touchX = e.getX();
    float touchY = e.getY();
            x.x = (int) touchX;
            x.y= (int) touchY;
//            break;
//    }
//    v.setLayoutParams(x);
//    v.setLeft(x.x);
//    v.setTop(x.y);
//    w.removeViewImmediate(v);
//    w.addView(v,x);
    w.updateViewLayout(v,x);
    Log.e("[Demo] "+this.tag, "RelativeLayout --> onTouchEvent "+String.valueOf(touchX)+" "+String.valueOf(touchY));
//    w.updateViewLayout(v,x);
}
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Log.e("[Demo] "+this.tag, "RelativeLayout --> onTouchEvent"+e.getAction());
handler(e,v,w);
//        return false;
//        return true;
//        x.x =;
        return !super.onTouchEvent(e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("[Demo] "+this.tag, "RelativeLayout --> dispatchTouchEvent"+ev.getAction());
//        return false;
        return !super.dispatchTouchEvent(ev);
    }

    // sure you can add this as a service.
    // but create a button first. must do this.
    // toggle transparency. -> few hope for accessibility service, since that is not about locking the screen. anyway you can check it in the LG ROM.
}
