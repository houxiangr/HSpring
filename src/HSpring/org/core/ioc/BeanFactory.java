package HSpring.org.core.ioc;

import java.lang.reflect.InvocationTargetException;

//������ӿ�
public interface BeanFactory {
	//�������ֻ�ȡBean
	Object getBean(String name) throws InstantiationException, 
	IllegalAccessException, InvocationTargetException, ClassNotFoundException;
}
