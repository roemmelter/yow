package yow.yourownway;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new MyAppWebViewClient());
        mWebView.loadUrl("https://www.google.com");

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
/*

        startBackgroundUpdater();

    }

    public void startBackgroundUpdater(){
        Intent backgroundUpdater = new Intent(this, BackgroundUpdater.class);
        this.startService(backgroundUpdater);
    }


<<<<<<< HEAD
=======
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            cur = cr.query(uri, projection, null, null, null);

            while (cur.moveToNext()) {
                CalendarProvider cp = new CalendarProvider(cur.getLong(PROJECT_ID), cur.getString(DISPLAY_NAME), cur.getString(ACCOUNT_NAME), cur.getString(OWNER));
                calenderProvider.add(cp);
            }

            return calenderProvider;

        } else {
            throw (new RuntimeException("No permissions"));
        }
*/
/*
        public void onBackPressed() {
            if(mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                super.onBackPressed();
            }
        }
*/

    }
>>>>>>> 9a4f26a8c759e792349b7260dc26f4b89ca6d43a
}
