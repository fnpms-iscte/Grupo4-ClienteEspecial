package Projeto3.Worker;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Response;
import Projeto3.Worker.Models.Room;
import Projeto3.Worker.Models.ScheduleResponse;

public class Worker {

	private List<Room> rooms;
	private List<Lecture> lectures;
	private String clientID;
	private AlgorithmsHandler algHandler;

	public JsonObject handleJson(JSONObject body) {
		try {
			clientID = body.getString("id");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		System.out.println("[Worker] Cliente ID: " + clientID);
		JSONArray files;
		try {
			files = body.getJSONArray("files");
			uploadFiles(files);
			algHandler = new AlgorithmsHandler(rooms, lectures, clientID);
			algHandler.runBasicAlg();
			algHandler.runMiddleAlg();
			algHandler.runIdealAlg();

			// Run algorithm and returns the list of lectures to use in jMetal
			List<Lecture> jmetalLectures = algHandler.runPerfectAlg();

			runQuery(jmetalLectures);

			return responseToJson(body.getString("id"), algHandler.getOutput());

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

	private void uploadFiles(JSONArray files) {
		JsonHandler loader = new JsonHandler(files);
		this.rooms = loader.getRooms();
		this.lectures = loader.getLectures();
		System.out.println("[Worker] Both files uploaded");
	}

	private void runQuery(List<Lecture> jMetallectures) {
		List<String> algsnames = Query.runQuery();
		for (String s : algsnames) {
			System.out.println(s);
			if (s.equalsIgnoreCase(":nsgaii")) {
				algHandler.runNSGAII(jMetallectures, rooms);
			} else {
				System.out.println("[Worker] Algorithm " + s + " not defined.");
			}
		}
	}

	private JsonObject stringToJSON(String jsonString) {
		JsonObject jsonResponse = (JsonObject) JsonParser.parseString(jsonString);
		return jsonResponse;
	}

	private JsonObject responseToJson(String clientID, List<Response> output) {
		ResponseToJSON transfer = new ResponseToJSON();
		ScheduleResponse trueOutput = new ScheduleResponse(clientID, output);
		String jsonString = transfer.ResToJSON(trueOutput);
		jsonString = jsonString.substring(1, jsonString.length() - 1);
		JsonObject jsonResponse = stringToJSON(jsonString);

		System.out.println("[Worker] Json response created");
		return jsonResponse;
	}

}
