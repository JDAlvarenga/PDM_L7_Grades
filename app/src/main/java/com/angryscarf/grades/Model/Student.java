package com.angryscarf.grades.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Jaime on 5/21/2018.
 */

@Entity
public class Student {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;

    private float grade;

    public Student(String id, String name, float grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getGrade() {
        return grade;
    }
}
