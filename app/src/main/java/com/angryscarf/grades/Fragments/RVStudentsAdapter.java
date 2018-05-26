package com.angryscarf.grades.Fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angryscarf.grades.Model.Student;
import com.angryscarf.grades.R;

import java.util.List;

/**
 * Created by Jaime on 5/23/2018.
 */

public class RVStudentsAdapter extends RecyclerView.Adapter<RVStudentsAdapter.StudentViewHolder>{

    private final LayoutInflater mInflater;
    private List<Student> mStudents;
    private OnStudentSelected mListener;

    public RVStudentsAdapter(Context context, @Nullable OnStudentSelected selectedCallback) {
        mInflater = LayoutInflater.from(context);
        mListener = selectedCallback;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_student_list, parent, false);

        final StudentViewHolder vHolder = new StudentViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null) {
                    mListener.selected(mStudents.get(vHolder.getAdapterPosition()));
                }
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        if(mStudents != null) {
            Student s = mStudents.get(position);
            holder.id.setText(s.getId());
            holder.name.setText(s.getName());
            holder.grade.setText(s.getGrade()+"");
        }
        else {
            holder.name.setText("---------");
            holder.grade.setText("--");
        }

    }

    @Override
    public int getItemCount() {
        if(mStudents != null){
            return mStudents.size();
        }
        return 0;
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView name;
        private final TextView grade;

        public StudentViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_text_id);
            name = itemView.findViewById(R.id.item_text_name);
            grade = itemView.findViewById(R.id.item_text_grade);
        }
    }

    public void setStudents(List<Student> students) {
        this.mStudents = students;
        notifyDataSetChanged();
    }

    interface OnStudentSelected{
        void selected(Student student);
    }
}
