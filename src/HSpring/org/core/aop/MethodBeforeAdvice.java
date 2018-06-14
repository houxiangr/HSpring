package HSpring.org.core.aop;

import java.lang.reflect.Method;

public abstract class MethodBeforeAdvice{
	public abstract void before(Method method, Object[] args, Object target);
}
