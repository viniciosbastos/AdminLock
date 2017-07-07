package admin.lock.com.br.adminlock.connection;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import admin.lock.com.br.adminlock.OptionsListActivity;
import admin.lock.com.br.adminlock.models.Session;

/**
 * Created by Vinicios on 05/07/2017.
 */

public class ConnectThread extends Thread{
    private final UUID MY_UUID_SECURE =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    private final BluetoothDevice bluetoothDevice;
    private final BluetoothSocket bluetoothSocket;

    public ConnectThread(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;

        BluetoothSocket _tmp = null;

        try {
            _tmp = this.bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.bluetoothSocket = _tmp;
    }

    @Override
    public void run() {
        try {
            this.bluetoothSocket.connect();
            OptionsListActivity.setConnected(true);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                this.bluetoothSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        Session.getInstance().setCommunicationThread(new CommunicationThread(this.bluetoothSocket));
        Session.getInstance().getCommunicationThread().start();
    }
}
