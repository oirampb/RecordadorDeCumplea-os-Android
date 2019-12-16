package com.example.apps1t.recordadordecumpleanos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterBirthdays extends ArrayAdapter {

    //Declaramos las variables que usaremos
    Context context;
    int _item_layout;
    ArrayList<Birthday> birthdays;
    TextView birthdayName, birthdayDate;
    ImageView birthdayImage;


    public AdapterBirthdays(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);

        this.context = context;
        this._item_layout = resource;
        this.birthdays = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(_item_layout, parent, false);
        }
        //Buscamos las variables en el layaut y le damos el valor

        birthdayName = convertView.findViewById(R.id.birthdayName);
        birthdayName.setText(birthdays.get(position).name);
        birthdayDate = convertView.findViewById(R.id.birthdayDate);
        long date = birthdays.get(position).date;
        DateFormat simple = new SimpleDateFormat("dd/MMM/yyyy");
        Date result = new Date(date);
        birthdayDate.setText(simple.format(result));
        birthdayImage = convertView.findViewById(R.id.birthdayImage);
        Picasso.get().load(birthdays.get(position).image).into(birthdayImage);

        return convertView;
    }
}
