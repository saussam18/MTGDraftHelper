package com.example.samsausville.mtgdrafthelper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CardSearchResults extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView.Adapter adapter;

    private String findName;
    private ArrayList<Card> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_search_results);
        Intent intent = getIntent();
        findName = intent.getStringExtra("name");
        getJson(this);
        changeArrayListToStart();
        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        adapter= new MyAdapter(cards, this);
        rv.setAdapter(adapter);
    }



        public void getJson (Context context){
        String json = null;
        try{
            JsonParser cardParser = new JsonParser();
            InputStream is = context.getAssets().open("xln.json");
            int size = is.available();
            byte [] buffer= new byte [size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("cards");
            for(int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                if(obj.getString("name").toLowerCase().contains(findName.toLowerCase())){
                   Card c = cardParser.parseFeed(obj);
                   cards.add(c);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "File Read Error", Toast.LENGTH_LONG).show();
            }catch(JSONException e){
            Toast.makeText(getApplicationContext(), "Json Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }



    public void changeArrayListToStart(){
        for(int i = 0; i < cards.size(); i++){
            String check = cards.get(i).getName();
            check = check.substring(0, findName.length());
            if(check.equalsIgnoreCase(findName)){
                Card temp = cards.get(i);
                cards.remove(i);
                cards.add(0, temp);
            }
        }
    }

}





