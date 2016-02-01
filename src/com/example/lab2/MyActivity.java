package com.example.lab2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends Activity {
    // declare size of nxn puzzle
    static int n = 3;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new LightsView(this, new LightsModel(n)));
        setContentView(R.layout.main);
    }

    public void resetModel(View view) {
        LightsView lightsView = (LightsView) findViewById(R.id.lightsView);
        lightsView.model.reset();
        lightsView.invalidate();
    }
}
