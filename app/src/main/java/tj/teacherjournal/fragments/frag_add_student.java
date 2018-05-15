package tj.teacherjournal.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import tj.teacherjournal.AuthActivity;
import tj.teacherjournal.DBHelper;
import tj.teacherjournal.R;
import tj.teacherjournal.RegActivity;
import tj.teacherjournal.Student;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frag_add_student.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frag_add_student#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_add_student extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText Name, Registration, Studnumber, Phone;
    public TextView Age;
    private Spinner Gander;
    private Button Add, Back;
    private DBHelper dbHelper;
    private Student student;

    Calendar dateAndTime=Calendar.getInstance();

    FragmentListStud fragmentListStud;

    public frag_add_student() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_add_student.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_add_student newInstance(String param1, String param2) {
        frag_add_student fragment = new frag_add_student();
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

        View v = inflater.inflate(R.layout.frag_add_student, container, false);

        Name = (EditText) v.findViewById(R.id.Name);
        Gander = (Spinner) v.findViewById(R.id.Gander); // Инициализация пола
        Registration = (EditText) v.findViewById(R.id.Registration);
        Studnumber = (EditText) v.findViewById(R.id.Studnumber);
        Phone = (EditText) v.findViewById(R.id.Phone);

        Add = (Button) v.findViewById(R.id.Add); // Добавить нового пользователя
        Add.setOnClickListener(this);
        Back = (Button) v.findViewById(R.id.Back); // Вернуться к списку студентов
        Back.setOnClickListener(this);

        fragmentListStud = new FragmentListStud();

        dbHelper = new DBHelper(getActivity());

        student = new Student();

        Age = (TextView) v.findViewById(R.id.Age);
        Age.setOnClickListener(this);

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
                PostDataInBd();
                break;
            case R.id.Back:

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragmentListStud);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.Age:
                setDate(view);
                break;
        }
    }

    private void PostDataInBd() {
        String name = Name.getText().toString();
        String gander = Gander.getSelectedItem().toString();
        String age = Age.getText().toString();
        String registration = Registration.getText().toString();
        String studnumber = Studnumber.getText().toString();
        String phone = Phone.getText().toString();

        // Проверка на заполнение всех четырех полей
        if(name.length() != 0 && gander.length() != 0 && age.length() != 0 && registration.length() != 0 && studnumber.length() != 0 && phone.length() != 0) {
            if (!dbHelper.checkUser(studnumber.trim())) {

                student.setName(name.trim());
                student.setGender(gander.trim());
                student.setAge(age.trim());
                student.setRegistration(registration.trim());
                student.setStudnumber(studnumber.trim());
                student.setPhone(phone.trim());

                dbHelper.addStudent(student);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragmentListStud);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                // Снек-бар, чтобы показать сообщение об успешном завершении записи
                Toast.makeText(getActivity(), "Студент успешно был добавлен!", Toast.LENGTH_SHORT).show();

                // Добавить здесь переход на страницу со списком студентов

            } else {
                // Снек-бар, чтобы показать сообщение об ошибке, запись которого уже существует
                Toast.makeText(getActivity(), "Студент с таким номер студ/билета уже существует", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Все поля обязательны для заполнения", Toast.LENGTH_SHORT).show();
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
