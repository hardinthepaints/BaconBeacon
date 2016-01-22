package com.xanderfehsenfeld.baconbeacon;


import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Tim on 1/19/2016.
 */
public class SendPing extends BroadcastReceiver{

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    boolean running = false;
    Locator locator;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Sending GPS", Toast.LENGTH_SHORT).show();
        System.out.println("SENDING GPS");
        locator = new Locator(context);
        locator.mGoogleApiClient.connect();
        locator.updateLocation();

        System.out.println(locator.mLastLocation);

    }
//        System.out.println("getString : " + intent.getExtras().getString("string1"));


}
