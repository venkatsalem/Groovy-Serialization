package venkat;

import net.sf.cglib.proxy.Enhancer;

public class GroovyProxyFactory {

	public static Object createProxy(Object obj) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(obj.getClass());
		enhancer.setInterfaces(new Class[] { GroovyProxy.class });
		enhancer.setCallback(new GroovyMethodInterceptor(obj));
		return enhancer.create();
	}
}
