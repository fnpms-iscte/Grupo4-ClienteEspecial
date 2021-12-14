package Projeto3.Worker.Metrics;

import java.util.*;

import Projeto3.Worker.Metric;
import Projeto3.Worker.Models.Lecture;

public class RoomAllocationChars extends Metric{

    public RoomAllocationChars(){
        super("RoomAllocationChars");
    }
    
    public int evaluate(List<Lecture> LectList){
        int score = 0;
        for(Lecture lect : LectList){
            int count = 0;
            if(lect.getReal_characteristics() != null){
                for(String caracter : lect.getReal_characteristics()){
                    if(lect.getRoom().has_Characteristic(caracter)){
                        count++;
                    }
                }
            }
            if (count == lect.getRequired_room_characteristics().length()){
                score++;
            }
        
        }

        return score;
    }
}
