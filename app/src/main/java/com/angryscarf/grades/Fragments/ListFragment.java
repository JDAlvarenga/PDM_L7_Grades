package com.angryscarf.grades.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angryscarf.grades.Model.GradesViewModel;
import com.angryscarf.grades.Model.Student;
import com.angryscarf.grades.R;

import java.util.List;


public class ListFragment extends Fragment implements RVStudentsAdapter.OnStudentSelected{

    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecycler;
    private TextView textAverage;
    private RVStudentsAdapter adapter;

    private GradesViewModel gradesViewModel;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RVStudentsAdapter(getContext(), this);
        gradesViewModel = ViewModelProviders.of(getActivity()).get(GradesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        textAverage = v.findViewById(R.id.list_text_average);
        mRecycler = v.findViewById(R.id.list_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(adapter);

        gradesViewModel.getAllStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable List<Student> students) {

                adapter.setStudents(students);
            }
        });

        gradesViewModel.getAverageGrade().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float grade) {
                if(grade != null)
                    textAverage.setText(grade.toString());
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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

    @Override
    public void selected(Student student) {
        gradesViewModel.setLastSelectedStudent(student);
        mListener.OnStudentSelected(student);
    }

    public interface OnListFragmentInteractionListener {
        void OnStudentSelected(Student student);
    }
}
