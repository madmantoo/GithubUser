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
import com.madmantoo.githubuser.model.UserItems;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private ArrayList<UserItems> mData = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickcallback) {
        this.onItemClickCallback = onItemClickcallback;
    }


    public void setData(ArrayList<UserItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_list, viewGroup, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersViewHolder holder, int position) {
        holder.bind(mData.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(mData.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView username, type;
        ImageView imgPhoto;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.txt_username);
            type = itemView.findViewById(R.id.txt_type);
            imgPhoto = itemView.findViewById(R.id.img_photo);

        }

        void bind(UserItems userItems) {
            String url_image = "https://avatars1.githubusercontent.com/u/" + userItems.getId();

            username.setText(userItems.getUsername());
            type.setText(userItems.getType());


            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(imgPhoto);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(UserItems data);
    }
}

