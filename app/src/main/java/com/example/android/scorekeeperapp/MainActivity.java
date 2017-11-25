package com.example.android.scorekeeperapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    static final String stateScoreTeamA = "emergencyScoreA";
    static final String stateScoreTeamB = "emergencyScoreB";
    static final String stateFoulCounterA = "emergencyFoulA";
    static final String stateFoulCounterB = "emergencyFoulB";
    static final String stateScoreThreePointTeamA = "emergencyThreeA";
    static final String stateScoreThreePointTeamB = "emergencyThreeB";
    static final String stateScoreTwoPointTeamA = "emergencyTwoA";
    static final String stateScoreTwoPointTeamB = "emergencyTwoA";
    static final String stateScoreOnePointTeamA = "emergencyOneA";
    static final String stateScoreOnePointTeamB = "emergencyOneA";
    DecimalFormat currency = new DecimalFormat("##.##");
    int scoreTeamA = 0;
    int scoreTeamB = 0;
    int foulCounterA = 0;
    int foulCounterB = 0;
    int scoreThreePointTeamA = 0;
    int scoreThreePointTeamB = 0;
    int scoreTwoPointTeamA = 0;
    int scoreTwoPointTeamB = 0;
    int scoreOnePointTeamA = 0;
    int scoreOnePointTeamB = 0;
    int periodCount = 0;
    private TextView tv1;
    private TextView period;
    private Button startbtn, cancelbtn;
    private ToggleButton togbtn;
    private boolean isPaused = false;
    private boolean isCanceled = false;
    private long remainingTime = 0;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt(stateScoreTeamA, scoreTeamA);
        savedInstanceState.putInt(stateScoreTeamB, scoreTeamB);
        savedInstanceState.putInt(stateFoulCounterA, foulCounterA);
        savedInstanceState.putInt(stateFoulCounterB, foulCounterB);
        savedInstanceState.putInt(stateScoreThreePointTeamA, scoreThreePointTeamA);
        savedInstanceState.putInt(stateScoreThreePointTeamB, scoreThreePointTeamB);
        savedInstanceState.putInt(stateScoreTwoPointTeamA, scoreTwoPointTeamA);
        savedInstanceState.putInt(stateScoreTwoPointTeamB, scoreTwoPointTeamB);
        savedInstanceState.putInt(stateScoreOnePointTeamA, scoreOnePointTeamA);
        savedInstanceState.putInt(stateScoreOnePointTeamB, scoreOnePointTeamB);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        scoreTeamA = savedInstanceState.getInt(stateScoreTeamA);
        scoreTeamB = savedInstanceState.getInt(stateScoreTeamB);
        foulCounterA = savedInstanceState.getInt(stateFoulCounterA);
        foulCounterB = savedInstanceState.getInt(stateFoulCounterB);
        scoreThreePointTeamA = savedInstanceState.getInt(stateScoreThreePointTeamA);
        scoreThreePointTeamB = savedInstanceState.getInt(stateScoreThreePointTeamB);
        scoreTwoPointTeamA = savedInstanceState.getInt(stateScoreTwoPointTeamA);
        scoreTwoPointTeamB = savedInstanceState.getInt(stateScoreTwoPointTeamB);
        scoreOnePointTeamA = savedInstanceState.getInt(stateScoreOnePointTeamA);
        scoreOnePointTeamB = savedInstanceState.getInt(stateScoreOnePointTeamB);
        displayForTeamA(scoreTeamA);
        displayForTeamAThreePoint(scoreThreePointTeamA);
        displayForTeamATwoPoint(scoreTwoPointTeamA);
        displayForTeamAOnePoint(scoreOnePointTeamA);
        displayForTeamAFoul(foulCounterA);
        displayForTeamB(scoreTeamB);
        displayForTeamBThreePoint(scoreThreePointTeamB);
        displayForTeamBTwoPoint(scoreTwoPointTeamB);
        displayForTeamBOnePoint(scoreOnePointTeamB);
        displayForTeamBFoul(foulCounterB);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        period = (TextView) findViewById(R.id.period_number);
        startbtn = (Button) findViewById(R.id.startBtn);
        cancelbtn = (Button) findViewById(R.id.cancelBtn);
        togbtn = (ToggleButton) findViewById(R.id.togBtn);

        cancelbtn.setEnabled(false);
        togbtn.setEnabled(false);

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                foulCounterA = 0;
                foulCounterB = 0;
                displayForTeamAFoul(foulCounterA);
                displayForTeamBFoul(foulCounterB);

                if (periodCount < 3)
                    periodCount = periodCount + 1;
                else periodCount = 4;
                period.setText("Period " + periodCount);

                startbtn.setEnabled(false);
                cancelbtn.setEnabled(true);
                togbtn.setEnabled(true);

                isPaused = false;
                isCanceled = false;

                long millisInFuture = 20000; /////20sec
                final long countDownInterval = 10; /////1sec
                new CountDownTimer(millisInFuture, countDownInterval) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (isPaused || isCanceled) {
                            cancel();
                        } else {
                            tv1.setText(currency.format(+millisUntilFinished / 1000.0));
                            remainingTime = millisUntilFinished;
                        }

                    }

                    @Override
                    public void onFinish() {

                        startbtn.setEnabled(true);
                        togbtn.setEnabled(false);

                        if (periodCount < 4)
                            tv1.setText("Times up!");
                        else tv1.setText("Game Over!");
                    }
                }.start();
            }
        });

        togbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (togbtn.isChecked()) {
                    isPaused = true;
                } else {
                    isPaused = false;
                    long millisInFuture = remainingTime;
                    long countDownInterval = 10; /////1sec
                    new CountDownTimer(millisInFuture, countDownInterval) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (isPaused || isCanceled) {
                                cancel();
                            } else {
                                tv1.setText(currency.format(+millisUntilFinished / 1000.0));
                                remainingTime = millisUntilFinished;
                            }

                        }

                        @Override
                        public void onFinish() {

                            startbtn.setEnabled(true);
                            togbtn.setEnabled(false);

                            if (periodCount < 4)
                                tv1.setText("Times up!");
                            else tv1.setText("Game Over!");

                        }
                    }.start();
                }
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                period.setText("Period");
                tv1.setText("Timer");
                isCanceled = true;
                togbtn.setChecked(false);
                startbtn.setEnabled(true);
                togbtn.setEnabled(false);
                cancelbtn.setEnabled(false);

                foulCounterA = 0;
                foulCounterB = 0;
                periodCount = 0;
                displayForTeamAFoul(foulCounterA);
                displayForTeamBFoul(foulCounterB);
            }
        });


    }

    public void threePointForTeamA(View v) {
        scoreTeamA = scoreTeamA + 3;
        scoreThreePointTeamA = scoreThreePointTeamA + 3;
        displayForTeamA(scoreTeamA);
        displayForTeamAThreePoint(scoreThreePointTeamA);
    }

    public void twoPointForTeamA(View v) {
        scoreTeamA = scoreTeamA + 2;
        scoreTwoPointTeamA = scoreTwoPointTeamA + 2;
        displayForTeamA(scoreTeamA);
        displayForTeamATwoPoint(scoreTwoPointTeamA);
    }

    public void onePointForTeamA(View v) {
        if (foulCounterA == 5) {
            scoreTeamA = scoreTeamA + 1;
            scoreOnePointTeamA = scoreOnePointTeamA + 1;
            displayForTeamA(scoreTeamA);
            displayForTeamAOnePoint(scoreOnePointTeamA);
        }
    }


    //Button clickButton = (Button) findViewById(R.id.freeThrowTeamA);
    //clickButton.setOnClickListener( new OnClickListener() {

      //  @Override
      // public void onClick(View v) {
       // }
    // });


   // public class MyActivity extends Activity {
   //     protected void onCreate(Bundle savedInstanceState) {
   //         super.onCreate(savedInstanceState);

//            setContentView(R.layout.activity_main);

  //          final Button button = findViewById(R.id.freeThrowTeamA);
    //        button.setOnClickListener(new View.OnClickListener() {
      //          public void onClick(View v) {
        //            if (foulCounterA == 5)
          //              button.setEnabled(true);
            //        else
              //          button.setEnabled(false);
               // }
            //});
     //   }
    //}

    public void foulCountForTeamA(View v) {
        if (foulCounterA < 5)
        foulCounterA = foulCounterA + 1;

        else
            foulCounterA = 5;

        displayForTeamAFoul(foulCounterA);
    }

    public void threePointForTeamB(View v) {
        scoreTeamB = scoreTeamB + 3;
        scoreThreePointTeamB = scoreThreePointTeamB + 3;
        displayForTeamB(scoreTeamB);
        displayForTeamBThreePoint(scoreThreePointTeamB);
    }

    public void twoPointForTeamB(View v) {
        scoreTeamB = scoreTeamB + 2;
        scoreTwoPointTeamB = scoreTwoPointTeamB + 2;
        displayForTeamB(scoreTeamB);
        displayForTeamBTwoPoint(scoreTwoPointTeamB);
    }

    public void onePointForTeamB(View v) {
        boolean condition = (foulCounterA == 5);
        v.setEnabled(condition);

        if (foulCounterB == 5) {
            scoreTeamB = scoreTeamB + 1;
            scoreOnePointTeamB = scoreOnePointTeamB + 1;
            displayForTeamB(scoreTeamB);
            displayForTeamBOnePoint(scoreOnePointTeamB);
        }
    }

    public void foulCountForTeamB(View v) {
        if (foulCounterB < 5)
            foulCounterB = foulCounterB + 1;
        else
            foulCounterB = 5;
        displayForTeamBFoul(foulCounterB);
    }

    public void resetGame(View v) {

        tv1.setText("Timer");
        period.setText("Period");
        isCanceled = true;
        togbtn.setChecked(false);
        startbtn.setEnabled(true);
        togbtn.setEnabled(false);
        cancelbtn.setEnabled(false);

        periodCount = 0;
        scoreTeamA = 0;
        scoreThreePointTeamA = 0;
        scoreTwoPointTeamA = 0;
        scoreOnePointTeamA = 0;
        foulCounterA = 0;
        scoreTeamB = 0;
        scoreThreePointTeamB = 0;
        scoreTwoPointTeamB = 0;
        scoreOnePointTeamB = 0;
        foulCounterB = 0;

        displayForTeamA(scoreTeamA);
        displayForTeamAThreePoint(scoreThreePointTeamA);
        displayForTeamATwoPoint(scoreTwoPointTeamA);
        displayForTeamAOnePoint(scoreOnePointTeamA);
        displayForTeamAFoul(foulCounterA);
        displayForTeamB(scoreTeamB);
        displayForTeamBThreePoint(scoreThreePointTeamB);
        displayForTeamBTwoPoint(scoreTwoPointTeamB);
        displayForTeamBOnePoint(scoreOnePointTeamB);
        displayForTeamBFoul(foulCounterB);
    }

    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamAThreePoint(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score_3_point);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamATwoPoint(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score_2_point);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamAOnePoint(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score_1_point);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamAFoul(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_foul);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamBThreePoint(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score_3_point);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamBTwoPoint(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score_2_point);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamBOnePoint(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score_1_point);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamBFoul(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_foul);
        scoreView.setText(String.valueOf(score));
    }

}
