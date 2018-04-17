package tj.teacherjournal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by risatgajsin on 16.04.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "app";
    public static final String TABLE_ACCOUNT = "account";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_MAIL = "email";
    public static final String KEY_PASS = "pass";
    public static final String KEY_GROUP = "groupid";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_ACCOUNT + "(" + KEY_ID + " integer primary key," + KEY_NAME + " text," + KEY_MAIL + " text," + KEY_PASS + " text," + KEY_GROUP + " text" + ")");
//      db.execSQL("create table " + TABLE_ACCOUNT + "(" + KEY_ID + " integer primary key," + KEY_NAME + " text," + KEY_MAIL + " text" + ")");
//        db.execSQL("create table " + TABLE_ACCOUNT + "(" + KEY_ID + " integer primary key," + KEY_NAME + " text," + KEY_MAIL + " text," + KEY_PASS + " text," + KEY_GROUP + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("drop table if exists " + TABLE_ACCOUNT);

        onCreate(db);
    }
}
