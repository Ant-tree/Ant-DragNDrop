package com.anttree.antdragndropdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnShow).setOnClickListener(view -> moveToArrange());
    }

    private void moveToArrange() {
        startActivity(new Intent(this, CardArrangeActivity.class));
        overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
    }
}