package venkat;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class GroovyMethodInterceptor implements MethodInterceptor {

	private Object parent;

	public GroovyMethodInterceptor(Object parent) {
		this.parent = parent;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println(method.getName());
		if (method.getName().startsWith("$")) {
			return proxy.invoke(parent, args);
		} else if (method.getName().equals("writeReplace")) {
			System.out.println("writeReplace");
			return new DataHolder(parent);
		}
		return method.invoke(parent, args);
	}

}
