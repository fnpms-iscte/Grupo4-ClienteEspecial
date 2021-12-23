package Projeto3.Worker.Algorithms;

import java.util.*;

import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Room;

// Algorithm that takes in account the capacity/availability of the rooms
public class SimpleAlg extends Algorithms{
    
    public void compute(List<Lecture> lectures, List<Room> rooms){
        for(Lecture l : lectures){
             //filters rooms with required capacity
             List<Room> capacity_filtered_rooms = super.getWithHigherCapacity(rooms, l.getInscritos_no_turno());

             //verifies if a room is available and if it is allocates the lecture to it
             super.room_available(l, capacity_filtered_rooms);
        }
    }
}
