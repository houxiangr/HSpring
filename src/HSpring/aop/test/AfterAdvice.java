package HSpring.aop.test;

import java.lang.reflect.Method;

import HSpring.org.core.aop.MethodAfterAdvice;

public class AfterAdvice extends MethodAfterAdvice {

	@Override
	public void after(Method method, Object[] args, Object target) {
		System.out.println("---after method1---");
	}

}
