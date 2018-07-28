package com.example.android.technews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArticleAdapter extends ArrayAdapter {
        private static final String LOG_TAG =ArticleAdapter.class.getSimpleName();
    public ArticleAdapter(@NonNull Context context, int resource, @NonNull List<Article> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.article_layout, parent,false);
        }

        Article currentArticle = (Article) getItem(position);

        ImageView image = listItemView.findViewById(R.id.image);
        if (currentArticle.hasImage()) {
            Picasso.get()
                    .load(currentArticle.getImageUrl())
                    .centerCrop()
                    .fit()
                    .into(image);
        }else{
            Picasso.get().load(R.drawable.ic_launcher_background).centerCrop().fit().into(image);
        }

        TextView title = listItemView.findViewById(R.id.title);
        title.setText(currentArticle.getHeadline());
        Log.v(LOG_TAG, currentArticle.getHeadline());

        TextView summary = listItemView.findViewById(R.id.summary);
        summary.setText(currentArticle.getSummary());
        Log.v(LOG_TAG, currentArticle.getSummary());

        TextView byline = listItemView.findViewById(R.id.author);
        byline.setText(stringFormatter(currentArticle.getByline()));
        Log.v(LOG_TAG, currentArticle.getByline());


        TextView datePublished = listItemView.findViewById(R.id.date_published);
        String currentPublishedDate = currentArticle.getPubishedDate();
        String date = getFormattedDate("yyyy-MM-dd'T'HH:mm:ss'Z'","MMMM dd, yyyy",currentPublishedDate);
        datePublished.setText(date);

        Log.d(LOG_TAG,"why won't it change "
                + date);



        return listItemView;
    }


    public static String getFormattedDate(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            Log.v(LOG_TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }
    public static String stringFormatter(String name){
        String formatted = "";
        String splitString = "and";
        if(name.contains(splitString)){
            String [] splitArray = name.split(splitString);
            formatted =  splitArray[0];
        }else{
            formatted = name;
        }
        return formatted;
    }

}
