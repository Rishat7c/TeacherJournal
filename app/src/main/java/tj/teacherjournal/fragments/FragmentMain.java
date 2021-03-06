package tj.teacherjournal.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tj.teacherjournal.AuthActivity;
import tj.teacherjournal.DBHelper;
import tj.teacherjournal.MainActivity;
import tj.teacherjournal.R;
import tj.teacherjournal.RegActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView user_name, email, group_id, stud_count, subject_count;
    private Button log_out;
    private DBHelper dbHelper;

    public FragmentMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMain.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMain newInstance(String param1, String param2) {
        FragmentMain fragment = new FragmentMain();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        user_name = (TextView) v.findViewById(R.id.user_name);
        email = (TextView) v.findViewById(R.id.email);
        group_id = (TextView) v.findViewById(R.id.group_id);
        stud_count = (TextView) v.findViewById(R.id.stud_count);
        subject_count = (TextView) v.findViewById(R.id.subject_count);

        log_out = (Button) v.findViewById(R.id.log_out);
        log_out.setOnClickListener(this);

        Bundle bundle = getArguments();
        String mail = null;
        if (bundle != null) {
            mail = bundle.getString("EMAIL");
            email.setText(mail);
        }

        dbHelper = new DBHelper(getActivity());

        // Вытаскиваем инфу из БД
        Cursor cursor = dbHelper.getByIdAccount(mail);
        if (cursor.moveToFirst()) {
            int NameToInput = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int GroupToInput = cursor.getColumnIndex(DBHelper.KEY_GROUP);
            do {
                user_name.setText("Привет, " + cursor.getString(NameToInput));
                group_id.setText("Номер группы " + cursor.getString(GroupToInput));

            } while (cursor.moveToNext());
        }
        cursor.close();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int numRows = (int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM student", null);
        stud_count.setText("Студентов в Вашей группе " + numRows);

        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
        int numRows1 = (int) DatabaseUtils.longForQuery(db1, "SELECT COUNT(*) FROM subject", null);
        subject_count.setText("Количество предметов " + numRows1);

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
            case R.id.log_out:

                Intent intent_3;
                intent_3 = new Intent(getActivity(), AuthActivity.class);
                startActivity(intent_3);

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
