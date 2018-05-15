package tj.teacherjournal.fragments;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tj.teacherjournal.DBHelper;
import tj.teacherjournal.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frag_detail_subject.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frag_detail_subject#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_detail_subject extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText Subject_teacher, Subject_name;
    private Button Update, Delete, Back;
    private DBHelper dbHelper;
    private int SubjectID;

    final String LOG_TAG = "myLogs"; // Логи

    FragmentSubject fragmentSubject;

    public frag_detail_subject() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_detail_subject.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_detail_subject newInstance(String param1, String param2) {
        frag_detail_subject fragment = new frag_detail_subject();
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

        View v = inflater.inflate(R.layout.frag_detail_subject, container, false);

        Subject_name = (EditText) v.findViewById(R.id.Subject_name);
        Subject_teacher = (EditText) v.findViewById(R.id.Subject_teacher);

        Update = (Button) v.findViewById(R.id.Update);
        Update.setOnClickListener(this);
        Delete = (Button) v.findViewById(R.id.Delete);
        Delete.setOnClickListener(this);
        Back = (Button) v.findViewById(R.id.Back);
        Back.setOnClickListener(this);

        dbHelper = new DBHelper(getActivity());

        fragmentSubject = new FragmentSubject();

        Bundle bundle = getArguments();
        if (bundle != null) {
            SubjectID = bundle.getInt("tag");
            //Integer recieveInfo = bundle.getInt("tag");
        }

        // Вытаскиваем инфу из БД
        Cursor cursor = dbHelper.getByIdSubject(SubjectID);
        if (cursor.moveToFirst()) {
            int NameToInput = cursor.getColumnIndex(DBHelper.SUBJECT_NAME);
            int TeacherToInput = cursor.getColumnIndex(DBHelper.SUBJECT_TEACHER);
            do {
                Subject_name.setText(cursor.getString(NameToInput));
                Subject_teacher.setText(cursor.getString(TeacherToInput));
            } while (cursor.moveToNext());
        }

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

        ContentValues cv = new ContentValues();
        // Создаем подключение к БД йобана только пока глобально для клика , можно сделать локально для батона
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Получаем данные с инпатов
        String sName = Subject_name.getText().toString();
        String sTeacher = Subject_teacher.getText().toString();

        switch (view.getId()) {
            case R.id.Update:
                cv.put(DBHelper.SUBJECT_NAME, sName);
                cv.put(DBHelper.SUBJECT_TEACHER, sTeacher);

                // обновляем по id
                int updCount = db.update(DBHelper.TABLE_SUBJECT, cv, DBHelper.SUBJECT_ID + " = ?", new String[] { String.valueOf(SubjectID) });
                Log.d(LOG_TAG, "updated rows count = " + updCount);

                FragmentTransaction f = getFragmentManager().beginTransaction();
                f.replace(R.id.container, fragmentSubject);
                f.addToBackStack(null);
                f.commit();

                break;
            case R.id.Delete:
                dbHelper.deleteSubject(SubjectID);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragmentSubject);
                ft.addToBackStack(null);
                ft.commit();

                break;
            case R.id.Back:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragmentSubject);
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
