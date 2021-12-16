package Projeto3.Worker;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import Projeto3.Worker.Models.Response;

public class ResponseToJSON {

	public String ResToJSON(List<Response> output) {
		System.out.println("testetet");
		ObjectMapper Obj = new ObjectMapper();
		System.out.println(Obj.toString() + " treste");
		Obj.registerModule(new JodaModule());
		try {
			// Converting the Java object into a JSON string
			String jsonStr = "";
			for (Response response : output) {
				jsonStr = Obj.writeValueAsString(response);
				// Displaying Java object into a JSON string
				System.out.println(jsonStr);
			}
			return jsonStr;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static void main(String[] args) {
		ObjectMapper Obj = new ObjectMapper();
	}

}
