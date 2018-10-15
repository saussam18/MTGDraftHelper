package com.example.samsausville.mtgdrafthelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView ivImage= findViewById(R.id.pic);
        String imageUrl = "http://mountainview.gamekastle.com/wp-content/uploads/2017/04/magic-booster-draft-1.png";
        new ImageDownloader(ivImage).execute(imageUrl);
    }

    public void searchOnClick (View view) {
        Intent intent = new Intent(this, CardSearchResults.class);
        EditText txt = (EditText)findViewById(R.id.input);
        String name = txt.getText().toString();
        if(!TextUtils.isEmpty(name)){
            intent.putExtra("name", name);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Please Write Text", Toast.LENGTH_SHORT).show();
        }

        //Only thing really to do here is make it look a bit nicer, otherwise this is all that is needed
    }

}
