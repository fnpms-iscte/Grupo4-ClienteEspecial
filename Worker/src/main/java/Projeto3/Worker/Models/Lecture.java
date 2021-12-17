package Projeto3.Worker.Models;

import java.util.ArrayList;
import java.util.LinkedList;
import org.joda.time.DateTime;

public class Lecture {

	private  LinkedList<String> Curso;
	private  String Unidade_de_execucaoo;
	private  String Turno;
	private  String Turma;
	private  int Inscritos_no_turno;
	private  boolean Turnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas;
    private  boolean Turno_com_inscricoes_superiores_a_capacidade_das_salas;
	private  String Dia_da_Semana;
	private  DateTime Inicio;
	private  DateTime Fim;
	private  String Caracteristicas_da_sala_pedida_para_a_aula;
	private String Sala_da_aula;
	private int Lotacao;
	private ArrayList<String> Caracteristicas_reais_da_sala;


	//Headers of the csv file because of json scrambling
	public static final String[] HEADERS = {"?Curso","Unidade de execução","Turno","Turma","Inscritos no turno (no 1º semestre é baseado em estimativas)",
	"Turnos com capacidade superior à capacidade das características das salas","Turno com inscrições superiores à capacidade das salas","Dia da Semana","Início","Fim","Dia",
	"Características da sala pedida para a aula","Sala da aula" , "Lotação", "Características reais da sala"
	};

	public Lecture(LinkedList<String> course, String name, String shift, String class_name, int n_students, boolean Free_Spots, boolean Capacity_Overflow,
	String week_day, DateTime start_date, DateTime end_date, String required_room_characteristics) {
		this.Curso = course;
		this.Unidade_de_execucaoo = name;
		this.Turno = shift;
		this.Turma = class_name;
		this.Inscritos_no_turno = n_students;
		this.Turnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas = Free_Spots;
		this.Turno_com_inscricoes_superiores_a_capacidade_das_salas = Capacity_Overflow;
		this.Dia_da_Semana = week_day;
		this.Inicio = start_date;
		this.Fim = end_date;
		this.Caracteristicas_da_sala_pedida_para_a_aula = required_room_characteristics;
	}

	public LinkedList<String> getCourse() {
		return Curso;
	}

	public void setCourse(LinkedList<String> course) {
		this.Curso = course;
	}



	public String getName() {
		return Unidade_de_execucaoo;
	}



	public void setName(String name) {
		this.Unidade_de_execucaoo = name;
	}



	public String getShift() {
		return Turno;
	}



	public void setShift(String shift) {
		this.Turno = shift;
	}



	public String getClass_name() {
		return Turma;
	}



	public void setClass_name(String class_name) {
		this.Turma = class_name;
	}



	public int getN_students() {
		return Inscritos_no_turno;
	}



	public void setN_students(int n_students) {
		this.Inscritos_no_turno = n_students;
	}



	public boolean isFree_Spots() {
		return Turnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas;
	}



	public void setFree_Spots(boolean free_Spots) {
		Turnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas = free_Spots;
	}



	public boolean isCapacity_Overflow() {
		return Turno_com_inscricoes_superiores_a_capacidade_das_salas;
	}



	public void setCapacity_Overflow(boolean capacity_Overflow) {
		Turno_com_inscricoes_superiores_a_capacidade_das_salas = capacity_Overflow;
	}



	public String getWeek_day() {
		return Dia_da_Semana;
	}



	public void setWeek_day(String week_day) {
		this.Dia_da_Semana = week_day;
	}



	public DateTime getStart_date() {
		return Inicio;
	}



	public void setStart_date(DateTime start_date) {
		this.Inicio = start_date;
	}



	public DateTime getEnd_date() {
		return Fim;
	}



	public void setEnd_date(DateTime end_date) {
		this.Fim = end_date;
	}



	public String getRequired_room_characteristics() {
		return Caracteristicas_da_sala_pedida_para_a_aula;
	}



	public void setRequired_room_characteristics(String required_room_characteristics) {
		this.Caracteristicas_da_sala_pedida_para_a_aula = required_room_characteristics;
	}
	
	public String getRoom_name() {
		return Sala_da_aula;
	}

	public void setRoom_name(String room_name) {
		this.Sala_da_aula = room_name;
	}

	public int getRoom_lotation() {
		return Lotacao;
	}

	public void setRoom_lotation(int room_lotation) {
		this.Lotacao = room_lotation;
	}

	public ArrayList<String> getRoom_characteristics() {
		return Caracteristicas_reais_da_sala;
	}

	public void setRoom_characteristics(ArrayList<String> room_characteristics) {
		this.Caracteristicas_reais_da_sala = room_characteristics;
	}
}