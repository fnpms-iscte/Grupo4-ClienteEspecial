package Projeto3.Worker;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.opencsv.CSVWriter;

import Projeto3.Worker.Models.Lecture;

public class CSVGenerator {

	public void createCSVfile(List<Lecture> lectures, String name) {
		File file = new File( "./timetables/" + name + ".csv");
		try {
			FileWriter outputfile = new FileWriter(file, StandardCharsets.ISO_8859_1);

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
