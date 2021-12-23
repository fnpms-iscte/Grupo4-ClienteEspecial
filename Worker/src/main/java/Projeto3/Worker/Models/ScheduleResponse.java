package Projeto3.Worker.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ScheduleResponse {
    //@JsonProperty("Horarios")
    public List<Response> Horarios;
    //@JsonProperty("id")
    public String id;

    public ScheduleResponse(String id, List<Response> Horarios) {
        this.id = id;
        this.Horarios = Horarios;
    }

    public List<Response> getHorarios() {
        return Horarios;
    }
    public String getId() {
        return id;
    }

    public void setHorarios(List<Response> Horarios) {
        this.Horarios = Horarios;
    }

    public void setId(String id) {
        this.id = id;
    }

}
