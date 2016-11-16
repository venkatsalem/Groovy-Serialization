package venkat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializerUtils {

	public static void serialize(Object obj, String fileName) throws IOException {
		FileOutputStream fos = new FileOutputStream("object.serialized");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.close();
		fos.close();
	}

}
