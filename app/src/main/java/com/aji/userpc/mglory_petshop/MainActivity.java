package com.aji.userpc.mglory_petshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private CardView StatusCard, AksesorisCard,FoodCard, CareCard, BuyCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusCard = findViewById(R.id.status_card);
        AksesorisCard = findViewById(R.id.aksesoris_card);
        FoodCard = findViewById(R.id.food_card);
        CareCard = findViewById(R.id.care_card);
        BuyCard = findViewById(R.id.buy_card);

        StatusCard.setOnClickListener(this);
        AksesorisCard.setOnClickListener(this);
        FoodCard.setOnClickListener(this);
        CareCard.setOnClickListener(this);
        BuyCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i ;

        switch (view.getId()){
            case R.id.status_card : i = new Intent(this,Status.class);startActivity(i); break;
            case R.id.aksesoris_card : i = new Intent(this,Aksesoris.class);startActivity(i); break;
            case R.id.food_card : i = new Intent(this,Food.class);startActivity(i); break;
            case R.id.care_card : i = new Intent(this,Care.class);startActivity(i); break;
            case R.id.buy_card : i = new Intent(this,Buy.class);startActivity(i); break;
        }

    }
}
