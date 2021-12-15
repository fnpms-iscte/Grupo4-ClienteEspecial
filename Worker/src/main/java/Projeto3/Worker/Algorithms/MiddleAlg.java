package Projeto3.Worker.Algorithms;

import java.util.List;

import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Room;

// Algorithm that takes in account the capacity/characteristics of the rooms

public class MiddleAlg {

    public void compute(List<Lecture> lectures, List<Room> rooms){
        for(Lecture l : lectures){
            for(Room r : rooms){

                 //filters rooms with required capacity
                boolean capacity = r.getNormal_capacity() >= l.getCapacity();

                //filters rooms with required characteristic
                boolean characteristic = r.getCharacteristicsString().contains(l.getRequired_room_characteristics());
                
                if(capacity && characteristic){
                    l.setRoom(r);
                    l.setCapacity(r.getNormal_capacity());
                    l.setReal_characteristics(r.getCharacteristicsString());
                    break;
                }else 
                    continue;
            }
        }
    }
}
