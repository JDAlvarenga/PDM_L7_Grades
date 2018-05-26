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


public class EditFragment extends Fragment  implements
        StudentRepository.OnGetStudentCallback,
        StudentRepository.OnStudentUpdatedCallback{

    protected boolean calledOnCreateView = false;
    private Student student;
    private GradesViewModel gradesViewModel;

    private EditText id, name, grade;

    private Button save, search;


    private OnEditFragmentInteractionListener mListener;

    public EditFragment() {
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
        calledOnCreateView = true;
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit, container, false);

        id = v.findViewById(R.id.edit_edit_id);
        name = v.findViewById(R.id.edit_edit_name);
        grade = v.findViewById(R.id.edit_edit_grade);

        save = v.findViewById(R.id.edit_button_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(
                !id.getText().toString().isEmpty() &&
                        !name.getText().toString().isEmpty() &&
                        !grade.getText().toString().isEmpty()
                ){
                    gradesViewModel.updateStudent(new Student(id.getText().toString(), name.getText().toString(), Float.parseFloat(grade.getText().toString()) ), EditFragment.this);
                }
                else {
                    Toast.makeText(getActivity(), "Please complete all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        search = v.findViewById(R.id.edit_button_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gradesViewModel.getStudent(id.getText().toString(), EditFragment.this);
            }
        });

        //load data if student has been selected
        setStudent(gradesViewModel.getLastSelectedStudent());
        gradesViewModel.setLastSelectedStudent(null);


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        calledOnCreateView = false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEditFragmentInteractionListener) {
            mListener = (OnEditFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEditFragmentInteractionListener");
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
    public void onStudentUpdated() {
        mListener.OnFinishEdit();
    }

    @Override
    public void onStudentNotFound() {
        Toast.makeText(getActivity(), "Student not found (ID)", Toast.LENGTH_SHORT).show();
    }

    public interface OnEditFragmentInteractionListener {
        void OnFinishEdit();

    }

    @Override
    public void onGetStudent(Student student) {
        if(student != null) {
            this.name.setText(student.getName());
            this.grade.setText(student.getGrade()+"");
        }
        else {
            Toast.makeText(getActivity(), "Student not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void setStudent(Student student) {
        if(this.calledOnCreateView == true && student != null) {
            this.id.setText(student.getId());
            this.name.setText(student.getName());
            this.grade.setText(student.getGrade() + "");
        }
    }
}
