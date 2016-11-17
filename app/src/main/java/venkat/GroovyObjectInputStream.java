package venkat;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.nio.charset.Charset;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;

public class GroovyObjectInputStream extends ObjectInputStream {

	protected GroovyObjectInputStream(InputStream is) throws IOException, SecurityException {
		super(is);
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
		if (desc.getName().equals("venkat.Blah")) {
			try {
				ScriptEngineManager factory = new ScriptEngineManager(GroovyObjectInputStream.class.getClassLoader());
				ScriptEngine engine = factory.getEngineByName("Groovy");
				String groovyScript = IOUtils.toString(GroovyObjectInputStream.class.getResourceAsStream("/blah.groovy"), Charset.defaultCharset());
				Class<?> type = (Class<?>) engine.eval(groovyScript);
				return type;
			} catch (Exception exp) {
				throw new RuntimeException(exp);
			}
		} else {
			return Thread.currentThread().getContextClassLoader().loadClass(desc.getName());
		}
	}
}
