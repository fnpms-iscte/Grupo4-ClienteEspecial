package Projeto3.Worker.Loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

import com.opencsv.CSVReader;

import org.apache.commons.io.input.BOMInputStream;
import org.joda.time.DateTime;
import Projeto3.Worker.Models.Lecture;

public class LectureLoader {

	public static final LinkedList<Lecture> readLecturePath(final File file) {

		LinkedList<Lecture> lectures = new LinkedList<>();

		try {
			// Creates the reader
			final InputStreamReader reader = new InputStreamReader(new BOMInputStream(new FileInputStream(file)),
					StandardCharsets.UTF_8);
			final CSVReader csvReader = new CSVReader(reader);

			// Saves the headers names for the correct allocation of values to variables
			String[] headers = csvReader.readNext();
			int[] order_headers = getOrder(headers);

			// Will contain a row of the csv
			String[] tokens;

			while ((tokens = csvReader.readNext()) != null) {
				lectures.add(creationlectures(tokens, order_headers));
			}
			csvReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lectures;
	}

	private static Lecture creationlectures(String[] tokens, int[] order_headers) {

		// all variables in String form in their correct order

		for (String i : tokens) {
			System.out.println(i);
		}

		final String courseS = allocate(order_headers[0], tokens);
		final String name = allocate(order_headers[1], tokens);
		final String shift = allocate(order_headers[2], tokens);
		final String class_name = allocate(order_headers[3], tokens);
		final String n_studentsS = allocate(order_headers[4], tokens);
		final String Free_SpotsS = allocate(order_headers[5], tokens);
		final String Capacity_OverflowS = allocate(order_headers[6], tokens);
		final String week_day = allocate(order_headers[7], tokens);
		final String startS = allocate(order_headers[8], tokens);
		final String endS = allocate(order_headers[9], tokens);
		final String dateS = allocate(order_headers[10], tokens);
		final String required_room_characteristics = allocate(order_headers[11], tokens);

		// Converting to their correct form
		final LinkedList<String> course = new LinkedList<>(Arrays.asList(courseS.split(", ")));
		final int n_students = n_studentsS.isEmpty() ? -1 : Integer.parseInt(n_studentsS);

		boolean Free_Spots = true;
		if (Free_SpotsS.equals("FALSO")) {
			Free_Spots = false;
		}

		boolean Capacity_Overflow = true;
		if (Capacity_OverflowS.equals("FALSO")) {
			Capacity_Overflow = false;
		}

		// Date and time creation of schedules and missing values
		if (!dateS.isEmpty() && !startS.isEmpty() && !endS.isEmpty()) {
			String[] date = dateS.split("/");
			String[] start = startS.split(":");
			String[] end = endS.split(":");
			DateTime start_date = new DateTime(Integer.parseInt(date[2]), Integer.parseInt(date[1]),
					Integer.parseInt(date[0]), Integer.parseInt(start[0]), Integer.parseInt(start[1]),
					Integer.parseInt(start[2]));
			DateTime end_date = new DateTime(Integer.parseInt(date[2]), Integer.parseInt(date[1]),
					Integer.parseInt(date[0]), Integer.parseInt(end[0]), Integer.parseInt(end[1]),
					Integer.parseInt(end[2]));
			Lecture lecture = new Lecture(course, name, shift, class_name, n_students, Free_Spots, Capacity_Overflow,
					week_day, start_date, end_date, required_room_characteristics);
			return lecture;
		} else if (dateS.isEmpty() && !startS.isEmpty() && !endS.isEmpty()) {
			String[] start = startS.split(":");
			String[] end = endS.split(":");
			DateTime start_date = new DateTime(0, 12, 31, Integer.parseInt(start[0]), Integer.parseInt(start[1]),
					Integer.parseInt(start[2]));
			DateTime end_date = new DateTime(0, 12, 31, Integer.parseInt(end[0]), Integer.parseInt(end[1]),
					Integer.parseInt(end[2]));
			Lecture lecture = new Lecture(course, name, shift, class_name, n_students, Free_Spots, Capacity_Overflow,
					week_day, start_date, end_date, required_room_characteristics);
			return lecture;
		} else {
			Lecture lecture = new Lecture(course, name, shift, class_name, n_students, Free_Spots, Capacity_Overflow,
					week_day, null, null, required_room_characteristics);
			return lecture;
		}
	}

	// Due to json files coming with the collums of the csv in different order
	private static int[] getOrder(String[] headers) {
		String[] order = Lecture.HEADERS;
		int[] indexes = new int[order.length];
		int count1 = 0;
		for (String label : headers) {
			System.out.println("\n\nLabel :" + label + " -------------------");
			int count2 = 0;
			for (String label2 : order) {
				System.out.println("Label2 :" + label2 + " == " + label);
				System.out.println(label2.equals(label));

				if (label2.equals(label) || label.contains("Curso")) {
					if (label.contains("Curso")) {
						label = "Curso";
					}
					indexes[count1] = count2;
					break;
				}
				count2++;
			}
			count1++;
		}
		for (int i : indexes) {
			System.out.println(i);
		}

		return indexes;
	}

	private static String allocate(int position, String[] tokens) {
		String s = "";
		if (position < tokens.length)
			s = tokens[position];
		return s;
	}
}
