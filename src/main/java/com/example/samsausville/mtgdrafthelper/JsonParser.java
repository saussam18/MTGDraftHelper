package com.example.samsausville.mtgdrafthelper;

import org.json.JSONException;
import org.json.JSONObject;


public class JsonParser {


    public static Card parseFeed(JSONObject obj) {

        try {
            Card card = new Card();
            card.setName(obj.getString("name"));
            card.setManaCost(obj.getString("manaCost"));
            card.setRarity(obj.getString("rarity"));
            card.setType(obj.getString("type"));
            card.setText(obj.getString("text"));
            card.setImageId(obj.getInt("multiverseid"));
            return card;
        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

}








