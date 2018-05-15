package tj.teacherjournal.fragments;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
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
    private EditText Name, Registration, Studnumber, Phone;
    private TextView Age;
    private Spinner Gander;
    private DBHelper dbHelper;
    private Student student;
    FragmentListStud fragmentListStud;
    private int StudentID;
    final String LOG_TAG = "myLogs"; // Логи

    Calendar dateAndTime=Calendar.getInstance();

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
        Gander = (Spinner) v.findViewById(R.id.Gander);
        Age = (TextView) v.findViewById(R.id.Age);
        Registration = (EditText) v.findViewById(R.id.Registration);
        Studnumber = (EditText) v.findViewById(R.id.Studnumber);
        Phone = (EditText) v.findViewById(R.id.Phone);

        Age = (TextView) v.findViewById(R.id.Age);
        Age.setOnClickListener(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            StudentID = bundle.getInt("tag");
            //Integer recieveInfo = bundle.getInt("tag");
//            Toast.makeText(getActivity(), "Ха " + StudentID, Toast.LENGTH_SHORT).show();
        }

        fragmentListStud = new FragmentListStud();

        dbHelper = new DBHelper(getActivity());

        // Вытаскиваем инфу из БД
        Cursor cursor = dbHelper.getByIdStudent(StudentID);
        if (cursor.moveToFirst()) {
            int NameToInput = cursor.getColumnIndex(DBHelper.STUD_NAME);
            int GanderToInput = cursor.getColumnIndex(DBHelper.STUD_GENDER);
            int AgeToInput = cursor.getColumnIndex(DBHelper.STUD_AGE);
            int RegistrationToInput = cursor.getColumnIndex(DBHelper.STUD_REGISTRATION);
            int StudnumberToInput = cursor.getColumnIndex(DBHelper.STUD_NUMBER);
            int PhoneToInput = cursor.getColumnIndex(DBHelper.STUD_PHONE);
            do {
                Name.setText(cursor.getString(NameToInput));
                Gander.setSelection(getIndex(Gander, cursor.getString(GanderToInput)));
                Age.setText(cursor.getString(AgeToInput));
                Registration.setText(cursor.getString(RegistrationToInput));
                Studnumber.setText(cursor.getString(StudnumberToInput));
                Phone.setText(cursor.getString(PhoneToInput));

            } while (cursor.moveToNext());
        }

        cursor.close();
        return v;
    }

    // вытаскиваем данные из спиннера с указанным значением из БД
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
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
        String sName = Name.getText().toString();
        String sGander = Gander.getSelectedItem().toString();
        String sAge = Age.getText().toString();
        String sRegistration = Registration.getText().toString();
        String sNumber = Studnumber.getText().toString();
        String sPhone = Phone.getText().toString();
        //

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
                break;
            case R.id.Update: // Обновление и сохранение в БД
                // подготовим значения для обновления
                cv.put(DBHelper.STUD_NAME, sName);
                cv.put(DBHelper.STUD_GENDER, sGander);
                cv.put(DBHelper.STUD_AGE, sAge);
                cv.put(DBHelper.STUD_REGISTRATION, sRegistration);
                cv.put(DBHelper.STUD_NUMBER, sNumber);
                cv.put(DBHelper.STUD_PHONE, sPhone);
                // обновляем по id
                int updCount = db.update(DBHelper.TABLE_STUDENT, cv, DBHelper.STUD_ID + " = ?", new String[] { String.valueOf(StudentID) });
                Log.d(LOG_TAG, "updated rows count = " + updCount);

                FragmentTransaction f = getFragmentManager().beginTransaction();
                f.replace(R.id.container, fragmentListStud);
                f.addToBackStack(null);
                f.commit();
                break;
            case R.id.Age:
                setDate(view);
                break;
        }
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(getActivity(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {

        Age.setText(DateUtils.formatDateTime(getActivity(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

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
