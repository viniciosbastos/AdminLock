package admin.lock.com.br.adminlock.handlers;

import android.os.Handler;
import android.os.Message;

import admin.lock.com.br.adminlock.OptionsListActivity;
import admin.lock.com.br.adminlock.models.Session;

/**
 * Created by Vinicios on 06/07/2017.
 */

public class GrantPermissionHandler implements IHandler{

    private byte[] response;
    private Handler handler;

    public GrantPermissionHandler(byte[] response, Handler handler) {
        this.response = response;
        this.handler = handler;
    }

    @Override
    public void handle() {
        String msg;
        if (this.response[3] == 0x01) {
            msg = "Permissões Garantidas";
        } else {
            msg = "Operação Recusada";
        }
        Message message = handler.obtainMessage();
        message.what = OptionsListActivity.GRANT_ACCESS;
        message.obj = msg;
        message.sendToTarget();
    }
}
