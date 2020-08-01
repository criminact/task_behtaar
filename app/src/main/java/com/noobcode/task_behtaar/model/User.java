package com.noobcode.task_behtaar.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class User {
    @ColumnInfo(name = "id")
    @SerializedName("id")
    public String id;

    @ColumnInfo(name = "user_id")
    @SerializedName("userId")
    public String userId;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    public String title;

    @ColumnInfo(name = "body")
    @SerializedName("body")
    public String body;

    @PrimaryKey(autoGenerate = true)
    public int uuid;

    public User(String id, String userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
