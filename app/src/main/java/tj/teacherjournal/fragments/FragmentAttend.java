package tj.teacherjournal.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import tj.teacherjournal.DBHelper;
import tj.teacherjournal.Protection;
import tj.teacherjournal.R;

import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAttend.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAttend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAttend extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private DBHelper dbHelper;

    private TextView date;
    Calendar dateInput=Calendar.getInstance();
    LinearLayout myLayout;
    LayoutParams lp;

    public FragmentAttend() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAttend.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAttend newInstance(String param1, String param2) {
        FragmentAttend fragment = new FragmentAttend();
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

        View v = inflater.inflate(R.layout.fragment_attend, container, false);

        date = (TextView) v.findViewById(R.id.date);
        date.setOnClickListener(this);

        myLayout = (LinearLayout) v.findViewById(R.id.containerAttend); // Был hl и LinearLayout

        lp = new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        dbHelper = new DBHelper(getActivity());

        setInitialDateTime();
//        ListLoader();

        return v;
    }

    private void ListLoader() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Вытаскиваем инфу из БД
        Cursor cursor = db.query("student", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(DBHelper.STUD_ID);
            int Name = cursor.getColumnIndex(DBHelper.STUD_NAME);
            int StudNumber = cursor.getColumnIndex(DBHelper.STUD_NUMBER);
            do {
                final LinearLayout c = new LinearLayout(getActivity());
                c.setLayoutParams(lp);
                c.setTag(cursor.getString(StudNumber));
                c.setOrientation(LinearLayout.HORIZONTAL);
                c.setBackgroundColor(Color.LTGRAY);
                myLayout.addView(c);

                final CheckBox b = new CheckBox(getActivity());
                b.setLayoutParams(lp);
                b.setTag(cursor.getString(StudNumber));
                c.addView(b);

                b.setChecked(checkInBd(b.getTag().toString(),date.getText().toString()));

                b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();

                        if(isChecked) { // isChecked = либо true , либо false
//                                Toast.makeText(getActivity(), "Studnumber = " + b.getTag(), Toast.LENGTH_SHORT).show();

                            contentValues.put(DBHelper.ATTEND_NUMBER, b.getTag().toString());
                            contentValues.put(DBHelper.ATTEND_DATE, date.getText().toString());

                            database.insert(DBHelper.TABLE_ATTEND, null, contentValues);

                        } else {
//                            Toast.makeText(getActivity(), "Удалили", Toast.LENGTH_SHORT).show();

                            int delCount = database.delete(DBHelper.TABLE_ATTEND, "number=\'" + b.getTag().toString() + "\' AND date=\'" + date.getText().toString() + "\'", null);

//                            int delCount = database.delete("attend","number=? and date=?",new String[]{b.getTag().toString(),date.getText().toString()});

                            Log.d("mLog", "deleted rows count = " + delCount);
                        }

                    }
                });

                TextView a = new TextView(getActivity());
                a.setTextSize(15);
                a.setLayoutParams(lp);
                a.setTag(cursor.getString(StudNumber));
                a.setText(cursor.getString(Name));
                c.addView(a);

                //Name.setText(cursor.getString(NameToInput));
            } while (cursor.moveToNext());
        } else {
            TextView a = new TextView(getActivity());
            a.setTextSize(15);
            a.setLayoutParams(lp);
            a.setText("Заполните таблицу Студентов");
            myLayout.addView(a);
        }
        cursor.close();
        db.close();
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
            case R.id.date:
                setDate(view);
                break;
        }
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(getActivity(), d,
                dateInput.get(Calendar.YEAR),
                dateInput.get(Calendar.MONTH),
                dateInput.get(Calendar.DAY_OF_MONTH))
                .show();

        myLayout.removeAllViews(); // Удаляем все объекты со сцены
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {

        date.setText(DateUtils.formatDateTime(getActivity(),
                dateInput.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

        ListLoader(); // Загружаем данные на сцену
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateInput.set(Calendar.YEAR, year);
            dateInput.set(Calendar.MONTH, monthOfYear);
            dateInput.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    private boolean checkInBd(String number, String date) {

//        db.query(TABLE_ACCOUNT, null, "email = \'" + email + "\'", null, null, null, null);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_ATTEND, null, "number = \'" + number + "\' AND date = \'" + date + "\'", null, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
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
