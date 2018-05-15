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

import tj.teacherjournal.fragments.frag_detail_subject;

public class SubjectRecyclerAdapter extends RecyclerView.Adapter<SubjectRecyclerAdapter.SubjectViewHolder> {

    private List<Subject> listSubject;
    frag_detail_subject detail_subject;

    public SubjectRecyclerAdapter(List<Subject> listSubject) {
        this.listSubject = listSubject;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject_recycler, parent, false);

        return new SubjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {
        holder.subjectTeacher.setText(listSubject.get(position).getName());
        holder.subjectName.setText(listSubject.get(position).getTeacher());
        holder.setting.setTag(listSubject.get(position).getId());
//        if(listStudent.get(position).getGender().equals("МУЖ")) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                holder.item_student_icon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4286f4")));
//            }
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                holder.item_student_icon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffa0d6")));
//            }
//        }
    }

    @Override
    public int getItemCount() {
        Log.v(SubjectRecyclerAdapter.class.getSimpleName(),""+listSubject.size());
        return listSubject.size();
    }

    /**
     * ViewHolder class
     */
    public class SubjectViewHolder extends RecyclerView.ViewHolder {

        public TextView subjectTeacher;
        public TextView subjectName;
        public TextView item_subject_icon;
        public RelativeLayout setting;

        public SubjectViewHolder(View view) {
            super(view);
            subjectTeacher = (TextView) view.findViewById(R.id.subjectTeacher);
            subjectName = (TextView) view.findViewById(R.id.subjectName);
            setting = (RelativeLayout) view.findViewById(R.id.setting);
            item_subject_icon = (TextView) view.findViewById(R.id.item_subject_icon);
//
            detail_subject = new frag_detail_subject();
//
            item_subject_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("tag", (Integer) setting.getTag()); // ID предмета
                    detail_subject.setArguments(bundle); // Отправляем данные нахрен на другой фрагмент

                    FragmentTransaction fragmentTransaction = ((Activity)v.getContext()).getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, detail_subject);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

        }
    }


}