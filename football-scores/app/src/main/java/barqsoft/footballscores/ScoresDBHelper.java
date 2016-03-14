package barqsoft.footballscores;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import barqsoft.footballscores.DatabaseContract.scores_table;

/**
 * Created by yehya khaled on 2/25/2015.
 */
public class ScoresDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "Scores.db";
    private static final int DATABASE_VERSION = 6;
    public ScoresDBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String CreateScoresTable = "CREATE TABLE " + DatabaseContract.SCORES_TABLE + " ("
                + scores_table._ID + " INTEGER PRIMARY KEY,"
                + scores_table.COL_DATE + " TEXT NOT NULL,"
                + scores_table.COL_STATUS + " TEXT NOT NULL,"
                + scores_table.COL_TIME + " INTEGER NOT NULL,"
                + scores_table.COL_HOME_NAME + " TEXT NOT NULL,"
                + scores_table.COL_AWAY_NAME + " TEXT NOT NULL,"
                + scores_table.COL_LEAGUE + " INTEGER NOT NULL,"
                + scores_table.COL_HOME_GOALS + " TEXT NOT NULL,"
                + scores_table.COL_AWAY_GOALS + " TEXT NOT NULL,"
                + scores_table.COL_MATCH_ID + " INTEGER NOT NULL,"
                + scores_table.COL_MATCH_DAY + " INTEGER NOT NULL,"
                + " UNIQUE ("+scores_table.COL_MATCH_ID +") ON CONFLICT REPLACE"
                + " );";
        db.execSQL(CreateScoresTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Remove old values when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.SCORES_TABLE);
    }
}
