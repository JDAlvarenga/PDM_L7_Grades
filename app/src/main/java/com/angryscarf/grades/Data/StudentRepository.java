package com.angryscarf.grades.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.angryscarf.grades.Model.Student;

import java.util.List;

/**
 * Created by Jaime on 5/22/2018.
 */

public class StudentRepository {
    private StudentDao mStudentDao;
    private LiveData<List<Student>> mAllStudents;
    private LiveData<Float> average;

    public StudentRepository(Application application) {
        StudentRoomDatabase db = StudentRoomDatabase.getDatabase(application);

        mStudentDao = db.studentDao();
        average = mStudentDao.getAverage();
        mAllStudents = mStudentDao.getAllStudents();
    }

    public LiveData<List<Student>> getAllStudents() {
        return mAllStudents;
    }

    public LiveData<Float> getAverageGrade() { return this.average;}


    public void insertStudent(Student student, OnStudentAddedCallback callback) {
        new insertStudentAsyncTask(mStudentDao, callback).execute(student);
    }

    public void updateStudent(Student student, OnStudentUpdatedCallback callback) {
        new updateStudentAsyncTask(mStudentDao, callback).execute(student);
    }

    public void getStudent(String sid, @NonNull OnGetStudentCallback callback) {
        new getStudentAsyncTask(mStudentDao, callback).execute(sid);
    }



    private static class insertStudentAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDao mAsyncTaskDao;
        private OnStudentAddedCallback callback;

        public insertStudentAsyncTask(StudentDao dao, @Nullable OnStudentAddedCallback callback) {
            mAsyncTaskDao = dao;
            this.callback = callback;
        }

            @Override
            protected Void doInBackground(Student... students) {
                if(mAsyncTaskDao.getStudent(students[0].getId()) == null) {
                    mAsyncTaskDao.insert(students[0]);
                }
                else{
                    cancel(true);
                }
                return null;
            }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(callback != null){
                callback.onStudentAdded();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            callback.onStudentExists();
        }
    }

    private static class updateStudentAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDao mAsyncTaskDao;
        private OnStudentUpdatedCallback callback;

        public updateStudentAsyncTask(StudentDao dao, @Nullable OnStudentUpdatedCallback callback) {
            mAsyncTaskDao = dao;
            this.callback = callback;
        }

            @Override
            protected Void doInBackground(Student... students) {
                if(mAsyncTaskDao.getStudent(students[0].getId()) != null) {
                    mAsyncTaskDao.update(students[0]);
                }
                else {
                    cancel(true);
                }
                return null;
            }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            callback.onStudentNotFound();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(callback != null){
                callback.onStudentUpdated();
            }
        }
    }

    private static class getStudentAsyncTask extends AsyncTask<String, Student, Student> {

        private StudentDao mAsyncTaskDao;
        private OnGetStudentCallback callback;

        public getStudentAsyncTask(StudentDao dao, @NonNull OnGetStudentCallback callback) {
            mAsyncTaskDao = dao;
            this.callback = callback;
        }

        @Override
        protected Student doInBackground(String... strings) {
            return mAsyncTaskDao.getStudent(strings[0]);
        }

        @Override
        protected void onPostExecute(Student student) {
            super.onPostExecute(student);
            callback.onGetStudent(student);

        }
    }

    public interface OnGetStudentCallback {
        void onGetStudent(Student student);
    }

    public interface  OnStudentAddedCallback {
        void onStudentAdded();
        void onStudentExists();
    }

    public interface  OnStudentUpdatedCallback {
        void onStudentUpdated();
        void onStudentNotFound();
    }
}
