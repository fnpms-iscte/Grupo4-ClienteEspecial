package Projeto3.Worker;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Worker {

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
			Socket socket = IO.socket(url, options);
			System.out.println("Connected");
			socket.emit("worker", 659812);
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
		JSONArray files = body.getJSONArray("files");
		// System.out.println(files);
		for (int i = 0; i < files.length(); i++) {
			JSONArray pessoas = files.getJSONArray(i);	
			// File dir = new File("c:uploads");
			File file = new File("uploads/test.csv");
			// dir.mkdir();
			file.createNewFile();
			String csv = CDL.toString(pessoas);
			FileUtils.writeStringToFile(file, csv, "ISO-8859-1");
			// System.out.println("Data has been Sucessfully Writeen to "+ file);
			// System.out.println(csv);
			for (int j = 0; j < pessoas.length(); j++) {
				JSONObject pessoa = pessoas.getJSONObject(j);
				System.out.println("Pessoa: " + pessoa.toString());
				;
			}
			// System.out.println(pessoas.toString());
		}

	}
	
}