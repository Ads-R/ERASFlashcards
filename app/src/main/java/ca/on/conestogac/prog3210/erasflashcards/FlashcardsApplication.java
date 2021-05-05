package ca.on.conestogac.prog3210.erasflashcards;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FlashcardsApplication extends Application {

    private static final String DB_NAME = "db_flashcards";
    private static final int DB_VERSION = 22;

    private SQLiteOpenHelper helper;

    String[][] dbStrings = new String[][] {
            {"Who is the first man in to reach space", "History", "Yuri Gagarin", "Neil Armstrong", "Buzz Lightyear", "Anakin Skywalker"},
            {"Who is the first U.S. president", "History", "George Washington", "James Madison", "Ronald Reagan", "Bill Clinton"},
            {"When was the first Nobel Prize in economics awarded", "History", "1969", "1909", "1929", "1949"},
            {"Who was the first man to walk on the moon", "History", "Neil Armstrong", "Yuri Gagarin", "Elon Musk", "Bill Gates"},
            {"By what nickname was Edward Teach also known", "History", "Blackbeard", "Whitebeard", "Francis Drake", "Monkey D Luffy"},
            {"Who is the first black American to be elected to the US House of Representatives", "History", "Hiram Revels", "James Madison", "Joseph H. Ralney", "Barack Obama"},
            {"The founder of Chicago", "History", "Jean Baptiste DuSable", "Estevanico", "Joseph H. Ralney", "Oliver Lewis"},
            {"Capital of Canada", "History", "Ottawa", "Toronto", "Vancouver", "Montreal"},
            {"Remembrance Day in Canada falls on November 11. November 11 was the last day of which war?", "History", "World War 1", "World War 2", "Korean War", "Vietnam War"},
            {"The members of which ethnic group were once forced to pay a head tax to immigrate to Canada?", "History", "Chinese", "Whites", "Blacks", "Aboriginals"},

            {"What is 7 multiplied by itself", "Math", "49", "63", "38", "56"},
            {"What is the sum of 5 and 5", "Math", "10", "15", "5", "0"},
            {"What is 6 divided by itself", "Math", "1", "0", "2", "1.5"},
            {"What is half of 28", "Math", "14", "13", "15", "18"},
            {"What is the difference between 63 and 13", "Math", "50", "40", "45", "46"},
            {"An algebraic expression that contains three terms is called?", "Math", "Trinomial", "Monomial", "Binomial", "None of these"},
            {"A die is rolled. What is the probability of getting an even number?", "Math", "1/2", "1/6", "1/3", "1/4"},
            {"All the real numbers have reciprocal except", "Math", "0", "1", "2", "3"},
            {"Single term algebraic expression is called?", "Math", "Monomial", "Binomial", "Trinomial", "None of these"},
            {"In a race , Ram covers 5 km in 20 min. How much distance will he cover in 100 min ?", "Math", "25 km", "26 km", "35 km", "40 km"},

            {"When did the Space Age began?", "Science", "1957", "1937", "1997", "1947"},
            {"What is the visible part of the Sun called?", "Science", "Photosphere", "Solar", "Chromosphere", "Radiative zone"},
            {"Approximately how many miles are there in a light year", "Science", "5.9 trillion", "1.9 trillion", "2.5 trillion", "3.4 trillion"},
            {"What two motions do all the planets have", "Science", "Orbit and spin", "Oscillating", "Linear", "Rotary"},
            {"Who invented the World Wide Web", "Science", "Tim Berners-Lee", "A laboratory in Ireland", "A laboratory in U.S.A,", "A laboratory in Canada"},
            {"Who is considered the father of the scientific method", "Science", "Galileo Galilei", "James Hank", "Rueprto Khokha", "Lawrence Adroi"},
            {"How much of the Earth is covered with water", "Science", "71 percent", "61 percent", "51 percent", "81 percent"},
            {"What is the average surface temperrature of the Earth between 1951-1980", "Science", "14 C", "13 C", "12 C", "11 C"},
            {"How old is sunglight by the time it reaches Earth", "Science", "8 minutes", "4 minutes", "7 minutes", "9 minutes"},
            {"What planet is closest to Saturn", "Science", "Jupiter", "Pluto", "Venus", "Mars"},

            {"What is always coming but never arrives", "English", "Tomorrow", "Yesterday", "Afternoon", "Evening"},
            {"What can be broken but is never held", "English", "Promise", "Trust", "Love", "Feelings"},
            {"What is it that lives if it is fed and dies if you give it a drink", "English", "Fire", "Water", "Animals", "Humans"},
            {"What can one catch that is not thrown", "English", "Ice", "Cold", "Rain", "Wind"},
            {"Some months have 29 days others have 30 days but how many have 31 days", "English", "7", "5", "2", "3"},
            {"What is it that goes up but never comes down", "English", "Age", "Leaf", "Sun", "Rain"},
            {"What is a verb", "English", "An action word", "A describing word", "A noun", "A pronoun"},
            {"What is an adjective", "English", "A describing word", "An action word", "A synonym", "An antonym"},
            {"What do you call a similar sound for the last syllable of two or more words", "English", "Rhyme", "Nursery", "Adjective", "Noun"},
            {"A subject is the half of what makes a sentence what is the other half", "English", "Predicate", "Noun", "Pronoun", "Phrase"},
    };

    @Override
    public void onCreate() {

        helper = new SQLiteOpenHelper(this, DB_NAME, null, DB_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE IF NOT EXISTS tbl_questions(" +
                        "q_id INTEGER PRIMARY KEY AUTOINCREMENT, q_text TEXT NOT NULL, q_category TEXT NOT NULL)");
                db.execSQL("CREATE TABLE IF NOT EXISTS tbl_choices(" +
                        "a_id INTEGER PRIMARY KEY AUTOINCREMENT, q_id INTEGER, a_text TEXT NOT NULL, a_answer INTEGER NOT NULL)");
                db.execSQL("CREATE TABLE IF NOT EXISTS tbl_grades(" +
                        "g_id INTEGER PRIMARY KEY, fname TEXT NOT NULL, lname TEXT NOT NULL, grade REAL NOT NULL, category TEXT NOT NULL, timestamp INTEGER)");


                Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
                cursor.moveToFirst();
                for(int i = 0; i<dbStrings.length; i++){
                    for(int j = 0; j<dbStrings[i].length; j++){
                        db.execSQL("INSERT INTO tbl_questions(q_text, q_category) " +
                                "VALUES ('"+ dbStrings[i][0] +"', '"+ dbStrings[i][1] +"')");
                        cursor = db.rawQuery("SELECT last_insert_rowid()", null);
                        cursor.moveToFirst();
                        int lastId = cursor.getInt(0);
                        db.execSQL("INSERT INTO tbl_choices(q_id, a_text, a_answer) VALUES("+ lastId +", '"+ dbStrings[i][2] +"', "+ 1 +")");
                        db.execSQL("INSERT INTO tbl_choices(q_id, a_text, a_answer) VALUES("+ lastId +",'"+ dbStrings[i][3] +"', "+ 0 +")");
                        db.execSQL("INSERT INTO tbl_choices(q_id, a_text, a_answer) VALUES("+ lastId +", '"+ dbStrings[i][4] +"', "+ 0 +")");
                        db.execSQL("INSERT INTO tbl_choices(q_id, a_text, a_answer) VALUES("+ lastId +", '"+ dbStrings[i][5] +"', "+ 0 +")");
                        j = 6;
                    }
                }

            }


            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        super.onCreate();
    }

    public void addQuestionsAndChoices(Question question, Choice first, Choice second, Choice third, Choice fourth){
        SQLiteDatabase db = helper.getWritableDatabase();
        /*db.execSQL("INSERT INTO tbl_questions (q_text, q_category) "
                + "VALUES ('One plus one', 'Math')");*/
        db.execSQL("INSERT INTO tbl_questions(q_text, q_category) " +
                "VALUES ('"+ question.getQtext() +"', '"+ question.getCategory() +"')");
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        cursor.moveToFirst();
        int lastId = cursor.getInt(0);
        Log.i("info", lastId+"");

        db.execSQL("INSERT INTO tbl_choices(q_id, a_text, a_answer) VALUES("+ lastId +", '"+ first.getCtext() +"', "+ first.getAnswer() +")");
        db.execSQL("INSERT INTO tbl_choices(q_id, a_text, a_answer) VALUES("+ lastId +", '"+ second.getCtext() +"', "+ second.getAnswer() +")");
        db.execSQL("INSERT INTO tbl_choices(q_id, a_text, a_answer) VALUES("+ lastId +", '"+ third.getCtext() +"', "+ third.getAnswer() +")");
        db.execSQL("INSERT INTO tbl_choices(q_id, a_text, a_answer) VALUES("+ lastId +", '"+ fourth.getCtext() +"', "+ fourth.getAnswer() +")");
    }

    public void addScore(Score score){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_grades(fname, lname, grade, category, timestamp)" +
                " VALUES ('"+ score.getFname() +"', '"+ score.getLname() +"', "+ score.getGrade() +", '"+ score.getCategory() +"', "+ score.getDate() +")");
    }

    public Cursor getScore(String filter){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs;
        if(filter == "All"){
            cs = db.rawQuery("SELECT * FROM tbl_grades", null);
        }
        else{
            cs = db.rawQuery("SELECT * FROM tbl_grades WHERE category = '"+ filter +"'", null);
        }
        if(cs != null){
            cs.moveToFirst();
        }
        return cs;
    }

    public Cursor getQuestion(String category){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs;
        cs = db.rawQuery("SELECT * FROM tbl_questions WHERE q_category = '"+ category +"' ORDER BY RANDOM()", null);
        if(cs != null){
            cs.moveToFirst();
        }
        return cs;
    }

    public Cursor getChoices(int questionId){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs;
        cs = db.rawQuery("SELECT * FROM tbl_choices WHERE q_id = "+ questionId +" ORDER BY RANDOM()", null);
        if(cs != null){
            cs.moveToFirst();
        }
        return cs;
    }



}
