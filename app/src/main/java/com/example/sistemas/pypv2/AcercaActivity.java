package com.example.sistemas.pypv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AcercaActivity extends AppCompatActivity {

    Button salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);
        salir=(Button)findViewById(R.id.sali);
    }

    public void regresar (View view){
        finish();
    }

}
