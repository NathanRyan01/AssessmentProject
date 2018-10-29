package com.example.virg.assessment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SingleRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_record);
        TextView descriptionTextView = findViewById(R.id.description);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        // Get data from previous activity
        String value = (String) bundle.getSerializable("data");
        // Set textview text with value variable
        descriptionTextView.setText(value);
    }
}
