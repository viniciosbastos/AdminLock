package admin.lock.com.br.adminlock.handlers;

import android.os.Handler;

import admin.lock.com.br.adminlock.enums.ECommands;

import static admin.lock.com.br.adminlock.enums.ECommands.*;

/**
 * Created by Vinicios on 06/07/2017.
 */

public class ResponseProcessor {
    public static Handler handler;

    public static void HandleFactory(byte[] response) {
        byte op = response[0];
        IHandler iHandler = null;
        switch(op) {
            case 'U':
                iHandler = new LoginHandler(response, handler);
                break;
            case 'C':
                iHandler = new GrantPermissionHandler(response, handler);
                break;
            case 'E':
                break;
            case 'L':
                break;
            case 'A':
                iHandler = new OpenHandler(response, handler);
                break;
            case 'F':
                iHandler = new CloseHandler(response, handler);
                break;
        }
        iHandler.handle();
    }
}
