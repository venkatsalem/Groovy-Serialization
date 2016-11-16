import java.io.FileInputStream;
import java.io.ObjectInputStream;

import groovy.lang.GroovyObject;
import venkat.SerializerUtils;

public class Deserialization {

	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream("object.serialized");
		ObjectInputStream ois = new ObjectInputStream(fis);
		GroovyObject groovyObject = (GroovyObject) ois.readObject();
		System.out.println(groovyObject.getProperty("x"));
		ois.close();
		fis.close();
		
		SerializerUtils.serialize(groovyObject, "object.serialized");

	}

}
