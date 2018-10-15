package com.example.samsausville.mtgdrafthelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CardDisplay extends AppCompatActivity implements View.OnClickListener{

    private Card card;
    private ArrayAdapter<String> listAdapter ;

    final DatabaseHandler db = new DatabaseHandler(this);
    EditText editText;
    DecimalFormat df;
    RatingBar rb;
    Button sb1, sb2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_display);
        //db.removeAll();
        Intent intent = getIntent();
        card = (Card) intent.getSerializableExtra("card");
        TextView tvName = (TextView) findViewById(R.id.cName);
        tvName.setText(card.getName());
        TextView tvType = (TextView) findViewById(R.id.cType);
        tvType.setText(card.getType());
        TextView tvManaCost = (TextView) findViewById(R.id.cMana);
        tvManaCost.setText(card.getManaCost());
        TextView tvRarity = (TextView) findViewById(R.id.cRarity);
        tvRarity.setText(card.getRarity());
        TextView tvDesc = (TextView) findViewById(R.id.cDesc);
        tvDesc.setText(card.getText());
        String beginImageUrl = "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=";
        int multiverseId = card.getImageId();
        String endImageUrl = "&type=card";
        String imageUrl = beginImageUrl + String.valueOf(multiverseId) + endImageUrl;
        ImageView ivImage = findViewById(R.id.cImage);
        new ImageDownloader(ivImage).execute(imageUrl);
        rb  = findViewById(R.id.cRating);
        df = new DecimalFormat("0.00");
        TextView tvRating = (TextView) findViewById(R.id.cTotal);
        ArrayList<Double> beforeRatings = new ArrayList<>();
        if (db.containsCard(card) == true) {
            try {
                card = db.getCardRating(card);
                beforeRatings = card.getRatings();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            double beforeTotal = 0;
            for (int i = 0; i < beforeRatings.size(); i++) {
                beforeTotal += beforeRatings.get(i);
            }
            beforeTotal = beforeTotal / beforeRatings.size();
            tvRating.setText("Current Rating : " + df.format(beforeTotal));
        }
            sb1 = (Button) findViewById(R.id.ratingBtn);
            sb1.setOnClickListener(this);



            editText = (EditText) findViewById(R.id.inputReview);
            NonScrollListView listView = findViewById(R.id.cReview);
            ArrayList<String> beforeReviews = new ArrayList<>();
            if (db.containsCard(card) == true) {
                try {
                    card = db.getCardReviews(card);
                    beforeReviews = card.getReviews();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listAdapter = new ArrayAdapter<String>(this, R.layout.review_item, beforeReviews);
                listView.setAdapter(listAdapter);
            }
            sb2 = (Button) findViewById(R.id.reviewBtn);
            sb2.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.reviewBtn:
                ArrayList<String> reviews = new ArrayList<>();
                String review = editText.getText().toString();
                if(!TextUtils.isEmpty(review)){
                    if (db.containsCard(card) == true) {
                        try {
                            card = db.getCardReviews(card);
                            reviews = card.getReviews();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            db.addCard(card);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    reviews.add(review);
                    Toast.makeText(getApplicationContext(), "Review Submitted", Toast.LENGTH_SHORT).show();
                    card.setReviews(reviews);
                    try {
                        db.updateReview(card);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please Write Text", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ratingBtn:
                ArrayList<Double> ratings = new ArrayList<>();
                double rating = (double) rb.getRating();
                if (db.containsCard(card) == true) {
                    try {
                        card = db.getCardRating(card);
                        ratings = card.getRatings();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        db.addCard(card);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ratings.add(rating);
                String ratingStr = "Rating : " + rating;
                double total = 0;
                for (int i = 0; i < ratings.size(); i++) {
                    total += ratings.get(i);
                }
                total = total / ratings.size();
                String totalLifetime = "Total : " + df.format(total);
                Toast.makeText(getApplicationContext(), ratingStr + "\n " + totalLifetime, Toast.LENGTH_SHORT).show();
                card.setRatings(ratings);
                try {
                    db.updateCardRating(card);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
