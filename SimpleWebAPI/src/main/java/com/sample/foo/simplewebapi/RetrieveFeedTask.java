package com.sample.foo.simplewebapi;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import static com.sample.foo.simplewebapi.MainActivity.API_KEY;
import static com.sample.foo.simplewebapi.MainActivity.API_URL;

/**
 *  Created by alexander on 1/19/18.
 *
 *  AsyncTask has 4 important methods:
 *  onPreExecute()       - what to do before the expensive task begins
 *  doInBackground()     - the actual expensive operation goes in here
 *  onProgressUpdate()   - what to do to show progress
 *  and onPostExecute()  - what to do when the task is complete
 */


public class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

    private TextView responseView;
    private EditText emailText;
    private ProgressBar progressBar;

    RetrieveFeedTask(TextView responseView, EditText emailText, ProgressBar progressBar){
        this.responseView = responseView;
        this.emailText = emailText;
        this.progressBar = progressBar;
    }

    private Exception exception;

    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        responseView.setText("");
    }

    protected String doInBackground(Void... urls) {
        String email = emailText.getText().toString();
        // TODO: Do some validation on email here

        try {
            URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }


    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }

        progressBar.setVisibility(View.GONE);

        // display response to textview
        responseView.setText(response);
        Log.i("INFO", response);

        // do something with the feed
        MainActivity.parseResponse(response);
    }
}