package Projeto3.Worker.Algorithms;

import java.util.List;

import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Room;

public class PerfectAlg extends Algorithms {
// Algorithm that takes in account the capacity/characteristics/availability of the rooms
    public void compute(List<Lecture> lectures, List<Room> rooms){
        for(Lecture l : lectures){

            //filters rooms with required characteristic
            List<Room> characteristic_filtered_rooms = super.getWithCharacteristics(rooms, l.getCaracteristicas_da_sala_pedida_para_a_aula());

            //filters rooms with required capacity
            List<Room> capacity_filtered_rooms = super.getWithHigherCapacity(characteristic_filtered_rooms, l.getInscritos_no_turno());

            //filters rooms with reasonable capacity
            List<Room> reasonable_capacity_filtered_rooms = super.getWithReasonableCapacity(capacity_filtered_rooms, l.getInscritos_no_turno());

            //verifies if a room is available and if it is allocates the lecture to it
            super.room_available(l, reasonable_capacity_filtered_rooms);
        }
    }
}
