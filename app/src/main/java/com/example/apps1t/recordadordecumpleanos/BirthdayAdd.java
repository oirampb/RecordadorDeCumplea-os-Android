package com.example.apps1t.recordadordecumpleanos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;

public class BirthdayAdd extends Fragment {

    //Declaramos las variables que usaremos
    long date;
    String currentImage, image, name;
    Button buttonImageAdd, calendarButton, addBirthday;
    ImageView imageAdd;
    long miliseconds;
    Calendar mcurrentDate;
    ArrayList<Birthday> birthdaysAll;
    EditText nameET;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_birthday_add, container, false);

        buttonImageAdd = view.findViewById(R.id.buttonImageAdd);
        calendarButton = view.findViewById(R.id.calendarButton);
        addBirthday = view.findViewById(R.id.addBirthday);
        imageAdd = view.findViewById(R.id.imageAdd);
        nameET = view.findViewById(R.id.nameET);


        buttonImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SelectImage.class);
                startActivityForResult(intent, 1);

            }
        });

        //Calendario y añadir fecha
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcurrentDate = Calendar.getInstance();
                int year = mcurrentDate.get(Calendar.YEAR);
                int month = mcurrentDate.get(Calendar.MONTH);
                int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {

                        //Poner la fecha elegida en el texto del boton
                        calendarButton.setText(selectedDay + "/" + (1 +selectedMonth) + "/" + selectedYear);

                        //Tiempo en milisegundos
                        Date date = mcurrentDate.getTime();
                        miliseconds = date.getTime();
                        Log.d("*****", String.valueOf(miliseconds));

                    }
                }, year, month, day);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });

        addBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameET.getText().toString();
                MainActivity mainActivity = (MainActivity) getActivity();
                if(name.isEmpty()|| currentImage.isEmpty() || miliseconds<0){
                    return;
                }
                mainActivity.addNewBirthday(name, currentImage, miliseconds);
                mainActivity.changeScreen(1);
            }
        });

        return view;
    }


    //Recibe el string mediante un intent de la otra pantalla
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                currentImage = data.getStringExtra("Image");
                Log.d("***Resukt", currentImage);
                if(currentImage != null) {
                    //Pone la imagen seleccionada en el ImageView
                    Picasso.get().load(currentImage).into(imageAdd);
                }else{
                    Toast.makeText(getContext(), "No se ha añadido ninguna foto", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}


