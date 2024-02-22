 /************************************************
 * Class Name: Stoplight.java                    *
 * Purpose: This class has boolean methods to    *
 *          determine which light is on          *
 ************************************************/
package edu.niu.android.stoplight;

import android.graphics.drawable.ColorDrawable;
import android.view.View;

public class Stoplight {

    public Stoplight()
    {
    }

    /*********************************************************************
     * redLightOn, and the two methods for the other two lights          *
     * are alike, with the exception being the color that the background *
     * color of the view is being compared to (red,green,yellow)         *
     ********************************************************************/
    public boolean redLightOn(View textView) //returns true if red light is on
    {
        //the color of the view is found and placed in an int variable
        ColorDrawable viewColor = (ColorDrawable) textView.getBackground();
        int color = viewColor.getColor();

        //this if-statement compares the color of the view and the color in colors.xml
        if(color == textView.getResources().getColor(R.color.red))
        {
            return true;
        }

        return false;
    }

    public boolean yellowLightOn(View textView) //returns true if yellow light is on
    {
        ColorDrawable viewColor = (ColorDrawable) textView.getBackground();
        int color = viewColor.getColor();

        if(color == textView.getResources().getColor(R.color.yellow))
        {
            return true;
        }

        return false;
    }

    public boolean greenLightOn(View textView) //returns true if green light is on
    {
        ColorDrawable viewColor = (ColorDrawable) textView.getBackground();
        int color = viewColor.getColor();

        if(color == textView.getResources().getColor(R.color.green))
        {
            return true;
        }

        return false;
    }
}
