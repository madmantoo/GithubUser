package com.madmantoo.githubuserconsumerapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.madmantoo.githubuserconsumerapp.R;
import com.madmantoo.githubuserconsumerapp.model.UserItems;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private ArrayList<UserItems> listFavorite = new ArrayList<>();
    private Activity activity;
    private FavoriteAdapter.OnItemClickCallback onItemClickCallback;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setOnItemClickCallback(FavoriteAdapter.OnItemClickCallback onItemClickcallback) {
        this.onItemClickCallback = onItemClickcallback;
    }

    public ArrayList<UserItems> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(ArrayList<UserItems> list) {
        this.listFavorite.clear();
        this.listFavorite.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(UserItems userItems) {
        this.listFavorite.add(userItems);
        notifyItemInserted(listFavorite.size() - 1);
    }

    public void updateItem(int position, UserItems userItems) {
        this.listFavorite.set(position, userItems);
        notifyItemChanged(position, userItems);
    }

    public void removeItem(int position) {
        this.listFavorite.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listFavorite.size());
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteAdapter.FavoriteViewHolder holder, int position) {

        holder.bind(listFavorite.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listFavorite.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }


    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvType;
        ImageView avatarFavorite;
        ListView listView;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.txt_username_favorite);
            tvType = itemView.findViewById(R.id.txt_type_favorite);
            listView = itemView.findViewById(R.id.rv_favorite);
            avatarFavorite = itemView.findViewById(R.id.avatar_favorite);
        }

        void bind(UserItems userItems) {
            String url_image = "https://avatars1.githubusercontent.com/u/" + userItems.getId();

            tvUsername.setText(userItems.getUsername());
            tvType.setText(userItems.getType());


            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(avatarFavorite);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(UserItems userItems);
    }
}
