package admin.lock.com.br.adminlock.models.commands;

/**
 * Created by Vinicios on 05/07/2017.
 */

public class RevokePermissionCommand implements ICommand {
    @Override
    public byte[] createCommandArray() {
        return new byte[0];
    }
}
