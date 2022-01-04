package Projeto3.Worker;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import Projeto3.Worker.Algorithms.IdealAlg;
import Projeto3.Worker.Algorithms.MiddleAlg;
import Projeto3.Worker.Algorithms.SimpleAlg;
import Projeto3.Worker.Metrics.ClassCapacityOver;
import Projeto3.Worker.Metrics.ClassCapacityUnder;
import Projeto3.Worker.Metrics.RoomAllocationChars;
import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Response;
import Projeto3.Worker.Models.ResponseType;
import Projeto3.Worker.Models.Room;
import Projeto3.Worker.Models.ScheduleResponse;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ConnectionHandler {

    private Socket socket;
    private Worker1 worker;

    public ConnectionHandler(String url) {
        initConnection(url);
    }

    private void initConnection(String url) {
        try {
            IO.Options options = new IO.Options();
            options.transports = new String[] { "websocket" };
            // Number of failed retries
            options.reconnectionAttempts = 10;
            // Time interval for failed reconnection
            options.reconnectionDelay = 1000;
            // Connection timeout (ms)
            options.timeout = 500;

            socket = IO.socket(url, options);
            System.out.println("Connected");

            socket.on("welcome", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println(args[0]);
                    socket.emit("worker", 659812);
                }
            });

            socket.on("message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println(args[0]); // world
                }
            });

            socket.on("files_to_handle", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONObject body = (JSONObject) args[0];
                        upload_jsons(body);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            socket.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();

        }
    }

}