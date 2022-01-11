package Projeto3.Worker.Models;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;
import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import Projeto3.Worker.Metrics.ClassCapacityOver;
import Projeto3.Worker.Metrics.ClassCapacityUnder;
import Projeto3.Worker.Metrics.RoomAllocationChars;
import Projeto3.Worker.Metrics.TimetableOverlap;

@SuppressWarnings("serial")
public class ProblemConfiguration extends AbstractIntegerProblem {

	private List<Room> rooms;
	private List<Lecture> lecturesNoRooms;

	public ProblemConfiguration(List<Lecture> lectures, List<Room> rooms) {
		this.rooms = rooms;
		this.lecturesNoRooms = getLecturesNoRoom(lectures);
		setNumberOfVariables(lecturesNoRooms.size());
		setNumberOfObjectives(4); // 3 metrics + 1 timetable overlap

		setName("ProblemConfiguration");

		List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
		List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

		for (int i = 0; i < getNumberOfVariables(); i++) {
			lowerLimit.add(0);
			upperLimit.add(rooms.size() - 1);
		}
		setVariableBounds(lowerLimit, upperLimit);
	}

	@Override
	public IntegerSolution evaluate(IntegerSolution solution) {
		TimetableOverlap metric0 = new TimetableOverlap();
		ClassCapacityOver metric1 = new ClassCapacityOver();
		ClassCapacityUnder metric2 = new ClassCapacityUnder();
		RoomAllocationChars metric3 = new RoomAllocationChars();

		int[] x = new int[getNumberOfVariables()];
		double[] metrics = new double[solution.objectives().length];

		for (int i = 0; i < solution.variables().size(); i++) {
			x[i] = solution.variables().get(i);
		}
		setRooms(x);

		metrics[0] = metric0.evaluate(lecturesNoRooms, rooms);

		metrics[1] = metric1.evaluate(lecturesNoRooms);

		metrics[2] = metric2.evaluate(lecturesNoRooms);

		metrics[3] = metric3.evaluate(lecturesNoRooms);
		removeIntervals(x);

		solution.objectives()[0] = metrics[0];
		solution.objectives()[1] = 100 - metrics[1];
		solution.objectives()[2] = 100 - metrics[2];
		solution.objectives()[3] = 100 - metrics[3];

		return solution;
	}

	private List<Lecture> getLecturesNoRoom(List<Lecture> lectures) {
		List<Lecture> auxLectures = new ArrayList<Lecture>();
		for (Lecture l : lectures) {
			if (!l.getCaracteristicas_da_sala_pedida_para_a_aula().equals("Não necessita de sala") && !l.hasRoom()
					&& !l.getDia_da_Semana().equals("Not Given")) {
				auxLectures.add(l);
			}
		}

		return auxLectures;
	}

	private void setRooms(int[] x) {
		for (int i = 0; i < x.length; i++) {
			lecturesNoRooms.get(i).setRoom(rooms.get(x[i]));
			Interval interval = new Interval(lecturesNoRooms.get(i).getInicio(), lecturesNoRooms.get(i).getFim());
			rooms.get(x[i]).addLecture(interval);
		}
	}

	private void removeIntervals(int[] x) {
		for (int i = 0; i < x.length; i++) {
			rooms.get(x[i])
					.removeLecture(new Interval(lecturesNoRooms.get(i).getInicio(), lecturesNoRooms.get(i).getFim()));
		}

	}

}