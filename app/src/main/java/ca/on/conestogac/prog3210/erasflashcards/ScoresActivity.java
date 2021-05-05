package ca.on.conestogac.prog3210.erasflashcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ScoresActivity extends AppCompatActivity {
    private SharedPreferences shared;
    private boolean darkTheme;
    GradesListAdapter adapter;
    String[] categories = new String[]{"All", "English", "History", "Math", "Science"};
    Spinner category;
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
        setContentView(R.layout.activity_scores);

        final FlashcardsApplication application;
        application =((FlashcardsApplication) getApplication());

        category = (Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<String> spinAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        category.setAdapter(spinAdapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Cursor cs = application.getScore(category.getSelectedItem().toString());
                List<Score> glist = new ArrayList<>();

                if(cs.moveToFirst()){
                    do{
                        Score x = new Score(cs.getInt(cs.getColumnIndex("g_id")),
                                cs.getString(cs.getColumnIndex("fname")),
                                cs.getString(cs.getColumnIndex("lname")),
                                cs.getInt(cs.getColumnIndex("grade")),
                                cs.getString(cs.getColumnIndex("category")),
                                cs.getInt(cs.getColumnIndex("timestamp")));
                        glist.add(x);
                    }while(cs.moveToNext());
                }
                cs.close();

                RecyclerView recyclerView = findViewById(R.id.recycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new GradesListAdapter(glist, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}