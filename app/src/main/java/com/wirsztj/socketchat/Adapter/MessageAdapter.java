package com.wirsztj.socketchat.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wirsztj.socketchat.Model.Message;
import com.wirsztj.socketchat.R;
import com.wirsztj.socketchat.View.MessageView;

import java.util.List;

/**
 * Created by wirszt_j on 16/11/14.
 */
public class MessageAdapter extends BaseAdapter {

    private List<Message> messages;
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setList(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public void addMessage(Message msg) {
        messages.add(msg);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages != null ? messages.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return messages != null ? messages.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = new MessageView(parent.getContext());
        }

        Message message = messages.get(position);

        ((MessageView)convertView).bind(message, username.equals(message.getAuthor()));

        return convertView;
    }
}
