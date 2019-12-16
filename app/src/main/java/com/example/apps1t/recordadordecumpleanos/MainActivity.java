package com.example.apps1t.recordadordecumpleanos;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    //Declaramos las variables que usaremos
    String name, image;
    Long date;
    ListView menu;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    BirthDayAPI birthDayAPI;
    BirthDayAPI piAPI;
    ArrayList<Birthday> birthdaysAll;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencias de los elementos
        menu = findViewById(R.id.menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        //Selecciona los elementos del menu
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeScreen(position);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.abrir_menu, R.string.cerrar_menu);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        changeScreen(0);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://tonterias.herokuapp.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        birthDayAPI = retrofit.create(BirthDayAPI.class);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.4.163/today.php/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        piAPI = retrofit.create(BirthDayAPI.class);

        getAllBirthdays();

    }

    void changeScreen(int screen) {
        Fragment fragment = null;
        FragmentManager manager = getSupportFragmentManager();

        switch (screen) {
            case 0:
                fragment = new BirthdayAdd();
                break;
            case 1:
                if(birthdaysAll!=null) {
                    fragment = new AllBirthday();
                    recieveArrayList(birthdaysAll, (AllBirthday) fragment);
                    pi();
                }
                break;
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
        drawerLayout.closeDrawer(Gravity.START);
    }

    // Permite desplegar el menu lateral
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    // Permite cerrar el menu lateral
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewBirthday(String name, String image, long date) {
        Call<String> call = birthDayAPI.postBirthday(name, image, date);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                response.body();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getAllBirthdays() {
        Call<ArrayList<Birthday>> call = birthDayAPI.getBirthdays();

        call.enqueue(new Callback<ArrayList<Birthday>>() {
            @Override
            public void onResponse(Call<ArrayList<Birthday>> call, Response<ArrayList<Birthday>> response) {
                ArrayList<Birthday> birthdays = response.body();
                sendArrayList(birthdays);
            }

            @Override
            public void onFailure(Call<ArrayList<Birthday>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendArrayList(ArrayList<Birthday> birthdays){
        birthdaysAll = birthdays;
    }

    public void recieveArrayList(ArrayList<Birthday> birthdays, AllBirthday fragment){
        fragment.sendArrayList(birthdaysAll);
    }
    public void pi(){

        Call<String> call = piAPI.pi();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

