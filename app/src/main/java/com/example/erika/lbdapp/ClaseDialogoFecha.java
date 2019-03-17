package com.example.erika.lbdapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class ClaseDialogoFecha extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Calendar calendario=Calendar.getInstance();
        int anyo=calendario.get(Calendar.YEAR);
        int mes=calendario.get(Calendar.MONTH);
        int dia=calendario.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog eldialogo= new DatePickerDialog(getActivity(),this, anyo,mes,dia);
        return eldialogo;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView fech = getActivity().findViewById(R.id.fechaeleg);
        // se coje la fecha en un string y se cambia la fecha con un settext
        String stringOfDate = dayOfMonth + "/" + month + "/" + year;
        fech.setText(stringOfDate);

    }
}
