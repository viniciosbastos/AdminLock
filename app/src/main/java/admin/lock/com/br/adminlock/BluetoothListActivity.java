package admin.lock.com.br.adminlock;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import admin.lock.com.br.adminlock.connection.CommunicationThread;
import admin.lock.com.br.adminlock.connection.ConnectThread;
import admin.lock.com.br.adminlock.models.Session;
import admin.lock.com.br.adminlock.models.commands.LoginCommand;

public class BluetoothListActivity extends AppCompatActivity{

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter bluetoothAdapter;

    private ArrayList<BluetoothDevice> devicesList;
    private ArrayList<String> devicesNameList;
    private ListView devicesListView;
    private ArrayAdapter<String> listAdapter;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                devicesList.add(device);
                devicesNameList.add(device.getName());
                listAdapter.notifyDataSetChanged();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                bluetoothAdapter.cancelDiscovery();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_list);

        devicesList = new ArrayList<BluetoothDevice>();
        devicesNameList = new ArrayList<String>();
        devicesListView = (ListView) findViewById(R.id.bluetoothListView);

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, devicesNameList);
        devicesListView.setAdapter(listAdapter);

        devicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cancelDiscovery();
                BluetoothDevice device = devicesList.get(position);


                ConnectThread connectThread = new ConnectThread(device);
                connectThread.start();
                finish();
            }
        });

        startBluetooth();
        discoverDevices();
    }

    private void startBluetooth() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Dispositivo não suporta Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.enable();
            }
        }
    }

    private void discoverDevices() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(broadcastReceiver, intentFilter);
        while (!bluetoothAdapter.isEnabled()) {}
        bluetoothAdapter.startDiscovery();
    }

    public void refreshButtonOnClick(View view) {
        devicesList.clear();
        bluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (bluetoothAdapter !=null) {
            bluetoothAdapter.cancelDiscovery();
        }

        unregisterReceiver(broadcastReceiver);
    }

    private void cancelDiscovery() {
        this.bluetoothAdapter.cancelDiscovery();
    }

}
