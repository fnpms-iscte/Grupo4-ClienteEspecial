package Projeto3.Worker.Models;

import java.util.LinkedList;
import org.joda.time.DateTime;

public class Lecture {

	private final LinkedList<String> course;
	private final String name;
	private final String shift;
	private final String class_name;
	private final int n_students;
	private final boolean Free_Spots;
    private final boolean Capacity_Overflow;
	private final String week_day;
	private final DateTime start_date;
	private final DateTime end_date;
	private final String required_room_characteristics;
	private Room room;
	private int capacity;
	private LinkedList<String> real_characteristics;

	//Headers of the csv file because of json scrambling
	public static final String[] HEADERS = {"?Curso","Unidade de execução","Turno","Turma","Inscritos no turno (no 1º semestre é baseado em estimativas)",
	"Turnos com capacidade superior à capacidade das características das salas","Turno com inscrições superiores à capacidade das salas","Dia da Semana","Início","Fim","Dia",
	"Características da sala pedida para a aula","Sala da aula" , "Lotação", "Características reais da sala"
	};

	public Lecture(LinkedList<String> course, String name, String shift, String class_name, int n_students, boolean Free_Spots, boolean Capacity_Overflow,
	String week_day, DateTime start_date, DateTime end_date, String required_room_characteristics) {
		this.course = course;
		this.name = name;
		this.shift = shift;
		this.class_name = class_name;
		this.n_students = n_students;
		this.Free_Spots = Free_Spots;
		this.Capacity_Overflow = Capacity_Overflow;
		this.week_day = week_day;
		this.start_date = start_date;
		this.end_date = end_date;
		this.required_room_characteristics = required_room_characteristics;
	}

	public LinkedList<String> getCourse() {
		return course;
	}

	public String getName() {
		return name;
	}

	public String getShift() {
		return shift;
	}

	public String getClass_name() {
		return class_name;
	}

	public int getN_students() {
		return n_students;
	}

	public boolean getFree_Spots() {
		return Free_Spots;
	}

	public boolean getCapacity_Overflow() {
		return Capacity_Overflow;
	}

	public String getWeek_day() {
		return week_day;
	}

	public DateTime getStart_date() {
		return start_date;
	}

	public DateTime getEnd_date() {
		return end_date;
	}

	public String getRequired_room_characteristics() {
		return required_room_characteristics;
	}

	public int getCapacity() {
		return capacity;
	}

	public LinkedList<String> getReal_characteristics() {
		return real_characteristics;
	}
	
	// Room allocation
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setReal_characteristics(LinkedList<String> list) {
		this.real_characteristics = list;
	}

}