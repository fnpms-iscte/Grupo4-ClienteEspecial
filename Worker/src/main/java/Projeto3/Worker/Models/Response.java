package Projeto3.Worker.Models;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import Projeto3.Worker.Metric;

public class Response {
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private String id;
    @JsonProperty("lectures")
    private List<Lecture> lectures;
    @JsonProperty("metrics")
    private List<Metric>  metrics;
    @JsonProperty("best_metric")
    private String  best_metric;
    
    public Response(String name, String id, List<Lecture> lectures, List<Metric> resultList, String best_metric) {
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

    public List<Metric> getMetrics() {
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

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    public void setBest_metric(String best_metric) {
        this.best_metric = best_metric;
    }
 
}
