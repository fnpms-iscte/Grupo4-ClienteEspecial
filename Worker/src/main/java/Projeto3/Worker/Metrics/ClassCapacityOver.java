package Projeto3.Worker.Metrics;

import java.util.*;

import Projeto3.Worker.Metric;
import Projeto3.Worker.Models.Lecture;

public class ClassCapacityOver extends Metric{

    public ClassCapacityOver(){
        super("ClassCapacityOver");
    }

    public int evaluate(List<Lecture> LectList){
        int score = 0;
        for(Lecture lect : LectList){
            if(lect.getRoom() != null){
                if(lect.getN_students() <= lect.getRoom().getNormal_capacity()){
                    score++;
                }
            }
        }

        return score;
    }
    
    
}
