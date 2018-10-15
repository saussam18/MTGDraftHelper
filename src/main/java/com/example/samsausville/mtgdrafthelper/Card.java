package com.example.samsausville.mtgdrafthelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


public class Card implements Serializable {

    private String name;
    private String type;
    private String rarity;
    private String text;
    private String ManaCost;
    private int imageId;

    private ArrayList<Double> ratings = new ArrayList<>();
    private ArrayList<String> reviews = new ArrayList<>();

    public Card(){

    }
    public ArrayList<Double> getRatings() {
        return ratings;
    }
    public String getRatingsString() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uniqueArrays", new JSONArray(ratings));
        String arrayList = json.toString();
        return arrayList;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }
    public String getReviewsString() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uniqueArrays2", new JSONArray(reviews));
        String arrayList = json.toString();
        return arrayList;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public void setRatings(ArrayList<Double> ratings) {
        this.ratings = ratings;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setManaCost(String manaCost) {
        ManaCost = manaCost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getRarity() {
        return rarity;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }


    public String getManaCost() {
        return ManaCost;
    }

    public int getImageId() {
        return imageId;
    }


}

