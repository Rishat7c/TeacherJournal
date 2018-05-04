package tj.teacherjournal.fragments;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tj.teacherjournal.DBHelper;
import tj.teacherjournal.R;
import tj.teacherjournal.Student;
import tj.teacherjournal.StudentRecyclerAdapter;

import static java.lang.Integer.parseInt;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frag_detail_student.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frag_detail_student#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_detail_student extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<Student> listStudent;
    private Button Update, Delete, Back;
    private EditText Name, Age, Registration, Studnumber, Phone;
    private Spinner Gander;
    private DBHelper dbHelper;
    private Student student;
    FragmentListStud fragmentListStud;
    private int StudentID;
    final String LOG_TAG = "myLogs"; // Логи

    public frag_detail_student() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_detail_student.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_detail_student newInstance(String param1, String param2) {
        frag_detail_student fragment = new frag_detail_student();
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

        View v = inflater.inflate(R.layout.frag_detail_student, container, false);

        listStudent = new ArrayList<>();
        StudentRecyclerAdapter myAdapter = new StudentRecyclerAdapter(listStudent);

        Back = (Button) v.findViewById(R.id.Back);
        Back.setOnClickListener(this);

        Delete = (Button) v.findViewById(R.id.Delete);
        Delete.setOnClickListener(this);

        Update = (Button) v.findViewById(R.id.Update);
        Update.setOnClickListener(this);

        Name = (EditText) v.findViewById(R.id.Name);

        Bundle bundle = getArguments();
        if (bundle != null) {
            StudentID = bundle.getInt("tag");
            //Integer recieveInfo = bundle.getInt("tag");
            Toast.makeText(getActivity(), "Ха " + StudentID, Toast.LENGTH_SHORT).show();
        }

        fragmentListStud = new FragmentListStud();

        dbHelper = new DBHelper(getActivity());

        // Вытаскиваем инфу из БД
        Cursor cursor = dbHelper.getByIdStudent(StudentID);
        if (cursor.moveToFirst()) {
            int NameToInput = cursor.getColumnIndex(DBHelper.STUD_NAME);
//            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
//            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
            do {
//                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
//                        ", name = " + cursor.getString(nameIndex) +
//                        ", email = " + cursor.getString(emailIndex));
                Name.setText(cursor.getString(NameToInput));
            } while (cursor.moveToNext());
        }

        cursor.close();
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
            case R.id.Back: // Назад
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragmentListStud);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.Delete: // Удалить
                dbHelper.deleteStudent(StudentID);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragmentListStud);
                ft.addToBackStack(null);
                ft.commit();
                Toast.makeText(getActivity(), "Ха удалили твоего студентика " + StudentID, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Update: // Обновление и сохранение в БД
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
