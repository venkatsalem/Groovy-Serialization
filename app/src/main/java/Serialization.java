import java.nio.charset.Charset;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;

import venkat.GroovyProxyFactory;
import venkat.SerializerUtils;

public class Serialization {

	public static void main(String[] args) throws Exception {
		ScriptEngineManager factory = new ScriptEngineManager(Serialization.class.getClassLoader());
		ScriptEngine engine = factory.getEngineByName("Groovy");
		String groovyScript = IOUtils.toString(Serialization.class.getResourceAsStream("/blah.groovy"), Charset.defaultCharset());
		Class<?> myClass = (Class<?>) engine.eval(groovyScript);

		Object myObj = myClass.newInstance();
		myObj = GroovyProxyFactory.createProxy(myObj);

		/*-Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(myClass);
		enhancer.setInterfaces(new Class[] { GroovyProxy.class });
		enhancer.setCallback(new GroovyMethodInterceptor(myObj));
		myObj = enhancer.create();*/

		// Serialization.class.getClassLoader().loadClass("org.venkat.Blah");\

		SerializerUtils.serialize(myObj, "object.serialized");
	}

}
