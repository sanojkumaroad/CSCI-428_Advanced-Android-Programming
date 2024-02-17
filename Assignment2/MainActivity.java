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

    /*this method is supposed to check which light is on when the button is clicked
      and turns that light off, then turns on the next one */
    public void start()
    {
        View redLightView = findViewById(R.id.redLight);
        View yellowLightView = findViewById(R.id.yellowLight);
        View greenLightView = findViewById(R.id.greenLight);

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
