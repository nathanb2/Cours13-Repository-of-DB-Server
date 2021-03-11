package com.example.ex10_listfromdb.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ex10_listfromdb.R;
import com.example.ex10_listfromdb.model.User;
import com.example.ex10_listfromdb.repository.UserRepository;
import com.example.ex10_listfromdb.databinding.ActivityMainBinding;
import com.example.ex10_listfromdb.injection.Injection;

public class MainActivity extends AppCompatActivity {

    private int counter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.ex10_listfromdb.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        UserRepository userRepository = Injection.provideUserRepository(this, Injection.provideExecutor());
        findViewById(R.id.AM_add_btn).setOnClickListener(view -> {
            Injection.provideExecutor().execute(() ->
                    userRepository.createUser(new User("User " + ++counter, 100 + counter)));
        });
    }

}