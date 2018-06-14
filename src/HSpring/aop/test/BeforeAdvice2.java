package HSpring.aop.test;

import java.lang.reflect.Method;

import HSpring.org.core.aop.MethodBeforeAdvice;

public class BeforeAdvice2 extends MethodBeforeAdvice{
	public void before(Method method, Object[] args, Object target) {
		// TODO Auto-generated method stub
		System.out.println("---before method2---");
	}
}
