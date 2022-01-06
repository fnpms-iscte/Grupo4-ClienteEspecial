package Projeto3.Worker;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.google.gson.JsonObject;

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
			System.out.println("[ConnectionHandler] Connected");

			socket.on("welcome", new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("[ConnectionHandler]" + args[0]);
					socket.emit("worker", 659812);

				}
			});

			socket.on("message", new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("[ConnectionHandler] " + args[0]); // world
				}
			});

			socket.on("files_to_handle", new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					try {
						JSONObject body = (JSONObject) args[0];
						JsonObject res = worker.handleJson(body);

						socket.emit("results", res);
						String client_id = res.get("id").toString();
						client_id = client_id.substring(1, client_id.length() - 1);
						sendPost(client_id);
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

	private void sendPost(String client_id) {
		try {
			System.out.println(client_id.toString() + "_Horario1.csv");
			HttpEntity entity = MultipartEntityBuilder.create()
					.addBinaryBody("file", new File("./timetables/" + client_id + "_Horario1.csv"), //
							ContentType.create("text/csv"), client_id + "_Horario1.csv")
					.addBinaryBody("file", new File("./timetables/" + client_id + "_Horario2.csv"),
							ContentType.create("text/csv"), client_id + "_Horario2.csv")
					.addBinaryBody("file", new File("./timetables/" + client_id + "_Horario3.csv"),
							ContentType.create("text/csv"), client_id + "_Horario3.csv")
					.build();

			HttpPost request = new HttpPost("http://localhost:3000/csv-files");
			request.setEntity(entity);
			System.out.println("[ConnectionHandler] Response sent");

			HttpClient client = HttpClientBuilder.create().build();
			HttpResponse response = client.execute(request);
			System.out.println("[Worker] Server's response: " + response.toString());
		} catch (Exception e) {
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