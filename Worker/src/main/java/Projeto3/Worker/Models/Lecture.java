package Projeto3.Worker.Models;

import java.util.LinkedList;
import org.joda.time.DateTime;

public class Lecture {

	private  LinkedList<String> course;
	private  String name;
	private  String shift;
	private  String class_name;
	private  int n_students;
	private  boolean Free_Spots;
    private  boolean Capacity_Overflow;
	private  String week_day;
	private  DateTime start_date;
	private  DateTime end_date;
	private  String required_room_characteristics;
	private String room_name;
	private int room_lotation;
	private LinkedList<String> room_characteristics;


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

	public void setCourse(LinkedList<String> course) {
		this.course = course;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getShift() {
		return shift;
	}



	public void setShift(String shift) {
		this.shift = shift;
	}



	public String getClass_name() {
		return class_name;
	}



	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}



	public int getN_students() {
		return n_students;
	}



	public void setN_students(int n_students) {
		this.n_students = n_students;
	}



	public boolean isFree_Spots() {
		return Free_Spots;
	}



	public void setFree_Spots(boolean free_Spots) {
		Free_Spots = free_Spots;
	}



	public boolean isCapacity_Overflow() {
		return Capacity_Overflow;
	}



	public void setCapacity_Overflow(boolean capacity_Overflow) {
		Capacity_Overflow = capacity_Overflow;
	}



	public String getWeek_day() {
		return week_day;
	}



	public void setWeek_day(String week_day) {
		this.week_day = week_day;
	}



	public DateTime getStart_date() {
		return start_date;
	}



	public void setStart_date(DateTime start_date) {
		this.start_date = start_date;
	}



	public DateTime getEnd_date() {
		return end_date;
	}



	public void setEnd_date(DateTime end_date) {
		this.end_date = end_date;
	}



	public String getRequired_room_characteristics() {
		return required_room_characteristics;
	}



	public void setRequired_room_characteristics(String required_room_characteristics) {
		this.required_room_characteristics = required_room_characteristics;
	}
	
	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public int getRoom_lotation() {
		return room_lotation;
	}

	public void setRoom_lotation(int room_lotation) {
		this.room_lotation = room_lotation;
	}

	public LinkedList<String> getRoom_characteristics() {
		return room_characteristics;
	}

	public void setRoom_characteristics(LinkedList<String> room_characteristics) {
		this.room_characteristics = room_characteristics;
	}
}