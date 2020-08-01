package com.noobcode.task_behtaar.network;

import com.noobcode.task_behtaar.model.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetUsersService {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private UserAPI userAPI = null;

    public GetUsersService(){
        userAPI = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(UserAPI.class);
    }

    //RX SINGLE FOR CODE CONSISTENCY
    public Single<List<User>> getUsers(){
        return userAPI.getUsers();
    }

}
