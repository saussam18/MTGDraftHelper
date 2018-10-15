package com.example.samsausville.mtgdrafthelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "cardRatings";

    private static final String TABLE_RATINGS = "allRatings";



    private static final String KEY_NUMID = "numID";
    private static final String KEY_NAME = "cardName";
    private static final String KEY_RATINGS = "cardRating";
    private static final String KEY_REVIEWS = "cardReview";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RATINGS_TABLE = "CREATE TABLE " + TABLE_RATINGS + "(" + KEY_NUMID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_RATINGS + " TEXT" + ");";
        db.execSQL(CREATE_RATINGS_TABLE);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATINGS);
        onCreate(db);
    }

    public void removeAll()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.TABLE_RATINGS, null, null);


    }

    public void addCard(Card c) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        //String addReviews = "ALTER TABLE " + TABLE_RATINGS+ " ADD " + KEY_REVIEWS + " TEXT";
       // db.execSQL(addReviews);
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, c.getName());
        values.put(KEY_RATINGS, c.getRatingsString());
        values.put(KEY_REVIEWS, c.getReviewsString());

        db.insert(TABLE_RATINGS, null, values);
        db.close();
    }

    public Card getCardRating(Card c) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RATINGS, new String[] {
                        KEY_NAME, KEY_RATINGS }, KEY_NAME + "=?",
                new String[] { c.getName() }, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        JSONObject json = new JSONObject(cursor.getString(1));
                JSONArray array = json.optJSONArray("uniqueArrays");
         ArrayList<Double> ratings = new ArrayList<>();

        for(int i = 0; i < array.length(); i++){
                    double x = array.optDouble(i);
                    ratings.add(x);
                }
                c.setRatings(ratings);


                cursor.close();
                db.close();
                return c;
    }

    public void updateCardRating(Card c) throws JSONException {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RATINGS, c.getRatingsString());

         db.update(TABLE_RATINGS, values, KEY_NAME + " = ?",
                new String[] { c.getName() });

         db.close();
    }

    public boolean containsCard(Card c){
            SQLiteDatabase db = this.getReadableDatabase();
            String Query = "Select * from " + TABLE_RATINGS + " where " + KEY_NAME+ " =?";
            Cursor cursor = db.rawQuery(Query, new String[] {c.getName()});
            boolean yeet = false;
            if(cursor.moveToFirst()){
                yeet = true;
                int count = 0;
                while(cursor.moveToNext()){
                    count++;
                }
            }
        cursor.close();
        db.close();
        return yeet;
        }

        public void updateReview(Card c) throws JSONException {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_REVIEWS, c.getReviewsString());
            db.update(TABLE_RATINGS, values, KEY_NAME + " = ?",
                    new String[] { c.getName() });
            db.close();
        }

        public Card getCardReviews (Card c) throws JSONException {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_RATINGS, new String[] {
                            KEY_NAME, KEY_REVIEWS }, KEY_NAME + "=?",
                    new String[] { c.getName() }, null, null, null, null);
            if (cursor != null){
                cursor.moveToFirst();
            }
            JSONObject json = new JSONObject(cursor.getString(1));
            JSONArray array = json.optJSONArray("uniqueArrays2");
            ArrayList<String> reviews = new ArrayList<>();

            for(int i = 0; i < array.length(); i++){
                String x = array.optString(i);
                reviews.add(x);
            }
            c.setReviews(reviews);


            cursor.close();
            db.close();
            return c;



        }
    }




