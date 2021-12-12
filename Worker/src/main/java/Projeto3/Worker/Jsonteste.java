package Projeto3.Worker;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Jsonteste {

//	public JSONObject res = new JSONObject("{id : d }");

	private static String a = "{ 'horarios' : { 'horario1' : 'json horar' , " + "'metricas' : { lista : { 'nome' : 'valores' } , "
			+ "'detalhe' : 'melhor metrica'} } , " + "'id_client': 'JnQOMDyv6gmYFMasAAAL'   }";
	
	public static  JsonObject jsonObject = (JsonObject) JsonParser.parseString(a);
	
	public static void main(String[] args) {
		System.out.println(jsonObject);
	}
}
