package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodapp.Adapters.MainAdapter;
import com.example.foodapp.Models.MainModel;
import com.example.foodapp.databinding.ActivitySampleFoodBinding;

import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;

public class SampleFood extends AppCompatActivity {


    ActivitySampleFoodBinding binding;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySampleFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        ArrayList<MainModel> list=new ArrayList<>();
        list.add(new MainModel(R.drawable.burger,"Burger","54","Our Best Selling Burger ,Golden Crispy Chicken Patty Made With Secret Indian Spices."));
        list.add(new MainModel(R.drawable.fries,"Fries","86","Our Best Selling Fries ,Golden Crispy .¨"));
        list.add(new MainModel(R.drawable.pizza,"Pizza","98","Deep-dish pizza made to perfection! Can never go wrong with deep-dish pizza."));
        list.add(new MainModel(R.drawable.fries,"Fries","86","Our Best Selling Fries ,Golden Crispy .¨"));
        list.add(new MainModel(R.drawable.fries,"Fries","86","Our Best Selling Fries ,Golden Crispy .¨"));
        list.add(new MainModel(R.drawable.fries,"Fries","86","Our Best Selling Fries ,Golden Crispy .¨"));
        list.add(new MainModel(R.drawable.fries,"Fries","86","Our Best Selling Fries ,Golden Crispy .¨"));
        MainAdapter adapter=new MainAdapter(list,this);
        binding.recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_log,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.logout) {
            auth.signOut();
            Intent intent = new Intent(SampleFood.this, SignIn.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}