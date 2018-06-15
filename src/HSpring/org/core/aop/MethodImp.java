package HSpring.org.core.aop;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

public interface MethodImp {
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy,List<Object> advices,List<String> methodsList) throws Throwable;
}
