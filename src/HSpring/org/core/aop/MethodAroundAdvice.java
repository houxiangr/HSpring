package HSpring.org.core.aop;

import java.lang.reflect.Method;

public abstract class MethodAroundAdvice {
	public abstract void before(Method method, Object[] args, Object target);
	public abstract void after(Method method, Object[] args, Object target);
}
