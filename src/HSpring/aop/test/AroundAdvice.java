package HSpring.aop.test;

import java.lang.reflect.Method;

import HSpring.org.core.aop.MethodAroundAdvice;

public class AroundAdvice extends MethodAroundAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) {
		System.out.println("---aroundBefore1---");
	}

	@Override
	public void after(Method method, Object[] args, Object target) {
		System.out.println("---aroundAfter1---");
	}

}
