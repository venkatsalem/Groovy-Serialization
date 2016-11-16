package venkat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class DataHolder implements Serializable {

	private static final long serialVersionUID = 1L;

	private byte[] data;

	public DataHolder(Object serItem) {
		try {
			// create a byte array output stream
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(serItem);
			data = baos.toByteArray();
			baos.close();
			oos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object readResolve() throws ObjectStreamException {
		Object returnValue = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ObjectInputStream ois = new GroovyObjectInputStream(bais);
			returnValue = (Object) ois.readObject();
			bais.close();
			ois.close();
			// returnValue = <have the logic to wrap the object in proxy here>
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return GroovyProxyFactory.createProxy(returnValue);
	}
}