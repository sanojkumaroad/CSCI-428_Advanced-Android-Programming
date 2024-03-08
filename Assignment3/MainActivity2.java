package edu.niu.android.voicerecognition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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
    private TextView result;
    private TextView intOne;
    private TextView intTwo;

    private int firstInt;
    private int secondInt;
    private int sum;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

            intOne.setText(String.valueOf(firstInt));//TextView that displays the first operand
            intTwo.setText(String.valueOf(secondInt));//TextView that displays the second operand
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
        listenIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What is the result?");
        listenIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        listenIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        startActivityForResult(listenIntent, RESULT_REQUEST);
    }

    /* the setEquation() method sets a new equation when the user presses the
       button to restart, it also resets the TextView that displays "CORRECT!"
       or "INCORRECT!" back to " "*/
    public void setEquation(View view)
    {
        result = findViewById(R.id.result); //TextView that displays whether the user was correct or incorrect

        //the two random integers are generated and stored in integer variables
        randInt = new Random();
        firstInt = randInt.nextInt(9);
        secondInt = randInt.nextInt(9);

        sum = firstInt + secondInt;

        //the 3 TextViews are set
        result.setText(" ");
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

            // display results
            int i = 0;
            for (String word : returnedWords)
            {
                if (scores != null && i < scores.length)
                {
                    Log.w("MainActivity", word + ": " + scores[i]);
                }
                i++;
            }
        }
     }
}
