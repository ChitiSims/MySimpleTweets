package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity{

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApp.getRestClient(this);

    }

    public void postTweet(View view) {
        postTweetOnTimeline();
    }

    private void postTweetOnTimeline() {
        EditText etName = (EditText) findViewById(R.id.etComposeTweet);
        String newTweet = etName.getText().toString();

        client.sendTweet(newTweet,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                // convert each object to a Tweet model
                // add that Tweet model to our data source
                // notify the adapter that we've added an item
                try {
                    Tweet tweet = Tweet.fromJSON(response);
                    Intent data = new Intent(ComposeActivity.this, TimelineActivity.class);
                    // Pass relevant data back as a result
                    data.putExtra("tweet", Parcels.wrap(tweet));
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish();
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //           Log.d("TwitterClient", response.toString());
                // iterate through the JSON array
                // for each entry, deserialize the JSON object

//                for (int i = 0; i < response.length(); i++) {
//                    // convert each object to a Tweet model
//                    // add that Tweet model to our data source
//                    // notify the adapter that we've added an item
//                    try {
//                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
//                        Intent data = new Intent(ComposeActivity.this, TimelineActivity.class);
//                        // Pass relevant data back as a result
//                        data.putExtra("tweet", Parcels.wrap(tweet));
//                        setResult(RESULT_OK, data); // set result code and bundle data for response
//                        finish();
//                    } catch(JSONException e) {
//
//                    }
//                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }
}
