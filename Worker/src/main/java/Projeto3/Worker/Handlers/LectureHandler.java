package Projeto3.Worker.Handlers;

import java.util.LinkedList;
import java.util.List;

import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Room;

public class LectureHandler {

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
	public static LinkedList<Room> getWithHighCapacity(List<Room> rooms, int lecture_capacity){
		final LinkedList<Room> output = new LinkedList<>();
		for (Room r :rooms){
			if (r.getNormal_capacity() >= lecture_capacity){
				output.add(r);
			}
		}
		return output;
	} 

	public static boolean verifyFeatures(Room r, Lecture l){
		//Verifies if its possible for all students to come to class
		boolean capacities = r.getNormal_capacity() < l.getN_students();
		//Verifies if the room has the characteristic that the lecture requires
		boolean characteristic = r.getCharacteristicsString().contains(l.getRequired_room_characteristics());
		return capacities && characteristic;
	}
}
