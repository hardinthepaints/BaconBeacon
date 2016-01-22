package com.xanderfehsenfeld.baconbeacon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean bad = false;
    private String message;
    private Intent curr_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        message = getResources().getString(R.string.good_message);

        /* start the service */
        start();


    }

    public void toggleMessage(View view){
        if (bad){
            message = getResources().getString( R.string.good_message);
            bad = false;
        } else {
            message = getResources().getString( R.string.bad_message);
            bad = true;

        }
        TextView textView = (TextView) findViewById( R.id.message );
        textView.setText(message);

        /* stop the previous service */
        stopService();

        /* start the new service */
        start();
    }

    private void stopService(){
        getApplicationContext().stopService(curr_intent);
        Toast toast = Toast.makeText(getApplicationContext(), "service stopped!", Toast.LENGTH_SHORT);
        toast.show();
    }
    private void start(){
        // use this to start and trigger a service
        Context context = getApplicationContext();
        Intent i= new Intent(context, MyService.class);
        // potentially add data to the intent
        i.putExtra("KEY1", message );
        //context.bindService(i, new MyServiceConnection(), Context.BIND_ABOVE_CLIENT);
        context.startService(i);

        curr_intent = i;

        /* notes on services
        *       you cant call a method on a service directly, instead you must bind to it
        *       Binding
        *           in order to bind an activity with a service, implement ServiceConnection
         */
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
