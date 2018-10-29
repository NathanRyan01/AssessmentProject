package com.example.virg.assessment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    EditText input;
    Intent intent;
    Bundle bundle = new Bundle();
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.input);
    }

    // all books
    public void allBooksBtnClick(View view) {
        this.sendData(id);
    }

    // single book
    public void singlebookBtnClick(View view) {
        // get the id from the edittext
        id = input.getText().toString();
        // check if user has  not entered a value
        if(id.equals("")){
            Toast.makeText(getApplicationContext(),
                    "Please enter an id",
                    Toast.LENGTH_LONG).show();
        }
        else{
            this.sendData(id);
        }
    }

    /* if all books are requested than come straight here
    if one record then go to singlebookBtnClick method first */

    public void sendData(String id){
        // set up data to get passed with intent
        intent = new Intent(MainActivity.this, AllRecordsActivity.class);
        bundle.putSerializable("data", id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}


