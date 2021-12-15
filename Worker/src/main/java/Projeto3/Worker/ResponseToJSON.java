package Projeto3.Worker;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import Projeto3.Worker.Models.Response;

public class ResponseToJSON {

	public String ResToJSON(List<Response> output) {
		ObjectMapper Obj = new ObjectMapper();
		Obj.registerModule(new JodaModule());
		try {
			// Converting the Java object into a JSON string
			String jsonStr = "";
			for (Response response : output) {
				jsonStr = Obj.writeValueAsString(response);
				// Displaying Java object into a JSON string
//				System.out.println(jsonStr);
			}
			return jsonStr;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
