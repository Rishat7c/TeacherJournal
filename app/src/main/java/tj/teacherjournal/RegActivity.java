package tj.teacherjournal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegActivity extends AppCompatActivity implements View.OnClickListener{

    Button b_reg_newbie, b_back_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        b_reg_newbie = (Button) findViewById(R.id.b_reg_newbie);
        b_reg_newbie.setOnClickListener(this);

        b_back_log = (Button) findViewById(R.id.b_back_log); // Добавить на лайоут
        b_back_log.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.b_reg_newbie:
                // Проверка на заполнение всех четырех полей
                // Отправка запроса на создание нового пользователя
                System.out.println("Used b_reg_newbie");
                break;

            case R.id.b_back_log:
                // Возвращение обратно на страницу авторизации
                Intent intent_1;
                intent_1 = new Intent(RegActivity.this, AuthActivity.class);
                startActivity(intent_1);
                System.out.println("Used b_back_log");
                break;

        }

    }
}
