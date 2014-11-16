package com.wirsztj.socketchat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.wirsztj.socketchat.Network.ServerConnection;
import com.wirsztj.socketchat.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


public class MainActivity extends ActionBarActivity {

    public final static String USERNAME = "USERNAME";

    Button connect;
    ProgressBar loadingConnect;
    EditText username;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = (Button) findViewById(R.id.connectBtn);
        loadingConnect = (ProgressBar) findViewById(R.id.loadingConnect);
        username = (EditText)findViewById(R.id.username);
        tvError = (TextView)findViewById(R.id.tvError);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingConnect.setVisibility(View.VISIBLE);

                ServerConnection socket = ServerConnection.getInstance();

                socket.init("http://192.168.1.2:1337");

                socket.getSocket().connect();

                socket.getSocket().once(Socket.EVENT_CONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);

                        intent.putExtra(USERNAME, username.getText().toString());

                        startActivity(intent);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingConnect.setVisibility(View.GONE);
                            }
                        });
                    }
                });

                socket.getSocket().on(Socket.EVENT_CONNECT_ERROR, errorListener);
                socket.getSocket().on(Socket.EVENT_CONNECT_TIMEOUT, errorListener);

            }
        });

    }

    private Emitter.Listener errorListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            ServerConnection.getInstance().getSocket().close();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingConnect.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("Error: Cannot reach server, please check your internet connection");
                }
            });
        }
    };

}
