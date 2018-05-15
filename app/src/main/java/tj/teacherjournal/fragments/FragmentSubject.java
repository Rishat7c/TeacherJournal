package tj.teacherjournal.fragments;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tj.teacherjournal.DBHelper;
import tj.teacherjournal.R;
import tj.teacherjournal.StudentRecyclerAdapter;
import tj.teacherjournal.Subject;
import tj.teacherjournal.SubjectRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSubject.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSubject#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSubject extends Fragment implements View.OnClickListener {

    private FragmentSubject activity = FragmentSubject.this;
    private RecyclerView recyclerViewSubjects;
    private List<Subject> listSubject;
    private SubjectRecyclerAdapter subjectsRecyclerAdapter;
    private DBHelper databaseHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button add_subject;

    frag_add_subject as_subject;

    public FragmentSubject() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSubject.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSubject newInstance(String param1, String param2) {
        FragmentSubject fragment = new FragmentSubject();
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

        View v = inflater.inflate(R.layout.fragment_perform, container, false);

        AppCompatTextView textViewName = (AppCompatTextView) v.findViewById(R.id.textViewName);
        RecyclerView recyclerViewSubjects = (RecyclerView) v.findViewById(R.id.recyclerViewSubjects);

        add_subject = (Button) v.findViewById(R.id.add_subject);
        add_subject.setOnClickListener(this);

         as_subject = new frag_add_subject();

        listSubject = new ArrayList<>();
        subjectsRecyclerAdapter = new SubjectRecyclerAdapter(listSubject);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewSubjects.setLayoutManager(mLayoutManager);
        recyclerViewSubjects.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSubjects.setHasFixedSize(true);
        recyclerViewSubjects.setAdapter(subjectsRecyclerAdapter);
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
                listSubject.clear();
                listSubject.addAll(databaseHelper.getAllSubject());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                subjectsRecyclerAdapter.notifyDataSetChanged();
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
            case R.id.add_subject:

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, as_subject);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

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
