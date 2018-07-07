package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ReplyActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        client = TwitterApp.getRestClient(this);

        Parcelable parcel = getIntent().getParcelableExtra("username");
        Tweet tweet = (Tweet) Parcels.unwrap(parcel);

        String mention = (String) "@" + tweet.user.name;
        EditText etName = (EditText) findViewById(R.id.etReplyTweet);

        etName.setText(mention, TextView.BufferType.EDITABLE);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }


    public void onBack(MenuItem mi) {
        finish();

    }


    public void postReply(View view) {
        postTweetOnTimeline();
        //test();
    }



    private void test() {
        Parcelable parcel = getIntent().getParcelableExtra("username");
        Tweet tweet = (Tweet) Parcels.unwrap(parcel);

        String mention = (String) "@" + tweet.user.screenName;
        Toast.makeText(this, mention, Toast.LENGTH_SHORT).show();

    }
    private void postTweetOnTimeline() {
        EditText etName = (EditText) findViewById(R.id.etReplyTweet);



        String newTweet = etName.getText().toString();
//        newTweet = mention + " " + newTweet;

        client.sendTweet(newTweet,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                // convert each object to a Tweet model
                // add that Tweet model to our data source
                // notify the adapter that we've added an item
                try {
                    Tweet tweet = Tweet.fromJSON(response);
                    Intent data = new Intent(ReplyActivity.this, TimelineActivity.class);
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
