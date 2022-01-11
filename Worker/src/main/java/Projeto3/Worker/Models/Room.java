package Projeto3.Worker.Models;

import java.util.ArrayList;
import java.util.LinkedList;

import org.joda.time.Interval;

public class Room {

	// Headers and CHARACTERISTICS of the csv file because of json scrambling
	public static final String[] CHARACTERISTICS_LIST = { "Anfiteatro aulas", "Apoio técnico eventos", "Arq 1", "Arq 2",
			"Arq 3", "Arq 4", "Arq 5", "Arq 6", "Arq 9", "BYOD (Bring Your Own Device)", "Focus Group",
			"Horário sala visível portal público", "Laboratório de Arquitectura de Computadores I",
			"Laboratório de Arquitectura de Computadores II", "Laboratório de Bases de Engenharia",
			"Laboratório de Electrónica", "Laboratório de Informática", "Laboratório de Jornalismo",
			"Laboratório de Redes de Computadores I", "Laboratório de Redes de Computadores II",
			"Laboratório de Telecomunicações", "Sala Aulas Mestrado", "Sala Aulas Mestrado Plus", "Sala NEE",
			"Sala Provas", "Sala Reunião", "Sala de Arquitectura", "Sala de Aulas normal", "videoconferencia",
	"Átrio" };

	public static final String[] HEADERS = { "Edifício", "Nome sala", "Capacidade Normal", "Capacidade Exame",
			"Nº características", "Anfiteatro aulas", "Apoio técnico eventos", "Arq 1", "Arq 2", "Arq 3", "Arq 4",
			"Arq 5", "Arq 6", "Arq 9", "BYOD (Bring Your Own Device)", "Focus Group",
			"Horário sala visível portal público", "Laboratório de Arquitectura de Computadores I",
			"Laboratório de Arquitectura de Computadores II", "Laboratório de Bases de Engenharia",
			"Laboratório de Electrónica", "Laboratório de Informática", "Laboratório de Jornalismo",
			"Laboratório de Redes de Computadores I", "Laboratório de Redes de Computadores II",
			"Laboratório de Telecomunicações", "Sala Aulas Mestrado", "Sala Aulas Mestrado Plus", "Sala NEE",
			"Sala Provas", "Sala Reunião", "Sala de Arquitectura", "Sala de Aulas normal", "videoconferencia",
	"Átrio" };

	private final String building;
	private final String name;
	private final int normal_capacity;
	private final int exam_capacity;
	private final int n_characteristics;
	private final ArrayList<String> characteristics;
	private LinkedList<Interval> lectures_times_booked;
	private final int id;

	public Room(String building, String name, int normal_capacity, int exam_capacity, int n_characteristics,
			ArrayList<String> characteristics , int id) {
		this.building = building;
		this.name = name;
		this.normal_capacity = normal_capacity;
		this.exam_capacity = exam_capacity;
		this.n_characteristics = n_characteristics;
		this.characteristics = characteristics;
		this.lectures_times_booked = new LinkedList<Interval>();
		this.id = id;
	}

	public String getBuilding() {
		return building;
	}

	public String getName() {
		return name;
	}

	public int getNormal_capacity() {
		return normal_capacity;
	}

	public int getExam_capacity() {
		return exam_capacity;
	}

	public int getN_characteristics() {
		return n_characteristics;
	}

	public ArrayList<String> getCharacteristics() {
		return characteristics;
	}

	public int getId() {
		return id;
	}

	// Check if room has the correct one
	public boolean has_Characteristic(String characteristics) throws IllegalArgumentException {
		boolean result = false;
		if (characteristics.equals("Não necessita de sala")) {
			return false;
		}
		if (this.has_Valid_Characteristic(characteristics))
			result = true;

		return result;
	}

	// Validates if the characteristic is valid
	public boolean has_Valid_Characteristic(String characteristic) throws IllegalArgumentException {
		for (String c : characteristics) {
			if (c.equals(characteristic))
				return true;
		}
		return false;
	}

	// Methods for the ideal Alg
	public LinkedList<Interval> getLectures_times_booked() {
		return lectures_times_booked;
	}

	public void addLecture(Interval lecture_time) {
		lectures_times_booked.add(lecture_time);
	}

	public void clearLecture() {
		lectures_times_booked.clear();
	}
	
	public void removeLecture(Interval interval) {
		lectures_times_booked.remove(interval);		
		
	}

}