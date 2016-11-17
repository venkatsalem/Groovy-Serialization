import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;

import groovy.lang.GroovyObject;
import venkat.GroovyProxyFactory;
import venkat.SerializerUtils;

public class ScriptEngineTest {

	public static void main(String[] args) throws Exception {
		serialize();
		deserialize();
	}

	public static void serialize() throws Exception {
		ScriptEngineManager factory = new ScriptEngineManager(ScriptEngineTest.class.getClassLoader());
		ScriptEngine engine = factory.getEngineByName("Groovy");
		String groovyScript = IOUtils.toString(ScriptEngineTest.class.getResourceAsStream("/blah.groovy"), Charset.defaultCharset());
		Class<?> myClass = (Class<?>) engine.eval(groovyScript);

		Object myObj = myClass.newInstance();
		myObj = GroovyProxyFactory.createProxy(myObj);

		SerializerUtils.serialize(myObj, "object.serialized");
	}

	public static void deserialize() throws Exception {
		FileInputStream fis = new FileInputStream("object.serialized");
		ObjectInputStream ois = new ObjectInputStream(fis);
		GroovyObject groovyObject = (GroovyObject) ois.readObject();
		System.out.println(groovyObject.getProperty("x"));
		ois.close();
		fis.close();

		SerializerUtils.serialize(groovyObject, "object.serialized");

	}
}
