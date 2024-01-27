package ai.lazero.lazero.m6;

//import android.app.Service;
import android.content.Context;
//import android.content.Intent;
//import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
//import android.widget.TextView;


public class basic_overlay extends View {
public Context mContext;
    //private GestureDetector mGesture;
    public String tag;

    public basic_overlay(Context context,String tag) {
//        this
        this(context, null,tag);
    }

    public basic_overlay(Context context, AttributeSet attrs,String tag) {
        super(context, attrs);
//        Log.e(TAG, "MyView");
        mContext = context;
        this.tag=tag;
        //手势初始化
        // mGesture = new GestureDetector(mContext, mGestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("[Demo] "+this.tag, "RelativeLayout --> onTouchEvent"+event.getAction());
//        return false;
//        return true;
        return !super.onTouchEvent(event);
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
