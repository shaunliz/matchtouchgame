package com.brownrice.matchtouch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreDBHelper extends SQLiteOpenHelper
{

    
    public ScoreDBHelper(Context context)
    {
        super(context, "score.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
     // :: table 
        db.execSQL("CREATE TABLE score(_id INTEGER PRIMARY KEY AUTOINCREMENT, stage TEXT, score TEXT, time TEXT, combo TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS score");
        onCreate(db);
    }
}
