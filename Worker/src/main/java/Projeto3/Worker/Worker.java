package Projeto3.Worker;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opencsv.CSVWriter;

import Projeto3.Worker.Algorithms.IdealAlg;
import Projeto3.Worker.Algorithms.MiddleAlg;
import Projeto3.Worker.Algorithms.NSGAIIRunner;
import Projeto3.Worker.Algorithms.PerfectAlg;
import Projeto3.Worker.Algorithms.SimpleAlg;
import Projeto3.Worker.Metrics.ClassCapacityOver;
import Projeto3.Worker.Metrics.ClassCapacityUnder;
import Projeto3.Worker.Metrics.RoomAllocationChars;
import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Response;
import Projeto3.Worker.Models.Room;
import Projeto3.Worker.Models.ScheduleResponse;

public class Worker {

	private List<Room> rooms;
	private List<Lecture> lectures;
	private String clientID;
	private List<String> algsnames;

	public JsonObject handleJson(JSONObject body) {
		try {
			clientID = body.getString("id");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		System.out.println("[Worker] Cliente ID: " + clientID);
		JSONArray files;
		algsnames = new ArrayList<String>();
		try {
			files = body.getJSONArray("files");
			uploadFiles(files);
			AlgorithmsHandler algHandler = new AlgorithmsHandler(rooms, lectures, clientID);
			algHandler.runBasicAlg();
			algHandler.runMiddleAlg();
			algHandler.runIdealAlg();
			// Run algorithm and returns the list of lectures to use in jMetal
			List<Lecture> jmetalLectures = algHandler.runPerfectAlg();
			
			NSGAIIRunner nsgaii = new NSGAIIRunner(lectures,rooms);
			nsgaii.runAlg();
//			algsnames = runQuery();
//			System.out.println(algsnames);

			return responseToJson(body.getString("id"), algHandler.getOutput());

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

//	jMetal --- retorna as aulas sem salas
	

	private void uploadFiles(JSONArray files) {
		JsonHandler loader = new JsonHandler(files);
		this.rooms = loader.getRooms();
		this.lectures = loader.getLectures();
		System.out.println("[Worker] Both files uploaded");
	}
// for testing
	private List<String> runQuery() {
		return Query.runQuery();
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
