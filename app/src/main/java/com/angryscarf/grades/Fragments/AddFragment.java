package com.angryscarf.grades.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.angryscarf.grades.Data.StudentRepository;
import com.angryscarf.grades.Model.GradesViewModel;
import com.angryscarf.grades.Model.Student;
import com.angryscarf.grades.R;


public class AddFragment extends Fragment implements StudentRepository.OnStudentAddedCallback{

    private OnAddFragmentInteractionListener mListener;
    private GradesViewModel gradesViewModel;
    private EditText id, name, grade;
    private Button save;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gradesViewModel = ViewModelProviders.of(getActivity()).get(GradesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add, container, false);

        id = v.findViewById(R.id.add_edit_id);
        name = v.findViewById(R.id.add_edit_name);
        grade = v.findViewById(R.id.add_edit_grade);

        save = v.findViewById(R.id.add_button_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(
                !id.getText().toString().isEmpty() &&
                !name.getText().toString().isEmpty() &&
                !grade.getText().toString().isEmpty()
                ){
                    gradesViewModel.insertStudent(new Student(id.getText().toString(), name.getText().toString(), Float.parseFloat(grade.getText().toString())), AddFragment.this);
                }
                else {
                    Toast.makeText(getActivity(), "Please complete all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddFragmentInteractionListener) {
            mListener = (OnAddFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void clearViews() {
        this.id.setText("");
        this.grade.setText("");
        this.name.setText("");
    }

    @Override
    public void onStudentAdded() {
        mListener.StudentAdded();
    }

    @Override
    public void onStudentExists() {
        mListener.FailStudentAdd();
    }

    public interface OnAddFragmentInteractionListener {
        void StudentAdded();
        void FailStudentAdd();
    }
}
