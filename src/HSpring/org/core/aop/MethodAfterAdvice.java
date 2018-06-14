package HSpring.org.core.aop;

import java.lang.reflect.Method;

public abstract class MethodAfterAdvice {
	public abstract void after(Method method, Object[] args, Object target);
}
