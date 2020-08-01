package com.noobcode.task_behtaar.network;

import com.noobcode.task_behtaar.model.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface UserAPI {
    @GET("posts")
    Single<List<User>> getUsers();
}
