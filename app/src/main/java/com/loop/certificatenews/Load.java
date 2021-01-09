package com.loop.certificatenews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class Load extends AsyncTaskLoader<List<News>> {
    private static String newUrl;
    public Load(Context context, String url) {
        super(context);
        newUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public List<News> loadInBackground() {
        if (newUrl == null) {
            return null;
        }

        List<News> list = QueryUtils.fetchNewsData(newUrl);
        return list;
    }
}
