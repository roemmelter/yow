package yow.yourownway;


import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by felix on 18.05.17.
 */

public class BackgroundUpdater extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fetchNewEvents();
        stopSelf();
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void fetchNewEvents(){
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MONTH, 1);

        List<EventProvider> availableEvents = findEventsInPeriod(start, end);

        if(availableEvents.size() != 0){
            sendNotification("Neue Reise gefunden", "Möchtest du direkt eine Zugverbindung buchen?", 123, MainActivity.class);
        }

    }

    @Override
    public void onDestroy() {
      setRestartIn(10);
    }

    public void sendNotification(String title, String text, int uID, Class classToOpen){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(title)
                        .setContentText(text);

        //callback point on notification click
        Intent resultIntent = new Intent(this, classToOpen);


        android.support.v4.app.TaskStackBuilder stackBuilder = android.support.v4.app.TaskStackBuilder.create(this);
        stackBuilder.addParentStack(classToOpen);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(uID, mBuilder.build());
    }
    public List<EventProvider> findEventsInPeriod(Calendar begin, Calendar end) {

        List<EventProvider> eventProvider = new ArrayList<EventProvider>();

        String[] projection = new String[] {
                CalendarContract.Events._ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
                CalendarContract.Events.ALL_DAY,
                CalendarContract.Events.EVENT_LOCATION
        };

        final int ID = 0;
        final int TITLE = 1;
        final int DESCRIPTION = 2;
        final int DTSTART = 3;
        final int DTEND = 4;
        final int ALL_DAY = 5;
        final int EVENT_LOCATION = 6;

        String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + begin.getTimeInMillis() + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + end.getTimeInMillis() + " ))";

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Cursor cur = this.getBaseContext().getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection, selection, null, null);

            while(cur.moveToNext()){
                EventProvider ep = new EventProvider(cur.getString(ID),cur.getString(TITLE), cur.getString(DESCRIPTION), new Date(cur.getLong(DTSTART)), new Date(cur.getLong(DTEND)));
                eventProvider.add(ep);
            }

        }

        return eventProvider;

    }
    public List<CalendarProvider> getAvailableCalenders(){

        List<CalendarProvider> calenderProvider = new ArrayList<CalendarProvider>();
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;

        String[] projection = new String[] {
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.OWNER_ACCOUNT
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

        }else{
            throw(new RuntimeException("No permissions"));
        }


    }

    public void setRestartIn(int seconds){
        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarm.set(
                alarm.RTC_WAKEUP,
                System.currentTimeMillis() + (1000 * seconds),
                PendingIntent.getService(this, 0, new Intent(this, BackgroundUpdater.class), 0)
        );
    }



}
