package admin.lock.com.br.adminlock.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vinicios on 04/07/2017.
 */

public class UserList implements Serializable{
    private final long serialVersionUID = 1L;
    private ArrayList<User> teacherDatabase;

    public UserList() {
        teacherDatabase = new ArrayList<User>();
    }

    public ArrayList<User> getTeacherDatabase() {
        return teacherDatabase;
    }

    public void setTeacherDatabase(ArrayList<User> teacherDatabase) {
        this.teacherDatabase = teacherDatabase;
    }

    public void add(User teacher) {
        this.teacherDatabase.add(teacher);
    }

    public void remove(User teacher) {
        this.teacherDatabase.remove(teacher);
    }
}
