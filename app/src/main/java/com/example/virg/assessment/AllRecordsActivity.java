package com.example.virg.assessment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AllRecordsActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv = null;
    ArrayList<HashMap<String, String>> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_records);

        bookList = new ArrayList<>();
        lv = findViewById(R.id.bookList);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;

        // Get data from MainActivity
        String value = (String) bundle.getSerializable("data");

        // execute call to APIRequest class for AsyncTask to begin
        new APIRequest().execute(value);

    }

    private class APIRequest extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            // initial set up
            super.onPreExecute();
            Toast.makeText(AllRecordsActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(String... arg0) {
            // get parameters
            String value = arg0[0];
            HttpRequestClass sh = new HttpRequestClass();
            // Making a request to url and getting response
            String url = "http://tpbookserver.herokuapp.com/books";
            if (value != null){
                String id = value;
                url = "http://tpbookserver.herokuapp.com/book/"+id;
            }
            // call HttpRequestClass for API interaction
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            // if a json response is returned
            if (jsonStr != null) {
                try {
                    // set up variables
                    String id, author, title, isbn, price, currencyCode, description;
                    // scenario for one value requested
                    if (value != null){
                        // create json object
                        // tmp hash map for single contact
                        HashMap<String, String> bookInfo = new HashMap<>();
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        description = jsonObject.getString("description");
                        id = jsonObject.getString("id");
                        author = jsonObject.getString("author");
                        title = jsonObject.getString("title");
                        isbn = jsonObject.getString("isbn");
                        price = jsonObject.getString("price");
                        currencyCode = jsonObject.getString("currencyCode");
                        // adding each child node to HashMap key => value
                        bookInfo.put("id", id);
                        bookInfo.put("author", author);
                        bookInfo.put("title", title);
                        bookInfo.put("isbn", isbn);
                        bookInfo.put("price", price);
                        bookInfo.put("currencyCode", currencyCode);
                        bookInfo.put("description", description);
                        // adding contact to contact list
                        bookList.add(bookInfo);
                    }
                    // scenario when all records are requested
                    else{
                        JSONArray jsonArray  = new JSONArray(jsonStr);
                        // looping through All Contacts
                        for (int i = 0; i < jsonArray.length(); i++) {
                            // tmp hash map for single contact
                            HashMap<String, String> bookInfo = new HashMap<>();
                            JSONObject c = jsonArray.getJSONObject(i);
                            id = c.getString("id");
                            author = c.getString("author");
                            title = c.getString("title");
                            isbn = c.getString("isbn");
                            price = c.getString("price");
                            currencyCode = c.getString("currencyCode");
                            // adding each child node to HashMap key => value
                            bookInfo.put("id", id);
                            bookInfo.put("author", author);
                            bookInfo.put("title", title);
                            bookInfo.put("isbn", isbn);
                            bookInfo.put("price", price);
                            bookInfo.put("currencyCode", currencyCode);
                            // adding contact to contact list
                            bookList.add(bookInfo);
                        }
                    }
                } catch (final JSONException e) {
                    // catch and log parsing errors
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

            } else {
                // log server issues
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Id is invalid",
                                Toast.LENGTH_LONG).show();
                    }
                });
                Intent intent = new Intent(AllRecordsActivity.this, MainActivity.class);
                startActivity(intent);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // complete with data returned
            super.onPostExecute(result);
            // set up adapater and pass data to listview
            ListAdapter adapter = new SimpleAdapter(AllRecordsActivity.this, bookList,
                    R.layout.list_item, new String[]{ "title", "author", "id", "price", "isbn", "currencyCode"},
                    new int[]{R.id.title, R.id.author, R.id.id, R.id.price, R.id.isbn, R.id.currencyCode});
            lv.setAdapter(adapter);
            // add listener in the event of a row on the listview being clicked
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = "None available";
                    // if the record has a description key
                    if (bookList.get(position).get("description") != null){
                        selectedItem = bookList.get(position).get("description");
                    }
                    // set up intent and pass data to SingleRecordActivity
                    Intent intent = new Intent(AllRecordsActivity.this, SingleRecordActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", selectedItem);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }
}



