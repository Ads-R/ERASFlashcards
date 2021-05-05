package ca.on.conestogac.prog3210.erasflashcards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences shared;
    private boolean darkTheme;
    private Button submit;
    private EditText fnameTxt, lnameTxt, categoryTxt;
    private FloatingActionButton fab;

    String[] categories = new String[]{"English", "History", "Math", "Science"};
    String [] numberOfQuestions = new String[]{"5", "10"};
    Spinner category, questionNumber;

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
        setContentView(R.layout.activity_main);


        fnameTxt = (EditText) findViewById(R.id.textBoxFirstName);
        lnameTxt = (EditText) findViewById(R.id.textBoxLastName);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        category = (Spinner) findViewById(R.id.spinnerTopics);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        category.setAdapter(adapter);

        questionNumber = (Spinner) findViewById(R.id.spinnerQuestions);
        ArrayAdapter<String> adapterQ = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, numberOfQuestions);
        questionNumber.setAdapter(adapterQ);

        submit = (Button) findViewById(R.id.buttonsubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fnameTxt.getText().toString().isEmpty() || lnameTxt.getText().toString().isEmpty()){
                    submit.setEnabled(false);
                    Snackbar.make(findViewById(android.R.id.content), R.string.snackbar_main, Snackbar.LENGTH_INDEFINITE )
                            .setAction(R.string.Ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    submit.setEnabled(true);
                                }
                            })
                            .show();
                }
                else{
                    Intent intent = new Intent(new Intent(getApplicationContext(), StartActivity.class));
                    intent.putExtra("fname", fnameTxt.getText().toString());
                    intent.putExtra("lname", lnameTxt.getText().toString());
                    intent.putExtra("category", category.getSelectedItem().toString());
                    intent.putExtra("numberOfQuestions", Integer.parseInt(questionNumber.getSelectedItem().toString()));
                    startActivity(intent);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(android.R.id.content), R.string.fab_text, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.Ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        fnameTxt.setText(shared.getString("firstName", null));
        lnameTxt.setText(shared.getString("lastName",null));
        category.setSelection(shared.getInt("categoryIndex", 0));
        questionNumber.setSelection(shared.getInt("questionNumberIndex", 0));

    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = shared.edit();
        String firstName = fnameTxt.getText().toString();
        String lastName = lnameTxt.getText().toString();
        int categoryIndex = category.getSelectedItemPosition();
        int questionNumberIndex = questionNumber.getSelectedItemPosition();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putInt("categoryIndex", categoryIndex);
        editor.putInt("questionNumberIndex", questionNumberIndex);
        editor.commit();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        boolean ret = true;

        switch(item.getItemId()){
            case R.id.menu_preferences:
                startActivity(new Intent(getApplicationContext(), PreferencesActivity.class));
                break;

            case R.id.menu_addflashcards:
                startActivity(new Intent(getApplicationContext(), AddFlashCardsActivity.class));
                break;

            case R.id.menu_scores:
                startActivity(new Intent(getApplicationContext(), ScoresActivity.class));
                break;

            default:
                ret = super.onOptionsItemSelected(item);
                break;
        }

        return ret;
    }



}