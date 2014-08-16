package com.lixiangers.dingji.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Socket;

public class IOUtil {

    public static void closeSocketQuietly(Socket socket) {
        if (socket == null) return;
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

    public static void closeStreamQuietly(InputStream is) {
        if (is == null) return;
        try {
            is.close();
            is = null;
        } catch (IOException ignored) {
        }
    }

    public static void disconnectQuietly(HttpURLConnection connection) {
        if (connection == null) return;
        connection.disconnect();
    }
}
