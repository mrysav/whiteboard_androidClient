package com.umdcs4995.whiteboard.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.umdcs4995.whiteboard.Globals;
import com.umdcs4995.whiteboard.R;
import com.umdcs4995.whiteboard.drawing.DrawingEventQueue;
import com.umdcs4995.whiteboard.whiteboarddata.GoogleUser;

/**
 * Activity handling the opening splash screen.
 */
public class SplashActivity extends AppCompatActivity {
    /**
     * Value for the time to show the splash screen (in milliseconds).
     */
    private final int DELAYTIME = 1000;

    /**
     * Executes when an instance of the activity is created. used for initialization.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tv = (TextView) findViewById(R.id.txtWelcome);


        //Initiatie the singleton.
        Globals.init(this.getApplicationContext());
        Globals g = Globals.getInstance();

        //Create the DrawingEventQueue
        g.setDrawEventQueue(new DrawingEventQueue());

        //Start the socket service.
        g.startSocketService();

        //Create a GooglecUser to represent the current client
        GoogleUser gu = new GoogleUser();
        g.setClientUser(gu);

        //Edit Welcome text to reflect the name of the client.
        if (gu.isLoggedIn()) {
            tv.setText("Welcome, " + gu.getFirstname());
        } else {
            tv.setText("Welcome!");
        }



        /**
         * Post delayed event is fired after DELAYTIME has expired.  This method is called as soon
         * as the splash screen is rendered.  launchActivity() returns an object of type Runnable,
         * whose run() method is overridden (see private methods).
         */
        new Handler().postDelayed(launchActivity(), DELAYTIME);
    }


    /**
     * Method returns an intent to begin the intro activity.  A private method is required to
     * get a "this" reference to the current activity, since the intent is begun in a runnable type
     * object.
     * @return Intent to execute the IntroActivity.
     */
    private Intent makeMainIntent() {
        return new Intent(this, MainActivity.class);
    }


    /**
     * This method is exectued on creation of the splash screen after DELAYTIME ms has passed.
     * @return a runnable to execute.
     */
    private Runnable launchActivity() {
        return new Runnable() {
            @Override
            public void run() {
                //Creates an intent to the next activity.
                Intent in = makeMainIntent();
                //Fire that intent.
                startActivity(in);
            }
        };
    }

}
