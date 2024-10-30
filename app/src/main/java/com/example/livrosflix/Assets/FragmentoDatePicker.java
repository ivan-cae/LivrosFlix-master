package com.example.livrosflix.Assets;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FragmentoDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private final TextView textView;

    public FragmentoDatePicker(TextView textView) {
        this.textView = textView;
    }

    //Sobrescrita do método onCreateDialog, nele são definidos os parâmetros do calendário quando aberto
    @NonNull
    @Override
    public DatePickerDialog onCreateDialog(Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);
        int m = mes;
        mes += 1;

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, dia, mes, ano);

        datePickerDialog.getDatePicker().init(ano, m, dia, null); //System.currentTimeMillis() - 1000);
        return datePickerDialog;
    }


    //Sobrescrita do método onDateSet, nele é setada a data selecionada no respectivo textview
    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, ano);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.DAY_OF_MONTH, dia);
        mes += 1;

        String a = String.valueOf(ano);
        String m = String.valueOf(mes);
        String d = String.valueOf(dia);

        if (mes < 10) m = "0" + m;

        if (dia < 10) d = "0" + d;

        String auxDia = d;
        String auxMes = m;
        String auxAno = String.valueOf(ano);

        String data = (auxDia + "-" + auxMes + "-" + auxAno).trim();
        textView.setText(data);
    }
}

