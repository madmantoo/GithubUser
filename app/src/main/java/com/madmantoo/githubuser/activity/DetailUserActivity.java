package com.madmantoo.githubuser.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.madmantoo.githubuser.BuildConfig;
import com.madmantoo.githubuser.R;
import com.madmantoo.githubuser.adapter.SectionsPagerAdapter;
import com.madmantoo.githubuser.db.UserHelper;
import com.madmantoo.githubuser.model.UserItems;

import org.json.JSONObject;

import java.util.Objects;

import cz.msebera.android.httpclient.Header;

import static android.provider.BaseColumns._ID;
import static com.madmantoo.githubuser.db.DatabaseContract.UserColumns.AVATAR;
import static com.madmantoo.githubuser.db.DatabaseContract.UserColumns.CONTENT_URI;
import static com.madmantoo.githubuser.db.DatabaseContract.UserColumns.LOCATION;
import static com.madmantoo.githubuser.db.DatabaseContract.UserColumns.NAME;
import static com.madmantoo.githubuser.db.DatabaseContract.UserColumns.TYPE;
import static com.madmantoo.githubuser.db.DatabaseContract.UserColumns.USER_NAME;

public class DetailUserActivity extends AppCompatActivity {

    public static final String USERNAME = "username";
    private int id;
    private UserHelper userHelper;
    private UserItems userItems;
    private Uri uriWithId;

    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_DELETE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        showSectionPager();
        showDetail();

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userHelper = UserHelper.getInstance(getApplicationContext());
        userHelper.open();

        userItems = getIntent().getParcelableExtra(EXTRA_USER);
        if (userItems != null) {
            id = userItems.getId();
            int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        } else {
            userItems = new UserItems();
        }

        uriWithId = Uri.parse(CONTENT_URI + "/" + userItems.getId());
    }

    public void showDetail() {
        userItems = getIntent().getParcelableExtra(EXTRA_USER);

        Objects.requireNonNull(getSupportActionBar()).setTitle(userItems.getUsername());

        final ImageView imgPhoto = findViewById(R.id.img);
        final TextView txtLocation = findViewById(R.id.tv_location);
        final TextView txtName = findViewById(R.id.tv_name);

        AsyncHttpClient client = new AsyncHttpClient();
        String apiKey = BuildConfig.API_KEY;
        String url = "https://api.github.com/users/" + userItems.getUsername();
        client.addHeader("User-Agent", "request");
        client.addHeader("Authorization", "token " + apiKey);

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject object = new JSONObject(result);
                    userItems.setName(object.getString("name"));
                    userItems.setLocation(object.getString("location"));
                    txtName.setText(userItems.getName());
                    txtLocation.setText(userItems.getLocation());
                    Glide.with(DetailUserActivity.this)
                            .load(userItems.getImgPhoto())
                            .into(imgPhoto);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    public void showSectionPager() {
        String username = getIntent().getStringExtra(USERNAME);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        sectionsPagerAdapter.setData(username);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        Bundle mBundle = new Bundle();
        mBundle.putString(FollowingFragment.USERNAME, username);
        FollowersFragment mFollowerFragment = new FollowersFragment();
        mFollowerFragment.setArguments(mBundle);
        FollowingFragment mFollowingFragment = new FollowingFragment();
        mFollowingFragment.setArguments(mBundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        boolean exist = userHelper.checkFavorite(id);
        if (exist) {
            getMenuInflater().inflate(R.menu.ready_favorite_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.favorite_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_favorite) {
            boolean exist = userHelper.checkFavorite(id);
            if (exist) {
                showAlertDialog(ALERT_DIALOG_DELETE);
                item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));

            } else {
                ContentValues values = new ContentValues();
                values.put(_ID, userItems.getId());
                values.put(USER_NAME, userItems.getUsername());
                values.put(NAME, userItems.getName());
                values.put(TYPE, userItems.getType());
                values.put(AVATAR, userItems.getImgPhoto());
                values.put(LOCATION, userItems.getLocation());
                Uri nice = getContentResolver().insert(CONTENT_URI, values);
                String res = nice.getLastPathSegment();
                int result = Integer.parseInt(res);
                if (result > 0) {
                    Toast.makeText(DetailUserActivity.this, getString(R.string.success_add), Toast.LENGTH_SHORT).show();
                    item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                } else {
                    Toast.makeText(DetailUserActivity.this, getString(R.string.fail_add), Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showAlertDialog(int type) {
        final boolean isDialogDelete = type == ALERT_DIALOG_DELETE;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.title_delete);
        alertDialogBuilder
                .setMessage(R.string.ask_delete)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDialogDelete) {
                            getContentResolver().delete(uriWithId, null, null);
                            finish();
                            Toast.makeText(DetailUserActivity.this, R.string.success_delete, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailUserActivity.this, R.string.fail_delete, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
