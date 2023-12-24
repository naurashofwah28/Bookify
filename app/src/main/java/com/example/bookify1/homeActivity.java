package com.example.bookify1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homeActivity extends AppCompatActivity implements View.OnClickListener{
    private Button arsip;
    private Button btnKeluar;
    private FirebaseAuth mAuth;
    private TextView tvEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        arsip = findViewById(R.id.arsip);
        btnKeluar = findViewById(R.id.logout);
        tvEmail = findViewById(R.id.tv_email);
        mAuth = FirebaseAuth.getInstance();
        btnKeluar.setOnClickListener(this);
        arsip.setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            tvEmail.setText(currentUser.getEmail());
        }
    }
    @Override
    public void onClick(View view) {
        if (view.equals(arsip)) {
            Intent intent = new Intent(homeActivity.this,
                    DaftarBukuActivity.class);
            startActivity(intent);
        } else if (view.equals(btnKeluar)) {
            mAuth.signOut();
            Intent intent = new Intent(homeActivity.this,
                    MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
