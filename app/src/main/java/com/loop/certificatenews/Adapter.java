package com.loop.certificatenews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<News> {

public Adapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
        }

@Override
public View getView(int position, View convertView, ViewGroup parentLinear) {
        View list = convertView;
        if (list == null) {
        list = LayoutInflater.from(getContext()).inflate(R.layout.news, parentLinear, false);
        }

        News currentNews = getItem(position);
        TextView titleHere = list.findViewById(R.id.title);
        String topic = currentNews.getTitle();
        titleHere.setText(topic);


        TextView newCategory = list.findViewById(R.id.category);
        String category = currentNews.getCategory();
        newCategory.setText(category);

        TextView newDate = list.findViewById(R.id.date);
        String date = currentNews.getDate();
        newDate.setText(date);

        TextView newAuthor = list.findViewById(R.id.authorname);
        String author = currentNews.getAuthor();
        newAuthor.setText(author);

        return list;
        }
        }
