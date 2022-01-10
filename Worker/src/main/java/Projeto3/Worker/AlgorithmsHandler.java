package Projeto3.Worker;

import java.util.ArrayList;
import java.util.List;

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

public class AlgorithmsHandler {

	private List<Room> rooms;
	private List<Lecture> lectures;
	private List<Metric> metricList;
	private List<Response> output;
	private CSVGenerator csv_generate;
	private String clientID;

	public AlgorithmsHandler(List<Room> rooms, List<Lecture> lectures, String clientID) {
		this.rooms = rooms;
		this.lectures = lectures;
		this.clientID = clientID;
		this.csv_generate = new CSVGenerator();
		this.output = new ArrayList<Response>();
		initMetrics();
	}

	private void initMetrics() {
		metricList = new ArrayList<>();
		ClassCapacityOver metric1 = new ClassCapacityOver();
		ClassCapacityUnder metric2 = new ClassCapacityUnder();
		RoomAllocationChars metric3 = new RoomAllocationChars();
		metricList.add(metric1);
		metricList.add(metric2);
		metricList.add(metric3);
		System.out.println("[AlgorithmsHandler] Initialized Metrics");
	}

	public List<Response> getOutput() {
		return output;
	}

	private void clearLecturesRooms() {
		for (Room r : rooms) {
			r.clearLecture();
		}

		for (Lecture l : lectures) {
			l.cleanRoom();
		}
	}

	public void runBasicAlg() {
		SimpleAlg sa = new SimpleAlg();
		List<Lecture> simpleLectures = new ArrayList<>();
		simpleLectures.addAll(lectures);
		sa.compute(simpleLectures, rooms);

		// Evaluation of metrics
		Evaluation simpleEv = new Evaluation(simpleLectures, metricList);

		System.out.println("[AlgorithmsHandler] Basic alg computed");

		csv_generate.createCSVfile(simpleLectures, clientID + "_Horario1");
		System.out.println("[AlgorithmsHandler] File Horario1 created");

		output.add(new Response("Horario1", "Horario1", simpleEv.resultList, simpleEv.bestResult));

	}

	public void runMiddleAlg() {
		clearLecturesRooms();
		MiddleAlg ma = new MiddleAlg();
		List<Lecture> middleLectures = new ArrayList<>();
		middleLectures.addAll(lectures);
		ma.compute(middleLectures, rooms);

		// Evaluation of metrics
		Evaluation middleEv = new Evaluation(middleLectures, metricList);

		System.out.println("[AlgorithmsHandler] Middle alg computed");
		csv_generate.createCSVfile(middleLectures, clientID + "_Horario2");
		System.out.println("[AlgorithmsHandler] File Horario2 created");

		output.add(new Response("Horario2", "Horario2", middleEv.resultList, middleEv.bestResult));
		clearLecturesRooms();
	}

	public void runIdealAlg() {
		clearLecturesRooms();
		IdealAlg ia = new IdealAlg();
		List<Lecture> idealLectures = new ArrayList<>();
		idealLectures.addAll(lectures);
		ia.compute(idealLectures, rooms);

		// Evaluation of metrics
		Evaluation idealEv = new Evaluation(idealLectures, metricList);

		System.out.println("[AlgorithmsHandler] Ideal alg computed");
		csv_generate.createCSVfile(idealLectures, clientID + "_Horario3");
		System.out.println("[AlgorithmsHandler] File Horario3 created");
		output.add(new Response("Horario3", "Horario3", idealEv.resultList, idealEv.bestResult));

	}

	public List<Lecture> runPerfectAlg() {
		clearLecturesRooms();
		PerfectAlg pa = new PerfectAlg();
		List<Lecture> perfectLectures = new ArrayList<>();
		perfectLectures.addAll(lectures);
		pa.compute(perfectLectures, rooms);

		// Evaluation of metrics
		Evaluation perfectEv = new Evaluation(perfectLectures, metricList);

		System.out.println("[AlgorithmsHandler] perfect alg computed");
		csv_generate.createCSVfile(perfectLectures, clientID + "_Horario4");
		System.out.println("[AlgorithmsHandler] File Horario4 created");
		output.add(new Response("Horario4", "Horario4", perfectEv.resultList, perfectEv.bestResult));

		return perfectLectures;
	}

}
