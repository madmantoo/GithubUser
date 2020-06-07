package com.madmantoo.githubuser.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.madmantoo.githubuser.R;
import com.madmantoo.githubuser.model.FollowerItems;

import java.util.ArrayList;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.FollowerViewHolder> {

    private final ArrayList<FollowerItems> dataFollowers = new ArrayList<>();

    public void setDataFollowers(ArrayList<FollowerItems> items) {
        dataFollowers.clear();
        dataFollowers.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FollowerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_followers, viewGroup, false);
        return new FollowersAdapter.FollowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerViewHolder holder, int position) {
        holder.bind(dataFollowers.get(position));
    }

    @Override
    public int getItemCount() {
        return dataFollowers.size();
    }

    public class FollowerViewHolder extends RecyclerView.ViewHolder {
        TextView username, type;
        ImageView imgPhoto;

        public FollowerViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.txt_username_followers);
            type = itemView.findViewById(R.id.txt_type_followers);
            imgPhoto = itemView.findViewById(R.id.img_followers);
        }

        void bind(FollowerItems followerItems) {
            String url_image = "https://avatars1.githubusercontent.com/u/" + followerItems.getIdFollower();

            username.setText(followerItems.getUsernameFollower());
            type.setText(followerItems.getTypeFollower());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(imgPhoto);
        }
    }
}
