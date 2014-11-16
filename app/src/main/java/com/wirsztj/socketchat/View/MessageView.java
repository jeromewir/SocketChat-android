package com.wirsztj.socketchat.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wirsztj.socketchat.Model.Message;
import com.wirsztj.socketchat.R;

/**
 * Created by wirszt_j on 16/11/14.
 */
public class MessageView extends RelativeLayout {

    TextView tvMessage;
    TextView author;

    public MessageView(Context context) {
        this(context, null);
    }

    public MessageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.message_view, this, true);

        tvMessage = (TextView)findViewById(R.id.message);
        author = (TextView)findViewById(R.id.author);
    }

    public void bind(Message message, boolean isAuthor) {
        if (isAuthor) {
            ((RelativeLayout.LayoutParams)tvMessage.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            ((RelativeLayout.LayoutParams)author.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        else {
            ((RelativeLayout.LayoutParams)tvMessage.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            ((RelativeLayout.LayoutParams)author.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        author.setText(!isAuthor ? message.getAuthor() : "You");
        tvMessage.setText(message.getMsg());
    }
}
