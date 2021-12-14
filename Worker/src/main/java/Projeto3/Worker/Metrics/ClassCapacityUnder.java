package Projeto3.Worker.Metrics;

import java.util.*;

import Projeto3.Worker.Metric;
import Projeto3.Worker.Models.Lecture;

public class ClassCapacityUnder extends Metric {

    public ClassCapacityUnder(){
        super("ClassCapacityUnder");
    }

    public int evaluate(List<Lecture> LectList){
        int score = 0;
        for(Lecture lect : LectList){
            if(lect.getRoom() != null){
                if(lect.getN_students() >= (lect.getRoom().getNormal_capacity() / 2)){
                    score++;
                }
            }
        }

        return score;
    }
    
    
}
