package com.wirsztj.socketchat.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wirszt_j on 16/11/14.
 */
public class Message {

    public enum MessageType {
        MESSAGE,
        CONNECTION
    }

    private String author;
    private String msg;
    private String date;
    private MessageType messageType;

    private Message() {}

    public Message(JSONObject jsonObject, MessageType type) throws JSONException {
        this(jsonObject);
        this.messageType = MessageType.CONNECTION;
    }

    public Message(String msg, String author) {
        this.author = author;
        this.msg = msg;
        this.messageType = MessageType.MESSAGE;
    }

    public Message(JSONObject jsonObject) throws JSONException {
        fromJSONObject(jsonObject);
    }

    public Message(String jsonString) throws JSONException {
        fromJSONObject(new JSONObject(jsonString));
    }

    public void fromJSONObject(JSONObject message) throws JSONException {
        if (message.has("author"))
            author = message.getString("author");
        if (message.has("message"))
            msg = message.getString("message");
        if (message.has("date"))
            date = message.getString("date");
        messageType = MessageType.MESSAGE;
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

    public MessageType getMessageType() {
        return messageType;
    }
}
