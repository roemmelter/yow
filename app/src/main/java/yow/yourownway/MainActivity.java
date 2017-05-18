package yow.yourownway;

import android.Manifest;
import android.content.ContentResolver;
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

//    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        mWebView.loadUrl("https://www.google.de/");
*/

        List<CalendarProvider> availableCalenders = getAvailableCalenders();

        Calendar start = Calendar.getInstance();
        start.set(2017,05,10);

        Calendar end= Calendar.getInstance();
        end.set(2017,05,30);

        List<EventProvider> availableEvents = findEventsInPeriod(start, end);

        // Debuging
        /*
            Output:
            Fronleichnam
            15.6.
            16.6.
            -> ???
         */
        while (availableEvents.iterator().hasNext() ) {
            EventProvider event = availableEvents.iterator().next();
            Log.d("event", event.title);
            Log.d("description", event.description);
            Log.d("startDate", event.start.toString());
            Log.d("endDate", event.end.toString());
        }
    }

    public List<EventProvider> findEventsInPeriod(Calendar begin, Calendar end) {

        List<EventProvider> eventProvider = new ArrayList<EventProvider>();

        String[] eventCompound = new String[] {
                CalendarContract.Events.CALENDAR_ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
                CalendarContract.Events.ALL_DAY,
                CalendarContract.Events.EVENT_LOCATION
        };

        final int CALENDAR_ID = 0;
        final int TITLE = 1;
        final int DESCRIPTION = 2;
        final int DTSTART = 3;
        final int DTEND = 4;
        final int ALL_DAY = 5;
        final int EVENT_LOCATION = 6;

        String selection = "(( " + CalendarContract.Events.DTSTART
                + " >= " + begin.getTimeInMillis()
                + " ) AND ( " + CalendarContract.Events.DTSTART
                + " <= " + end.getTimeInMillis() + " ))";

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Cursor cur = this.getBaseContext().getContentResolver().query(CalendarContract.Events.CONTENT_URI, eventCompound, selection, null, null);

            while(cur.moveToNext()){
                eventProvider.add(new EventProvider(cur.getString(TITLE), cur.getString(DESCRIPTION), new Date(cur.getLong(DTSTART)), new Date(cur.getLong(DTEND))));
            }
        }
        return eventProvider;
    }

    public List<CalendarProvider> getAvailableCalenders(){

        List<CalendarProvider> calenderProvider = new ArrayList<CalendarProvider>();
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = Calendars.CONTENT_URI;

        String[] projection = new String[] {
                Calendars._ID,                           // 0
                Calendars.ACCOUNT_NAME,                  // 1
                Calendars.CALENDAR_DISPLAY_NAME,         // 2
                Calendars.OWNER_ACCOUNT                  // 3
        };

        final int PROJECT_ID = 0;
        final int ACCOUNT_NAME = 1;
        final int DISPLAY_NAME = 2;
        final int OWNER = 3;



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

    }
}
