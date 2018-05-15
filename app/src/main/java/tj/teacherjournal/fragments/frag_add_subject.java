package tj.teacherjournal.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tj.teacherjournal.DBHelper;
import tj.teacherjournal.R;
import tj.teacherjournal.Subject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frag_add_subject.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frag_add_subject#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_add_subject extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

   private EditText Teacher, Name;
   private Button Add, Back;
   private Subject subject;
   private DBHelper dbHelper;

   FragmentSubject fragmentSubject;

    public frag_add_subject() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_add_subject.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_add_subject newInstance(String param1, String param2) {
        frag_add_subject fragment = new frag_add_subject();
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

        View v = inflater.inflate(R.layout.frag_add_subject, container, false);

        Teacher = (EditText) v.findViewById(R.id.Subject_teacher);
        Name = (EditText) v.findViewById(R.id.Subject_name);

        Add = (Button) v.findViewById(R.id.Add);
        Add.setOnClickListener(this);
        Back = (Button) v.findViewById(R.id.Back);
        Back.setOnClickListener(this);

        fragmentSubject = new FragmentSubject();

        dbHelper = new DBHelper(getActivity());

        subject = new Subject();

        return v;
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
            case R.id.Add:

                PostSubjectInBd();

                break;
            case R.id.Back:

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragmentSubject);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
        }
    }

    private void PostSubjectInBd() {

        String name = Name.getText().toString();
        String teacher = Teacher.getText().toString();

        if (name.length() != 0 && teacher.length() != 0) {

            subject.setTeacher(teacher.trim());
            subject.setName(name.trim());

            dbHelper.addSubject(subject);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragmentSubject);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else {
            Toast.makeText(getActivity(), "Все поля обязательны для заполнения", Toast.LENGTH_SHORT).show();
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
