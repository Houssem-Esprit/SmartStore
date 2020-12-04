package com.p2j.MohamedApp.Utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.dynamicfeatures.Constants;

import java.util.UUID;

public class BluetoothOperation {
    public Activity activity;
    TextView BlutoothName;
    public static  int REQUEST_ENABLE_BT =1;
    public static BluetoothAdapter bluetoothAdapter;
    public static BluetoothSocket mmSocket;
    UUID uuid=UUID.randomUUID();


    public BluetoothOperation(Activity a,TextView Bn){
        this.activity=a;
        this.BlutoothName=Bn;
    }




    public void SettingUpBluetoothOperation(){
         bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            Toast.makeText(activity.getApplicationContext(), "This device doesn't support Bluetooth", Toast.LENGTH_SHORT).show();
        }
        if (!bluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent,REQUEST_ENABLE_BT);
        }
        //**************
        bluetoothAdapter.enable();
        bluetoothAdapter.startDiscovery();
        //***************
        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        activity.startActivity(discoverableIntent);
        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        activity.registerReceiver(receiver, filter);
        Log.d("receiver","receiver : "+receiver.getResultData());

    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String Mac = device.getAddress();
                Log.d("Adress","adress :"+Mac);
               // BlutoothName.setText(deviceName);

                if (device.getAddress().equals("18:d2:76:95:19:0b")) {
                    startCommunication(device);
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {


            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                // Your bluetooth device is connected to the Android bluetooth

            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                // Your device is disconnected. Try connecting again via startDiscovery
                bluetoothAdapter.startDiscovery();
            }



        }

    };

    void startCommunication(BluetoothDevice device) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ConnectThread mConnectThread = new ConnectThread(device);
            mConnectThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            ConnectThread mConnectThread = new ConnectThread(device);
            mConnectThread.execute((Void) null);
        }
    }



    public  class ConnectThread extends AsyncTask<Void, Void, String> {

        private BluetoothDevice btDevice;




        public ConnectThread(BluetoothDevice device) {
            this.btDevice = device;

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                mmSocket = btDevice.createRfcommSocketToServiceRecord(uuid);
                bluetoothAdapter.cancelDiscovery();
                mmSocket.connect();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return "";
        }

        @Override
        protected void onPostExecute(final String result) {

            if (result != null){/* Success */}
            else {/* Connection Failed */}

        }

        @Override
        protected void onCancelled() {}

    }




}
