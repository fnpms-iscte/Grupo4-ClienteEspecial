package Projeto3.Worker;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Jsonteste {

//	public JSONObject res = new JSONObject("{id : d }");

	private String a;

	public JsonObject jsonObject;

	public Jsonteste(String id) {
		this.a = "{ 'horarios' : { 'horario1' : [{\"Idade\":\"21222\","
				+ "\"Local\":\"Almada\",\"Nome\":\"Francisco\"},"
				+ "{\"Idade\":\"2222\",\"Local\":\"Sintra\",\"Nome\":\"Hugo\"},{\"Idade\":\"223232\","
				+ "\"Local\":\"Sintra\",\"Nome\":\"Chico\"}] , "
				+ "'metricas' : { lista : [{'furos' : 0 , 'aulas_sem_salas' : 15},] , "
				+ "'detalhe' : 'Poucos furos'} } ," + " " + "'id_client': " + id + "}";

		this.jsonObject = (JsonObject) JsonParser.parseString(a);
	}

}
