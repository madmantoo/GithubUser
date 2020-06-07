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
import com.madmantoo.githubuser.model.FollowingItems;

import java.util.ArrayList;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder> {

    private ArrayList<FollowingItems> dataFollowing = new ArrayList<>();

    public void setDataFollowing(ArrayList<FollowingItems> items) {
        dataFollowing.clear();
        dataFollowing.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FollowingAdapter.FollowingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_following, viewGroup, false);
        return new FollowingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingAdapter.FollowingViewHolder holder, int position) {
        holder.bind(dataFollowing.get(position));
    }

    @Override
    public int getItemCount() {
        return dataFollowing.size();
    }

    public class FollowingViewHolder extends RecyclerView.ViewHolder {

        TextView username, type;
        ImageView imgPhoto;

        public FollowingViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.txt_username_following);
            type = itemView.findViewById(R.id.txt_type_following);
            imgPhoto = itemView.findViewById(R.id.img_following);
        }

        void bind(FollowingItems followingItems) {
            String url_image = "https://avatars1.githubusercontent.com/u/" + followingItems.getIdFollowing();

            username.setText(followingItems.getUsernameFollowing());
            type.setText(followingItems.getTypeFollowing());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(imgPhoto);
        }
    }
}
