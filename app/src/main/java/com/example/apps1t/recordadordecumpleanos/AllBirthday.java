package com.example.apps1t.recordadordecumpleanos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllBirthday extends Fragment {

    //Declaramos las variables que usaremos
    ListView ListAllBirthdays;
    AdapterBirthdays adapterBirthdays;
    ArrayList<Birthday> birthdaysAll;       // Array general de todos los cumpleaños
    ArrayList<Birthday> nextBirthdays;      // Array de los cumpleaños restantes del año
    ArrayList<Birthday> nextBirthday;       // Array final del proximo cumpleaños
    ArrayList<Long> LongArrayList;          // Array del dia de los cumpleaños

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_all_birthday, container, false);
        ListAllBirthdays = view.findViewById(R.id.ListAllBirthdays);

        Calendar cal = Calendar.getInstance();
        long actualDate = cal.getTimeInMillis();
        nextBirthday = new ArrayList<>();
        nextBirthdays = new ArrayList<>();
        LongArrayList = new ArrayList<>();
        if(birthdaysAll!=null) {        // Comprobamos que el Array de todos los cumpleaños no este vacio
            for (int i = 0; i < birthdaysAll.size(); i++) {     //Realizamos un for para recorer el Array de todos los cumpleaños
                if (birthdaysAll.get(i).date >= (actualDate- 16400000)) {   //Comprobamos que el cumpleaños si es mayor que el dia actual menos 2.4 horas
                    nextBirthdays.add(birthdaysAll.get(i));     // Añadimos si es un cumpleaños proximo se añade al array
                    LongArrayList.add(birthdaysAll.get(i).date);        // Se añade el dia en milisegundos
                }
            }

            Collections.sort(LongArrayList);        // Ordenamos el Array de dias
            for(int i =0; i<nextBirthdays.size(); i++){     // Recorremos el Array de los compleaños que quedan
                if(!nextBirthdays.get(i).image.isEmpty()||!nextBirthdays.get(i).name.isEmpty()){        // Comprobamos que ningun dato que se vaya a mostrar este vacio para que no entre en el Arraylist
                    if(nextBirthdays.get(i).date==LongArrayList.get(0)){        // Comprobamos cual es el cumpleaños proximo con el Array de los dias
                        nextBirthday.add(nextBirthdays.get(i));     // Añadimos al Array final del cumpleaños
                        break;
                    }
                }
            }
        }

        adapterBirthdays = new AdapterBirthdays(this.getContext(), R.layout.birthdaylist, nextBirthday);        // Pasamos el Array del cumpleaños al adapter para que se muestre
        ListAllBirthdays.setAdapter(adapterBirthdays);
        return view;
    }

    public void sendArrayList(ArrayList<Birthday> birthdays) {
        birthdaysAll = birthdays;
    }
}
