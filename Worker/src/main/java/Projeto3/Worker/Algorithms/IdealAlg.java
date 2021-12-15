package Projeto3.Worker.Algorithms;

import java.util.List;

import org.joda.time.Interval;

import Projeto3.Worker.Handlers.LectureHandler;
import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Room;

// Algorithm that takes in account the capacity/characteristics/availability of the rooms

public class IdealAlg {
    public void compute(List<Lecture> lectures, List<Room> rooms){
        for(Lecture l : lectures){

            //filters rooms with required characteristic
            List<Room> characteristic_filtered_rooms = LectureHandler.getWithCharacteristics(rooms, l.getRequired_room_characteristics());

            //filters rooms with required capacity
            List<Room> capacity_filtered_rooms = LectureHandler.getWithHighCapacity(characteristic_filtered_rooms, l.getCapacity());

            //Time interval of the lecture (includes date)
            Interval interval = new Interval(l.getStart_date(),l.getEnd_date());

            roomloop:
            for(Room r : capacity_filtered_rooms){

                // if the room has no bookings make one
                if(r.getLectures_times_booked().isEmpty()){
                        l.setRoom(r);
                        l.setCapacity(r.getNormal_capacity());
                        l.setReal_characteristics(r.getCharacteristicsString());
                        r.addLecture(interval);
                        break roomloop;
                }

                // checks if the interval of the lecture overlaps with those already booked in the classrom
                for(Interval i : r.getLectures_times_booked()){
                    if( i.overlaps(interval) )
                        continue roomloop;
                }

                // if the loop isnt broken its because the room has time for the lecture
                l.setRoom(r);
                l.setCapacity(r.getNormal_capacity());
                l.setReal_characteristics(r.getCharacteristicsString());
                r.addLecture(interval);
                break roomloop;
            }
        }
    }
}
