package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;


@Parcel
public class Tweet implements Serializable {

    // list out the attributes
    private static final long serialVersionUID = 5177222050535318633L;
    public String body;
    public long uid;// database ID for the tweet
    public User user;
    public String createdAt;
    public String id_str;

    // empty  constructor
    public Tweet() {

    }

    public Tweet(String body, long uid, User user, String createdAt, String id_str){
        this.body = body;
        this.uid = uid;
        this.user = user;
        this.createdAt = createdAt;
        this.id_str = id_str;

    }

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.id_str = jsonObject.getString("id_str");
        return tweet;


    }
}

