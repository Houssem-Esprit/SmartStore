package com.p2j.MohamedApp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.p2j.MohamedApp.R;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Bluetooth extends AppCompatActivity {
    BluetoothSocket clientSocket;
    TextView Bluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        String action ;
        action = BluetoothDevice.ACTION_ACL_DISCONNECTED;
        Bluetooth=findViewById(R.id.bluetoothName);
        String enableBT = BluetoothAdapter.ACTION_REQUEST_ENABLE;
        startActivityForResult(new Intent(enableBT), 0);
        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        try {
            BluetoothDevice device = bluetooth.getRemoteDevice("18:D2:76:95:1C:F4");
            if (device.ACTION_ACL_DISCONNECTED.equals(action)){
                bluetooth.startDiscovery();
            }
            Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
            clientSocket = (BluetoothSocket) m.invoke(device, 1);
            Bluetooth.setText(device.getName());
            Log.d("BLUETOOTH_Name",device.getName() );
            bluetooth.cancelDiscovery();
            clientSocket.connect();

        } catch (IOException e) {
            // Unable to connect; close the socket and return.
            try {
                clientSocket.close();
            } catch (IOException closeException) {
                Log.d("BLUETOOTH", "Could not close the client socket", closeException);
            }
            Log.d("BLUETOOTH", e.getMessage());
            return;
        } catch (SecurityException e) {
            Log.d("BLUETOOTH", e.getMessage());
        } catch (NoSuchMethodException e) {
            Log.d("BLUETOOTH", e.getMessage());
        } catch (IllegalArgumentException e) {
            Log.d("BLUETOOTH", e.getMessage());
        } catch (IllegalAccessException e) {
            Log.d("BLUETOOTH", e.getMessage());
        } catch (InvocationTargetException e) {
            //Log.d("BLUETOOTH", e.getMessage());
        }

        Toast.makeText(getApplicationContext(), "CONNECTED", Toast.LENGTH_LONG).show();


    }
}