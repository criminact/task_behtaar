package com.noobcode.task_behtaar.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.noobcode.task_behtaar.model.User;
import com.noobcode.task_behtaar.model.UserDao;
import com.noobcode.task_behtaar.model.UserDatabase;
import com.noobcode.task_behtaar.network.GetUsersService;
import com.noobcode.task_behtaar.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {

    public MutableLiveData<List<User>> users = new MutableLiveData<List<User>>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> error = new MutableLiveData<Boolean>();

    private GetUsersService service = new GetUsersService();

    //for easy cancelling of tasks
    private CompositeDisposable disposable = new CompositeDisposable();

    //to run heavy tasks on new thread
    private AsyncTask<List<User>, Void, List<User>> insertUserTask = null;
    private AsyncTask<Void, Void, List<User>> retrieveUserTask = null;

    private SharedPreferencesHelper prefHelper = SharedPreferencesHelper.getInstance(getApplication());

    //default refresh time of 1 minute - time in nanoseconds
    private long refreshTime = 60 * 1000 * 1000 * 1000L;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void getData() {
        long updateTime = prefHelper.getUpdateTime();
        long currentTime = System.nanoTime();
        if (updateTime != 0 && currentTime - updateTime < refreshTime) {
            //fetches from room if the last update was less than the refresh time
            getFromDatabse();
        } else {
            //fetches from remote if the refresh time exceeds
            getFromRemote();
        }
    }

    private void getFromDatabse() {
        loading.setValue(true);
        error.setValue(false);
        retrieveUserTask = new RetrieveUserTask();
        retrieveUserTask.execute();
    }

    private void getFromRemote() {
        loading.setValue(true);
        error.setValue(false);
        disposable.add(
                service.getUsers()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<User>>() {
                            @Override
                            public void onSuccess(List<User> newUsers) {
                                insertUserTask = new InsertUserTask();
                                insertUserTask.execute(newUsers);
                                Toast.makeText(getApplication(), "Fetched from API", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                loading.setValue(false);
                                error.setValue(true);
                                e.printStackTrace();
                            }
                        })
        );
    }

    private void userRetrieved(List<User> newUsers) {
        loading.setValue(false);
        error.setValue(false);
        users.setValue(newUsers);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

        if (insertUserTask != null) {
            insertUserTask.cancel(true);
            insertUserTask = null;
        }

        if (retrieveUserTask != null) {
            retrieveUserTask.cancel(true);
            retrieveUserTask = null;
        }
    }

    //INNER CLASS TO RETRIEVE AND INSERT DATA IN ROOM

    private class InsertUserTask extends AsyncTask<List<User>, Void, List<User>> {

        @Override
        protected List<User> doInBackground(List<User>... lists) {
            List<User> list = lists[0];
            UserDao userDao = UserDatabase.getInstance(getApplication()).userDao();
            userDao.deleteAllUsers();

            ArrayList<User> newList = new ArrayList<>(list);
            List<Long> result = userDao.insertAll(newList.toArray(new User[0]));

            int i = 0;
            while (i < list.size()) {
                list.get(i).uuid = result.get(i).intValue();
                ++i;
            }

            return list;
        }

        //for main thread execution
        @Override
        protected void onPostExecute(List<User> users) {
            userRetrieved(users);
            //updates updated time in Shared Preferences once the data is fetched from remote
            prefHelper.saveUpdateTime(System.nanoTime());
        }
    }

    public class RetrieveUserTask extends AsyncTask<Void, Void, List<User>> {
        @Override
        protected List<User> doInBackground(Void... voids) {
            return UserDatabase.getInstance(getApplication()).userDao().getAllUsers();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            userRetrieved(users);
            Toast.makeText(getApplication(), "Fetched from Room Database", Toast.LENGTH_SHORT).show();
        }
    }
}
