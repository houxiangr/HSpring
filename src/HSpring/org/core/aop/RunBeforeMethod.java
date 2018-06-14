package HSpring.org.core.aop;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

public class RunBeforeMethod implements MethodImp {


	@Override
	public Object intercept(Object obj, Method method, Object[] args, 
			MethodProxy proxy, List<Object> advices)
			throws Throwable {
		for(Object advice:advices) {
             // 在目标方法执行前执行前置通知代码
             ((MethodBeforeAdvice)advice).before(method, args, obj);
		}
		Object result=null;
		result = method.invoke(obj, args);
		return result;
	}

}
