package tj.teacherjournal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tj.teacherjournal.fragments.FragmentListStud;
import tj.teacherjournal.fragments.frag_detail_student;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.StudentViewHolder> {

    private List<Student> listStudent;
    frag_detail_student detail_student;

    public StudentRecyclerAdapter(List<Student> listStudent) {
        this.listStudent = listStudent;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_recycler, parent, false);

        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        holder.textViewName.setText(listStudent.get(position).getName());
        holder.id_student.setText(listStudent.get(position).getPhone());
        holder.setting.setTag(listStudent.get(position).getId());
        if(listStudent.get(position).getGender().equals("МУЖ")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.item_student_icon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4286f4")));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.item_student_icon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffa0d6")));
            }
        }
    }

    @Override
    public int getItemCount() {
        Log.v(StudentRecyclerAdapter.class.getSimpleName(),""+listStudent.size());
        return listStudent.size();
    }

    /**
     * ViewHolder class
     */
    public class StudentViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView id_student;
        public TextView item_student_icon;
        public RelativeLayout setting;

        public StudentViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewName);
            id_student = (TextView) view.findViewById(R.id.id_student);
            setting = (RelativeLayout) view.findViewById(R.id.setting);
            item_student_icon = (TextView) view.findViewById(R.id.item_student_icon);

            detail_student = new frag_detail_student();

            item_student_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "ID: " + setting.getTag(), Toast.LENGTH_LONG).show();

                    Bundle bundle = new Bundle();
                    bundle.putInt("tag", (Integer) setting.getTag()); // ID студента
                    detail_student.setArguments(bundle); // Отправляем данные нахрен на другой фрагмент

                    FragmentTransaction fragmentTransaction = ((Activity)v.getContext()).getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, detail_student);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

        }
    }


}