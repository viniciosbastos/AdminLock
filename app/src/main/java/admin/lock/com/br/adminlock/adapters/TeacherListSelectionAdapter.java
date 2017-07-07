package admin.lock.com.br.adminlock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import admin.lock.com.br.adminlock.R;
import admin.lock.com.br.adminlock.models.User;

/**
 * Created by Vinicios on 30/06/2017.
 */

public class TeacherListSelectionAdapter extends BaseAdapter {
    private ArrayList<User> teacherList;
    private Context context;

    public TeacherListSelectionAdapter(Context context, int textViewResourceId ,List<User> objects) {
        teacherList = new ArrayList<>();
        teacherList.addAll(objects);
        this.context = context;
    }

    private class Holder {
        CheckBox checkBox;
    }

    @Override
    public int getCount() {
        return teacherList.size();
    }

    @Override
    public Object getItem(int position) {
        return teacherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(User teacher) {
        teacherList.remove(teacher);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.checkbox_list, null);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            convertView.setTag(holder);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    User user = (User) cb.getTag();
                    user.setSelected(cb.isChecked());
                }
            });
        } else {
            holder = (Holder) convertView.getTag();
        }

        User user = teacherList.get(position);
        holder.checkBox.setText(user.getUsername());
        holder.checkBox.setChecked(user.isSelected());
        holder.checkBox.setTag(user);

        return convertView;
    }


}
