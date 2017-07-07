package admin.lock.com.br.adminlock.enums;

/**
 * Created by Vinicios on 06/07/2017.
 */

public enum ECommands {

    LOGIN ((byte) 'U'),
    GRANT_PERMISSION ((byte) 'C'),
    REVOKE_PERMISSION ((byte) 'E'),
    LOG ((byte) 'L'),
    OPEN ((byte) 'A'),
    CLOSE ((byte) 'F');

    private byte id;

    private ECommands(byte id) {
        this.id = id;
    }

    public byte getId() {
        return this.id;
    }

    public static ECommands valueOf(byte op) {
        ECommands eCommands = null;

        for(ECommands command : values()) {
            if (command.getId() == op) {
                eCommands = command;
                break;
            }
        }

        return eCommands;
    }
}
