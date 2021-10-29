package com.soorajmohan.test.jav1001lab_scorekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
implements View.OnClickListener
{
    //variables to hold Runs and Wickets scored by both teams
    private int teamOneRun = 0;
    private int teamOneWicket = 0;
    private int teamTwoRun = 0;
    private int teamTwoWicket = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declaring the buttons
        Button teamOneUp = findViewById(R.id.TeamOneIncrease);
        Button teamOneDown = findViewById(R.id.TeamOneReduce);
        Button teamTwoUp = findViewById(R.id.TeamTwoIncrease);
        Button teamTwoDown = findViewById(R.id.TeamTwoReduce);
        Button newGame = findViewById(R.id.newGame);

        //calling update screen to update the button status as per the opening values
        updateScreen();

        //Events for the buttons. They are called when user clicks on the buttons
        teamOneUp.setOnClickListener(this);
        teamOneDown.setOnClickListener(this);
        teamTwoUp.setOnClickListener(this);
        teamTwoDown.setOnClickListener(this);
        newGame.setOnClickListener(this);

        //Event is triggered when any of the radiobutton is selected, it calls the update screen method
        RadioGroup scoringOptions = findViewById(R.id.ScoringOptions);
        scoringOptions.setOnCheckedChangeListener((group, checkedId) -> updateScreen());
    }



    @Override
    public void onClick(View view)
    {
        //integer array to hold run and wicket value from the TextView
        int[] runsAndWicket;

        //fetching the score for team one from TextView
        TextView teamOneScore = findViewById(R.id.TeamOneScore);
        //fetching the run and wicket value from the TextView
        runsAndWicket = getRunAndWickets(String.valueOf(teamOneScore.getText()));
        //Run value is stored in the first array position of runsAndWicket
        teamOneRun = runsAndWicket[0];
        //If wicket is equal to 10, the score will only have runs value - hence the condition
        //If wicket value is available, it will be there on the second array position of runsAndWicket
        if (runsAndWicket.length > 1){teamOneWicket = runsAndWicket[1]; }

        //fetching the score for team two from TextView
        TextView teamTwoScore = findViewById(R.id.TeamTwoScore);
        //fetching the run and wicket value from the TextView
        runsAndWicket = getRunAndWickets(String.valueOf(teamTwoScore.getText()));
        //Run value is stored in the first array position of runsAndWicket
        teamTwoRun = runsAndWicket[0];
        //If wicket is equal to 10, the score will only have runs value - hence the condition
        //If wicket value is available, it will be there on the second array position of runsAndWicket
        if (runsAndWicket.length > 1){teamTwoWicket = runsAndWicket[1]; }

        //scoreValue is fetched as per the radiobutton selected by the user
        int scoreValue = getScoringValue();

        //switch statements to control the logic for each button
        switch(view.getId())
        {
            case R.id.TeamOneIncrease:
                //if user selects wicket radiobutton then the scoreValue will be -1
                if (scoreValue != -1)
                {
                    //increment team one run by the scoreValue
                    teamOneRun = teamOneRun + scoreValue;
                }
                else
                {
                    //increment team one wicket by 1
                    teamOneWicket = teamOneWicket + 1;
                }
                break;
            case R.id.TeamOneReduce:
                //if user selects wicket radiobutton then the scoreValue will be -1
                if (scoreValue != -1)
                {
                    //decrement team one run by the scoreValue
                    teamOneRun = teamOneRun - scoreValue;
                }
                else
                {
                    //decrement team one wicket by 1
                    teamOneWicket = teamOneWicket - 1;
                }
                break;

            case R.id.TeamTwoIncrease:
                //if user selects wicket radiobutton then the scoreValue will be -1
                if (scoreValue != -1)
                {
                    //increment team two run by scoreValue
                    teamTwoRun = teamTwoRun + scoreValue;
                }
                else
                {
                    //increment team two wicket by 1
                    teamTwoWicket = teamTwoWicket + 1;
                }
                break;
            case R.id.TeamTwoReduce:
                //if user selects wicket radiobutton then the scoreValue will be -1
                if (scoreValue != -1)
                {
                    //decrement team two run by the scoreValue
                    teamTwoRun = teamTwoRun - scoreValue;
                }
                else
                {
                    //decrement team two wicket by one
                    teamTwoWicket = teamTwoWicket - 1;
                }
                break;
            case R.id.newGame:
                //newGame button resets all score to zero
                teamOneRun = 0;
                teamTwoRun = 0;
                teamOneWicket = 0;
                teamTwoWicket = 0;
        }
        //update screen for updating screen values
        updateScreen();
    }

    public void updateScreen ()
    {
        //declaring buttons
        Button teamOneUp = findViewById(R.id.TeamOneIncrease);
        Button teamOneDown = findViewById(R.id.TeamOneReduce);
        Button teamTwoUp = findViewById(R.id.TeamTwoIncrease);
        Button teamTwoDown = findViewById(R.id.TeamTwoReduce);
        Button newGame = findViewById(R.id.newGame);

        //if run is 0 or less than 0 and wicket equal to or above 10 then disable minus button
        teamOneDown.setEnabled(teamOneRun > 0 && teamOneWicket < 10);
        teamTwoDown.setEnabled(teamTwoRun > 0 && teamTwoWicket < 10);
        //if wicket is more or equal to 10 then disable plus button
        teamOneUp.setEnabled(teamOneWicket < 10);
        teamTwoUp.setEnabled(teamTwoWicket < 10);

        //if radiobutton wicket is selected then enable the minus button until wicket is 0
        if (getScoringValue() == -1)
        {
            teamOneDown.setEnabled(teamOneWicket > 0);
            teamTwoDown.setEnabled(teamTwoWicket > 0);
        }

        //if all scores are zero then new game button is disabled
        newGame.setEnabled(teamOneRun > 0 || teamTwoRun > 0 || teamOneWicket > 0 || teamTwoWicket > 0);

        //update team one score TextView - getScore method returns the string with run and wicket separated by a "/"
        TextView teamOneScore = findViewById(R.id.TeamOneScore);
        teamOneScore.setText(getScore(teamOneRun,teamOneWicket));

        //update team two score TextView
        TextView teamTwoScore = findViewById(R.id.TeamTwoScore);
        teamTwoScore.setText(getScore(teamTwoRun,teamTwoWicket));
    }

    //method to extract run and wickets from the score in TextView
    public int[] getRunAndWickets (String score)
    {
        //the score is split into run and wickets using string function split() which returns string array
        String[] runAndWicketString;
        //the score is split at "/" : left side contains run and right side contains wicket
        runAndWicketString = score.split("/");
        //run and wicket values are passed onto int array
        int[] runAndWicket = new int[runAndWicketString.length];
        for(int i=0; i < runAndWicketString.length; i++)
        {
            runAndWicket[i] = Integer.parseInt(runAndWicketString[i]);
        }

        return runAndWicket;
    }

    //method to determine selected radiobutton
    public int getScoringValue ()
    {
        //scoreValue is determined based on the radiobutton selection
        int scoreValue;
        RadioGroup scoringOptions = findViewById(R.id.ScoringOptions);
        //the score value is assigned with a value corresponding to the number of runs mentioned for each radiobutton
        //for radiobutton "wicket" a value -1 is assigned. This will be identified in the calling methods.
        switch (scoringOptions.getCheckedRadioButtonId())
        {
            case R.id.ScoreOne:
                scoreValue = 1;
                break;
            case R.id.ScoreTwo:
                scoreValue = 2;
                break;
            case R.id.ScoreThree:
                scoreValue = 3;
                break;
            case R.id.ScoreFour:
                scoreValue = 4;
                break;
            case R.id.ScoreFive:
                scoreValue = 6;
                break;
            case R.id.Wicket:
                scoreValue = -1;
                break;
            default:
                scoreValue = 0;
                break;
        }

        return scoreValue;
    }

    //getScore method returns the string with run and wicket value
    public String getScore(int run, int wicket)
    {
        //to avoid negative values in run and wicket
        if (run < 0) {run = 0;}
        if (wicket<0) {wicket = 0;}

        //if wicket is equal to 10 then only runs is displayed
        if (wicket == 10)
        {
            return String.valueOf(run);
        }
        else
        //else if wicket is less than 10 then run and wicket is displayed separated by "/"
        {
            return run + "/" + wicket;
        }
    }
}