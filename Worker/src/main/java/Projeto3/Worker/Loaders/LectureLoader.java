package Projeto3.Worker.Loaders;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Projeto3.Worker.Models.Lecture;

public class LectureLoader {

	private JSONArray lectures_json;
	private LinkedList<Lecture> lectures = new LinkedList<>();
	private String courseS;
	private String nameS;
	private String shiftS;
	private String class_nameS;
	private String n_studentsS;
	private String free_SpotsS;
	private String Capacity_OverflowS;
	private String week_dayS;
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
					nameS = lectureJson.getString(key);
				} else if (key.equals("Turno")) {
					shiftS = lectureJson.getString(key);
				} else if (key.equals("Turma")) {
					class_nameS = lectureJson.getString(key);
				} else if (key.equals("Inscritos no turno (no 1º semestre é baseado em estimativas)")) {
					n_studentsS = lectureJson.getString(key);
				} else if (key.equals("Turnos com capacidade superior à capacidade das características das salas")) {
					free_SpotsS = lectureJson.getString(key);
				} else if (key.equals("Turno com inscrições superiores à capacidade das salas")) {
					Capacity_OverflowS = lectureJson.getString(key);
				} else if (key.equals("Dia da Semana")) {
					week_dayS = lectureJson.getString(key);
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

			// Converting to their correct form and verification
			LinkedList<String> course = new LinkedList<>(Arrays.asList(courseS.split(", ")));
			String name = checkEmptyField(nameS);
			if(name.equals("Not Given"))
				continue;
			String shift = checkEmptyField(shiftS);
			String class_name = checkEmptyField(class_nameS);
			int n_students = n_studentsS.isEmpty() ? -1 : Integer.parseInt(n_studentsS);
			boolean Turnos_com_capacidade_superior = checkBools(free_SpotsS);
			boolean Turno_com_inscrições_superiores = checkBools(Capacity_OverflowS);
			String week_day = checkEmptyField(week_dayS);
			DateTime start_date = convertDateTime(startS, dateS);
			DateTime end_date = convertDateTime(endS, dateS);

			// Lecture Object Creation
			Lecture lecture = new Lecture(course, name, shift, class_name, n_students, Turnos_com_capacidade_superior,
			Turno_com_inscrições_superiores, week_day, start_date, end_date, required_room_characteristics);
			lectures.add(lecture);			
		}
	}

	public DateTime convertDateTime(String timeS, String dateS){
		if(!timeS.isEmpty()){
			String[] time = timeS.split(":");
			if(dateS.isEmpty()){
				DateTime datetime = new DateTime(0, 12, 31, Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
				return datetime;
			}else{
				String[] date = dateS.split("/");
				DateTime datetime = new DateTime(Integer.parseInt(date[2]), Integer.parseInt(date[1]),Integer.parseInt(date[0]),
						Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
				return datetime;
			}
		}else{
			return null;
		}
	}

	public boolean checkBools(String s){
		boolean b = true;
			if (s.equals("FALSO")) {
				b = false;
			}
		return b;
	}

	public String checkEmptyField(String s){
		String output;
		if(s.isEmpty()){
			output = "Not Given";
		}else
			output = s;
		return output;
	}

	public LinkedList<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(LinkedList<Lecture> lectures) {
		this.lectures = lectures;
	}

}