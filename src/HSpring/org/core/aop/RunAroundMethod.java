package HSpring.org.core.aop;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

public class RunAroundMethod implements MethodImp {

	@Override
	public Object intercept(Object obj, Method method, Object[] args, 
			MethodProxy proxy, List<Object> advices)
			throws Throwable {
		for(Object advice:advices) {
             // ��Ŀ�귽��ִ��ǰִ��ǰ��֪ͨ����
             ((MethodAroundAdvice)advice).before(method, args, obj);
		}
		Object result=null;
		result = method.invoke(obj, args);
		for(Object advice:advices) {
            // ��Ŀ�귽��ִ��ǰִ��ǰ��֪ͨ����
            ((MethodAroundAdvice)advice).after(method, args, obj);
		}
		return result;
	}
}
