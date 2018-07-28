package com.example.android.technews;

public class Article {

    private String mHeadline;
    private String mUrl;
    private String mSummary;
    private String mImageUrl = null;
    private String mByline;

    private String mDate;

    public Article(String headline, String url, String summary, String imageUrl, String byline, String date){
        mHeadline = headline;
        mUrl = url;
        mSummary = summary;
        mImageUrl = imageUrl;
        mByline = byline;
        mDate = date;
    }
    public Article(String headline, String url, String summary, String byline){
        mHeadline = headline;
        mUrl = url;
        mSummary = summary;
        mByline = byline;
    }


    public String getHeadline(){
        return mHeadline;}

    public String getUrl(){
        return mUrl;
    }
    public String getSummary(){
        return mSummary;
    }
    public String getImageUrl(){
        return mImageUrl;
    }
    public String getByline(){
        return mByline;
    }
    public String getPubishedDate(){
        return mDate;
    }
    public boolean hasImage(){

        return mImageUrl != null;
    }


}
