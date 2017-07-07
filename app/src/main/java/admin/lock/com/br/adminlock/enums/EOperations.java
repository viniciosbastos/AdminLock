package admin.lock.com.br.adminlock.enums;

/**
 * Created by Vinicios on 29/06/2017.
 */

public enum EOperations {
    CONNECT_TO_DEVICE ("Conectar Dispositivo"),
    LOGIN ("Login"),
    CADASTRAR_PROFESSOR ("Cadastrar Professor"),
    REMOVER_PROFESSOR ("Remover Professor"),
    GARANTIR_PERMISSAO ("Garantir Permissão"),
    REMOVER_PERMISSAO ("Remover Permissão"),
    OPEN ("Abrir"),
    CLOSE ("Fechar"),
    LOG ("Log");


    private String descricao;

    private EOperations(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    @Override
    public String toString() {
        return this.getDescricao();
    }
}
