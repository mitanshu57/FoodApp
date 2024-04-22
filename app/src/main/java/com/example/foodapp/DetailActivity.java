package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_detail);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final int image=getIntent().getIntExtra("image",0);
        final int price;

            price = Integer.parseInt(getIntent().getStringExtra("price"));


        final String name=getIntent().getStringExtra("name");
        final String description=getIntent().getStringExtra("desc");
        binding.detailImage.setImageResource(image);
        binding.detailPrice.setText(String.valueOf(price));

        binding.detailName.setText(name);
        binding.detailDesc.setText(description);

        final DBHandler dbHandler=new DBHandler(this);

        binding.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted=dbHandler.insertOrder(
                        price,
                        image,
                        name,
                        description
                );
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.orders:
//                startActivity(new Intent(this,Order.class));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}