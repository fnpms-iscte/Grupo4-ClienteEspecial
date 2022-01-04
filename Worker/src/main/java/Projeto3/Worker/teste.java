package Projeto3.Worker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class teste {

    public void csvToJson() throws FileNotFoundException, IOException {
        try (InputStream in = new FileInputStream(new File("C:/Users/fnpm/Desktop/ADS/Timetable alterado.csv"));) {
            System.out.println("here");
            CSV csv = new CSV(true, ';', in);
            List<String> fieldNames = null;
            if (csv.hasNext())
                fieldNames = new ArrayList<>(csv.next());
            List<Map<String, String>> list = new ArrayList<>();
            while (csv.hasNext()) {
                List<String> x = csv.next();
                Map<String, String> obj = new LinkedHashMap<>();
                for (int i = 0; i < fieldNames.size(); i++) {
                    obj.put(fieldNames.get(i), x.get(i));
                }
                list.add(obj);
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(System.out, list);
        }
    }

    public static void main(String[] args) {
        teste t = new teste();
    }

}
