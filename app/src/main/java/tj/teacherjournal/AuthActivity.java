package tj.teacherjournal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    EditText l_email, l_pass;

    DBHelper dbHelper;

    Button b_reg, b_log;

    public int arResult[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        b_reg = (Button) findViewById(R.id.b_reg);
        b_reg.setOnClickListener(this);

        b_log = (Button) findViewById(R.id.b_log);
        b_log.setOnClickListener(this);

        l_email = (EditText) findViewById(R.id.l_email);
        l_pass = (EditText) findViewById(R.id.l_pass);

//        btnAdd = (Button) findViewById(R.id.btnAdd);
//        btnAdd.setOnClickListener(this);
//
//        btnRead = (Button) findViewById(R.id.btnRead);
//        btnRead.setOnClickListener(this);
//
//        btnClear = (Button) findViewById(R.id.btnClear);
//        btnClear.setOnClickListener(this);
//
//        etName = (EditText) findViewById(R.id.etName);
//        etEmail = (EditText) findViewById(R.id.etEmail);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {

        String email = l_email.getText().toString();
        String pass = l_pass.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        arResult = new int[50];

        switch (view.getId()) {

            case R.id.b_reg:
                Intent intent_0;
                intent_0 = new Intent(AuthActivity.this, RegActivity.class);
                startActivity(intent_0);
                break;

            case R.id.b_log:
                // Тут всякая дичь на проверка данных с полей и запрос в БД
                Cursor cursor = database.query(DBHelper.TABLE_ACCOUNT, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                    int passIndex = cursor.getColumnIndex(DBHelper.KEY_PASS);
                    int groupIndex = cursor.getColumnIndex(DBHelper.KEY_GROUP);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", email = " + cursor.getString(emailIndex) +
                                ", pass = " + cursor.getString(passIndex) +
                                ", group = " + cursor.getString(groupIndex));

                    } while (cursor.moveToNext());
                } else {
                    Log.d("mLog", "0 rows");
                }

                cursor.close();

                break;

//            case R.id.btnAdd:
//                contentValues.put(DBHelper.KEY_NAME, name);
//                contentValues.put(DBHelper.KEY_MAIL, email);
//
//                database.insert(DBHelper.TABLE_ACCOUNT, null, contentValues);
//                break;
//
//            case R.id.btnRead:
//                Cursor cursor = database.query(DBHelper.TABLE_ACCOUNT, null, null, null, null, null, null);
//
//                if (cursor.moveToFirst()) {
//                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
//                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
//                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
//                    do {
//                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
//                                ", name = " + cursor.getString(nameIndex) +
//                                ", email = " + cursor.getString(emailIndex));
//                    } while (cursor.moveToNext());
//                } else
//                    Log.d("mLog","0 rows");
//
//                cursor.close();
//                break;
//
//            case R.id.btnClear:
//                database.delete(DBHelper.TABLE_ACCOUNT, null, null);
//                break;
        }
//        dbHelper.close();

    }

}
