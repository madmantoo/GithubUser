package com.madmantoo.githubuserconsumerapp;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.madmantoo.githubuserconsumerapp.adapter.FavoriteAdapter;
import com.madmantoo.githubuserconsumerapp.db.DatabaseContract;
import com.madmantoo.githubuserconsumerapp.helper.MappingHelper;
import com.madmantoo.githubuserconsumerapp.model.UserItems;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements LoadUserCallback {

    private ProgressBar progressBar;
    private RecyclerView rvFavorite;
    private FavoriteAdapter adapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("consumer app");
        }

//        userHelper = UserHelper.getInstance(getApplicationContext());
//        userHelper.open();

        progressBar = findViewById(R.id.progress_bar_favorite);
        rvFavorite = findViewById(R.id.rv_favorite);

        showRecycler();

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(DatabaseContract.UserColumns.CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadUserAsync(this, this).execute();
        } else {
            ArrayList<UserItems> items = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (items != null) {
                adapter.setListFavorite(items);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadUserAsync(this, this).execute();
    }

    private void showRecycler() {
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvFavorite.setHasFixedSize(true);
        adapter = new FavoriteAdapter(this);
        rvFavorite.setAdapter(adapter);

//        adapter.setOnItemClickCallback(new FavoriteAdapter.OnItemClickCallback() {
//            @Override
//            public void onItemClicked(UserItems userItems) {
//                Intent moveDetail = new Intent(FavoriteActivity.this, DetailUserActivity.class);
//                moveDetail.putExtra(DetailUserActivity.EXTRA_USER, userItems);
//                moveDetail.putExtra(DetailUserActivity.USERNAME, userItems.getUsername());
//                startActivity(moveDetail);
//            }
//        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListFavorite());
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<UserItems> userItems) {
        progressBar.setVisibility(View.INVISIBLE);
        if (userItems.size() > 0) {
            adapter.setListFavorite(userItems);
        } else {
            adapter.setListFavorite(new ArrayList<UserItems>());
            Toast.makeText(this, "tidak ada data", Toast.LENGTH_SHORT).show();
        }
    }

    private static class LoadUserAsync extends AsyncTask<Void, Void, ArrayList<UserItems>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadUserCallback> weakCallback;

        private LoadUserAsync(Context context, LoadUserCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<UserItems> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DatabaseContract.UserColumns.CONTENT_URI, null, null, null, null);
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<UserItems> userItems) {
            super.onPostExecute(userItems);

            weakCallback.get().postExecute(userItems);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (data != null) {
//            if (requestCode == DetailUserActivity.REQUEST_ADD) {
//                if (resultCode == DetailUserActivity.RESULT_ADD) {
//                    UserItems userItems = data.getParcelableExtra(DetailUserActivity.EXTRA_USER);
//
//                    adapter.addItem(userItems);
//                    rvFavorite.smoothScrollToPosition(adapter.getItemCount() - 1);
//                }
//            } else if (resultCode == DetailUserActivity.RESULT_DELETE) {
//                int position = data.getIntExtra(DetailUserActivity.EXTRA_POSITION, 0);
//                adapter.removeItem(position);
//            }
//        }
//    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadUserAsync(context, (LoadUserCallback) context).execute();
        }
    }



//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        userHelper.close();
//    }
}

interface LoadUserCallback {
    void preExecute();

    void postExecute(ArrayList<UserItems> userItems);
}
