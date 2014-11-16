package com.wirsztj.socketchat.Network;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by wirszt_j on 16/11/14.
 */
public class ServerConnection {

    private static ServerConnection mInstance = null;

    private Socket mSocket = null;

    private ServerConnection() {}

    public static ServerConnection getInstance() {
        if (mInstance == null) {
            mInstance = new ServerConnection();
        }
        return mInstance;
    }

    public void init(String url) {
        try {
            mSocket = IO.socket(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

}
