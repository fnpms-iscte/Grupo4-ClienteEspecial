package Projeto3.Worker.Loaders;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Projeto3.Worker.Models.Room;

public class RoomLoader {
	
	private JSONArray rooms_json;
	private JSONObject roomsJson;
	private LinkedList<Room> rooms = new LinkedList<>();
	private String edificio;
	private String name;
	private int capacidade_normal;
	private int n_caracteristicas;
	private int capacidade_exame;

	public RoomLoader(JSONArray rooms_json) throws JSONException {
		this.rooms_json = rooms_json;
		setupRooms();
	}

	public void setupRooms() {

		for (int i = 0; i < rooms_json.length(); i++) {

			try {
				roomsJson = rooms_json.getJSONObject(i);

				Iterator<String> keys = roomsJson.keys();
				//characteristics = new HashMap<String, Boolean>();
				ArrayList<String> caract = new ArrayList<String>();
				while (keys.hasNext()) {

					String key = keys.next();

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
					}else if (acceptCharacteristic(key) && validateCharacteristic(roomsJson.getString(key))) {
						caract.add(key);
					}
				}

				Room r = new Room(edificio, name, capacidade_normal, capacidade_exame, n_caracteristicas,caract);
				rooms.add(r);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
		
		/*
		String[] json = new String[rooms_json.length()];
		String[] data = new String[rooms_json.length()];
		
		for(int i=0; i<json.length; i++) {
			json[i] = rooms_json.optString(i);
			data[i] = json[i].substring(1, json[i].length() - 1);
		}
		
		for (String string : data) {
			String name = null;
			String building= null;
			String normal_capacity= null;
			String exam_capacity= null;
			String n_caract= null;
			
			ArrayList<String> caract = new ArrayList<String>();
			
			String[] vals = string.split(",", 35);
			
			for (String string2 : vals) {
				
				String[] collum = string2.split(":");
				
				if(collum[0].substring(2, collum[0].length() - 1).equals(Room.HEADERS[0])) {
					building = collum[1].substring(1, collum[1].length() - 1);
				}else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[1])) {
					name = collum[1].substring(1, collum[1].length() - 1);
				}else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[2])) {
					normal_capacity = collum[1].substring(1, collum[1].length() - 1);
				}else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[3])) {
					exam_capacity = collum[1].substring(1, collum[1].length() - 1);
				}else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[4])) {
					n_caract = collum[1].substring(1, collum[1].length() - 1);
				}else if (acceptCharacteristic(collum[0].substring(1, collum[0].length() - 1)) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
					caract.add(collum[0].substring(1, collum[0].length() - 1));
				}
			}
			Room r = new Room(building, name, Integer.parseInt(normal_capacity), Integer.parseInt(exam_capacity), Integer.parseInt(n_caract),caract);
			rooms.add(r);
		}*/


	public LinkedList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(LinkedList<Room> rooms) {
		this.rooms = rooms;
	}
	
	private boolean acceptCharacteristic(String charac) {
		boolean marker = true;
		int aux = 0;
		while(marker && aux < Room.CHARACTERISTICS_LIST.length) {
			String c = Room.CHARACTERISTICS_LIST[aux];
			if(c.equals(charac))
				marker = false;
			aux++;
		}
		return !marker;
	}
	
	private boolean validateCharacteristic(String bool) {
		boolean marker = false;
		if(bool.isEmpty()) {
			marker = false;			
		}else if(bool.equals("X")) {
			marker = true;
		}
		return marker;
	}
	
	/*
	}else if  (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[5]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {	
			caract.add(Room.HEADERS[5]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[6]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[6]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[7]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[7]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[8]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[8]);				
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[9]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[9]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[10]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[10]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[11]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[11]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[12]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[12]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[13]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[13]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[14]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[14]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[15]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[15]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[16]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[16]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[17]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[17]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[18]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[18]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[19]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[19]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[20]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[20]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[21]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[21]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[22]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[22]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[23]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[23]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[24]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[24]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[25]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[25]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[26]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[26]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[27]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[27]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[28]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[28]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[29]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[29]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[30]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[30]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[31]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[31]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[32]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[32]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[33]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[33]);
	} else if (collum[0].substring(1, collum[0].length() - 1).equals(Room.HEADERS[34]) && validateCharacteristic(collum[1].substring(1, collum[1].length() - 1))) {
			caract.add(Room.HEADERS[34]);
	}*/
	
}
