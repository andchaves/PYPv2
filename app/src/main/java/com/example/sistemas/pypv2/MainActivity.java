package com.example.sistemas.pypv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String[] PYP = {"4 - 5", "6 - 7", "8 - 9", "0 - 1", "2 - 3"};
    private static final Integer[] FESTIVOS = {1, 9, 79, 103, 104, 121, 149, 170, 177, 184, 201, 219, 233, 289, 310, 317, 342, 359};

    TextView tv1, tv_placa, tv_proximo_dia;
    Button acercade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String picoYplacaHoy;
        tv1 = (TextView) findViewById(R.id.tv1);
        tv_placa = (TextView) findViewById(R.id.tv_placa);
        tv_proximo_dia = (TextView) findViewById(R.id.tv_proximo_dia);
        acercade = (Button) findViewById(R.id.acercade);

        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        tv_placa.setText(prefe.getString("placa", "0-1"));

        Calendar localCalendar = Calendar.getInstance();
        int diaAnio = localCalendar.get(Calendar.DAY_OF_YEAR);
        int diaSemana = localCalendar.get(Calendar.DAY_OF_WEEK);
        if (Arrays.asList(FESTIVOS).contains(diaAnio) || diaSemana == 1 || diaSemana == 7) {// si es festivo o sabado o domingo
            picoYplacaHoy = "NO APLICA";
        } else {
            int j = diaAnio;
            while (j > 7) {
                j = localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ? j - 11 : j - 6;//si es viernes resta 11 y si es otro dia resta 6
                localCalendar.set(Calendar.DAY_OF_YEAR, j);// cambia la fecha a la fecha restada
            }
            picoYplacaHoy = PYP[localCalendar.get(Calendar.DAY_OF_YEAR) - 2];//se resta dos porque lunes comenzo el dia 2
        }
        tv1.setText(" " + picoYplacaHoy + " ");
        localCalendar.set(Calendar.DAY_OF_YEAR, diaAnio);//vuelve al dia actual
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        tv_placa.setText(prefe.getString("placa", "0-1"));
        Date fecha = new Date();
        Calendar localCalendar = Calendar.getInstance();
        int diaAnio = localCalendar.get(Calendar.DAY_OF_YEAR);

        boolean encontrado = false;
        int posicion = 2;

        for (int i = 0; i < PYP.length && !encontrado; i++) {
            if (tv_placa.getText().equals(PYP[i])) {
                encontrado = true;
                posicion = posicion + i;
            }
        }
        while (posicion <= diaAnio) {
            localCalendar.set(Calendar.DAY_OF_YEAR, posicion);
            posicion=localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY ? posicion + 11 : posicion + 6;
            localCalendar.set(Calendar.DAY_OF_YEAR, posicion);
            if (Arrays.asList(FESTIVOS).contains(posicion)){
                posicion=localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY ? posicion + 11 : posicion + 6;
                localCalendar.set(Calendar.DAY_OF_YEAR, posicion);
            }
        }

        fecha = localCalendar.getTime();
        SimpleDateFormat fech = new SimpleDateFormat("EEEE, d' de 'MMMM",new Locale("es"));
        String fech1 = fech.format(fecha);
        tv_proximo_dia.setText("En " + (posicion - diaAnio) + " dias, " + fech1);
    }

    public void configuracion(View v)
    {
        Intent i = new Intent(this, ConfigActivity.class );
        startActivity(i);
    }
    public void acercade(View v)
    {
        Intent i = new Intent(this, AcercaActivity.class);
        startActivity(i);
    }

}
