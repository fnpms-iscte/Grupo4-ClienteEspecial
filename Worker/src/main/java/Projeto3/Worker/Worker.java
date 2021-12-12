package Projeto3.Worker;

import java.io.File;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;

public class Worker {

	private File file;
	private Socket socket;

	public void connection() {
		String url = "http://localhost:3000/";
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
					System.out.println(args[0]); // world
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

						System.out.println(args[0].getClass());
						JSONObject body = (JSONObject) args[0];
						upload_jsons(body);

						//send_timetables(Jsonteste.jsonObject);

					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});
			socket.connect();

		} catch (URISyntaxException e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Worker w = new Worker();
		w.connection();

	}

	public void upload_jsons(JSONObject body) throws Exception {
		JSONArray pessoas = body.getJSONArray("file");
		String id_name = body.getString("name");
		file = new File("uploads/" + id_name +".csv");
		file.createNewFile();
		String csv = CDL.toString(pessoas);
		FileUtils.writeStringToFile(file, csv, "ISO-8859-1");
	}

	public void send_timetables(JsonObject body) {
		socket.emit("results", body);
		System.out.println("Resultados enviados ...");
	}

}