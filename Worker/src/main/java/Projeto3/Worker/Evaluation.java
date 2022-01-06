package Projeto3.Worker;

import java.util.*;

import Projeto3.Worker.Models.Lecture;

public class Evaluation {
	List<Metric> resultList = new ArrayList<Metric>();
	String bestResult = "";

	public Evaluation(List<Lecture> LectList, List<Metric> MetricList) {
		String highestMetric = "";
		Double highestScore = 0.0;
		for (Metric metric : MetricList) {
			Metric clone = new Metric(metric.name);
			clone.results = metric.evaluate(LectList);
			if (clone.results > highestScore) {
				highestScore = clone.results;
				highestMetric = clone.name;
			}
			this.resultList.add(clone);
		}

		this.bestResult = getBestMetric(highestMetric);

	}

	private String getBestMetric(String highestMetric) {
		String aux = "";

		if (highestMetric.equals("ClassCapacityOver")) {
			aux = "As salas não estão sobrelotadas";
		} else if (highestMetric.equals("ClassCapacityUnder")) {
			aux = "As salas não estão a ser desperdiçadas na sua lotação";
		} else if (highestMetric.equals("RoomAllocationChars")) {
			aux = "As características pedidas são asseguradas pela sala assignada";
		}

		return aux;
	}

}