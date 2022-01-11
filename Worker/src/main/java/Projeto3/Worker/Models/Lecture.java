package Projeto3.Worker.Models;

import java.util.ArrayList;
import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

public class Lecture {

	private int id;
	private LinkedList<String> Curso;
	private String Unidade_de_execucaoo;
	private String Turno;
	private String Turma;
	private int Inscritos_no_turno;
	private boolean Turnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas;
	private boolean Turno_com_inscricoes_superiores_a_capacidade_das_salas;
	private String Dia_da_Semana;
	private DateTime Inicio;
	private DateTime Fim;
	private String Caracteristicas_da_sala_pedida_para_a_aula;
	private String Sala_da_aula;
	private int Lotacao;
	private ArrayList<String> Caracteristicas_reais_da_sala;

	// Headers of the csv file because of json scrambling
	public static final String[] HEADERS = { "?Curso", "Unidade de execução", "Turno", "Turma",
			"Inscritos no turno (no 1º semestre é baseado em estimativas)",
			"Turnos com capacidade superior à capacidade das características das salas",
			"Turno com inscrições superiores à capacidade das salas", "Dia da Semana", "Início", "Fim", "Dia",
			"Características da sala pedida para a aula", "Sala da aula", "Lotação", "Características reais da sala" };

	public Lecture(int id, LinkedList<String> course, String name, String shift, String class_name, int n_students,
			boolean Free_Spots, boolean Capacity_Overflow, String week_day, DateTime start_date, DateTime end_date,
			String required_room_characteristics) {
		this.id = id;
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

	public int getId() {
		return id;
	}

	public LinkedList<String> getCurso() {
		return Curso;
	}

	public void setCurso(LinkedList<String> curso) {
		Curso = curso;
	}

	public String getUnidade_de_execucaoo() {
		return Unidade_de_execucaoo;
	}

	public void setUnidade_de_execucaoo(String unidade_de_execucaoo) {
		Unidade_de_execucaoo = unidade_de_execucaoo;
	}

	public String getTurno() {
		return Turno;
	}

	public void setTurno(String turno) {
		Turno = turno;
	}

	public String getTurma() {
		return Turma;
	}

	public void setTurma(String turma) {
		Turma = turma;
	}

	public int getInscritos_no_turno() {
		return Inscritos_no_turno;
	}

	public void setInscritos_no_turno(int inscritos_no_turno) {
		Inscritos_no_turno = inscritos_no_turno;
	}

	@JsonIgnore
	public boolean isTurnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas() {
		return Turnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas;
	}

	@JsonIgnore
	public void setTurnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas(
			boolean turnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas) {
		Turnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas = turnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas;
	}

	@JsonIgnore
	public boolean isTurno_com_inscricoes_superiores_a_capacidade_das_salas() {
		return Turno_com_inscricoes_superiores_a_capacidade_das_salas;
	}

	@JsonIgnore
	public void setTurno_com_inscricoes_superiores_a_capacidade_das_salas(
			boolean turno_com_inscricoes_superiores_a_capacidade_das_salas) {
		Turno_com_inscricoes_superiores_a_capacidade_das_salas = turno_com_inscricoes_superiores_a_capacidade_das_salas;
	}

	public String getDia_da_Semana() {
		return Dia_da_Semana;
	}

	public void setDia_da_Semana(String dia_da_Semana) {
		Dia_da_Semana = dia_da_Semana;
	}

	public DateTime getInicio() {
		return Inicio;
	}

	public void setInicio(DateTime inicio) {
		Inicio = inicio;
	}

	public DateTime getFim() {
		return Fim;
	}

	public void setFim(DateTime fim) {
		Fim = fim;
	}

	public String getCaracteristicas_da_sala_pedida_para_a_aula() {
		return Caracteristicas_da_sala_pedida_para_a_aula;
	}

	public void setCaracteristicas_da_sala_pedida_para_a_aula(String caracteristicas_da_sala_pedida_para_a_aula) {
		Caracteristicas_da_sala_pedida_para_a_aula = caracteristicas_da_sala_pedida_para_a_aula;
	}

	public String getSala_da_aula() {
		return Sala_da_aula;
	}

	public void setSala_da_aula(String sala_da_aula) {
		Sala_da_aula = sala_da_aula;
	}

	public int getLotacao() {
		return Lotacao;
	}

	public void setLotacao(int lotacao) {
		Lotacao = lotacao;
	}

	public ArrayList<String> getCaracteristicas_reais_da_sala() {
		return Caracteristicas_reais_da_sala;
	}

	public void setCaracteristicas_reais_da_sala(ArrayList<String> caracteristicas_reais_da_sala) {
		Caracteristicas_reais_da_sala = caracteristicas_reais_da_sala;
	}

	public void cleanRoom() {
		this.Sala_da_aula = "";
		this.Lotacao = 0;
		this.Caracteristicas_reais_da_sala = null;
	}

	public boolean hasRoom() {
		if (this.Sala_da_aula == null || this.Sala_da_aula.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	public void setRoom(Room r) {
		this.Sala_da_aula = r.getName();
		this.Lotacao = r.getNormal_capacity();
		this.Caracteristicas_reais_da_sala = r.getCharacteristics();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("ID", id).append("name", Curso)
				.append("Unidade_de_execução", Unidade_de_execucaoo).append("Turno", Turno).append("Turma", Turma)
				.append("Inscritos_no_turno", Inscritos_no_turno)
				.append("Turnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas",
						Turnos_com_capacidade_superior_a_capacidade_das_caracteristicas_das_salas)
				.append("Turno_com_inscricoes_superiores_a_capacidade_das_salas",
						Turno_com_inscricoes_superiores_a_capacidade_das_salas)
				.append("Dia_da_Semana", Dia_da_Semana).append("Inicio", Inicio).append("Fim", Fim)
				.append("Caracteristicas_da_sala_pedida_para_a_aula", Caracteristicas_da_sala_pedida_para_a_aula)
				.append("Sala_da_aula", Sala_da_aula).append("Lotacao", Lotacao)
				.append("Caracteristicas_reais_da_sala", Caracteristicas_reais_da_sala).toString();
	}

}