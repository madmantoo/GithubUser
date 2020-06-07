package com.madmantoo.githubuser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.madmantoo.githubuser.R;
import com.madmantoo.githubuser.adapter.UsersAdapter;
import com.madmantoo.githubuser.model.UserItems;
import com.madmantoo.githubuser.viewModel.UserViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private UsersAdapter adapter;
    private EditText edtSearch;
    private ProgressBar progressBar;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSearch = findViewById(R.id.edt_search);
        progressBar = findViewById(R.id.progress_bar_detail);
        Button btnSearch = findViewById(R.id.btn_search);

        showRecyclerList();

        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtSearch.getText().toString();

                if (TextUtils.isEmpty(username)) return;

                showLoading(true);
                userViewModel.setUsers(username);
            }
        });

        userViewModel.getUsers().observe(this, new Observer<ArrayList<UserItems>>() {
            @Override
            public void onChanged(ArrayList<UserItems> userItems) {
                if (userItems != null) {
                    adapter.setData(userItems);
                    showLoading(false);
                }
            }
        });

    }

    private void showRecyclerList() {
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsersAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickCallback(new UsersAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(UserItems data) {
                showSelectedUser(data);
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

    private void showSelectedUser(UserItems userItems) {
        Intent moveDetail = new Intent(MainActivity.this, DetailUserActivity.class);
        moveDetail.putExtra(DetailUserActivity.EXTRA_USER, userItems);
        moveDetail.putExtra(DetailUserActivity.USERNAME, userItems.getUsername());
        startActivity(moveDetail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(this, SettingActivity.class);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.action_favorite) {
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
