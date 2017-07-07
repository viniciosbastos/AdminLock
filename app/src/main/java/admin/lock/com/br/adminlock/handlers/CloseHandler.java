package admin.lock.com.br.adminlock.handlers;

import android.os.Handler;
import android.os.Message;

import admin.lock.com.br.adminlock.OptionsListActivity;
import admin.lock.com.br.adminlock.models.Session;

/**
 * Created by Vinicios on 06/07/2017.
 */

public class CloseHandler implements IHandler {

    private byte[] response;
    private Handler handler;

    public CloseHandler(byte[] response, Handler handler) {
        this.response = response;
        this.handler = handler;
    }

    @Override
    public void handle() {
        String msg;
        if (this.response[3] == 0x01) {
            msg = "Porta foi trancada";
        } else {
            msg = "Operação Recusada";
        }
        Message message = handler.obtainMessage();
        message.what = OptionsListActivity.CLOSE_LOCK;
        message.obj = msg;
        message.sendToTarget();
    }
}
