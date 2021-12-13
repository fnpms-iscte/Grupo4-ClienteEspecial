package Projeto3.Worker;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

public class Worker {

	private File file;
	private Socket socket;
	private String id = "yFaHnhlJJ17fZNQWAAAU";
	private Jsonteste a;

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
						//TimeUnit.SECONDS.sleep(5);
					/*JSONObject body = (JSONObject) args[0];
					try {
						//String id = body.getString("id");
						System.out.println("ID: " + id);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					//send_timetables(this.a.jsonObject);
				}
			});

			socket.on("files_to_handle", new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					try {

						//System.out.println(args[0]);
						JSONObject body = (JSONObject) args[0];
						upload_jsons(body);
						//System.out.println(body);
						Jsonteste a = new Jsonteste(body.getString("id"));
						send_timetables(a.jsonObject);

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
		/*
		 * JSONArray pessoas = body.getJSONArray("file"); String id_name =
		 * body.getString("name"); file = new File("uploads/" + id_name + ".csv");
		 * file.createNewFile(); String csv = CDL.toString(pessoas);
		 * FileUtils.writeStringToFile(file, csv, "ISO-8859-1"); this.a = new
		 * Jsonteste(body.getString("id"));
		 */
		JSONArray files = body.getJSONArray("files");
		// System.out.println(files);
		for (int i = 0; i < files.length(); i++) {
			JSONArray pessoas = files.getJSONArray(i);
			// File dir = new File("c:uploads");
			File file;
			if(i==0) {
				file = new File("uploads/" + body.getString("id") +"_rooms.csv");
			}else {
				file = new File("uploads/" + body.getString("id") +"_lectures.csv");
			}
			// dir.mkdir();

			file.createNewFile();

			// TODO Auto-generated catch block

			String csv = CDL.toString(pessoas);

			FileUtils.writeStringToFile(file, csv, "ISO-8859-1");
			// System.out.println("Data has been Sucessfully Writeen to "+ file);
			// System.out.println(csv);

			// TODO Auto-generated catch block

			/*for (int j = 0; j < pessoas.length(); j++) {
				JSONObject pessoa = pessoas.getJSONObject(j);
				System.out.println("Pessoa: " + pessoa.toString());
				;
			}*/
			// System.out.println(pessoas.toString());
		}
	}

	public void send_timetables(JsonObject body) {
		
		socket.emit("results", body);
		System.out.println("Resultados enviados ...");
	}

}