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
			metric.results = metric.evaluate(LectList);
			if (metric.results > highestScore) {
				highestScore = metric.results;
				highestMetric = metric.name;
			}
			this.resultList.add(metric);
		}

		this.bestResult = highestMetric;

	}

}