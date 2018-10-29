package com.example.virg.assessment;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpRequestClass {

    private static final String TAG = HttpRequestClass.class.getSimpleName();

    // constructor
    HttpRequestClass() {
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            // set up and make request to API
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
            // set up various scenarios to catch if failures occur
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        //read through the response data
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            // catch error
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // close input stream
                is.close();
                // catch error if uanble to close stream
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
