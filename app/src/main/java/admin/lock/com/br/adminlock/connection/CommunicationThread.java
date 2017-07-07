package admin.lock.com.br.adminlock.connection;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import admin.lock.com.br.adminlock.handlers.ResponseProcessor;
import admin.lock.com.br.adminlock.models.Session;
import admin.lock.com.br.adminlock.models.commands.ICommand;

/**
 * Created by Vinicios on 05/07/2017.
 */

public class CommunicationThread extends Thread implements Serializable{
    private BluetoothSocket socket;
    private final InputStream inputstream;
    private final OutputStream outputStream;
    private byte[] buffer;
    private Session session;

    public CommunicationThread(BluetoothSocket socket) {
        this.session = Session.getInstance();
        this.socket = socket;
        this.buffer = new byte[128];
        InputStream _inputstream = null;
        OutputStream _outputStream = null;
        try {
            _inputstream = socket.getInputStream();
            _outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.inputstream = _inputstream;
        this.outputStream = _outputStream;
    }

    @Override
    public void run() {
        byte[] response;
        ICommand commandToSend;
        while(!Thread.currentThread().isInterrupted()) {
            try{
                if (session.getCommandsToSend().isEmpty()) {
                    synchronized (session) {
                        session.wait();
                    }
                }
                commandToSend = session.getCommandsToSend().remove(0);
                write(commandToSend.createCommandArray());
                session.incrementSeqNumber();

                response = read();
                proccessResponse(response);
            } catch (InterruptedException ex) {
                close();
                Thread.currentThread().interrupt();
            }
        }
    }

    private byte[] read() {
        byte[] response=  null;
        try {
            int bytes = inputstream.read(buffer);
            response = new byte[bytes];

            System.arraycopy(buffer, 0, response, 0, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void write(byte[] command) {
        try {
            this.outputStream.write(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void proccessResponse(byte[] response) {
        ResponseProcessor.HandleFactory(response);
    }

    private void close() {
        try {
            socket.close();
            inputstream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
