package edu.niu.android.addtwo;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int SPEECH_REQUEST_CODE = 0;
    private TextView num1;      // First Operand from the View
    private TextView num2;      // Second Operand from the View
    private Button speakBtn;    // Button to Record Voice for Answer
    private Button restartBtn;  // Button to Restart or Generate a new equation

    private int correctAnswer;  // Stores the Correct Answer of the Equation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = findViewById(R.id.fOperand);         // First Operand
        num2 = findViewById(R.id.sOperand);         // Second Operand
        speakBtn = findViewById(R.id.answer);       // Speak Button
        restartBtn = findViewById(R.id.restart);    // Restart Button

        // generates two numbers and displays them on the activity_main
        getNumbers();

        

        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognition();
            }
        });
    }

    private void getNumbers() {
        Random random = new Random();
        int operand1 = random.nextInt(30) + 1;
        int operand2 = random.nextInt(20)+ 1;

        correctAnswer = operand1 + operand2;    // Stores the Correct Answer of the Equation
        num1.setText(operand1);                 // Updates Operand 1 on the View
        num2.setText(operand2);                 // Updates Operand 2 on the View
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your answer");

        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    private void checkAnswer(String spokenText) {
        try {
            int userAnswer = Integer.parseInt(spokenText);

            if (userAnswer == correctAnswer) {
                Toast.makeText(this, "CORRECT!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "WRONG!", Toast.LENGTH_SHORT).show();
            }

            // Generate a new equation for the user to solve
            getNumbers();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please speak a number.", Toast.LENGTH_SHORT).show();
        }
    }
}
