package admin.lock.com.br.adminlock.handlers;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import admin.lock.com.br.adminlock.OptionsListActivity;
import admin.lock.com.br.adminlock.models.Session;

/**
 * Created by Vinicios on 06/07/2017.
 */

public class LoginHandler implements IHandler{

    private byte[] response;
    private Handler handler;

    public LoginHandler(byte[] response, Handler handler) {
        this.response = response;
        this.handler = handler;
    }

    @Override
    public void handle() {
        String msg;
        Session session = Session.getInstance();
        if (this.response[3] == 0x01) {
            session.setAdmin(true);
            msg = "Admin Logado";
        } else {
            session.setLogged(true);
            msg = "Login Recusado";
        }
        Message message = handler.obtainMessage();
        message.what = OptionsListActivity.LOGIN_RESPONSE;
        message.obj = msg;
        message.sendToTarget();
    }
}
