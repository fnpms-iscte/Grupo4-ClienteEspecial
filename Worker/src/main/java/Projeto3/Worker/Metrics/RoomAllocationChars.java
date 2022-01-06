package Projeto3.Worker.Metrics;

import java.util.*;

import Projeto3.Worker.Metric;
import Projeto3.Worker.Models.Lecture;

public class RoomAllocationChars extends Metric{

    public RoomAllocationChars(){
        super("RoomAllocationChars");
    }
    
    public Double evaluate(List<Lecture> LectList){
        double score = 0;
        double count = 0;
        for(Lecture lect : LectList){
            String caracter = lect.getCaracteristicas_da_sala_pedida_para_a_aula();
            if(!caracter.isEmpty() || caracter != null){
                if(lect.getCaracteristicas_reais_da_sala() != null && lect.getCaracteristicas_reais_da_sala().contains(caracter) )
                    score++; 
            }
            count++;
        }
        return (score/count) * 100;
    }
}
