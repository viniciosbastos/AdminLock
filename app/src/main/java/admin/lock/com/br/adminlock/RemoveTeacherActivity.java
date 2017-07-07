package admin.lock.com.br.adminlock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import admin.lock.com.br.adminlock.adapters.TeacherListSelectionAdapter;
import admin.lock.com.br.adminlock.models.User;
import admin.lock.com.br.adminlock.storage.TeacherStorage;

public class RemoveTeacherActivity extends AppCompatActivity {

    ArrayList<User> teacherList;
    TeacherListSelectionAdapter adapter;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_teacher);

        listView = (ListView) findViewById(R.id.teachersList);

        teacherList = TeacherStorage.getInstance().getAll();

        adapter = new TeacherListSelectionAdapter(this, android.R.layout.simple_list_item_1, teacherList);
        listView.setAdapter(adapter);
    }

    public void finishOnClick(View v) {
        ArrayList<User> selectedTeachers = new ArrayList<>();
        for (int i = 0; i < this.teacherList.size(); i++) {
            if (teacherList.get(i).isSelected())
                selectedTeachers.add(teacherList.get(i));
        }

        if (!selectedTeachers.isEmpty()) {
            for (User t : selectedTeachers) {
                adapter.remove(t);
                TeacherStorage.getInstance().remove(t);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
