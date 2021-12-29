package Projeto3.Worker;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

import Projeto3.Worker.Models.ScheduleResponse;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ResponseToJSON {
	private String concat;

	public String ResToJSON(ScheduleResponse output) {
		ObjectMapper Obj = new ObjectMapper();
		Obj.setVisibility(Obj.getSerializationConfig().getDefaultVisibilityChecker()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		String jsonStr = "{";
		try {
			// Converting the Java object into a JSON string
			concat = jsonStr.concat(Obj.writeValueAsString(output) + "}");
			return concat;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}
