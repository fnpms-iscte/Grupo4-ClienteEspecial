package Projeto3.Worker;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
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

public class Worker {

	private List<Room> rooms;
	private List<Lecture> lectures;
	private List<Metric> metricList;
	private List<Response> output;
	private ClassCapacityOver metric1;
	private ClassCapacityUnder metric2;
	private RoomAllocationChars metric3;

	public JsonObject handleJson(JSONObject body) {
		JSONArray files;
		output = new ArrayList<Response>();
		try {
			files = body.getJSONArray("files");
			uploadFiles(files);
			initMetrics();

			output.add(getBasicAlg());
			output.add(getMiddleAlg());
			output.add(getIdealAlg());

			return responseToJson(body.getString("id"), output);

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

	private void initMetrics() {
		metricList = new ArrayList<>();
		metric1 = new ClassCapacityOver();
		metric2 = new ClassCapacityUnder();
		metric3 = new RoomAllocationChars();
		metricList.add(metric1);
		metricList.add(metric2);
		metricList.add(metric3);
		System.out.println("[Worker] Initialized Metrics");
	}

	private Response getBasicAlg() {
		SimpleAlg sa = new SimpleAlg();
		List<Lecture> simpleLectures = new ArrayList<>();
		simpleLectures.addAll(lectures);
		sa.compute(simpleLectures, rooms);
		clearLectureOffRoom();

		// Evaluation of metrics
		Evaluation simpleEv = new Evaluation(simpleLectures, metricList);

		List<ResponseType> response = new ArrayList<>();
		for (Lecture l : simpleLectures) {
			response.add(new ResponseType(l));
		}

		System.out.println("[Worker] Basic alg computed");

		return new Response("Horario1", "Horario1", response, simpleEv.resultList, simpleEv.bestResult);
	}

	private Response getMiddleAlg() {
		MiddleAlg ma = new MiddleAlg();
		List<Lecture> middleLectures = new ArrayList<>();
		middleLectures.addAll(lectures);
		ma.compute(middleLectures, rooms);

		clearLectureOffRoom();

		// Evaluation of metrics
		Evaluation middleEv = new Evaluation(middleLectures, metricList);

		List<ResponseType> response = new ArrayList<>();
		for (Lecture l : middleLectures) {
			response.add(new ResponseType(l));
		}

		System.out.println("[Worker] Middle alg computed");

		return new Response("Horario2", "Horario2", response, middleEv.resultList, middleEv.bestResult);
	}

	private Response getIdealAlg() {
		IdealAlg ia = new IdealAlg();
		List<Lecture> idealLectures = new ArrayList<>();
		idealLectures.addAll(lectures);
		ia.compute(idealLectures, rooms);

		clearLectureOffRoom();

		// Evaluation of metrics
		Evaluation idealEv = new Evaluation(idealLectures, metricList);

		List<ResponseType> response = new ArrayList<>();
		for (Lecture l : idealLectures) {
			response.add(new ResponseType(l));
		}

		System.out.println("[Worker] Ideal alg computed");

		return new Response("Horario3", "Horario3", response, idealEv.resultList, idealEv.bestResult);
	}

	private void clearLectureOffRoom() {
		for (Room r : rooms) {
			r.clearLecture();
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
