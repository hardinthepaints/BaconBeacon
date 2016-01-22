package com.xanderfehsenfeld.baconbeacon;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.FileDescriptor;

/* A service to periodically update the location and send to a server */

//public class MyService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
public class MyService extends Service {

    /* location */
    //private GoogleApiClient mGoogleApiClient;
    //private Location mLastLocation;
    private Locator locator;

    String message;



    public MyService() {}


    /* get the most current location */
//    public void updateLocation(){
//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//
//    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId) {
        Context c = getApplicationContext();
        Toast toast = Toast.makeText(c, "service started!", Toast.LENGTH_SHORT);
        toast.show();
//        buildGoogleApiClient();
//        mGoogleApiClient.connect();
        locator = new Locator(this);
        locator.mGoogleApiClient.connect();

        Bundle extras = intent.getExtras();
        message = (String) extras.get("KEY1");

        //locator.updateLocation();
        getCoordinates();
        //String message = String.valueOf(R.string.message);

        /* start alarm to periodically update location & contact server */

        return Service.START_NOT_STICKY;
    }

    private void getCoordinates(){
        Location curr_location = locator.updateLocation();
        if ( curr_location != null ) {
            System.out.println( "latitude: " + curr_location.getLatitude());
            System.out.println( "longitude: " + curr_location.getLongitude());
        }

        //Context c = getApplicationContext();
        //TextView latitude =

    }


    @Override
    public IBinder onBind(Intent intent) {
        Toast toast = Toast.makeText(getApplicationContext(), "service bound!", Toast.LENGTH_SHORT);
        toast.show();
        return new IBinder() {

            @Override
            public String getInterfaceDescriptor() throws RemoteException {
                return null;
            }



            @Override
            public boolean pingBinder() {
                return false;
            }

            @Override
            public boolean isBinderAlive() {
                return false;
            }

            @Override
            public IInterface queryLocalInterface(String descriptor) {
                return null;
            }

            @Override
            public void dump(FileDescriptor fd, String[] args) throws RemoteException {

            }

            @Override
            public void dumpAsync(FileDescriptor fd, String[] args) throws RemoteException {

            }

            @Override
            public boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                return false;
            }

            @Override
            public void linkToDeath(DeathRecipient recipient, int flags) throws RemoteException {

            }

            @Override
            public boolean unlinkToDeath(DeathRecipient recipient, int flags) {
                return false;
            }
        };
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");

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
