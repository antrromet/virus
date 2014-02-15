package com.google.android.virus;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: antriksh
 * Date: 31/01/14
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class VirusService extends Service {

    private static boolean viewAdded;
    private long upTime;
    private long downTime;
    private long ellapseTime;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        addToWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void addToWindow() {
        if (!viewAdded) {
            final WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams paramsNotification = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,// | WindowManager.LayoutParams.TYPE_PRIORITY_PHONE ,// this will intersect with the status bar but also needed to handle the click on the notification view!!!
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.OPAQUE);
            paramsNotification.gravity = Gravity.LEFT | Gravity.TOP;
            paramsNotification.windowAnimations = android.R.style.Animation_Dialog;
            paramsNotification.setTitle("Fooled!");

            LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflator.inflate(R.layout.main, null, false);
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        downTime = System.currentTimeMillis();
                        return true;
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        upTime = System.currentTimeMillis();
                        ellapseTime = upTime - downTime;
                        if (ellapseTime > 4000) {
                            Log.d("XXX", "Removed successfully");
                            Toast.makeText(VirusService.this, getString(R.string.veil_lifted), Toast.LENGTH_LONG).show();
                            wm.removeView(v);
                            viewAdded = false;
                            stopSelf();
                        }
                    }
                    return false;  //To change body of implemented methods use File | Settings | File Templates.
                }
            });
            wm.addView(view, paramsNotification);
            Log.d("XXX", "Added successfully");
            viewAdded = true;
        }
    }
}
