package Projeto3.Worker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import Projeto3.Worker.Models.Response;
import Projeto3.Worker.Models.ScheduleResponse;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ResponseToJSON {
	
// 	public List<String> ResToJSON(List<Response> output, String id) {
// 		ObjectMapper Obj = new ObjectMapper();
// 		Obj.registerModule(new JodaModule());
// 		List<String> jsonList = new ArrayList<String>();
// 		try {
// 			// Converting the Java object into a JSON string
// 			String jsonStr = "{";
// 			for (Response response : output) {
// 				jsonStr = Obj.writeValueAsString(response);
// 				jsonList.add(jsonStr);
// 				// Displaying Java object into a JSON string
// //				System.out.println(jsonStr);
// 			}
// 			jsonList.add("\"id_client\":\"" + id + "\"}");
// 			return jsonList;
// 		} catch (IOException e) {
// 			e.printStackTrace();
// 		}
// 		return null;

// 	}
	private String concat;

	public String ResToJSON(ScheduleResponse output) {
		ObjectMapper Obj = new ObjectMapper();
		Obj.setVisibility(Obj.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		Obj.registerModule(new JodaModule());
		String jsonStr = "{";
		try {
			// Converting the Java object into a JSON string
				concat = jsonStr.concat(Obj.writeValueAsString(output) + "}");
				// Displaying Java object into a JSON string
	//				System.out.println(jsonStr);
			return concat;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}
