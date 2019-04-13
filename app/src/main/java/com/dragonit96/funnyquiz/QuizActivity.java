package com.dragonit96.funnyquiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLS = 30000;

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUEST_COUNT = "keyQuestCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUEST_LIST = "keyQuestList";

    private TextView tvQuest;
    private TextView tvScore;
    private TextView tvQuestCount;
    private TextView tvDifficulty;
    private TextView tvCountdown;
    private RadioGroup radioGroup;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private Button btnConfirm;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCb;

    private CountDownTimer countDownTimer;
    private long timeLeftMills;

    private ArrayList<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answer;

    private long backPressTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuest = findViewById(R.id.tvQuest);
        tvScore = findViewById(R.id.tvScore);
        tvQuestCount = findViewById(R.id.tvQuestCount);
        tvDifficulty = findViewById(R.id.tvDifficulty);
        tvCountdown = findViewById(R.id.tvCountdown);
        radioGroup = findViewById(R.id.radioGroup);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        radio3 = findViewById(R.id.radio3);
        btnConfirm = findViewById(R.id.btnConfirm);

        textColorDefaultRb = radio1.getTextColors();
        textColorDefaultCb = tvCountdown.getTextColors();

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(MainActivity.EXTRA_DIFFICULTY);

        tvDifficulty.setText("Difficulty: " + difficulty);

        if (savedInstanceState == null) {
            QuizDbHelper dbHelper = new QuizDbHelper(this);
            questionList = dbHelper.getQuestion(difficulty);
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);

            showNextQuest();
        }else{
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUEST_LIST);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUEST_COUNT);
            currentQuestion = questionList.get(questionCounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftMills = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answer = savedInstanceState.getBoolean(KEY_ANSWERED);

            if (!answer){
                startCountDown();
            }else {
                updateCountDownText();
                showSolution();
            }
        }
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answer){
                    if(radio1.isChecked() || radio2.isChecked() || radio3.isChecked()){
                        checkAnswer();
                    }else{
                        Toast.makeText(QuizActivity.this,"Hãy chọn 1 đáp án!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showNextQuest();
                }
            }
        });
    }
    private void showNextQuest(){
        radio1.setTextColor(textColorDefaultRb);
        radio2.setTextColor(textColorDefaultRb);
        radio3.setTextColor(textColorDefaultRb);
        radioGroup.clearCheck();

        if(questionCounter < questionCountTotal){
            currentQuestion = questionList.get(questionCounter);

            tvQuest.setText(currentQuestion.getQuestion());
            radio1.setText(currentQuestion.getOption1());
            radio2.setText(currentQuestion.getOption2());
            radio3.setText(currentQuestion.getOption3());

            questionCounter++;
            tvQuestCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answer = false;
            btnConfirm.setText("CONFIRM");

            timeLeftMills = COUNTDOWN_IN_MILLS;
            startCountDown();
        }else{
            finishQuiz();
        }
    }

    private void startCountDown(){
        countDownTimer = new CountDownTimer(timeLeftMills,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMills = millisUntilFinished;
                updateCountDownText();
            }


            @Override
            public void onFinish() {
                timeLeftMills = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText(){
        int minutes = (int) (timeLeftMills / 1000) /60;
        int seconds = (int) (timeLeftMills /1000) % 60;

        String timeFomatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);

        tvCountdown.setText(timeFomatted);

        if(timeLeftMills < 10000){
            tvCountdown.setTextColor(Color.RED);
        }else {
            tvCountdown.setTextColor(textColorDefaultCb);
        }
    }

    private void checkAnswer(){
        answer = true;

        countDownTimer.cancel();
        RadioButton radioSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNb = radioGroup.indexOfChild(radioSelected) + 1;

        if (answerNb == currentQuestion.getAnswerNb()){
            score += 10;                            //Điểm
            tvScore.setText("Score: " + score);
        }

        showSolution();
    }

    private void showSolution(){
        radio1.setTextColor(Color.RED);
        radio2.setTextColor(Color.RED);
        radio3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNb()){
            case 1:
                radio1.setTextColor(Color.GREEN);
                tvQuest.setText("Đáp án 1 đúng!");
                break;
            case 2:
                radio2.setTextColor(Color.GREEN);
                tvQuest.setText("Đáp án 2 đúng!");
                break;
            case 3:
                radio3.setTextColor(Color.GREEN);
                tvQuest.setText("Đáp án 3 đúng!");
                break;
        }

        if (questionCounter < questionCountTotal){
            btnConfirm.setText("NEXT");
        }else{
            btnConfirm.setText("FINISH");
        }
    }
    private void finishQuiz(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE,score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()){
            finishQuiz();
        }else{
            Toast.makeText(this,"Nhấn lần nữa để thoát",Toast.LENGTH_SHORT).show();
        }

        backPressTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(KEY_SCORE,score);
        outState.putInt(KEY_QUEST_COUNT,questionCounter);
        outState.putLong(KEY_MILLIS_LEFT,timeLeftMills);
        outState.putBoolean(KEY_ANSWERED,answer);
        outState.putParcelableArrayList(KEY_QUEST_LIST,questionList);
    }
}
