package Projeto3.Worker.Models;

import java.util.Hashtable;
import java.util.List;

public class Response {
    private String name;
    private String id;
    private List<Lecture> lectures;
    private Hashtable<String, Double>  metrics;
    private String        best_metric;
    
    public Response(String name, String id, List<Lecture> lectures, Hashtable<String, Double> resultList, String best_metric) {
        this.name = name;
        this.id= id;
        this.lectures = lectures;
        this.metrics = resultList;
        this.best_metric = best_metric;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }
    
    public List<Lecture> getLectures() {
        return lectures;
    }

    public Hashtable<String, Double> getMetrics() {
        return metrics;
    }

    public String getBest_metric() {
        return best_metric;
    }

    public String setName(String name) {
        return name;
    }

    public String setID(String id) {
        return id;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public void setMetrics(Hashtable<String, Double> metrics) {
        this.metrics = metrics;
    }

    public void setBest_metric(String best_metric) {
        this.best_metric = best_metric;
    }

    @Override    
    public String toString() {    
        // TODO Auto-generated method stub    
        return "\"Response [Name " + this.name + "id " + this.id + "Lecture List "+ this.lectures + ", Metric results = " + this.metrics + ", Best Metric to use = " + this.best_metric + "]";    
    }  
}
