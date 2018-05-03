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
import tj.teacherjournal.fragments.FragmentNote;
import tj.teacherjournal.fragments.FragmentPerform;
import tj.teacherjournal.fragments.FragmentProfile;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentAttend fAttend;
    FragmentListStud fListStud;
    FragmentNote fNote;
    FragmentPerform fPerform;
    FragmentProfile fProfile; // Настройки

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

        String emailFromIntent = getIntent().getStringExtra("EMAIL");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.m_hello_user);
        nav_user.setText("Привет, " + emailFromIntent);

        navigationView.setNavigationItemSelectedListener(this);

        fAttend = new FragmentAttend();
        fListStud = new FragmentListStud();
        fNote = new FragmentNote();
        fPerform = new FragmentPerform();
        fProfile = new FragmentProfile();

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
        if (id == R.id.list_stud) {
            fragmentTransaction.replace(R.id.container, fListStud);
        } else if (id == R.id.perform) { // Действие с галлерей
            fragmentTransaction.replace(R.id.container, fPerform);
        } else if (id == R.id.attend) { // Действие с слайдером
            fragmentTransaction.replace(R.id.container, fAttend);
        } else if (id == R.id.note) { // Действие с пагинацией
            fragmentTransaction.replace(R.id.container, fNote);
        } fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
