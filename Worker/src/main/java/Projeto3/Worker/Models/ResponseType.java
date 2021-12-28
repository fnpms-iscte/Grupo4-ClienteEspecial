package Projeto3.Worker.Models;

import java.util.ArrayList;

public class ResponseType {

    private final int id;
    private final String sala_da_aula;
    private final int lotacao;
    private final ArrayList<String> carateristicas_reais_sala;

    
    public ResponseType(Lecture lecture) {
        this.id = lecture.getId();
        this.sala_da_aula = lecture.getSala_da_aula();
        this.lotacao = lecture.getLotacao();
        this.carateristicas_reais_sala = lecture.getCaracteristicas_reais_da_sala();
    }


    public int getId() {
        return id;
    }


    public String getSala_da_aula() {
        return sala_da_aula;
    }


    public int getLotacao() {
        return lotacao;
    }


    public ArrayList<String> getCarateristicas_reais_sala() {
        return carateristicas_reais_sala;
    }
    
    
}
