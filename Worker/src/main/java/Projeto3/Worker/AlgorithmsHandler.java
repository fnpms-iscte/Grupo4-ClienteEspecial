package Projeto3.Worker;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import Projeto3.Worker.Algorithms.IdealAlg;
import Projeto3.Worker.Algorithms.MiddleAlg;
import Projeto3.Worker.Algorithms.NSGAIIIRunner;
import Projeto3.Worker.Algorithms.NSGAIIRunner;
import Projeto3.Worker.Algorithms.PerfectAlg;
import Projeto3.Worker.Algorithms.SMSEMOARunner;
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

		csv_generate.createCSVfile(simpleLectures, clientID + "_Horario1-Simple");
		System.out.println("[AlgorithmsHandler] File Horario1 created");

		output.add(new Response("Horario1-Simple", "Horario1-Simple", simpleEv.resultList, simpleEv.bestResult));

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
		csv_generate.createCSVfile(middleLectures, clientID + "_Horario2-Middle");
		System.out.println("[AlgorithmsHandler] File Horario2 created");

		output.add(new Response("Horario2-Middle", "Horario2-Middle", middleEv.resultList, middleEv.bestResult));
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
		csv_generate.createCSVfile(idealLectures, clientID + "_Horario3-Ideal");
		System.out.println("[AlgorithmsHandler] File Horario3 created");
		output.add(new Response("Horario3-Ideal", "Horario3-Ideal", idealEv.resultList, idealEv.bestResult));

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
		csv_generate.createCSVfile(perfectLectures, clientID + "_Horario4-Perfect");
		System.out.println("[AlgorithmsHandler] File Horario4 created");
		output.add(new Response("Horario4-Perfect", "Horario4-Perfect", perfectEv.resultList, perfectEv.bestResult));

		return perfectLectures;
	}

	public void runNSGAII(List<Lecture> lectures, List<Room> rooms) {
		NSGAIIRunner nsgaii = new NSGAIIRunner(lectures, rooms);
		nsgaii.runAlg();
		String[] lectures_nsgaii = readCSV("NSGAII");
		allocateRoomsToLectures(lectures_nsgaii);
		List<Lecture> nsgaiiLectures = lectures;

		Evaluation nsgaiiEv = new Evaluation(nsgaiiLectures, metricList);
		System.out.println("[AlgorithmsHandler] perfect alg computed");
		csv_generate.createCSVfile(nsgaiiLectures, clientID + "_Horario5-NSGAII");
		System.out.println("[AlgorithmsHandler] File Horario5-NSGAII created");
		output.add(new Response("Horario5-NSGAII", "Horario5-NSGAII", nsgaiiEv.resultList, nsgaiiEv.bestResult));
	}

	public void runNSGAIII(List<Lecture> lectures, List<Room> rooms) {
		NSGAIIIRunner nsgaiii = new NSGAIIIRunner(lectures, rooms);
		nsgaiii.runAlg();
		String[] lectures_nsgaiii = readCSV("NSGAIII");
		allocateRoomsToLectures(lectures_nsgaiii);
		List<Lecture> nsgaiiiLectures = lectures;

		Evaluation nsgaiiiEv = new Evaluation(nsgaiiiLectures, metricList);
		System.out.println("[AlgorithmsHandler] perfect alg computed");
		csv_generate.createCSVfile(nsgaiiiLectures, clientID + "_Horario6-NSGAIII");
		System.out.println("[AlgorithmsHandler] File Horario6-NSGAIII created");
		output.add(new Response("Horario6-NSGAII", "Horario6-NSGAIII", nsgaiiiEv.resultList, nsgaiiiEv.bestResult));
	}

	public void runSMSEMOA(List<Lecture> lectures, List<Room> rooms) {
		SMSEMOARunner smsemoa = new SMSEMOARunner(lectures, rooms);
		smsemoa.runAlg();
		String[] lectures_smsemoa = readCSV("SMSEMOA");
		allocateRoomsToLectures(lectures_smsemoa);
		List<Lecture> smsemoaLectures = lectures;

		Evaluation smsemoaEv = new Evaluation(smsemoaLectures, metricList);
		System.out.println("[AlgorithmsHandler] perfect alg computed");
		csv_generate.createCSVfile(smsemoaLectures, clientID + "_Horario7-SMSEMOA");
		System.out.println("[AlgorithmsHandler] File Horario6-NSGAIII created");
		output.add(new Response("Horario7-SMSEMOA", "Horario7-SMSEMOA", smsemoaEv.resultList, smsemoaEv.bestResult));
	}

	private String[] readCSV(String name) {
		String path = "./RESULTADOS/" + name + "Study/data/" + name + "/ProblemConfiguration/";
		String[] allData = null;
		File var = new File(path + "VAR0.csv");
		File fun = new File(path + "FUN0.csv");
		try {
			FileReader filereader = new FileReader(var);
			CSVReader csvReader = new CSVReaderBuilder(filereader).build();
			allData = csvReader.readNext();

		} catch (Exception e) {
			e.printStackTrace();
		}

		var.delete();
		fun.delete();

		return allData;

	}

	private void allocateRoomsToLectures(String[] data) {
		int count = 0;
		boolean allocate;
		for (Lecture l : lectures) {
			allocate = true;
			if (!l.getCaracteristicas_da_sala_pedida_para_a_aula().equals("Não necessita de sala") && !l.hasRoom()
					&& !l.getDia_da_Semana().equals("Not Given")) {
				Interval new_booking = new Interval(l.getInicio(), l.getFim());
				for (Interval interval : rooms.get(count).getLectures_times_booked()) {
					if (interval.overlaps(new_booking)) {
						allocate = false;
					}
				}
				if (allocate) {
					l.setRoom(rooms.get(count));
				}
				count++;
			}
		}
	}

}
