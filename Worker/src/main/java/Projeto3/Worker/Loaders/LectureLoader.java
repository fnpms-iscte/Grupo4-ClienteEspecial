package Projeto3.Worker.Loaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Room;

public class LectureLoader {

	private JSONArray lectures_json;
	private LinkedList<Lecture> lectures = new LinkedList<>();
	private String courseS;
	private String name;
	private String shift;
	private String class_name;
	private String n_studentsS;
	private String free_SpotsS;
	private String Capacity_OverflowS;
	private String week_day;
	private String startS;
	private String endS;
	private String dateS;
	private String required_room_characteristics;

	public LectureLoader(JSONArray lectures_json) throws JSONException {
		this.lectures_json = lectures_json;
		setupLecture();
	}

	public void setupLecture() throws JSONException {
		for (int i = 0; i < lectures_json.length(); i++) {
			JSONObject lectureJson = lectures_json.getJSONObject(i);

			Iterator<String> keys = lectureJson.keys();
			while (keys.hasNext()) {

				String key = keys.next();
				if (key.contains("Curso")) {
					courseS = lectureJson.getString(key);
				} else if (key.equals("Unidade de execução")) {
					name = lectureJson.getString(key);
				} else if (key.equals("Turno")) {
					shift = lectureJson.getString(key);
				} else if (key.equals("Turma")) {
					class_name = lectureJson.getString(key);
				} else if (key.equals("Inscritos no turno (no 1º semestre é baseado em estimativas)")) {
					n_studentsS = lectureJson.getString(key);
				} else if (key.equals("Turnos com capacidade superior à capacidade das características das salas")) {
					free_SpotsS = lectureJson.getString(key);
				} else if (key.equals("Turno com inscrições superiores à capacidade das salas")) {
					Capacity_OverflowS = lectureJson.getString(key);
				} else if (key.equals("Dia da Semana")) {
					week_day = lectureJson.getString(key);
				} else if (key.equals("Início")) {
					startS = lectureJson.getString(key);
				} else if (key.equals("Fim")) {
					endS = lectureJson.getString(key);
				} else if (key.equals("Dia")) {
					dateS = lectureJson.getString(key);
				} else if (key.equals("Características da sala pedida para a aula")) {
					required_room_characteristics = lectureJson.getString(key);
				}
			}

			// Converting to their correct form
			LinkedList<String> course = new LinkedList<>(Arrays.asList(courseS.split(", ")));
			final int n_students = n_studentsS.isEmpty() ? -1 : Integer.parseInt(n_studentsS);

			boolean Free_Spots = true;
			if (free_SpotsS.equals("FALSO")) {
				Free_Spots = false;
			}

			boolean Capacity_Overflow = true;
			if (Capacity_OverflowS.equals("FALSO")) {
				Capacity_Overflow = false;
			}

			// Date and time creation of schedules and missing values
			if (!dateS.isEmpty() && !startS.isEmpty() && !endS.isEmpty()) {
				String[] date = dateS.split("/");
				String[] start = startS.split(":");
				String[] end = endS.split(":");
				DateTime start_date = new DateTime(Integer.parseInt(date[2]), Integer.parseInt(date[1]),
						Integer.parseInt(date[0]), Integer.parseInt(start[0]), Integer.parseInt(start[1]),
						Integer.parseInt(start[2]));
				DateTime end_date = new DateTime(Integer.parseInt(date[2]), Integer.parseInt(date[1]),
						Integer.parseInt(date[0]), Integer.parseInt(end[0]), Integer.parseInt(end[1]),
						Integer.parseInt(end[2]));
				Lecture lecture = new Lecture(course, name, shift, class_name, n_students, Free_Spots,
						Capacity_Overflow, week_day, start_date, end_date, required_room_characteristics);
				lectures.add(lecture);
			} else if (dateS.isEmpty() && !startS.isEmpty() && !endS.isEmpty()) {
				String[] start = startS.split(":");
				String[] end = endS.split(":");
				DateTime start_date = new DateTime(0, 12, 31, Integer.parseInt(start[0]), Integer.parseInt(start[1]),
						Integer.parseInt(start[2]));
				DateTime end_date = new DateTime(0, 12, 31, Integer.parseInt(end[0]), Integer.parseInt(end[1]),
						Integer.parseInt(end[2]));
				Lecture lecture = new Lecture(course, name, shift, class_name, n_students, Free_Spots,
						Capacity_Overflow, week_day, start_date, end_date, required_room_characteristics);
				lectures.add(lecture);
			} else {
				Lecture lecture = new Lecture(course, name, shift, class_name, n_students, Free_Spots,
						Capacity_Overflow, week_day, null, null, required_room_characteristics);
				lectures.add(lecture);
			}

		}

	}

	public LinkedList<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(LinkedList<Lecture> lectures) {
		this.lectures = lectures;
	}

}