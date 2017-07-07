package admin.lock.com.br.adminlock.models.commands;

import java.util.ArrayList;

import admin.lock.com.br.adminlock.enums.ECommands;
import admin.lock.com.br.adminlock.models.Session;
import admin.lock.com.br.adminlock.models.User;

/**
 * Created by Vinicios on 05/07/2017.
 */

public class GrantPermissionCommand implements  ICommand{
    private ArrayList<User> users;
    private byte[] command;

    public GrantPermissionCommand(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public byte[] createCommandArray() {
        Session session = Session.getInstance();
        int size = users.size()*16;
        command = new byte[size+3];
        command[0] = ECommands.GRANT_PERMISSION.getId();
        command[1] = session.getSeqNumber();
        command[2] = (byte) size;

        User user;
        for (int i = 0; i < users.size(); i++) {
            user = users.get(i);
            System.arraycopy(user.getUsername().getBytes(), 0, command, 3 + (16*i), 8);
            System.arraycopy(user.getPassword().getBytes(), 0, command, 11 + (16*i), 8);
        }
        return command;
    }
}
