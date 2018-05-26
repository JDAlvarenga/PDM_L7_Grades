package com.angryscarf.grades.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.angryscarf.grades.Model.Student;

import java.util.List;

/**
 * Created by Jaime on 5/21/2018.
 */

@Dao
public interface StudentDao {

    @Insert
    long insert(Student student);

    @Insert
    List<Long> insert (List<Student> students);

    @Update
    int update(Student student);

    @Delete
    int delete(Student student);

    @Query("DELETE FROM student")
    void deleteAll();

    @Query("SELECT * FROM student ORDER BY name ASC")
    LiveData<List<Student>> getAllStudents();

    @Query("SELECT * FROM student WHERE id = :sid LIMIT 1")
    Student getStudent(String sid);

    @Query("SELECT round(avg(grade),2) FROM student")
    LiveData<Float> getAverage();

//    @Query("SELECT * FROM student WHERE _id = last_insert_rowid()")
//    LiveData<Student> lastStudent();


}
