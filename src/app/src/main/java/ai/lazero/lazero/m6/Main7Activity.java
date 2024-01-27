package ai.lazero.lazero.m6;

import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.Surface;
import android.view.View;
import android.widget.TextView;

import ai.lazero.lazero.R;

public class Main7Activity extends Activity {
    private MyPresentation mPresentation = null;
    public String TAG = "BLITZ";
    /*
     * This code is not production ready. Do not use
     * for anything other than test projects.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        populate(findViewById(R.id.main), getWindowManager()
                .getDefaultDisplay());
//        Surface sf = (Surface) findViewById(R.id.main).findViewById(R.id.surface);
        multiInit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main7, menu);
        return true;
    }

    private static void populate(View v, Display display) {
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float density = metrics.density;
        TextView actual =
                (TextView) v.findViewById(R.id.actual);
        if (actual != null) {
            actual.setText(String.format("%dx%d",
                    metrics.widthPixels,
                    metrics.heightPixels));
        }
        TextView df =
                (TextView) v.findViewById(R.id.density_factor);
        if (df != null) {
            df.setText(String.format("%f", density));
        }
        TextView dp =
                (TextView) v.findViewById(R.id.device_pixels);
        if (dp != null) {
            dp.setText(String.format("%dx%d",
                    ((int) ((float) metrics.widthPixels / density)),
                    ((int) ((float) metrics.heightPixels / density))));
        }
    }

    private void multiInit() {
        VirtualDisplay vd = createVd();
        try {
            if (vd != null) {
                Display display = vd.getDisplay();
                mPresentation = new MyPresentation(this, display);
                mPresentation.show();
                Log.e(TAG, "blitz service launched");
            } else {
                Log.e(TAG, "blitz service failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "blitz service error");
        }
    }
//        DisplayManager dm =
//                (DisplayManager) getSystemService(DISPLAY_SERVICE);
////        DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;
////        DisplayManager.DIS
//        if (dm != null) {
//            Display[] displays = dm.getDisplays(DisplayManager.)
////                    dm.getDisplays(
////                            DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
//            for (Display display : displays) {
//                mPresentation = new MyPresentation(this, display);
//                mPresentation.show();
//            }

    //there is no such thing.
    public VirtualDisplay createVd() {
        DisplayManager dm =
                (DisplayManager) getSystemService(DISPLAY_SERVICE);
        if (dm != null) {
            VirtualDisplay vd = dm.createVirtualDisplay("virtual_display", 500, 500, 200, null, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC);
            if (vd != null) {
                return vd;
            }
        }
        return null;
    }
//    Requires CAPTURE_VIDEO_OUTPUT or CAPTURE_SECURE_VIDEO_OUTPUT permission, or an appropriate MediaProjection token in order to create a screen sharing virtual display.
    public class MyPresentation extends Presentation {

        public MyPresentation(Context outerContext,
                              Display display) {
            super(outerContext, display);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main7);
            populate(findViewById(R.id.main),
                    getDisplay());
        }
    }
}