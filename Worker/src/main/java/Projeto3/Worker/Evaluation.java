package Projeto3.Worker;

import java.util.*;

import Projeto3.Worker.Models.Lecture;

public class Evaluation {
    Hashtable<String, Double> resultList = new Hashtable<String, Double>();
    String bestResult = "";

    public Evaluation(List<Lecture> LectList, List<Metric> MetricList){
        String highestMetric = "";
        Double highestScore = 0.0;
        for(Metric metric : MetricList){
            double score = metric.evaluate(LectList);
            if(score > highestScore){
                highestScore = score;
                highestMetric = metric.name;
            }
            this.resultList.put(metric.name, score);
        }
        
        this.bestResult = highestMetric;
        // System.out.println(this.resultList);
        // System.out.println(this.bestResult);

    }

    
}