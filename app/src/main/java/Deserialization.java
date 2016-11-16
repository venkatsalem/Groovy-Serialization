import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;

import groovy.lang.GroovyObject;

public class Deserialization {

	public static void main(String[] args) throws Exception {
		ScriptEngineManager factory = new ScriptEngineManager(Serialization.class.getClassLoader());
		ScriptEngine engine = factory.getEngineByName("Groovy");
		String groovyScript = IOUtils.toString(Serialization.class.getResourceAsStream("/blah.groovy"), Charset.defaultCharset());
		Class<?> myClass = (Class<?>) engine.eval(groovyScript);
		
		//Class.forName("org.venkat.Blah");

		FileInputStream fis = new FileInputStream("object.serialized");
		ObjectInputStream ois = new ObjectInputStream(fis);
		GroovyObject groovyObject = (GroovyObject) ois.readObject();
		ois.close();
		fis.close();
	}

}
