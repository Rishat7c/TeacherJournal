package tj.teacherjournal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AuthActivity.this;

    private EditText l_email, l_pass;
    private DBHelper dbHelper;
    private Button b_reg, b_log;

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
                // Тут всякая дичь на проверку данных с полей и запрос в БД
                CheckDataInBd();
                break;
        }

    }

    /**
     * Этот метод предназначен для проверки входных текстовых полей и проверки учетных данных для входа в SQLite
     */
    private void CheckDataInBd() {

        String email = l_email.getText().toString();
        String pass = l_pass.getText().toString();

        if(email.length() != 0 && pass.length() != 0) {

            if (dbHelper.checkUser(email.trim(), pass.trim())) {

                Intent accountsIntent = new Intent(activity, MainActivity.class);
                accountsIntent.putExtra("EMAIL", l_email.getText().toString().trim());
                startActivity(accountsIntent);

            } else {
                // Выкидываем ему тостер о неправильно введеных данных
                Toast.makeText(this, "Неправильный логин или пароль!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Одно или несколько полей не заполнены!", Toast.LENGTH_SHORT).show();
        }
    }

}
