package admin.lock.com.br.adminlock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import admin.lock.com.br.adminlock.models.Session;
import admin.lock.com.br.adminlock.models.User;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.usernameEditText = (EditText) findViewById(R.id.usernameTxt);
        this.passwdEditText = (EditText) findViewById(R.id.passwdEditText);
    }

    public void enterOnClick(View v) {
        String username = usernameEditText.getText().toString();
        String passwd = passwdEditText.getText().toString();
        Session.getInstance().setUser(new User(String.format("%-8s", username), String.format("%-8s", passwd)));

        Intent intent = new Intent(this, OptionsListActivity.class);
        startActivity(intent);
    }
}
