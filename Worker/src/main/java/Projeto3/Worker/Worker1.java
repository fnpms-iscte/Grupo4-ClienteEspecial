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

class Worker1 {
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

	public static void main(String[] args) {
		Worker1 w = new Worker1();
		w.connection();

	}

	public void upload_jsons(JSONObject body) throws Exception {
		ArrayList<File> filesList = new ArrayList<File>();
		JSONArray files = body.getJSONArray("files");
		JsonHandler loader = new JsonHandler(files);
		// Input: csv
		List<Room> rooms = loader.getRooms();
		// List<Room> rooms = new RoomLoader(files.getJSONArray(0)).getRooms();
		// List<Lecture> lectures = new
		// LectureLoader(files.getJSONArray(1)).getLectures();
		List<Lecture> lectures = loader.getLectures();
		System.out.println("Both files uploaded");

		// Metrics Initialization
		ClassCapacityOver metric1 = new ClassCapacityOver();
		ClassCapacityUnder metric2 = new ClassCapacityUnder();
		RoomAllocationChars metric3 = new RoomAllocationChars();

		List<Metric> MetricList = new ArrayList<Metric>();
		MetricList.add(metric1);
		MetricList.add(metric2);
		MetricList.add(metric3);
		System.out.println("Metricas inicializadas ...");

		// Response Initialization
		List<Response> output = new ArrayList<Response>();

		// Basic Alghoritm that acts as a FIFO
		SimpleAlg sa = new SimpleAlg();
		List<Lecture> Simple_lectures = new ArrayList<Lecture>();
		Simple_lectures.addAll(lectures);
		sa.compute(Simple_lectures, rooms);
		System.out.println("Basic alg ...");

		// Evaluation of metrics
		Evaluation simple_ev = new Evaluation(Simple_lectures, MetricList);

		List<ResponseType> response1 = new ArrayList<ResponseType>();
		for (Lecture l : Simple_lectures) {
			response1.add(new ResponseType(l));
		}

		Response out1 = new Response("Horario1", "Horario1", response1, simple_ev.resultList, simple_ev.bestResult);
		output.add(out1);

		for (Room r : rooms) {
			r.clearLecture();
		}

		// Middle Algorithm that acts based on capacity and required characteristic
		MiddleAlg ma = new MiddleAlg();
		List<Lecture> Middle_lectures = new ArrayList<Lecture>();
		Middle_lectures.addAll(lectures);
		ma.compute(Middle_lectures, rooms);

		System.out.println("Middle alg ...");

		Evaluation middle_ev = new Evaluation(Middle_lectures, MetricList);

		List<ResponseType> response2 = new ArrayList<ResponseType>();
		for (Lecture l : Middle_lectures) {
			response2.add(new ResponseType(l));
		}
		Response out2 = new Response("Horario2", "Horario2", response2, middle_ev.resultList, middle_ev.bestResult);
		output.add(out2);

		for (Room r : rooms) {
			r.clearLecture();
		}

		// Ideal Alghoritm that allocates room according to their
		// capacity/characteristic/availabity
		IdealAlg ia = new IdealAlg();
		List<Lecture> Ideal_lectures = new ArrayList<Lecture>();
		Ideal_lectures.addAll(lectures);
		ia.compute(Ideal_lectures, rooms);

		Evaluation ideal_ev = new Evaluation(Ideal_lectures, MetricList);
		List<ResponseType> response3 = new ArrayList<ResponseType>();
		for (Lecture l : Middle_lectures) {
			response3.add(new ResponseType(l));
		}
		Response out3 = new Response("Horario3", "Horario3", response3, ideal_ev.resultList, ideal_ev.bestResult);
		output.add(out3);

		System.out.println("Ideal alg ...");

		for (Room r : rooms) {
			r.clearLecture();
		}

		ResponseToJSON transfer = new ResponseToJSON();

		ScheduleResponse trueOutput = new ScheduleResponse(body.getString("id"), output);

		String jsonString = transfer.ResToJSON(trueOutput);
		jsonString = jsonString.substring(1, jsonString.length() - 1);
		System.out.println(jsonString);

		JsonObject jsonResponse = stringToJSON(jsonString);

		send_timetables(jsonResponse);

		// Delete files
		for (File aux : filesList) {
			aux.delete();
		}

	}

	public void send_timetables(JsonObject body) {
		try {
			socket.emit("results", body);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Resultados enviados ...");
	}

	public JsonObject stringToJSON(String jsonString) {
		JsonObject jsonResponse = (JsonObject) JsonParser.parseString(jsonString);
		return jsonResponse;
	}
}