package com.madmantoo.githubuser.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.madmantoo.githubuser.R;
import com.madmantoo.githubuser.adapter.FollowersAdapter;
import com.madmantoo.githubuser.model.FollowerItems;
import com.madmantoo.githubuser.viewModel.FollowersViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersFragment extends Fragment {

    public static String USERNAME = "username";
    private FollowersViewModel followersViewModel;
    private FollowersAdapter adapter;
    private ProgressBar progressBar;

    public FollowersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvFollowers = view.findViewById(R.id.rv_followers);
        progressBar = view.findViewById(R.id.progress_bar_followers);

        rvFollowers.setHasFixedSize(true);
        rvFollowers.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FollowersAdapter();
        adapter.notifyDataSetChanged();
        rvFollowers.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showRecycle();
        showLoading(true);

        if (getArguments() != null) {
            String username = getArguments().getString(USERNAME);
            followersViewModel.setFollowers(username);
        }
    }

    private void showRecycle() {
        followersViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel.class);

        followersViewModel.getFollowers().observe(getViewLifecycleOwner(), new Observer<ArrayList<FollowerItems>>() {
            @Override
            public void onChanged(ArrayList<FollowerItems> followerItems) {
                if (followerItems != null) {
                    adapter.setDataFollowers(followerItems);
                    showLoading(false);
                }
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
