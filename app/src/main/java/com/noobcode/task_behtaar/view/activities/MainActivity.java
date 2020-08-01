package com.noobcode.task_behtaar.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.noobcode.task_behtaar.R;
import com.noobcode.task_behtaar.model.User;
import com.noobcode.task_behtaar.view.adapters.UserViewAdapter;
import com.noobcode.task_behtaar.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
* USED BUTTERKNIFE FOR VIEWBINDING, (NOW DEPRECATED)
* */

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    @BindView(R.id.user_recyclerview)
    RecyclerView userView;

    @BindView(R.id.error)
    TextView error;

    @BindView(R.id.progress)
    ProgressBar progress;

    private Unbinder unbinder;

    private UserViewAdapter adapter = new UserViewAdapter(new ArrayList<User>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        userView.setLayoutManager(new LinearLayoutManager(this));
        userView.setAdapter(adapter);

        viewModel.getData();

        observeData();
    }

    private void observeData() {
        viewModel.users.observe(this, users -> {
            if (users != null) {
                adapter.updateList(users);
            }
        });

        viewModel.error.observe(this, isError -> {
            if (isError != null) {
                error.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
