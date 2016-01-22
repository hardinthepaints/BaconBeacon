package com.xanderfehsenfeld.baconbeacon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SystemClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.FileDescriptor;
import java.util.Timer;
import java.util.TimerTask;

/* A service to periodically update the location and send to a server */

//public class MyService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
public class MyService extends Service {


    /* the message which will be sent to the server */
    String message;

    private PendingIntent pendingIntent;
    private AlarmManager manager;

    /* Binder object for this service */
    private final IBinder mBinder = new LocalBinder();


    public MyService() {}


    /* get the most current location */
//    public void updateLocation(){
//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//
//    }
    @Override
    public void onDestroy(){
        manager.cancel(pendingIntent);
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId) {
        final Context c = getApplicationContext();
        Toast toast = Toast.makeText(c, "service started!", Toast.LENGTH_SHORT);
        toast.show();


        Bundle extras = intent.getExtras();
        message = (String) extras.get("KEY1");

        /* start alarm to periodically update location & contact server */
        final Intent alarmIntent = new Intent(c, SendPing.class);
        pendingIntent = PendingIntent.getBroadcast(c, 0, alarmIntent, 0);
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        System.out.println("ALARM STARTED");
        int interval = 1000;

        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), interval, pendingIntent);
        //AlarmManager.AlarmClockInfo info = manager.getNextAlarmClock();


        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast toast = Toast.makeText(getApplicationContext(), "service bound!", Toast.LENGTH_SHORT);
        toast.show();

        return mBinder;

        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");

    }

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }


}
