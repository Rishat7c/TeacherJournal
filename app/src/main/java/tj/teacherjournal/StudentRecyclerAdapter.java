package tj.teacherjournal;

import android.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.StudentViewHolder> implements View.OnClickListener {

    private List<Student> listStudent;

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
        holder.id_student.setText(listStudent.get(position).getStudnumber());
        holder.setting.setTag(listStudent.get(position).getId());
    }

    @Override
    public int getItemCount() {
        Log.v(StudentRecyclerAdapter.class.getSimpleName(),""+listStudent.size());
        return listStudent.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:

//                Toast.makeText(view.getContext(), StudentViewHolder.setting.getTag(), Toast.LENGTH_SHORT).show();
                break;
        }
    }


    /**
     * ViewHolder class
     */
    public class StudentViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView id_student;
        public ImageButton setting;

        public StudentViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            id_student = (AppCompatTextView) view.findViewById(R.id.id_student);
            setting = (ImageButton) view.findViewById(R.id.setting);
        }
    }


}