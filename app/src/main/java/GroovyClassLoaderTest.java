import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.codehaus.groovy.bsf.GroovyEngine;

import groovy.lang.GroovyClassLoader;

public class GroovyClassLoaderTest {

	public static void main(String[] args) throws Exception {
		serialize();
		deserialize();
	}

	private static void serialize() throws Exception {
		GroovyClassLoader classLoader = new GroovyClassLoader(GroovyEngine.class.getClassLoader());
		String groovyScript = IOUtils.toString(Serialization.class.getResourceAsStream("/blah.groovy"), Charset.defaultCharset());
		Class<?> clazz = classLoader.parseClass(groovyScript);
		Object obj = clazz.newInstance();

		FileOutputStream fos = new FileOutputStream("object.serialized");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.close();
		fos.close();
		classLoader.close();
	}

	private static void deserialize() throws Exception {
		GroovyClassLoader classLoader = new GroovyClassLoader(GroovyEngine.class.getClassLoader());
		String groovyScript = IOUtils.toString(Serialization.class.getResourceAsStream("/blah.groovy"), Charset.defaultCharset());
		Class<?> clazz = classLoader.parseClass(groovyScript);

		FileInputStream fis = new FileInputStream("object.serialized");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object groovyObject = (Object) ois.readObject();
		ois.close();
		fis.close();
		classLoader.close();
	}

}
