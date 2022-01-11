package Projeto3.Worker.Metrics;

import java.util.List;

import org.joda.time.Interval;

import Projeto3.Worker.Metric;
import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Room;

public class TimetableOverlap extends Metric {

	public TimetableOverlap() {
		super("TimetableOverlap");
	}

	public Double evaluate(List<Lecture> lectList, List<Room> rooms) {
		double score = lectList.size();
		for (Lecture l : lectList) {
			Interval new_booking = new Interval(l.getInicio(), l.getFim());
			String room_name = l.getSala_da_aula();

			roomloop: for (Room r : rooms) {

				if (r.getName().equals(room_name)) {
					List<Interval> booking_list = r.getLectures_times_booked();
					booking_list.remove(new_booking);
					for (Interval interval : booking_list) {

						if (interval.overlaps(new_booking)) {
							score--;
							break roomloop;
						}
					}
				}
			}

		}

		return (score / lectList.size()) * 100;
	}
}