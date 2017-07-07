package admin.lock.com.br.adminlock;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import admin.lock.com.br.adminlock.connection.CommunicationThread;
import admin.lock.com.br.adminlock.connection.ConnectThread;
import admin.lock.com.br.adminlock.enums.EOperations;
import admin.lock.com.br.adminlock.handlers.ResponseProcessor;
import admin.lock.com.br.adminlock.models.Session;
import admin.lock.com.br.adminlock.models.User;
import admin.lock.com.br.adminlock.models.commands.CloseCommand;
import admin.lock.com.br.adminlock.models.commands.GrantPermissionCommand;
import admin.lock.com.br.adminlock.models.commands.LoginCommand;
import admin.lock.com.br.adminlock.models.commands.OpenCommand;

public class OptionsListActivity extends AppCompatActivity {
    public static final int BLUETOOTH_SELECTED = 0;
    public static final int TEACHERS_SELECTED = 1;
    public static final int LOGIN_RESPONSE = 0;
    public static final int GRANT_ACCESS = 1;
    public static final int OPEN_LOCK = 2;
    public static final int CLOSE_LOCK = 3;

    ListView listView;

    private static Context context;

    private Handler handler;

    private Session session;

    private static boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_list);
        this.context = getApplicationContext();
        configureListView();

        this.handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == LOGIN_RESPONSE
                        || msg.what == GRANT_ACCESS
                        || msg.what == OPEN_LOCK
                        || msg.what == CLOSE_LOCK) {
                    Toast.makeText(getContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        ResponseProcessor.handler = handler;
        this.session = Session.getInstance();
        this.connected = false;
    }

    private void configureListView() {
        listView = (ListView) findViewById(R.id.optionsListView);
        listView.setAdapter(new ArrayAdapter<EOperations>(this, android.R.layout.simple_list_item_1, EOperations.values()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EOperations op = (EOperations) listView.getAdapter().getItem((int) id);
                chooseOperation(op);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TEACHERS_SELECTED) {
            if (resultCode == RESULT_OK) {
                ArrayList<User> teachers = (ArrayList<User>) data.getSerializableExtra("selectedTeachers");
                Session.getInstance().addCommand(new GrantPermissionCommand(teachers));
                Session.getInstance().sendCommands();
            }
        }
    }

    private void chooseOperation(EOperations op) {
        Intent intent = null;
        switch(op) {
            case CADASTRAR_PROFESSOR:
                if (Session.getInstance().isAdmin()) {
                    intent = new Intent(this, RegisterTeacherActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Admin não está logado", Toast.LENGTH_LONG).show();
                }
                break;

            case REMOVER_PROFESSOR:
                if (Session.getInstance().isAdmin()) {
                    intent = new Intent(this, RemoveTeacherActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Admin não está logado", Toast.LENGTH_LONG).show();
                }
                break;

            case GARANTIR_PERMISSAO:
                if (Session.getInstance().isAdmin()) {
                    intent = new Intent(this, ChooseTeacherActivity.class);
                    startActivityForResult(intent, TEACHERS_SELECTED);
                } else {
                    Toast.makeText(this, "Admin não está logado", Toast.LENGTH_LONG).show();
                }
                break;

            case REMOVER_PERMISSAO:
                break;

            case CONNECT_TO_DEVICE:
                if (!isConnected()) {
                    intent = new Intent(this, BluetoothListActivity.class);
                    startActivity(intent);
                }
                break;

            case LOG:
                break;

            case OPEN:
                if (!connected) {
                    Toast.makeText(this, "Conect-se ao dispositivo", Toast.LENGTH_SHORT).show();
                } else {
                    openLock();
                }
                break;

            case CLOSE:
                if (!connected) {
                    Toast.makeText(this, "Conect-se ao dispositivo", Toast.LENGTH_SHORT).show();
                } else {
                    closeLock();
                }
                break;

            case LOGIN:
                if (!connected) {
                    Toast.makeText(this, "Conect-se ao dispositivo", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
                break;
        }
    }

    private void login() {
        session.addCommand(new LoginCommand());
        session.sendCommands();
    }

    private void openLock() {
        session.addCommand(new OpenCommand());
        session.sendCommands();
    }

    private void closeLock() {
        session.addCommand(new CloseCommand());
        session.sendCommands();
    }

    public static Context getContext() {
        return context;
    }

    public static void setConnected(boolean c) {
        connected = c;
    }

    public static boolean isConnected() {
        return connected;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getInstance().getCommunicationThread().interrupt();
        BluetoothAdapter.getDefaultAdapter().disable();
    }

}
