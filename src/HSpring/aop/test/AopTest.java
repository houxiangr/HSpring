package HSpring.aop.test;

import java.lang.reflect.InvocationTargetException;

import org.dom4j.DocumentException;
import org.junit.Test;

import HSpring.org.core.ioc.BeanFactory;
import HSpring.org.core.ioc.HSpringContext;

public class AopTest {
	@Test
	public void testJdkProxy() throws ClassNotFoundException, 
	InstantiationException, IllegalAccessException, 
	InvocationTargetException, DocumentException {
		/*BeanFactory bf = new HSpringContext("HSpring.xml");
        TargetBeanImp targetImp = (TargetBeanImp) bf.getBean("targetBeanProxy");
        //�⿪����ע�ͻ��ִ��һ��ǰ�ú���
        //System.out.println(targetImp);
        System.out.println(targetImp.getClass());
        targetImp.show("houxiang");*/
		BeanFactory bf = new HSpringContext("HSpring.xml");
        CglibTargetBean cglibtarget = (CglibTargetBean)bf.getBean("cglibBeanProxy");
        cglibtarget.show("houxiang");
	}
}