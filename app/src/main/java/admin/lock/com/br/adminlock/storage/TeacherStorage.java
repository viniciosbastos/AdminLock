package admin.lock.com.br.adminlock.storage;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import admin.lock.com.br.adminlock.OptionsListActivity;
import admin.lock.com.br.adminlock.models.UserList;
import admin.lock.com.br.adminlock.models.User;

/**
 * Created by Vinicios on 04/07/2017.
 */

public class TeacherStorage {
    private final String FILA_NAME = "teacher_storage.ts";

    private UserList teacherDatabase;

    private static TeacherStorage instance = null;

    private TeacherStorage() {
        this.teacherDatabase = new UserList();
        readFile();
    }

    public static TeacherStorage getInstance() {
        if(instance == null)
            instance = new TeacherStorage();
        return instance;
    }

    public boolean add(User teacher) {
        for (User t : teacherDatabase.getTeacherDatabase()) {
            if (t.getUsername().equals(teacher.getUsername()))
                return false;
        }
        teacherDatabase.add(teacher);
        updateFile();
        return true;
    }

    public ArrayList<User> getAll() {
        return this.teacherDatabase.getTeacherDatabase();
    }

    private void updateFile() {
        try {
            FileOutputStream fos = OptionsListActivity.getContext().openFileOutput(this.FILA_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.teacherDatabase);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(OptionsListActivity.getContext(), "Arquivo não encontrado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(OptionsListActivity.getContext(), "IO Exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void readFile() {
        try {
            FileInputStream fis = OptionsListActivity.getContext().openFileInput(this.FILA_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.teacherDatabase = (UserList) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(OptionsListActivity.getContext(), "Arquivo não encontrado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            this.teacherDatabase = new UserList();
            Toast.makeText(OptionsListActivity.getContext(), "IO Exception", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(OptionsListActivity.getContext(), "Classo não encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    public void remove(User teacher) {
        this.teacherDatabase.remove(teacher);
        updateFile();
    }
}
