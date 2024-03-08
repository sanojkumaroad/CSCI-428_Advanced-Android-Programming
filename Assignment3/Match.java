package edu.niu.android.voicerecognition;

import java.util.ArrayList;

public class Match
{
    public static final float MIN_CONFIDENCE = 0.45f;
    public static final String DEFAULT_VALUE = null;

    public String firstMatchWithMinConfidence(ArrayList<String> words, float [] confidLevels)
    {
        if(words == null || confidLevels == null)
        {
            return DEFAULT_VALUE;
        }

        int numberOfWords = words.size();

        /* this loop searches through the list of words that input could be */
        for(int i = 0; i < numberOfWords && i < confidLevels.length; i++)
        {
            /*if confidence level is below the min confidence level, it moves to the next possible word*/
            if(confidLevels[i] < MIN_CONFIDENCE)
            {
                break;
            }
            String word = words.get(i);

            /*the word needs to contain a number to be returned to MainActivity*/
            if(word.matches(".*\\d.*"))
            {
                return word;
            }
        }
        return DEFAULT_VALUE;
    }
}
