package Projeto3.Worker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class teste {
 public String a;
 
 	public teste() throws FileNotFoundException, IOException {
 		csvToJson();
	}
 
    public void csvToJson() throws FileNotFoundException, IOException {
        try {
        	InputStream in = new FileInputStream(new File("C:/Users/fnpm/Desktop/ADS/Timetable alterado1.csv"));
            System.out.println("1");
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
            a  = mapper.writeValueAsString( list);
            //System.out.println(a);
            PrintWriter out = new PrintWriter("filename.txt");
            out.println(a);
            //System.out.println(a);
            
        }catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    

    public static void main(String[] args) throws FileNotFoundException, IOException {
        teste t = new teste();
        try {
			t.csvToJson();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
