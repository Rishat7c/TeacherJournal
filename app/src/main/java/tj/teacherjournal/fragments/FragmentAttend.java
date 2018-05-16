package tj.teacherjournal.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import tj.teacherjournal.DBHelper;
import tj.teacherjournal.R;

import android.view.ViewGroup.LayoutParams;

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

        LinearLayout myLayout = (LinearLayout) v.findViewById(R.id.hl); // Был hl и LinearLayout

        LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        dbHelper = new DBHelper(getActivity());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Вытаскиваем инфу из БД
        Cursor cursor = db.query("student", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(DBHelper.STUD_ID);
            int Name = cursor.getColumnIndex(DBHelper.STUD_NAME);
            do {
                    LinearLayout c = new LinearLayout(getActivity());
                    c.setLayoutParams(lp);
                    c.setId(cursor.getInt(id));
                    c.setOrientation(LinearLayout.HORIZONTAL);
                    c.setBackgroundColor(Color.LTGRAY);
                    myLayout.addView(c);

                    CheckBox b = new CheckBox(getActivity());
                    b.setLayoutParams(lp);
                    b.setId(cursor.getInt(id));
                    c.addView(b);

                    TextView a = new TextView(getActivity());
                    a.setTextSize(15);
                    a.setLayoutParams(lp);
                    a.setId(cursor.getInt(id));
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
