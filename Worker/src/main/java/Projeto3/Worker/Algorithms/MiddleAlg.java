package Projeto3.Worker.Algorithms;

import java.util.List;

import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Room;

// Algorithm that takes in account the characteristics/availability of the rooms
public class MiddleAlg extends Algorithms {

    public void compute(List<Lecture> lectures, List<Room> rooms){
        for(Lecture l : lectures){
            //filters rooms with required characteristic
            List<Room> characteristic_filtered_rooms = super.getWithCharacteristics(rooms, l.getRequired_room_characteristics());
             
            //verifies if a room is available and if it is allocates the lecture to it
            super.room_available(l, characteristic_filtered_rooms);
        }
    }
}
