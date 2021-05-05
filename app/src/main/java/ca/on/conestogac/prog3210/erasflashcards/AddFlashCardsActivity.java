package ca.on.conestogac.prog3210.erasflashcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddFlashCardsActivity extends AppCompatActivity {
    private SharedPreferences shared;
    private boolean darkTheme;
    String[] categories = new String[]{"English", "History", "Math", "Science"};
    Spinner category;
    EditText question, correct, second, third, fourth;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        shared = PreferenceManager.getDefaultSharedPreferences(this);
        darkTheme = shared.getBoolean("theme", false);
        if(darkTheme){
            setTheme(R.style.ERASFlashCardsDarkTheme);
        }
        else{
            setTheme(R.style.ERASFlashCardsTheme);
        }

        setContentView(R.layout.activity_add_flash_cards);

        category = (Spinner) findViewById(R.id.categoryTxt);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        category.setAdapter(adapter);

        question = (EditText) findViewById(R.id.questionTxt);
        correct = (EditText) findViewById(R.id.correctTxt);
        second = (EditText) findViewById(R.id.secondTxt);
        third = (EditText) findViewById(R.id.thirdTxt);
        fourth = (EditText) findViewById(R.id.fourthTxt);

        submit = (Button) findViewById(R.id.submitBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = 0;
                Question q = new Question(id, question.getText().toString(), category.getSelectedItem().toString());

                Choice st = new Choice(id, id, correct.getText().toString(), 1);
                Choice nd = new Choice(id, id, second.getText().toString(), 0);
                Choice rd = new Choice(id, id, third.getText().toString(), 0);
                Choice th = new Choice(id, id, fourth.getText().toString(), 0);

                ((FlashcardsApplication) getApplication()).addQuestionsAndChoices(q, st, nd, rd, th);


                Log.i("info", "Category: "+category.getSelectedItem().toString());
                Log.i("info", "Question: "+question.getText().toString());
                Log.i("info", "Correct: "+correct.getText().toString());
                Log.i("info", "Second: "+second.getText().toString());
                Log.i("info", "Third: "+third.getText().toString());
                Log.i("info", "Fourth: "+fourth.getText().toString());


            }
        });
    }
}