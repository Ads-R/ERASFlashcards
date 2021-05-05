package ca.on.conestogac.prog3210.erasflashcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private List<Question> qList = new ArrayList<>();
    private List<Choice> cList = new ArrayList<>();
    String fname, lname, category;
    Button submit;
    TextView questionTxt, scoreTxt;
    RadioButton chc1, chc2, chc3, chc4;
    ImageView faceStatus;
    int numberOfQuestions;
    int index = 0;
    int score = 0;
    int answerBoolean;
    Boolean HasAnswered = false;
    FlashcardsApplication application;
    LinearLayout top, bottom;
    private SharedPreferences shared;
    private boolean darkTheme, dragdrop;
    private boolean HasPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        shared = PreferenceManager.getDefaultSharedPreferences(this);
        darkTheme = shared.getBoolean("theme", false);
        dragdrop = shared.getBoolean("dragdrop", false);

        if(darkTheme){
            setTheme(R.style.ERASFlashCardsDarkTheme);
        }
        else{
            setTheme(R.style.ERASFlashCardsTheme);
        }

        setContentView(R.layout.activity_start);

        fname = getIntent().getStringExtra("fname");
        lname = getIntent().getStringExtra("lname");
        category = getIntent().getStringExtra("category");
        numberOfQuestions = getIntent().getIntExtra("numberOfQuestions", 0);

        questionTxt = (TextView) findViewById(R.id.start_questionTxt);
        scoreTxt = (TextView) findViewById(R.id.scoreTxt);
        chc1 = (RadioButton) findViewById(R.id.choiceA);
        chc2 = (RadioButton) findViewById(R.id.choiceB);
        chc3 = (RadioButton) findViewById(R.id.choiceC);
        chc4 = (RadioButton) findViewById(R.id.choiceD);
        submit = (Button) findViewById(R.id.start_submit);
        faceStatus = (ImageView) findViewById(R.id.start_face);
        top = (LinearLayout) findViewById(R.id.layoutTop);
        bottom = (LinearLayout) findViewById(R.id.layoutBottom);


        application =((FlashcardsApplication) getApplication());

        Cursor cs = application.getQuestion(category);
        if(cs.moveToFirst()){
            do{
                Question x = new Question(cs.getInt(cs.getColumnIndex("q_id")),
                        cs.getString(cs.getColumnIndex("q_text")),
                        cs.getString(cs.getColumnIndex("q_category")));
                qList.add(x);
            }while(cs.moveToNext());
        }
        cs.close();


        for(Question quest:qList){
            Log.i("info", "ID:"+quest.getId());
            Log.i("info", "Text:"+quest.getQtext());
        }

        if(qList.size()>1){
            SetQuestionAndChoices(index);
        }
        scoreTxt.setText(score+"/"+index);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HasAnswered) {
                    index++;
                    if (answerBoolean == 1) {
                        score++;
                        faceStatus.setImageResource(R.drawable.ic_happy);
                    } else {
                        faceStatus.setImageResource(R.drawable.ic_sad);
                    }
                    IsRadioAndButtonEnabled(false);
                    faceStatus.setAlpha(0f);
                    faceStatus.animate().alpha(1f).setDuration(2000).setListener(
                            new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    //faceStatus.setImageDrawable(null);
                                    if (index >= numberOfQuestions) {
                                        double percentage = ((double)score/index)*100.0;
                                        Score x = new Score(0,fname,lname,percentage,category,Math.round(System.currentTimeMillis()/1000));
                                        application.addScore(x);
                                        Snackbar.make(findViewById(android.R.id.content), "Your score is "+ score +"/"+ index, Snackbar.LENGTH_INDEFINITE)
                                                .setAction("Go Home", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                    }
                                                }).show();
                                    } else {
                                        SetQuestionAndChoices(index);
                                        IsRadioAndButtonEnabled(true);
                                    }
                                }
                            }
                    );
                    UpdateScoreAndRadioBtn();
                }
                else{
                    IsRadioAndButtonEnabled(false);
                    Snackbar.make(findViewById(android.R.id.content), R.string.snackbar_start, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.Ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    IsRadioAndButtonEnabled(true);
                                }
                            }).show();
                }
            }
        });
            if(dragdrop){
            setDragDrop();
        }
    }

    @Override
    protected void onPause() {
        Editor editor =  shared.edit();
        editor.putString("fname", fname);
        editor.putString("lname", lname);
        editor.putString("category", category);
        editor.putInt("index", index);
        editor.putInt("numberOfQuestions", numberOfQuestions);
        HasPaused = true;
        editor.putBoolean("HasPaused", HasPaused);

        Gson gson = new Gson();
        String jsonQuestions = gson.toJson(qList);
        editor.putString("questionList", jsonQuestions);
        editor.commit();
        super.onPause();
    }

    private List<Question> getQuestionList(String key){
        Gson gson = new Gson();
        List<Question> qList;
        String str = shared.getString(key, null);
        Type type = new TypeToken<List<Question>>(){}.getType();
        qList = gson.fromJson(str, type);
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        RetrieveData();
    }


    @Override
    protected void onStop() {
        startService(new Intent(getApplicationContext(), NotificationService.class));
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void RetrieveData(){
        if(HasPaused) {
            fname = shared.getString("fname", fname);
            lname = shared.getString("lname", lname);
            category = shared.getString("category", category);
            index = shared.getInt("index", index);
            numberOfQuestions = shared.getInt("numberOfQuestions", numberOfQuestions);
            getQuestionList("questionList");
            SetQuestionAndChoices(index);
        }
    }

    private void SetQuestionAndChoices(int count){
        Log.i("info","Size: " + qList.size());
        Log.i("info", "Count: "+count);
        questionTxt.setText(qList.get(count).getQtext());
        GetChoices(qList.get(count).getId());
    }

    private void GetChoices(int questionId){
        List<Choice> cList = new ArrayList<>();
        Cursor csc = application.getChoices(questionId);
        if(csc.moveToFirst()){
            do{
                Choice x = new Choice(csc.getInt(csc.getColumnIndex("a_id")),
                        csc.getInt(csc.getColumnIndex("q_id")),
                        csc.getString(csc.getColumnIndex("a_text")),
                        csc.getInt(csc.getColumnIndex("a_answer")));
                cList.add(x);
            }while(csc.moveToNext());
        }
        csc.close();

        chc1.setText(cList.get(0).getCtext());
        chc2.setText(cList.get(1).getCtext());
        chc3.setText(cList.get(2).getCtext());
        chc4.setText(cList.get(3).getCtext());

        chc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerBoolean = cList.get(0).getAnswer();
                HasAnswered = true;
            }
        });

        chc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerBoolean = cList.get(1).getAnswer();
                HasAnswered = true;
            }
        });

        chc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerBoolean = cList.get(2).getAnswer();
                HasAnswered = true;
            }
        });

        chc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerBoolean = cList.get(3).getAnswer();
                HasAnswered = true;
            }
        });
    }

    private void UpdateScoreAndRadioBtn(){
        scoreTxt.setText(score+"/"+index);
        chc1.setChecked(false);
        chc2.setChecked(false);
        chc3.setChecked(false);
        chc4.setChecked(false);
        answerBoolean = 0;
        HasAnswered = false;
    }

    private void IsRadioAndButtonEnabled(Boolean bool){
        chc1.setEnabled(bool);
        chc2.setEnabled(bool);
        chc3.setEnabled(bool);
        chc4.setEnabled(bool);
        submit.setEnabled(bool);
    }

    private void setDragDrop(){
        View.OnTouchListener onTouch = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean dragStatus = false;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
                    v.startDrag(null, shadow, v, 0);
                    dragStatus = true;
                }
                return dragStatus;
            }
        };
        final View.OnDragListener dragListener = new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(event.getAction() == DragEvent.ACTION_DROP){
                    View dragged = (View) event.getLocalState();
                    ViewGroup layoutSource = (ViewGroup) dragged.getParent();

                    layoutSource.removeView(dragged);
                    ((LinearLayout) v).addView(dragged);
                }
                return true;
            }
        };
        top.setOnDragListener(dragListener);
        bottom.setOnDragListener(dragListener);
        faceStatus.setOnTouchListener(onTouch);
    }
}