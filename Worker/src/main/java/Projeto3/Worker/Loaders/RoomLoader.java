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
				// characteristics = new HashMap<String, Boolean>();
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

	public LinkedList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(LinkedList<Room> rooms) {
		this.rooms = rooms;
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
