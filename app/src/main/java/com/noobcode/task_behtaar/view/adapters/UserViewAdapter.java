package com.noobcode.task_behtaar.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noobcode.task_behtaar.R;
import com.noobcode.task_behtaar.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* ADAPTER CLASS FOR userView(defined in MainActivity.class) instanceof RECYCLERVIEW
* */

public class UserViewAdapter extends RecyclerView.Adapter<UserViewAdapter.ViewHolder> {

    List<User> users;

    public UserViewAdapter(List<User> users) {
        this.users = users;
    }

    public void updateList(List<User> users){
        this.users.clear();
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_card, parent, false);
        return new UserViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.id.setText("Id - " + users.get(position).id);
            holder.userId.setText("UserId - " + users.get(position).userId);
            holder.title.setText("Title - " + users.get(position).title);
            holder.body.setText("Body - "+ users.get(position).body);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.userid)
        TextView userId;

        @BindView(R.id.id)
        TextView id;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.body)
        TextView body;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
