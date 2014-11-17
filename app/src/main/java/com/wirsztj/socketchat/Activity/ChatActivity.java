package com.wirsztj.socketchat.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.wirsztj.socketchat.Adapter.MessageAdapter;
import com.wirsztj.socketchat.Model.Message;
import com.wirsztj.socketchat.Network.ServerConnection;
import com.wirsztj.socketchat.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wirszt_j on 16/11/14.
 */
public class ChatActivity extends Activity {

    Socket socket;
    String username;

    MessageAdapter adapter;

    EditText etUserMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (getIntent() != null) {
            username = getIntent().getStringExtra(MainActivity.USERNAME);
        }

        adapter = new MessageAdapter();
        adapter.setUsername(username);
        adapter.setList(new ArrayList<Message>());

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Button sendBtn = (Button)findViewById(R.id.sendBtn);
        etUserMessage = (EditText)findViewById(R.id.etUserMessage);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkMessage()) {
                    return;
                }
                socket.emit("userMsg", new Message(etUserMessage.getText().toString(), username).toJSONObject());
                etUserMessage.setText("");
                etUserMessage.setError(null);
            }
        });

        socket = ServerConnection.getInstance().getSocket();

        socket.on("serverMsg", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                addMessage(Message.MessageType.MESSAGE, (JSONObject)args[0]);
            }
        });

        socket.on("userDisconnected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.e("tata", "userDisconnected");
                addMessage(Message.MessageType.DISCONNECT, (JSONObject)args[0]);
            }
        });

        socket.on("userConnected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                addMessage(Message.MessageType.CONNECT, (JSONObject)args[0]);
            }
        });
    }

    private void addMessage(final Message.MessageType messageType, final JSONObject jsonObject) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    adapter.addMessage(new Message(jsonObject, messageType));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        socket.close();
        socket.disconnect();
        super.onBackPressed();
    }

    public boolean checkMessage() {
        if (etUserMessage.getText().toString().trim().equalsIgnoreCase("")) {
            etUserMessage.setError("Message required");
            return false;
        }
        return true;
    }
}
