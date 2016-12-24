package com.sloydev.preferator.demo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sloydev.preferator.Preferator;

import java.util.HashSet;

import static java.util.Arrays.asList;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        findViewById(R.id.demo_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferator.launch(DemoActivity.this);
            }
        });
        findViewById(R.id.demo_prefill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefillPreferences();
            }
        });
    }

    private void prefillPreferences() {
        fill(getPreferences(MODE_PRIVATE));
        fill(PreferenceManager.getDefaultSharedPreferences(this));
        fill(getSharedPreferences("another file", MODE_PRIVATE));
    }

    private void fill(SharedPreferences preferences) {
        preferences
                .edit()
                .putString("some_string", "a string value")
                .putInt("some_int", 42)
                .putLong("some_long", System.currentTimeMillis())
                .putBoolean("some_boolean", true)
                .putFloat("some_float", 3.14f)
                .putStringSet("some_set", new HashSet<>(asList("a", "b", "c")))
                .apply();
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

}
