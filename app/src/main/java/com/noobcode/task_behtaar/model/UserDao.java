package com.noobcode.task_behtaar.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/*
* DATA ACCESS OBJECT FOR USER POJO
* */

@Dao
public interface UserDao {
    //To Insert Data to Local
    @Insert
    List<Long> insertAll(User... users);

    //To Get Data from Local
    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    //To Delete Data from Local
    @Query("DELETE FROM User")
    void deleteAllUsers();
}
