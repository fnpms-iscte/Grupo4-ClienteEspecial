package Projeto3.Worker.Algorithms;

import java.util.List;

import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Room;

// Algorithm that takes in account the capacity/characteristics/availability of the rooms
public class IdealAlg extends Algorithms {
    public void compute(List<Lecture> lectures, List<Room> rooms){
        for(Lecture l : lectures){

            //filters rooms with required characteristic
            List<Room> characteristic_filtered_rooms = super.getWithCharacteristics(rooms, l.getCaracteristicas_da_sala_pedida_para_a_aula());

            //filters rooms with required capacity
            List<Room> capacity_filtered_rooms = super.getWithHigherCapacity(characteristic_filtered_rooms, l.getInscritos_no_turno());

            //verifies if a room is available and if it is allocates the lecture to it
            super.room_available(l, capacity_filtered_rooms);
        }
    }
}
