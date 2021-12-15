package Projeto3.Worker.Loaders;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Projeto3.Worker.Models.Room;

public class RoomLoader {

	private HashMap<String, Boolean> characteristics;
	private JSONArray rooms_json;
	private LinkedList<Room> rooms = new LinkedList<>();
	private JSONObject roomsJson;
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
				characteristics = new HashMap<String, Boolean>();

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
					} else if (key.equals(Room.HEADERS[6])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[7])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[8])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[9])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[10])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[11])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[12])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[13])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[14])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[15])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[16])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[17])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[18])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[19])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[20])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[21])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[22])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[23])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[24])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[26])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[27])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[28])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[29])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[30])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[31])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[32])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[33])) {
						validateCharacteristic(key);
					} else if (key.equals(Room.HEADERS[34])) {
						validateCharacteristic(key);
					}

				}

				// Organizar hashmap ...

				Room r = new Room(edificio, name, capacidade_normal, capacidade_exame, n_caracteristicas,
						new LinkedList<Boolean>(characteristics.values()));
				rooms.add(r);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	public LinkedList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(LinkedList<Room> rooms) {
		this.rooms = rooms;
	}

	public void validateCharacteristic(String key) {
		try {
			if (this.roomsJson.getString(key).equals('x') || this.roomsJson.getString(key).equals('X')) {
				characteristics.put(key, true);
			} else {
				characteristics.put(key, false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
