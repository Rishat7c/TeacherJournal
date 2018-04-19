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

public class RegActivity extends AppCompatActivity implements View.OnClickListener{

    private Button b_reg_newbie, b_back_log;
    private EditText r_name, r_email, r_pass, r_group;
    private DBHelper dbHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        b_reg_newbie = (Button) findViewById(R.id.b_reg_newbie);
        b_reg_newbie.setOnClickListener(this);

        b_back_log = (Button) findViewById(R.id.b_back_log); // Добавить на лайоут
        b_back_log.setOnClickListener(this);

        r_name = (EditText) findViewById(R.id.r_name);
        r_email = (EditText) findViewById(R.id.r_email);
        r_pass = (EditText) findViewById(R.id.r_pass);
        r_group = (EditText) findViewById(R.id.r_group);

        dbHelper = new DBHelper(this);

        user = new User();

    }

    @Override
    public void onClick(View view) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String name = r_name.getText().toString();
        String email = r_email.getText().toString();
        String pass = r_pass.getText().toString();
        String group = r_group.getText().toString();

        switch (view.getId()) {

            case R.id.b_reg_newbie:
                // Отправка запроса на создание нового пользователя
                PostDataInBd();
//                System.out.println("Used b_reg_newbie");
                break;

            case R.id.b_back_log:
                // Возвращение обратно на страницу авторизации
                Intent intent_1;
                intent_1 = new Intent(RegActivity.this, AuthActivity.class);
                startActivity(intent_1);
//                System.out.println("Used b_back_log");
                break;

        }

    }

    private void PostDataInBd() {
        String name = r_name.getText().toString();
        String email = r_email.getText().toString();
        String pass = r_pass.getText().toString();
        String group = r_group.getText().toString();

        // Проверка на заполнение всех четырех полей
        if(name.length() != 0 && email.length() != 0 && pass.length() != 0 && group.length() != 0) {
            if (!dbHelper.checkUser(email.trim())) {

                user.setName(name.trim());
                user.setEmail(email.trim());
                user.setPassword(pass.trim());
                user.setGroup(group.trim());

                dbHelper.addUser(user);

                // Снек-бар, чтобы показать сообщение об успешном завершении записи
                Toast.makeText(this, "Вы успешно зарегистрировались!", Toast.LENGTH_SHORT).show();

                Intent intent_2;
                intent_2 = new Intent(RegActivity.this, AuthActivity.class);
                startActivity(intent_2);

            } else {
                // Снек-бар, чтобы показать сообщение об ошибке, запись которого уже существует
                Toast.makeText(this, "Пользователь с таким email адресом уже существует!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Все поля обязательны для заполнения", Toast.LENGTH_SHORT).show();
        }

    }

}
