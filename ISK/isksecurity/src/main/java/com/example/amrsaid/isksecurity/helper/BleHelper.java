package com.example.amrsaid.isksecurity.helper;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amrsaid.isksecurity.R;
import com.example.amrsaid.isksecurity.ble.BluetoothHandler;

/**
 * Created by AmrSaid on 07/01/2016.
 */
public class BleHelper {

    private BluetoothHandler bluetoothHandler;
    private BluetoothDevice connectedDevice;
    private StringBuffer recString;
    private StringBuffer sendString;
    private AlertDialog bleScanDialog;
    private boolean isConnected;
    private String textViewReceive = null;
    private String textViewCount = null;
    private int mRecvCount = 0;
    private int mSendCount = 0;
    Context context;
    public BleHelper(Context context){
        this.context = context;
        bluetoothHandler = new BluetoothHandler(context);
        bluetoothHandler.setOnConnectedListener(new BluetoothHandler.OnConnectedListener() {

            @Override
            public void onConnected(boolean isConnected) {
                // TODO Auto-generated method stub
                setConnectStatus(isConnected);
            }
        });

        bluetoothHandler.setOnRecievedDataListener(new BluetoothHandler.OnRecievedDataListener() {

            @Override
            public void onRecievedData(byte[] bytes) {
                // TODO Auto-generated method stub

                mRecvCount += bytes.length;
                recString.append(new String(bytes));
            }
        });
    }


    private void showMessage(String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public void scan(){
        if(!isConnected){
            bluetoothHandler.getDeviceListAdapter().clearDevice();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.device_list, null);

            ListView deviceListView = (ListView) view.findViewById(R.id.listViewDevice);
            deviceListView.setAdapter(bluetoothHandler.getDeviceListAdapter());

            deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    connectedDevice = bluetoothHandler.getDeviceListAdapter().getItem(position).device;
                    bleScanDialog.cancel();
                    // connect
                    bluetoothHandler.connect(connectedDevice.getAddress());
                }
            });

            builder.setView(view);
            bleScanDialog = builder.create();
            bleScanDialog.show();

            bluetoothHandler.scanLeDevice(true);
        }else{
            setConnectStatus(false);
        }
    }



    public void setConnectStatus(boolean isConnected){
        this.isConnected = isConnected;
        if(isConnected){
            showMessage("Connection successful");
           // getActionBar().setTitle(connectedDevice.getName());
            //scanButton.setText("break");
        }else{
            bluetoothHandler.onPause();
            bluetoothHandler.onDestroy();
            //scanButton.setText("scan");
        }
    }

    public void onButtonSend(String message) {

        bluetoothHandler.sendData(message);

    }


}
