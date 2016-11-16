import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;

import groovy.lang.GroovyObject;

public class Serialization {

	public static void main(String[] args) throws Exception {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("Groovy");
		String groovyScript = IOUtils.toString(Serialization.class.getResourceAsStream("/blah.groovy"), Charset.defaultCharset());
		Class<?> myClass = (Class<?>) engine.eval(groovyScript);
		GroovyObject myObj = (GroovyObject) myClass.newInstance();

		FileOutputStream fos = new FileOutputStream("object.serialized");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(myObj);
		oos.close();
		fos.close();
	}

}
