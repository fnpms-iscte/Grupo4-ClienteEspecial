package Projeto3.Worker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opencsv.CSVWriter;

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
	private String clientID;

	public JsonObject handleJson(JSONObject body) {
		try {
			clientID = body.getString("id");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		System.out.println("[Worker] Cliente ID: " + clientID);
		JSONArray files;
		output = new ArrayList<Response>();
		try {
			files = body.getJSONArray("files");
			uploadFiles(files);
			initMetrics();

			output.add(getBasicAlg());
//			output.add(getMiddleAlg());
//			output.add(getIdealAlg());

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

		createCSVfile(simpleLectures, clientID + "_Horario1");

		return new Response("Horario1", "Horario1", simpleEv.resultList, simpleEv.bestResult);
//		return new Response("Horario1", "Horario1", response, simpleEv.resultList, simpleEv.bestResult);
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

		return new Response("Horario2", "Horario2", middleEv.resultList, middleEv.bestResult);
//		return new Response("Horario2", "Horario2", response, middleEv.resultList, middleEv.bestResult);
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

		return new Response("Horario3", "Horario3", idealEv.resultList, idealEv.bestResult);
//		return new Response("Horario3", "Horario3", response, idealEv.resultList, idealEv.bestResult);
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

		System.out.println(jsonString);
		return jsonResponse;
	}

	private void createCSVfile(List<Lecture> lectures, String name) {
		File file = new File("./timetables/" + name + ".csv");
		try {
			FileWriter outputfile = new FileWriter(file, StandardCharsets.ISO_8859_1);

			// create CSVWriter object filewriter object as parameter
			CSVWriter writer = new CSVWriter(outputfile, ';', CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
			
			
			// adding header to csv
			String[] header = { "Curso", "Unidade de execução", "Turno", "Turma",
					"Inscritos no turno (no 1º semestre é baseado em estimativas)",
					"Turnos com capacidade superior à capacidade das características das salas",
					"Turno com inscrições superiores à capacidade das salas", "Dia da Semana", "Início", "Fim", "Dia",
					"Características da sala pedida para a aula", "Sala da aula", "Lotação",
					"Características reais da sala" };
			writer.writeNext(header);
			Lecture l = lectures.get(0);
			// add data to csv
			String[] data1 = { l.getCurso().toString(), l.getUnidade_de_execucaoo(), l.getTurno(), l.getTurma(),
					Integer.toString(l.getInscritos_no_turno()),
					String.valueOf(l.isTurnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas()),
					String.valueOf(l.isTurno_com_inscricoes_superiores_a_capacidade_das_salas()), l.getDia_da_Semana(),
					l.getInicio().toString("HH:mm"), l.getFim().toString("HH:mm"), l.getFim().toString("dd-MM-yyyy"),
					l.getCaracteristicas_da_sala_pedida_para_a_aula(), l.getSala_da_aula(),
					Integer.toString(l.getLotacao()), l.getCaracteristicas_reais_da_sala().toString() };
			writer.writeNext(data1);
			System.out.println("[Worker] File created");
//			String[] data2 = { "Suraj", "10", "630" };
//			writer.writeNext(data2);

			// closing writer connection
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
