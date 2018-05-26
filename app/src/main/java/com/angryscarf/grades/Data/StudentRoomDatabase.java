package com.angryscarf.grades.Data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.angryscarf.grades.Model.Student;

import java.util.ArrayList;

/**
 * Created by Jaime on 5/21/2018.
 */

@Database(entities = {Student.class}, version = 1)
public abstract class StudentRoomDatabase extends RoomDatabase{
    public abstract StudentDao studentDao();

    private static StudentRoomDatabase INSTANCE;

    public static StudentRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    StudentRoomDatabase.class,
                    "word_database")
                    .addCallback(sRoomDatabaseCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDBAsync(INSTANCE).execute();
        }
    };

    private static class populateDBAsync extends AsyncTask<Void, Void, Void> {
        private final StudentDao mDao;

        populateDBAsync(StudentRoomDatabase db){
            this.mDao = db.studentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();
            mDao.insert(new Student("001","Okabe", 7.4f));
            mDao.insert(new Student("002","Mayuri", 6.9f));
            mDao.insert(new Student("003","Daru", 8.3f));
            mDao.insert(new Student("004","Kurisu", 9.6f));

            return null;
        }
    }
}
