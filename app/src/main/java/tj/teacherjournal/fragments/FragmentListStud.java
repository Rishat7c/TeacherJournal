package tj.teacherjournal.fragments;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tj.teacherjournal.DBHelper;
import tj.teacherjournal.R;
import tj.teacherjournal.Student;
import tj.teacherjournal.StudentRecyclerAdapter;
import tj.teacherjournal.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentListStud.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentListStud#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListStud extends Fragment implements View.OnClickListener {

    private FragmentListStud activity = FragmentListStud.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewStudents;
    private List<Student> listStudent;
    private StudentRecyclerAdapter studentsRecyclerAdapter;
    private DBHelper databaseHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button add_student;
    frag_add_student as_fragment;

    public FragmentListStud() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListStud.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListStud newInstance(String param1, String param2) {
        FragmentListStud fragment = new FragmentListStud();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list_stud, container, false);
        AppCompatTextView textViewName = (AppCompatTextView) v.findViewById(R.id.textViewName);
        RecyclerView recyclerViewStudents = (RecyclerView) v.findViewById(R.id.recyclerViewStudents);
        add_student = (Button) v.findViewById(R.id.add_student);
        add_student.setOnClickListener(this);

        as_fragment = new frag_add_student();

        listStudent = new ArrayList<>();
        studentsRecyclerAdapter = new StudentRecyclerAdapter(listStudent);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewStudents.setLayoutManager(mLayoutManager);
        recyclerViewStudents.setItemAnimator(new DefaultItemAnimator());
        recyclerViewStudents.setHasFixedSize(true);
        recyclerViewStudents.setAdapter(studentsRecyclerAdapter);
        databaseHelper = new DBHelper(getActivity());

        getDataFromSQLite();

        return v;
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    //@SuppressLint("StaticFieldLeak")
    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listStudent.clear();
                listStudent.addAll(databaseHelper.getAllStudent());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                studentsRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_student:

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, as_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                Toast.makeText(getActivity(), "Хуя епте заработало!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
