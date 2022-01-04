package Projeto3.Worker;

import java.net.URISyntaxException;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ConnectionHandler {

	private Socket socket;
	private Worker worker;
	private String url;

	public ConnectionHandler(String url) {
		this.url = url;
		worker = new Worker();
	}

	private void initConnection() {
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
						JsonObject res = worker.handleJson(body);

						socket.emit("results", res);
						System.out.println("[ConnectionHandler] Response sent");
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
	
	public static void main(String[] args) {
		ConnectionHandler conn = new ConnectionHandler("http://localhost:3000/");
		conn.initConnection();
	}
	
//	public static void main(String[] args) {
//		ConnectionHandler conn = new ConnectionHandler("https://projeto-ads-3-grupo4.herokuapp.com/");
//		conn.initConnection();
//	}
}