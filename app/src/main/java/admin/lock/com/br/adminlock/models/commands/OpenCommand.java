package admin.lock.com.br.adminlock.models.commands;

import admin.lock.com.br.adminlock.enums.ECommands;
import admin.lock.com.br.adminlock.models.Session;

/**
 * Created by Vinicios on 06/07/2017.
 */

public class OpenCommand implements ICommand{
    private final byte COMMAND_SIZE = 19;
    private byte[] command;

    @Override
    public byte[] createCommandArray() {
        Session session = Session.getInstance();
        command = new byte[COMMAND_SIZE];
        command[0] = ECommands.OPEN.getId();
        command[1] = session.getSeqNumber();
        command[2] = 16;

        System.arraycopy(session.getUser().getUsername().getBytes(), 0, command, 3, 8);
        System.arraycopy(session.getUser().getPassword().getBytes(), 0, command, 11, 8);

        return command;
    }
}
