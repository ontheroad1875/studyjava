package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyClient implements InvocationHandler {
	private Object obj;
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		return method.invoke(obj, args);
	}

	public Object getJdkProxy(Object obj) {
		this.obj = obj;
		Object proxy = Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
		System.out.println("----------------proxy:" + proxy);
		return proxy;
	}
}
