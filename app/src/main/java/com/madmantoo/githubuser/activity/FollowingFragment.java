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
import com.madmantoo.githubuser.adapter.FollowingAdapter;
import com.madmantoo.githubuser.model.FollowingItems;
import com.madmantoo.githubuser.viewModel.FollowingViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {
    public static String USERNAME = "username";
    private FollowingViewModel followingViewModel;
    private FollowingAdapter adapter;
    private ProgressBar progressBar;

    public FollowingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvFollowing = view.findViewById(R.id.rv_following);
        progressBar = view.findViewById(R.id.progress_bar_following);

        rvFollowing.setHasFixedSize(true);
        rvFollowing.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FollowingAdapter();
        adapter.notifyDataSetChanged();
        rvFollowing.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showRecycle();
        showLoading(true);

        if (getArguments() != null) {
            String username = getArguments().getString(USERNAME);
            followingViewModel.setFolowing(username);
        }
    }

    private void showRecycle() {
        followingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel.class);

        followingViewModel.getFollowing().observe(getViewLifecycleOwner(), new Observer<ArrayList<FollowingItems>>() {
            @Override
            public void onChanged(ArrayList<FollowingItems> followingItems) {
                if (followingItems != null) {
                    adapter.setDataFollowing(followingItems);
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
