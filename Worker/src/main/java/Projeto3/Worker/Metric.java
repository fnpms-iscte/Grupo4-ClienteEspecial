package Projeto3.Worker;

import java.util.List;

import Projeto3.Worker.Models.Lecture;

public class Metric {
	final String name;
	Double results = 0.0;

	public Metric(String name) {
		this.name = name;

	}

	public Double evaluate(List<Lecture> lectList) {
		return 0.0;
	}

}
