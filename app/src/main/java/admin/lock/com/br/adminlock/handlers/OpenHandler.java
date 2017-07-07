package admin.lock.com.br.adminlock.handlers;

import android.os.Handler;
import android.os.Message;

import admin.lock.com.br.adminlock.OptionsListActivity;
import admin.lock.com.br.adminlock.models.Session;

/**
 * Created by Vinicios on 06/07/2017.
 */

public class OpenHandler implements IHandler{

    private byte[] response;
    private Handler handler;

    public OpenHandler(byte[] response, Handler handler) {
        this.response = response;
        this.handler = handler;
    }

    @Override
    public void handle() {
        String msg;
        if (this.response[3] == 0x01) {
            msg = "Porta foi destravada";
        } else {
            msg = "Operacação Recusada";
        }
        Message message = handler.obtainMessage();
        message.what = OptionsListActivity.OPEN_LOCK;
        message.obj = msg;
        message.sendToTarget();
    }
}
