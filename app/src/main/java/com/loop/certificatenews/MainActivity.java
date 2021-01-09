package com.loop.certificatenews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private Adapter newAdapter;
    private TextView emptyTextView;
    private static String GUARDIAN_BASED_URL="https://content.guardianapis.com/search?api-key=57778053-1565-4609-b191-4aee452f62f4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView newsList = findViewById(R.id.lv);
        emptyTextView = findViewById(R.id.tv);
        newsList.setEmptyView(emptyTextView);
        newAdapter = new Adapter(this, new ArrayList<News>());
        newsList.setAdapter(newAdapter);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News mainNews = newAdapter.getItem(position);
                Uri mainUri = Uri.parse(mainNews.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, mainUri);
                startActivity(intent);
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ntwrkInfo = connectivityManager.getActiveNetworkInfo();

        if (ntwrkInfo != null && ntwrkInfo.isConnected()) {
            LoaderManager loadManager = getLoaderManager();
            loadManager.initLoader(1, null, this);
        } else {
            emptyTextView.setText("check your internet connection");
        }
    }
    @Override
    public Loader<List<News>> onCreateLoader (int id, Bundle args){
        Uri newUri = Uri.parse(GUARDIAN_BASED_URL);
        Uri.Builder newBuilder = newUri.buildUpon();
        return new Load(this, newBuilder.toString());
    }

    @Override
    public void onLoadFinished (Loader < List < News >> loader, List < News > news){
        emptyTextView.setText("no news here");
        newAdapter.clear();

        if (news != null && !news.isEmpty()) {
            newAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset (Loader < List < News >> loader) {
        newAdapter.clear();
    }
}