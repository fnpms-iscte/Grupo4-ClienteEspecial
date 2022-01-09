package Projeto3.Worker;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opencsv.CSVWriter;

import Projeto3.Worker.Algorithms.IdealAlg;
import Projeto3.Worker.Algorithms.MiddleAlg;
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
	private List<Metric> metricList;
	private List<Response> output;
	private ClassCapacityOver metric1;
	private ClassCapacityUnder metric2;
	private RoomAllocationChars metric3;
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
		output = new ArrayList<Response>();
		algsnames = new ArrayList<String>();
		try {
			files = body.getJSONArray("files");
			uploadFiles(files);
			initMetrics();
			
			output.add(getBasicAlg());
			output.add(getMiddleAlg());
			output.add(getIdealAlg());
			output.add(getPerfectAlg());
			
			algsnames = runQuery();
			System.out.println(algsnames);
			
			List<Lecture> jmetalL = getLecturesNoRoom(lectures);
			
			return responseToJson(body.getString("id"), output);

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	private List<Lecture> getLecturesNoRoom(List<Lecture> lectures){
		
		List<Lecture> auxLectures = new ArrayList<Lecture>();
		for (Lecture l : lectures) {
			if(!l.hasRoom())
				auxLectures.add(l);
		}
		return auxLectures;
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
		clearLectures();
		
		// Evaluation of metrics
		Evaluation simpleEv = new Evaluation(simpleLectures, metricList);

		System.out.println("[Worker] Basic alg computed");

		createCSVfile(simpleLectures, clientID + "_Horario1");
		System.out.println("[Worker] File Horario1 created");
		clearLectures();
		
		return new Response("Horario1", "Horario1", simpleEv.resultList, simpleEv.bestResult);
	}

	private Response getMiddleAlg() {
		MiddleAlg ma = new MiddleAlg();
		List<Lecture> middleLectures = new ArrayList<>();
		middleLectures.addAll(lectures);
		ma.compute(middleLectures, rooms);

		clearLectureOffRoom();

		// Evaluation of metrics
		Evaluation middleEv = new Evaluation(middleLectures, metricList);

		System.out.println("[Worker] Middle alg computed");
		createCSVfile(middleLectures, clientID + "_Horario2");
		System.out.println("[Worker] File Horario2 created");
		clearLectures();
		return new Response("Horario2", "Horario2", middleEv.resultList, middleEv.bestResult);
	}

	private Response getIdealAlg() {
		IdealAlg ia = new IdealAlg();
		List<Lecture> idealLectures = new ArrayList<>();
		idealLectures.addAll(lectures);
		ia.compute(idealLectures, rooms);

		clearLectureOffRoom();

		// Evaluation of metrics
		Evaluation idealEv = new Evaluation(idealLectures, metricList);

		System.out.println("[Worker] Ideal alg computed");
		createCSVfile(idealLectures, clientID + "_Horario3");
		System.out.println("[Worker] File Horario3 created");
		
		
		
		
		///clearLectures();
		return new Response("Horario3", "Horario3", idealEv.resultList, idealEv.bestResult);
	}

	private Response getPerfectAlg() {
		PerfectAlg pa = new PerfectAlg();
		List<Lecture> perfectLectures = new ArrayList<>();
		perfectLectures.addAll(lectures);
		pa.compute(perfectLectures, rooms);

		clearLectureOffRoom();

		// Evaluation of metrics
		Evaluation perfectEv = new Evaluation(perfectLectures, metricList);

		System.out.println("[Worker] perfect alg computed");
		createCSVfile(perfectLectures, clientID + "_Horario4");
		System.out.println("[Worker] File Horario4 created");
		clearLectures();
		return new Response("Horario4", "Horario4", perfectEv.resultList, perfectEv.bestResult);
	}





	private List<String> runQuery(){
		return Query.runQuery();
	}

	private void clearLectureOffRoom() {
		for (Room r : rooms) {
			r.clearLecture();
		}
	}
	
	
	private void clearLectures() {
		for (Lecture l : lectures) {
			l.cleanRoom();
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

	private void createCSVfile(List<Lecture> lectures, String name) {
		File file = new File("./timetables/" + name + ".csv");
		try {
			FileWriter outputfile = new FileWriter(file); /*, StandardCharsets.ISO_8859_1);*/

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

			for (Lecture l : lectures) {
				String[] data = getData(l);
				writer.writeNext(data);
			}

			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String[] getData(Lecture l) {
		String curso = l.getCurso().toString();
		String unidade_execucao = l.getUnidade_de_execucaoo();
		String turno = l.getTurno();
		String turma = l.getTurma();
		String isTurnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas = String
				.valueOf(l.isTurnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas());
		String isTurno_com_inscricoes_superiores_a_capacidade_das_salas = String
				.valueOf(l.isTurno_com_inscricoes_superiores_a_capacidade_das_salas());
		String[] auxData = handleNullfields(l);
		String[] data = { curso, unidade_execucao, turno, turma, Integer.toString(l.getInscritos_no_turno()),
				isTurnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas,
				isTurno_com_inscricoes_superiores_a_capacidade_das_salas, auxData[0], auxData[1], auxData[2],
				auxData[3], auxData[4], auxData[5], auxData[6], auxData[7] };

		return data;
	}

	private String[] handleNullfields(Lecture l) {
		String dia_da_semana = "";
		if (!l.getDia_da_Semana().equals(null)) {
			dia_da_semana = l.getDia_da_Semana();
		}

		String inicio = "";
		if (l.getInicio() != null) {
			inicio = l.getInicio().toString("HH:mm");
		}

		String fim = "";
		if (l.getFim() != null) {
			fim = l.getFim().toString("HH:mm");
		}

		String dia = "";
		if (l.getFim() != null) {
			dia = l.getFim().toString("dd-MM-yyyy");
		}

		String caracteristicas_da_sala_pedida_para_a_aula = "";
		if (l.getCaracteristicas_da_sala_pedida_para_a_aula() != null) {
			caracteristicas_da_sala_pedida_para_a_aula = l.getCaracteristicas_da_sala_pedida_para_a_aula();
		}

		String sala = "";
		if (l.getSala_da_aula() != null) {
			sala = l.getSala_da_aula();
		}

		String lotacao = "";
		if (!Integer.toString(l.getLotacao()).equals(null)) {
			lotacao = Integer.toString(l.getLotacao());
		}

		String caracteristicas_reais_da_sala = "";
		if (l.getCaracteristicas_reais_da_sala() != null) {
			caracteristicas_reais_da_sala = l.getCaracteristicas_reais_da_sala().toString();
		}
		String[] data = { dia_da_semana, inicio, fim, dia, caracteristicas_da_sala_pedida_para_a_aula, sala, lotacao,
				caracteristicas_reais_da_sala };

		return data;
	}




}
