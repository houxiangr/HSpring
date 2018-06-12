package HSpring.org.core.ioc;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.DocumentException;

import HSpring.org.config.ImportConfig;
import HSpring.org.core.aop.ProxyBeanFactory;
import HSpring.org.entity.Bean;
import HSpring.org.entity.Property;

public class HSpringContext implements BeanFactory{
	//��������е�Bean����
	private Map<String,Object> context=new HashMap<>();
	//��������ļ���Ϣ
	private Map<String,Bean> configBean=new HashMap<String,Bean>();
	//ͨ�����췽������������Ϣ
	//��Bean��scopeΪsingleton��bean����������������
	public HSpringContext(String xmlName) throws DocumentException, 
	ClassNotFoundException, InstantiationException, 
	IllegalAccessException, InvocationTargetException{
		configBean=ImportConfig.parseXmlToBeanList(xmlName);
		for(Entry<String, Bean> bean:configBean.entrySet()) {
			//System.out.println(bean.getKey() + ":" + bean.getValue());
			if(bean.getValue().getScope()==Bean.SINGLETON) {
				Object beanObject=createBeanObject(bean.getValue());
				context.put(bean.getKey(), beanObject);
			}
		}
	}
	//ͨ�����������bean��Ϣ��������
	public Object createBeanObject(Bean bean) throws ClassNotFoundException, 
	InstantiationException, IllegalAccessException, InvocationTargetException {
		Object aimBeanObject=null;
		@SuppressWarnings("rawtypes")
		Class clazz=null;
		//ͨ�������ļ��ж�Ӧbean�����·�����class
		clazz=Class.forName(bean.getBeanClass());
		//���ʵ��
		aimBeanObject=clazz.newInstance();
		//���bean�е�property���������Ϣ
		List<Property> propertys=bean.getPropertys();
		for(Property property:propertys) {
			Map<String,Object> mp=new HashMap<String,Object>();
			//�������ļ�����ʾ��������value��ֱ�Ӹ�ֵ
			if(!property.getValue().equals("")) {
				//System.out.println(property.getName()+"   "+property.getValue());
				mp.put(property.getName(), property.getValue());
			}else if(!property.getRef().equals("")) {
				//��ȡ���õĶ���
				Object ref=context.get(property.getRef());
				//���������û�д˶�����ݹ���ô˺�����������
				if(ref==null) {
					ref=createBeanObject(configBean.get(property.getRef()));
				}
				mp.put(property.getName(), ref);
			}
			//��map�еļ�ֵӳ�䵽aimBeanObject����ȥ
			BeanUtils.copyProperties( aimBeanObject, mp);
		}
		
		if(clazz.equals(ProxyBeanFactory.class)) {
			ProxyBeanFactory pfb=(ProxyBeanFactory)aimBeanObject;
			aimBeanObject=pfb.createProxy();
		}
		
		return aimBeanObject;
	}
	@Override
	public Object getBean(String name) throws InstantiationException, 
	IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		Bean bean=configBean.get(name);
		Object result=null;
		//�������ǵ�����ֱ��ȥ��������
		if(bean.getScope()==Bean.SINGLETON) {
			result=context.get(bean.getId());
		}else if(bean.getScope()==Bean.PROTOTYPE) {//�������´���
			result=createBeanObject(bean);
		}
		return result;
	}
}