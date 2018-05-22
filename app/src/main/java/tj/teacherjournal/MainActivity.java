package tj.teacherjournal;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import tj.teacherjournal.fragments.FragmentAttend;
import tj.teacherjournal.fragments.FragmentListStud;
import tj.teacherjournal.fragments.FragmentMain;
import tj.teacherjournal.fragments.FragmentSubject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentAttend fAttend;
    FragmentListStud fListStud;
    FragmentSubject fPerform;
    FragmentMain fMain; // Главная страница

    private TextView link_main;
    private TextView nav_user;
    public String emailFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // бургер ?
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close); // Слой за кадром (открытие и закрытие)
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        emailFromIntent = getIntent().getStringExtra("EMAIL");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView =  navigationView.getHeaderView(0);
        nav_user = (TextView)hView.findViewById(R.id.m_hello_user);
        link_main = (TextView)hView.findViewById(R.id.m_app_name);
        nav_user.setText("Привет, " + emailFromIntent);

        navigationView.setNavigationItemSelectedListener(this);

        fAttend = new FragmentAttend();
        fListStud = new FragmentListStud();
        fPerform = new FragmentSubject();
        fMain = new FragmentMain();

        Bundle bundle=new Bundle();
        bundle.putString("EMAIL", emailFromIntent);

        // Добавить загрузочный контент , аля типа главная страница
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fMain.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, fMain);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        link_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("EMAIL", emailFromIntent);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fMain.setArguments(bundle);
                fragmentTransaction.replace(R.id.container, fMain);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Надуть меню; это добавляет элементы в панель действий, если она присутствует.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Нажмите здесь пункт «Действия». Панель действий будет автоматически обрабатывать клики на кнопке Home / Up, так долго как вы указываете родительскую активность в AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Нажатие кнопки навигации
        int id = item.getItemId();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        // Событие с кнопками в меню
        if (id == R.id.list_stud) { // Список студентов
            fragmentTransaction.replace(R.id.container, fListStud);
        } else if (id == R.id.perform) { // Предметы
            fragmentTransaction.replace(R.id.container, fPerform);
        } else if (id == R.id.attend) { // Посещаемость
            fragmentTransaction.replace(R.id.container, fAttend);
        } else if (id == R.id.profile) { // Параметры
            fragmentTransaction.replace(R.id.container, fMain);
        } fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
