package com.madmantoo.githubuser.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.madmantoo.githubuser.R;
import com.madmantoo.githubuser.helper.MappingHelper;
import com.madmantoo.githubuser.model.UserItems;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.madmantoo.githubuser.db.DatabaseContract.UserColumns.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> widgetItems = new ArrayList<>();
    private final Context context;
    private ArrayList<UserItems> listFavorite = new ArrayList<>();

    StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

        Cursor favoriteItem = context.getContentResolver().query(CONTENT_URI, null, null, null, null);

        ArrayList<UserItems> list = MappingHelper.mapCursorToArrayList(favoriteItem);

        listFavorite.addAll(list);
        URL avatarURL = null;
        for (int i = 0; i < listFavorite.size(); i++) {
            try {
                avatarURL = new URL("https://avatars1.githubusercontent.com/u/" + listFavorite.get(i).getId());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                widgetItems.add(BitmapFactory.decodeStream(avatarURL.openConnection().getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        remoteViews.setImageViewBitmap(R.id.imageView, widgetItems.get(position));

        Bundle extras = new Bundle();
        extras.putInt(ImageUserWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
