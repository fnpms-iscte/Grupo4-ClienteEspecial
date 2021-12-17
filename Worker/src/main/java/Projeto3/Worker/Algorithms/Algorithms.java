package Projeto3.Worker.Algorithms;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.Interval;

import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Room;

public abstract class Algorithms {

    public void room_available(Lecture l , List<Room> room ){
        for (Room r : room) {

            List<Interval> booking_list =  r.getLectures_times_booked();

            //Time interval of the lecture (includes date)
            Interval new_booking = new Interval(l.getStart_date(), l.getEnd_date());

            if(booking_list.isEmpty()){
                allocate(l, r, new_booking);
                break;
            }

            int count = 0;
            boolean checker = true;
            while(checker){
                if(!r.getLectures_times_booked().get(count).overlaps(new_booking)) {
                    allocate(l, r, new_booking);
                    checker = false;
                }
                count++;
            }
        }
        // if no room is available the lecture continues without a room
    }

    //Returns a list of rooms that can hold said lecture with x characteristic there
    public static List<Room> getWithCharacteristics(List<Room> rooms, String characteristics){
        final LinkedList<Room> output = new LinkedList<>();
		for (Room r :rooms){
			if (r.has_Characteristic(characteristics)){
				output.add(r);
			}
		}
		return output;
	}

	//Returns a list of rooms that can hold said lecture with x students there
	public static LinkedList<Room> getWithHigherCapacity(List<Room> rooms, int lecture_capacity){
		final LinkedList<Room> output = new LinkedList<>();
		for (Room r :rooms){
			if (r.getNormal_capacity() >= lecture_capacity){
				output.add(r);
			}
		}
		return output;
	} 
    
    //Allocates a room to the lecture and a booking to the same room
    public void allocate(Lecture l , Room r , Interval new_booking){
        l.setRoom_name(r.getName());
        l.setRoom_lotation(r.getNormal_capacity());
        l.setRoom_characteristics(r.getCharacteristics());
        r.addLecture(new_booking);
    }
}
