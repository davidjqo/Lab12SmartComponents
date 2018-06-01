package com.example.david.lab12_smartcomponents;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button btnConexion, btnLed1, btnLed2, btnLed3;

    private static final int SOLICITAR_ACTIVACION = 1;
    private static final int SOLICITAR_CONEXION = 2;

    BluetoothAdapter miBluetoothAdapter = null;
    BluetoothDevice miDevice = null;
    BluetoothSocket miSocket = null;

    boolean conexion = false;

    private static String MAC = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConexion = findViewById(R.id.btnConexion);
        btnLed1 = findViewById(R.id.btnLed1);
        btnLed2 = findViewById(R.id.btnLed2);
        btnLed3 = findViewById(R.id.btnLed3);

        miBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (miBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Su dispositivo no posee bluetoth",
                    Toast.LENGTH_LONG).show();
        } else if (!miBluetoothAdapter.isEnabled()) {
            Intent activarBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(activarBluetooth, SOLICITAR_ACTIVACION);
        }

        btnConexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(conexion) {
                    // desconectar
                } else {
                    // conectar
                    Intent abrirLista = new Intent(MainActivity.this, ListaDispositivos.class);
                    startActivityForResult(abrirLista, SOLICITAR_CONEXION);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SOLICITAR_ACTIVACION:
                if(resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "El bluetooth fue activado",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "El bluetooth no fue activado" ,
                            Toast.LENGTH_LONG).show();
                    //finish();
                }
                break;

            case SOLICITAR_CONEXION:
                if(resultCode == Activity.RESULT_OK) {
                    MAC = data.getExtras().getString(ListaDispositivos.DIRECCION_MAC);

                    //Toast.makeText(getApplicationContext(), "MAC: " + MAC, Toast.LENGTH_LONG).show();
                    miDevice = miBluetoothAdapter.getRemoteDevice(MAC);

                    /*try {

                    } catch (IOException error) {

                    }*/
                } else {
                    Toast.makeText(getApplicationContext(), "Falta de obtener MAC", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
