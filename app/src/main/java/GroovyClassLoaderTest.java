import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import venkat.SerializerUtils;

public class GroovyClassLoaderTest {

	public static void main(String[] args) throws Exception {
		serialize();
		deserialize();
	}

	private static void serialize() throws Exception {
		GroovyClassLoader classLoader = new GroovyClassLoader(GroovyClassLoaderTest.class.getClassLoader());
		String groovyScript = IOUtils.toString(GroovyClassLoaderTest.class.getResourceAsStream("/blah.groovy"), Charset.defaultCharset());
		Class<?> clazz = classLoader.parseClass(groovyScript);
		Object obj = clazz.newInstance();
		SerializerUtils.serialize(obj, "object.serialized");
		classLoader.close();
	}

	private static void deserialize() throws Exception {
		GroovyClassLoader classLoader = new GroovyClassLoader(GroovyClassLoaderTest.class.getClassLoader());
		FileInputStream fis = new FileInputStream("object.serialized");
		ObjectInputStream ois = new GroovyClassLoaderObjectInputStream(fis, classLoader);
		GroovyObject groovyObject = (GroovyObject) ois.readObject();
		System.out.println(groovyObject.getProperty("x"));
		ois.close();
		fis.close();
		classLoader.close();
	}

}

class GroovyClassLoaderObjectInputStream extends ObjectInputStream {

	private GroovyClassLoader classLoader;

	protected GroovyClassLoaderObjectInputStream(InputStream is, GroovyClassLoader classLoader) throws IOException, SecurityException {
		super(is);
		this.classLoader = classLoader;
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
		if (desc.getName().equals("venkat.Blah")) {
			try {
				String groovyScript = IOUtils.toString(GroovyClassLoaderTest.class.getResourceAsStream("/blah.groovy"), Charset.defaultCharset());
				Class<?> type = classLoader.parseClass(groovyScript);
				return type;
			} catch (Exception exp) {
				throw new RuntimeException(exp);
			}
		} else {
			return classLoader.loadClass(desc.getName());
		}
	}
}
