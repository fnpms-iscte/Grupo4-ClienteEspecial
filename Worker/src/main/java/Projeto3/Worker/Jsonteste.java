package Projeto3.Worker;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Jsonteste {

//	public JSONObject res = new JSONObject("{id : d }");

	private String a;

	public JsonObject jsonObject;

	public Jsonteste(String id) {
		this.a = "{ 'horarios' : [{ 'horario1' : [{\"Idade\":\"21222\","
				+ "\"Local\":\"Almada\",\"Nome\":\"Francisco\"},"
				+ "{\"Idade\":\"2222\",\"Local\":\"Sintra\",\"Nome\":\"Hugo\"},{\"Idade\":\"223232\","
				+ "\"Local\":\"Sintra\",\"Nome\":\"Chico\"}] , "
				+ "'metricas' : { lista : [{'furos' : 0 , 'aulas_sem_salas' : 15}] , "
				+ "'detalhe' : 'Poucos furos'} }, {'horario2' : [\r\n"
				+ "  {\r\n"
				+ "    \"Idade\": 55555,\r\n"
				+ "    \"Local\": \"Almada\",\r\n"
				+ "    \"Nome\": \"Francisco\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"Idade\": 5555555,\r\n"
				+ "    \"Local\": \"Sintra\",\r\n"
				+ "    \"Nome\": \"Hugo\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"Idade\": 55223,\r\n"
				+ "    \"Local\": \"Sintra\",\r\n"
				+ "    \"Nome\": \"Chico\"\r\n"
				+ "  }\r\n"
				+ "], 'metricas' = {lista :[{'furos' : 1110 , 'aulas_sem_salas' : 15}], detalhe : 'Muitos furos' }}] ," + " " + "'id_client': " + id + "}";

		this.jsonObject = (JsonObject) JsonParser.parseString(a);

	}

	public static void main(String[] args) {
		Jsonteste a = new Jsonteste("testesta");
		System.out.println(a.a);
	}
}
