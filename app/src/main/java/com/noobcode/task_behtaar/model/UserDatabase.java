package com.noobcode.task_behtaar.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/*
* DATABASE CLASS FOR USER POJO
 */

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;
    public static UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class,
                    "UserDatabase")
                    .build();
        }

        return instance;
    }

    public abstract UserDao userDao();
}
