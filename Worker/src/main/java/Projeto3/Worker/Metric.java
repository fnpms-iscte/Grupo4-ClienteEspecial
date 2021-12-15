package Projeto3.Worker;

import java.util.ArrayList;
import java.util.List;

import Projeto3.Worker.Models.Lecture;

public class Metric {
    final String name;
    final List<Double> results = new ArrayList<Double>();

    public Metric(String name){
        this.name = name;


    }

    public Double evaluate(List<Lecture> lectList) {
        return 0.0;
    }


}
