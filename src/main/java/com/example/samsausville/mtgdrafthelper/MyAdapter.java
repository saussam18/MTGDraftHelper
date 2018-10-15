package com.example.samsausville.mtgdrafthelper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Card> cards;
    Context context;

    public MyAdapter(ArrayList<Card> cards, Context context) {
        this.cards = cards;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int postion){
        final Card mCard = cards.get(postion);

        holder.cardName.setText(mCard.getName());
        holder.cardType.setText(mCard.getType());
        holder.cardMana.setText(mCard.getManaCost());
        holder.linearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view){
                Intent intent = new Intent(context, CardDisplay.class);
                intent.putExtra("card", mCard);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount(){
        if(cards.size() == 0){
            Toast.makeText(context, "No Cards were found, Please try again", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }

        return cards.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView cardName;
        public TextView cardType;
        public TextView cardMana;

        public LinearLayout linearLayout;

        public ViewHolder(View itemView){
            super(itemView);

            cardName = (TextView) itemView.findViewById(R.id.cardName);
            cardType = (TextView) itemView.findViewById(R.id.cardType);
            cardMana = (TextView) itemView.findViewById(R.id.cardManaCost);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }
}
