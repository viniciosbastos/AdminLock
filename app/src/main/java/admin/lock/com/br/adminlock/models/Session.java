package admin.lock.com.br.adminlock.models;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;

import admin.lock.com.br.adminlock.connection.CommunicationThread;
import admin.lock.com.br.adminlock.models.commands.ICommand;

/**
 * Created by Vinicios on 05/07/2017.
 */

public class Session {
    private BluetoothDevice connectedDevice;
    private User user;
    private ArrayList<ICommand> commandsToSend;
    private byte seqNumber;
    private boolean logged;
    private boolean admin;
    private CommunicationThread communicationThread;

    private static Session instance = null;

    private Session() {
        this.seqNumber = 1;
        this.logged = false;
        this.admin = false;
        this.commandsToSend = new ArrayList<>();
    }

    public static Session getInstance() {
        if (instance == null)
            instance = new Session();
        return instance;
    }

    public BluetoothDevice getConnectedDevice() {
        return connectedDevice;
    }

    public void setConnectedDevice(BluetoothDevice connectedDevice) {
        this.connectedDevice = connectedDevice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<ICommand> getCommandsToSend() {
        return commandsToSend;
    }

    public void setCommandsToSend(ArrayList<ICommand> commandsToSend) {
        this.commandsToSend = commandsToSend;
    }

    public void addCommand(ICommand command) {
        this.commandsToSend.add(command);
    }

    public byte getSeqNumber() {
        return this.seqNumber;
    }

    public synchronized void incrementSeqNumber() {
        this.seqNumber++;
    }

    public void sendCommands() {
        synchronized (this) {
            notifyAll();
        }
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public CommunicationThread getCommunicationThread() {
        return this.communicationThread;
    }

    public void setCommunicationThread(CommunicationThread com) {
        this.communicationThread = com;
    }
}
