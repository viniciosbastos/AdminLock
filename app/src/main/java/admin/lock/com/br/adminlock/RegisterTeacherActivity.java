package admin.lock.com.br.adminlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import admin.lock.com.br.adminlock.models.User;
import admin.lock.com.br.adminlock.storage.TeacherStorage;

public class RegisterTeacherActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_teacher);

        usernameEditText = (EditText) findViewById(R.id.userEditText);
        passwdEditText = (EditText) findViewById(R.id.passwdEditText);
    }

    public void registerButtonOnClick(View view) {
        String username = usernameEditText.getText().toString();
        String passwd = passwdEditText.getText().toString();
        User teacher = new User(String.format("%-8s", username), String.format("%-8s", passwd));
        if (TeacherStorage.getInstance().add(teacher))
            Toast.makeText(this, "Professor Cadastrado", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Professor não Cadastrado", Toast.LENGTH_LONG).show();
        usernameEditText.setText("");
        passwdEditText.setText("");
    }
}
