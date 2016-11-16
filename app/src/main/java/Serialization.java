import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;

import net.sf.cglib.proxy.Enhancer;
import venkat.GroovyMethodInterceptor;
import venkat.GroovyProxy;

public class Serialization {

	public static void main(String[] args) throws Exception {
		ScriptEngineManager factory = new ScriptEngineManager(Serialization.class.getClassLoader());
		ScriptEngine engine = factory.getEngineByName("Groovy");
		String groovyScript = IOUtils.toString(Serialization.class.getResourceAsStream("/blah.groovy"), Charset.defaultCharset());
		Class<?> myClass = (Class<?>) engine.eval(groovyScript);

		Object myObj = myClass.newInstance();

		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(myClass);
		enhancer.setInterfaces(new Class[] { GroovyProxy.class });
		enhancer.setCallback(new GroovyMethodInterceptor(myObj));
		myObj = enhancer.create();

		// Serialization.class.getClassLoader().loadClass("org.venkat.Blah");

		FileOutputStream fos = new FileOutputStream("object.serialized");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(myObj);
		oos.close();
		fos.close();
	}

}
