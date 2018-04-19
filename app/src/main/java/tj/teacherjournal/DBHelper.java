package tj.teacherjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by risatgajsin on 16.04.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "app.db";
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

    /**
     * Этот метод заключается в создании записи пользователя
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBHelper.KEY_NAME, user.getName());
        values.put(DBHelper.KEY_MAIL, user.getEmail());
        values.put(DBHelper.KEY_PASS, user.getPassword());
        values.put(DBHelper.KEY_GROUP, user.getGroup());

        // Добавление в БД
        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }

    /**
     * Этот метод предназначен для извлечения всех пользователей и возврата списка записей пользователя
     *
     * @return list
     */
    public List<User> getAllUser() {
        // массив столбцов для извлечения
        String[] columns = {
                KEY_ID,
                KEY_NAME,
                KEY_MAIL,
                KEY_PASS,
                KEY_GROUP
        };
        // сортировка
        String sortOrder =
                KEY_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // запросить таблицу пользователя
        /**
         * Здесь функция запроса используется для извлечения записей из таблицы пользователей, эта функция работает так, как мы используем sql-запрос.
         * SQL-запрос, эквивалентный этой функции запроса,
         * SELECT user_id, user_name, user_email, user_password, user_group FROM account ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_ACCOUNT, // Таблица для запроса
                columns,    // столбцы для возврата
                null,        // столбцы для предложения WHERE
                null,        // значения для предложения WHERE
                null,       // группировать строки
                null,       // группировать по группам строк
                sortOrder); // порядок сортировки


        // Перемещение по всем строкам и добавление в список
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(KEY_MAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASS)));
                user.setGroup(cursor.getString(cursor.getColumnIndex(KEY_GROUP)));
                // Добавление записи пользователя в список
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // список возвращаемых пользователей
        return userList;
    }

    /**
     * Этот метод обновления записи пользователя
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_MAIL, user.getEmail());
        values.put(KEY_PASS, user.getPassword());
        values.put(KEY_GROUP, user.getGroup());

        // обновление строки
        db.update(TABLE_ACCOUNT, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * Этот метод предназначен для удаления записи пользователя
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // удалить запись пользователя по идентификатору
        db.delete(TABLE_ACCOUNT, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * Этот метод проверки пользователя существует или нет
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // массив столбцов для извлечения
        String[] columns = {
                KEY_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // критерий выбора
        String selection = KEY_MAIL + " = ?";

        // аргумент выбора
        String[] selectionArgs = {email};

        // таблица запросов пользователя с условием
        /**
         * Здесь функция запроса используется для извлечения записей из таблицы пользователей, эта функция работает так, как мы используем sql-запрос.
         * SQL-запрос, эквивалентный этой функции запроса,
         * SELECT user_id FROM user WHERE user_email = 'xpan96@gmail.com';
         */
        Cursor cursor = db.query(TABLE_ACCOUNT, // таблица для запроса
                columns,                    // столбцы для возврата
                selection,                  // столбцы для предложения WHERE
                selectionArgs,              // значения для предложения WHERE
                null,                       // группировать строки
                null,                      // группировать по группам строк
                null);                      // порядок сортировки
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * Этот метод проверки пользователя существует или нет
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // массив столбцов для извлечения
        String[] columns = {
                KEY_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // критерии выбора
        String selection = KEY_MAIL + " = ?" + " AND " + KEY_PASS + " = ?";

        // аргумент выбора
        String[] selectionArgs = {email, password};

        // таблица запросов пользователя
        /**
         * Здесь функция запроса используется для извлечения записей из таблицы пользователей, эта функция работает так, как мы используем sql-запрос.
         * SQL-запрос, эквивалентный этой функции запроса,
         * SELECT user_id FROM user WHERE user_email = 'xpan96@gmail.com' И user_password = '123321';
         */
        Cursor cursor = db.query(TABLE_ACCOUNT, // таблица для запроса
                columns,                    // столбцы для возврата
                selection,                  // столбцы для предложения WHERE
                selectionArgs,              // значения для предложения WHERE
                null,                       // группировать строки
                null,                       // группировать по группам строк
                null);                      // порядок сортировки

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

}
