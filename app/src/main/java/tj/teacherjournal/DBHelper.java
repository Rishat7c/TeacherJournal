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

    /**
     * Таблица с аккаунтами
     */
    public static final String TABLE_ACCOUNT = "account"; // аккаунты
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_MAIL = "email";
    public static final String KEY_PASS = "pass";
    public static final String KEY_GROUP = "groupid";

    /**
     * Таблица с студентами
     */
    public static final String TABLE_STUDENT = "student"; // список студентов
    public static final String STUD_ID = "_id";
    public static final String STUD_NAME = "name";
    public static final String STUD_GENDER = "gender"; // Пол ?
    public static final String STUD_AGE = "age";
    public static final String STUD_GROUP = "groupid"; // TODO: Привязка с таблицой из аккаунтов
    public static final String STUD_REGISTRATION = "registration"; // Прописка
    public static final String STUD_NUMBER = "studnumber"; // Номер студенческого билета
    public static final String STUD_PHONE = "phone"; // Номер студенческого билета

    /**
     *  Таблица с предметами
     */
    public static final String TABLE_SUBJECT = "subject"; // Предметы
    public static final String SUBJECT_ID = "_id";
    public static final String SUBJECT_NAME = "name";
    public static final String SUBJECT_TEACHER = "teacher"; // Педагог

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //      db.execSQL("create table " + TABLE_ACCOUNT + "(" + KEY_ID + " integer primary key," + KEY_NAME + " text," + KEY_MAIL + " text" + ")");

        db.execSQL("create table " + TABLE_ACCOUNT + "(" + KEY_ID + " integer primary key," + KEY_NAME + " text," + KEY_MAIL + " text," + KEY_PASS + " text," + KEY_GROUP + " text" + ")");

        db.execSQL("create table " + TABLE_STUDENT + "(" + STUD_ID + " integer primary key," + STUD_NAME + " text," +
                STUD_GENDER + " text," + STUD_AGE + " text," + STUD_GROUP + " text," +
                STUD_REGISTRATION + " text," + STUD_NUMBER + " text," + STUD_PHONE + " text" + ")");

        db.execSQL("create table " + TABLE_SUBJECT + "(" + SUBJECT_ID + " integer primary key," + SUBJECT_NAME + " text," + SUBJECT_TEACHER + " text" +")");

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
     * Этот метод заключается в создании записи студентов
     *
     * @param student
     */
    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBHelper.STUD_NAME, student.getName());
        values.put(DBHelper.STUD_GENDER, student.getGender());
        values.put(DBHelper.STUD_AGE, student.getAge());
        values.put(DBHelper.STUD_GROUP, student.getGroupid());
        values.put(DBHelper.STUD_REGISTRATION, student.getRegistration());
        values.put(DBHelper.STUD_NUMBER, student.getStudnumber());
        values.put(DBHelper.STUD_PHONE, student.getStudnumber());

        // Добавление в БД
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    /**
     * Этот метод заключается в создании записи предмета
     *
     * @param subject
     */
    public void addSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBHelper.SUBJECT_NAME, subject.getName());
        values.put(DBHelper.SUBJECT_TEACHER, subject.getTeacher());
        // Добавление в БД
        db.insert(TABLE_SUBJECT, null, values);
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
     * Этот метод предназначен для извлечения всех студентов и возврата списка записей студентов
     *
     * @return list
     */
    public List<Student> getAllStudent() {
        // массив столбцов для извлечения
        String[] columns = {
                STUD_ID,
                STUD_NAME,
                STUD_GENDER,
                STUD_AGE,
                STUD_GROUP,
                STUD_REGISTRATION,
                STUD_NUMBER,
                STUD_PHONE
        };
        // сортировка
        String sortOrder =
                STUD_NAME + " ASC";
        List<Student> studentList = new ArrayList<Student>();

        SQLiteDatabase db = this.getReadableDatabase();

        // запросить таблицу пользователя
        /**
         * Здесь функция запроса используется для извлечения записей из таблицы студентов, эта функция работает так, как мы используем sql-запрос.
         * SQL-запрос, эквивалентный этой функции запроса,
         * SELECT stud_id, stud_name, ..., ... FROM student ORDER BY stud_name;
         */
        Cursor cursor = db.query(TABLE_STUDENT, // Таблица для запроса
                columns,    // столбцы для возврата
                null,        // столбцы для предложения WHERE
                null,        // значения для предложения WHERE
                null,       // группировать строки
                null,       // группировать по группам строк
                sortOrder); // порядок сортировки


        // Перемещение по всем строкам и добавление в список
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(STUD_ID))));
                student.setName(cursor.getString(cursor.getColumnIndex(STUD_NAME)));
                student.setGender(cursor.getString(cursor.getColumnIndex(STUD_GENDER)));
                student.setAge(cursor.getString(cursor.getColumnIndex(STUD_AGE)));
                student.setGroupid(cursor.getString(cursor.getColumnIndex(STUD_GROUP)));
                student.setRegistration(cursor.getString(cursor.getColumnIndex(STUD_REGISTRATION)));
                student.setStudnumber(cursor.getString(cursor.getColumnIndex(STUD_NUMBER)));
                student.setPhone(cursor.getString(cursor.getColumnIndex(STUD_PHONE)));
                // Добавление записи пользователя в список
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // список возвращаемых пользователей
        return studentList;
    }

    /**
     * Этот метод предназначен для извлечения всех предметов и возврата списка записей предметов
     *
     * @return list
     */
    public List<Subject> getAllSubject() {
        // массив столбцов для извлечения
        String[] columns = {
                SUBJECT_ID,
                SUBJECT_NAME,
                SUBJECT_TEACHER
        };
        // сортировка
        String sortOrder =
                SUBJECT_NAME + " ASC";
        List<Subject> subjectList = new ArrayList<Subject>();

        SQLiteDatabase db = this.getReadableDatabase();

        // запросить таблицу пользователя
        /**
         * Здесь функция запроса используется для извлечения записей из таблицы предметов, эта функция работает так, как мы используем sql-запрос.
         * SQL-запрос, эквивалентный этой функции запроса,
         * SELECT subject_id, subject_name, subject_teacher FROM account ORDER BY subject_name;
         */
        Cursor cursor = db.query(TABLE_SUBJECT, // Таблица для запроса
                columns,    // столбцы для возврата
                null,        // столбцы для предложения WHERE
                null,        // значения для предложения WHERE
                null,       // группировать строки
                null,       // группировать по группам строк
                sortOrder); // порядок сортировки


        // Перемещение по всем строкам и добавление в список
        if (cursor.moveToFirst()) {
            do {
                Subject subject = new Subject();
                subject.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SUBJECT_ID))));
                subject.setName(cursor.getString(cursor.getColumnIndex(SUBJECT_NAME)));
                subject.setTeacher(cursor.getString(cursor.getColumnIndex(SUBJECT_TEACHER)));
                // Добавление записи пользователя в список
                subjectList.add(subject);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // список возвращаемых пользователей
        return subjectList;
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
     * Этот метод предназначен для удаления записи студентов
     *
     * @param i
     */
//    public void deleteStudent(Student student) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        // удалить запись пользователя по идентификатору
//        db.delete(TABLE_STUDENT, STUD_ID + " = ?",
//                new String[]{String.valueOf(student.getId())});
//        db.close();
//    }
    public void deleteStudent(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        // удалить запись пользователя по идентификатору
        db.delete(TABLE_STUDENT, STUD_ID + " = " + i, null);
        db.close();
    }

    /**
     * Этот метод предназначен для удаления записи предмета
     *
     * @param subject
     */
    public void deleteSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        // удалить запись пользователя по идентификатору
        db.delete(TABLE_SUBJECT, SUBJECT_ID + " = ?",
                new String[]{String.valueOf(subject.getId())});
        db.close();
    }

    /**
     * Вытаскиваем инфу о студенте
     *
     * @param id
     * @return true/false
     */
    public Cursor getByIdStudent(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_STUDENT, null, "_id = " + id, null, null, null, null);
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
