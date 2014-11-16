package com.wirsztj.socketchat.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wirszt_j on 16/11/14.
 */
public class Message {

    private String author;
    private String msg;
    private String date;

    private Message() {}

    public Message(String msg, String author) {
        this.author = author;
        this.msg = msg;
    }

    public Message(JSONObject jsonObject) throws JSONException {
        fromJSONObject(jsonObject);
    }

    public Message(String jsonString) throws JSONException {
        fromJSONObject(new JSONObject(jsonString));
    }

    public void fromJSONObject(JSONObject message) throws JSONException {
        author = message.getString("author");
        msg = message.getString("message");
        date = message.getString("date");
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject();
            jsonObject.put("author", author);
            jsonObject.put("message", msg);
            jsonObject.put("date", "42/42/4242");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public String getAuthor() {
        return author;
    }

    public String getMsg() {
        return msg;
    }

    public String getDate() {
        return date;
    }
}
