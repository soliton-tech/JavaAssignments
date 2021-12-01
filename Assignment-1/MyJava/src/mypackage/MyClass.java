package mypackage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyClass {

	public static void main(String[] args) throws JsonProcessingException {
		MyAnimal a = MyAnimal.getInstance();		
		ObjectMapper obj = new ObjectMapper();
		System.out.println(obj.writeValueAsString(a));
	}

}
