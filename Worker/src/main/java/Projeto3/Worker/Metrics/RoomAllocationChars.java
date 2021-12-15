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
            String caracter = lect.getRequired_room_characteristics();
            if(!caracter.isEmpty()){
                if(lect.getRoom().getCharacteristicsString().contains(caracter))
                    score++; 
            }
            count++;
        }
        return (score/count) * 100;
    }
}
