package Projeto3.Worker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.Room;

public class JsonHandler {
    
    private JSONArray lecture_file;
    private JSONArray room_file;
    private LinkedList<Lecture> lectures;
    private LinkedList<Room> rooms;

    public JsonHandler(JSONArray files){
        try {
            this.room_file = files.getJSONArray(0);
            this.lecture_file = files.getJSONArray(1);
            this.rooms = new LinkedList<>();
            this.lectures = new LinkedList<>();
            readLectureFile();
            readRoomFile();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
    }


    public LinkedList<Lecture> getLectures(){
        return lectures;
    }

    
    public LinkedList<Room> getRooms(){
        return rooms;
    }

    private void readLectureFile() {
        String courseS = "";
        String nameS= "";
        String shiftS= "";
        String class_nameS= "";
        String n_studentsS= "";
        String free_SpotsS= "";
        String Capacity_OverflowS= "";
        String week_dayS= "";
        String startS= "";
        String endS= "";
        String dateS= "";
        String required_room_characteristics= "";

        for (int i = 0; i < this.lecture_file.length(); i++) {
            try {
                JSONObject lectureJson = this.lecture_file.getJSONObject(i);
                Iterator<?> keys = lectureJson.keys();
                while (keys.hasNext()) {

                    String key = (String) keys.next();
                    if (key.contains("Curso")) {
                        courseS = lectureJson.getString(key);
                    } else if (key.equals("Unidade de execução")) {
                        nameS = lectureJson.getString(key);
                    } else if (key.equals("Turno")) {
                        shiftS = lectureJson.getString(key);
                    } else if (key.equals("Turma")) {
                        class_nameS = lectureJson.getString(key);
                    } else if (key.equals("Inscritos no turno (no 1º semestre é baseado em estimativas)")) {
                        n_studentsS = lectureJson.getString(key);
                    } else if (key.equals("Turnos com capacidade superior à capacidade das características das salas")) {
                        free_SpotsS = lectureJson.getString(key);
                    } else if (key.equals("Turno com inscrições superiores à capacidade das salas")) {
                        Capacity_OverflowS = lectureJson.getString(key);
                    } else if (key.equals("Dia da Semana")) {
                        week_dayS = lectureJson.getString(key);
                    } else if (key.equals("Início")) {
                        startS = lectureJson.getString(key);
                    } else if (key.equals("Fim")) {
                        endS = lectureJson.getString(key);
                    } else if (key.equals("Dia")) {
                        dateS = lectureJson.getString(key);
                    } else if (key.equals("Características da sala pedida para a aula")) {
                        required_room_characteristics = lectureJson.getString(key);
                    }
                }

                // Converting to their correct form and verification
                LinkedList<String> course = new LinkedList<>(Arrays.asList(courseS.split(", ")));
                String name = checkEmptyField(nameS);
                if(name.equals("Not Given"))
                    continue;
                String shift = checkEmptyField(shiftS);
                String class_name = checkEmptyField(class_nameS);
                int n_students = n_studentsS.isEmpty() ? -1 : Integer.parseInt(n_studentsS);
                boolean Turnos_com_capacidade_superior = checkBools(free_SpotsS);
                boolean Turno_com_inscrições_superiores = checkBools(Capacity_OverflowS);
                String week_day = checkEmptyField(week_dayS);
                DateTime start_date = convertDateTime(startS, dateS);
                DateTime end_date = convertDateTime(endS, dateS);

                // Lecture Object Creation
                Lecture lecture = new Lecture(i+1, course, name, shift, class_name, n_students, Turnos_com_capacidade_superior,
                Turno_com_inscrições_superiores, week_day, start_date, end_date, required_room_characteristics);
                lectures.add(lecture);	
           } catch (JSONException e) {
                e.printStackTrace();
            }
		}
    }

    private DateTime convertDateTime(String timeS, String dateS){
		if(!timeS.isEmpty()){
			String[] time = timeS.split(":");
			if(dateS.isEmpty()){
				DateTime datetime = new DateTime(0, 12, 31, Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
				return datetime;
			}else{
				String[] date = dateS.split("/");
				DateTime datetime = new DateTime(Integer.parseInt(date[2]), Integer.parseInt(date[1]),Integer.parseInt(date[0]),
						Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
				return datetime;
			}
		}else{
			return null;
		}
	}

	private boolean checkBools(String s){
		boolean b = true;
			if (s.equals("FALSO")) {
				b = false;
			}
		return b;
	}

	private String checkEmptyField(String s){
		String output;
		if(s.isEmpty()){
			output = "Not Given";
		}else
			output = s;
		return output;
	}

    private void readRoomFile(){
        String edificio = "";
	    String name = "";
	    int capacidade_normal =0;
	    int n_caracteristicas=0;
	    int capacidade_exame=0;
        for (int i = 0; i < room_file.length(); i++) {

			try {
				JSONObject roomsJson = room_file.getJSONObject(i);

				Iterator<?> keys = roomsJson.keys();
				ArrayList<String> caract = new ArrayList<String>();
				while (keys.hasNext()) {

					String key = (String) keys.next();

					if (key.contains("Edifício")) {
						edificio = roomsJson.getString(key);
					} else if (key.equals("Nome sala")) {
						name = roomsJson.getString(key);
					} else if (key.equals("Capacidade Normal")) {
						capacidade_normal = Integer.parseInt(roomsJson.getString(key));
					} else if (key.equals("Nº características")) {
						n_caracteristicas = Integer.parseInt(roomsJson.getString(key));
					} else if (key.equals("Capacidade Exame")) {
						capacidade_exame = Integer.parseInt(roomsJson.getString(key));
					} else if (key.equals(Room.HEADERS[5])) {
						validateCharacteristic(key);
					} else if (acceptCharacteristic(key) && validateCharacteristic(roomsJson.getString(key))) {
						caract.add(key);
					}
				}

				Room r = new Room(edificio, name, capacidade_normal, capacidade_exame, n_caracteristicas, caract);
				rooms.add(r);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

    }

    private boolean acceptCharacteristic(String charac) {
		boolean marker = true;
		int aux = 0;
		while (marker && aux < Room.CHARACTERISTICS_LIST.length) {
			String c = Room.CHARACTERISTICS_LIST[aux];
			if (c.equals(charac))
				marker = false;
			aux++;
		}
		return !marker;
	}

	private boolean validateCharacteristic(String bool) {
		boolean marker = false;
		if (bool.isEmpty()) {
			marker = false;
		} else if (bool.equals("X")) {
			marker = true;
		}
		return marker;
	}

}
