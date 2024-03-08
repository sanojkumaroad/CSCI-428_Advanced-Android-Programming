package edu.niu.android.voicerecognition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Toast;
import android.widget.TextView;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private static final int RESULT_REQUEST = 1;
    private Random randInt;
    private TextView intOne; //the TextView representing the first integer
    private TextView intTwo; //the TextView representing the second integer

    private int firstInt; //the value of the first integer
    private int secondInt; //the value of the second integer
    private int sum; //the value of the sum of the two integers

    public static Match values;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        values = new Match(); //an object of the Match class

        //checks if device supports speech recognition
        PackageManager manager = getPackageManager();
        List<ResolveInfo> listOfMatches = manager.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (listOfMatches.size() > 0)
        {
            /* if the device supports speech recognition
               then the equation is set with 2 randomly
               generated integers from 0 - 9 */
            intOne = findViewById(R.id.fOperand);
            intTwo = findViewById(R.id.sOperand);

            randInt = new Random();
            firstInt = randInt.nextInt(9);
            secondInt = randInt.nextInt(9);

            sum = firstInt + secondInt;

            intOne.setText(String.valueOf(firstInt));//sets TextView that displays the first operand to the first integer
            intTwo.setText(String.valueOf(secondInt));//sets TextView that displays the second operand to second integer
        }
        else
        { // if speech recognition not supported, a Toast appears
            Log.w("MainActivity", String.valueOf(listOfMatches.size()));
            Toast.makeText(this, "Sorry - Your device does not support speech recognition", Toast.LENGTH_LONG).show();
        }
    }

    public void listen(View view)
    {
        Intent listenIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        listenIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What is the result?"); //adds a prompt to the microphone activity
        listenIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        listenIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
        startActivityForResult(listenIntent, RESULT_REQUEST);
    }

    /* the setEquation() method sets a new equation when the user presses the
       button to restart, it also resets the TextView that displays "CORRECT!"
       or "INCORRECT!" back to " "*/
    public void setEquation(View view)
    {
        //the two random integers are generated and stored in integer variables
        randInt = new Random();
        firstInt = randInt.nextInt(9);
        secondInt = randInt.nextInt(9);

        sum = firstInt + secondInt;

        //the 3 TextViews are set
        intOne.setText(String.valueOf(firstInt));
        intTwo.setText(String.valueOf(secondInt));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_REQUEST && resultCode == RESULT_OK)
        {
            // retrieve list of possible words
            ArrayList<String> returnedWords = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            // retrieve array of scores for returnedWords
            float [] scores = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

            //a match is returned
            String match = values.firstMatchWithMinConfidence(returnedWords, scores);

            /*int i = 0;
            for(String word: returnedWords)
            {
                if(scores != null && i < scores.length)
                {
                    Log.w("MainActivity", word + ": " + scores[i]);
                }
                i++;
            }*/

            //Log.w("MainActivity", "Input: " + match);
            //Log.w("MainActivity", "Sum: " + String.valueOf(sum));

            /*compares the match returned by firstMatchWithMinConfidence() to the sum of the integers
              and prints the appropriate message*/
            if(String.valueOf(sum) == match)
            {
                Toast.makeText(this, "CORRECT!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "INCORRECT!", Toast.LENGTH_LONG).show();
            }
        }
     }
}
