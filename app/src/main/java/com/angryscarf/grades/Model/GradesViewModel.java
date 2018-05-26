package com.angryscarf.grades.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.angryscarf.grades.Data.StudentRepository;
import com.angryscarf.grades.Model.Student;

import java.util.List;

/**
 * Created by Jaime on 5/22/2018.
 */

public class GradesViewModel extends AndroidViewModel {
    private StudentRepository mRepository;

    private LiveData<List<Student>> mAllStudents;
    private LiveData<Float> avgGrade;

    private Student lastSelectedStudent;

    public GradesViewModel(Application application) {
        super(application);
        mRepository = new StudentRepository(application);
        mAllStudents = mRepository.getAllStudents();
        avgGrade = mRepository.getAverageGrade();
    }

    public LiveData<List<Student>> getAllStudents() {
        return mAllStudents;
    }
    public LiveData<Float> getAverageGrade() { return avgGrade; }

    public void insertStudent(Student student, StudentRepository.OnStudentAddedCallback addedCallback) {
        mRepository.insertStudent(student, addedCallback);
    }

    public void updateStudent(Student student, StudentRepository.OnStudentUpdatedCallback updatedCallback) {
        mRepository.updateStudent(student, updatedCallback);
    }

    public void getStudent(String sid, StudentRepository.OnGetStudentCallback getCallback) {
        mRepository.getStudent(sid,getCallback );
    }


    public Student getLastSelectedStudent() {
        return lastSelectedStudent;
    }

    public void setLastSelectedStudent(Student lastSelectedStudent) {
        this.lastSelectedStudent = lastSelectedStudent;
    }
}
