package com.example.lab2;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

/**
 * Created by bernardot on 2/1/16.
 */
public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView wb = new WebView(this);
        // the root directory is assets
        wb.loadUrl("file:///android_asset/html/About.html");
        setContentView(wb);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                // assuming that the parent activity is
                // on the back stack, it's enough to just
                // finish the current activity to return to it
                // the alternate way is commented out
                // this.startActivity(upIntent);
                // NavUtils.navigateUpTo(this, upIntent);
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

