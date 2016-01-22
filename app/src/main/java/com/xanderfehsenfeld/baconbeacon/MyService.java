package com.xanderfehsenfeld.baconbeacon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
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

    /* location */
    //private GoogleApiClient mGoogleApiClient;
    //private Location mLastLocation;

    String message;

    private PendingIntent pendingIntent;
    private AlarmManager manager;
    //private Timer timer;



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
//        buildGoogleApiClient();
//        mGoogleApiClient.connect();
//        locator = new Locator(this);
//        locator.mGoogleApiClient.connect();

        Bundle extras = intent.getExtras();
        message = (String) extras.get("KEY1");

        //locator.updateLocation();
        //getCoordinates();
        //String message = String.valueOf(R.string.message);

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

        /* use a timer for a repeated task */
//        timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                c.startService( alarmIntent );
//            }
//        },
//        0, 1000
//        );



        return Service.START_NOT_STICKY;
    }

//    private void getCoordinates(){
//        //Location curr_location = locator.updateLocation();
//        if ( curr_location != null ) {
//            System.out.println( "latitude: " + curr_location.getLatitude());
//            System.out.println( "longitude: " + curr_location.getLongitude());
//        }
//
//        //Context c = getApplicationContext();
//        //TextView latitude =
//
//    }


    @Override
    public IBinder onBind(Intent intent) {
        Toast toast = Toast.makeText(getApplicationContext(), "service bound!", Toast.LENGTH_SHORT);
        toast.show();

        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

//    /* build a googleApiClient */
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder( this )
//                .addConnectionCallbacks( this )
//                .addOnConnectionFailedListener( this )
//                .addApi( LocationServices.API )
//                .build();
//    }
//
//
//    @Override
//    public void onConnected(Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//
//    }
}
