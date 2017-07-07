package admin.lock.com.br.adminlock.models;

import java.io.Serializable;

/**
 * Created by Vinicios on 04/07/2017.
 */

public class User implements Serializable{
    private final long serialVersionUID = 1L;

    private String username;

    private String password;

    private Boolean selected;

    public User() {
        this.username = "";
        this.password = "";
        this.selected = false;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.selected = false;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isSelected() {
        return this.selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (!username.equals(that.username)) return false;
        return password.equals(that.password);

    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
