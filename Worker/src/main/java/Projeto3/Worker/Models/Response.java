package Projeto3.Worker.Models;

import java.util.Hashtable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private String id;
    @JsonProperty("lectures")
    private List<Lecture> lectures;
    @JsonProperty("metrics")
    private Hashtable<String, Double>  metrics;
    @JsonProperty("best_metric")
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

    public void setName(String name) {
        this.name = name;
    }

    public void setID(String id) {
        this.id= id;
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
 
}
