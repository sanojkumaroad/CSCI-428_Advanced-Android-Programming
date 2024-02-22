/*************************************************
 * CSCI 428      Assignment 2        Spring 2024 *
 *                                               *
 * App Name: Stoplight                           *
 * Class Name: MainActivity.java                 *
 * Developer: Alyssa Romero (Z1976871)           *
 *            Sanoj Oad                          *
 * Due Date: 2/23/2024                           *
 * Purpose: The MainActivity class sets the      *
 *          view and contains the method that    *
 *          is called when the button is clicked *
 ************************************************/
package edu.niu.android.stoplight;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    Stoplight stoplight;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        stoplight = new Stoplight();
        setContentView(R.layout.activity_main);
    }

    /*******************************************************
     * the start method makes calls to the Stoplight class *
     * methods for each light to determine which light is  *
     * on, and then turns that one off and the next one on *
     *******************************************************/
    public void start(View view)
    {
        View redLightView = findViewById(R.id.redLight);
        View yellowLightView = findViewById(R.id.yellowLight);
        View greenLightView = findViewById(R.id.greenLight);

        /*the first if-statement checks if the red light is on, if it is, it turns it off
          and turns the green one on. Then the yellow one is checked, then the green one.*/

        if(stoplight.redLightOn(redLightView))
        {
            redLightView.setBackgroundColor(getResources().getColor(R.color.darkRed));
            greenLightView.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else if(stoplight.yellowLightOn(yellowLightView))
        {
            yellowLightView.setBackgroundColor(getResources().getColor(R.color.darkYellow));
            redLightView.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else if(stoplight.greenLightOn(greenLightView))
        {
            greenLightView.setBackgroundColor(getResources().getColor(R.color.darkGreen));
            yellowLightView.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
    }
}
