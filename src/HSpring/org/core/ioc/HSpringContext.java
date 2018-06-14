package HSpring.org.core.ioc;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import HSpring.org.config.ImportConfig;
import HSpring.org.core.aop.MethodImp;
import HSpring.org.core.aop.ProxyBeanFactory;
import HSpring.org.core.aop.RunAfterMethod;
import HSpring.org.core.aop.RunAroundMethod;
import HSpring.org.core.aop.RunBeforeMethod;
import HSpring.org.entity.Bean;
import HSpring.org.entity.Property;

public class HSpringContext implements BeanFactory{
	//存放容器中的Bean对象
	private Map<String,Object> context=new HashMap<>();
	//存放配置文件信息
	private Map<String,Bean> configBean=new HashMap<String,Bean>();
	//通过构造方法传入配置信息
	//将Bean的scope为singleton的bean创建出来放入容器
	public HSpringContext(String xmlName) throws Exception{
		configBean=ImportConfig.parseXmlToBeanList(xmlName);
		for(Entry<String, Bean> bean:configBean.entrySet()) {
			//System.out.println(bean.getKey() + ":" + bean.getValue());
			if(bean.getValue().getScope()==Bean.SINGLETON) {
				Object beanObject=createBeanObject(bean.getValue());
				context.put(bean.getKey(), beanObject);
			}
		}
	}
	//通过反射和利用bean信息创建对象
	public Object createBeanObject(Bean bean) throws ClassNotFoundException, 
	InstantiationException, IllegalAccessException, InvocationTargetException {
		Object aimBeanObject=null;
		@SuppressWarnings("rawtypes")
		Class clazz=null;
		//通过配置文件中对应bean的类的路径获得class
		clazz=Class.forName(bean.getBeanClass());
		//获得实例
		aimBeanObject=clazz.newInstance();
		//如果是代理对象对代理类型进行赋值
		if(bean.getProxyType().equals("before")) {
			Map<String,MethodImp> proxymp=new HashMap<String,MethodImp>();
			proxymp.put("interceptor", new RunBeforeMethod());
			BeanUtils.copyProperties( aimBeanObject, proxymp);
		}else if(bean.getProxyType().equals("after")) {
			Map<String,MethodImp> proxymp=new HashMap<String,MethodImp>();
			proxymp.put("interceptor",  new RunAfterMethod());
			BeanUtils.copyProperties( aimBeanObject, proxymp);
		}else if(bean.getProxyType().equals("around")) {
			Map<String,MethodImp> proxymp=new HashMap<String,MethodImp>();
			proxymp.put("interceptor",  new RunAroundMethod());
			BeanUtils.copyProperties( aimBeanObject, proxymp);
		}
		//获得bean中的property配置项的信息
		List<Property> propertys=bean.getPropertys();
		for(Property property:propertys) {
			Map<String,Object> mp=new HashMap<String,Object>();
			Map<String,List<Object>> mpList=new HashMap<String,List<Object>>();
			//在配置文件中显示的设置了value则直接赋值
			if(!property.getValue().equals("")) {
				//System.out.println(property.getName()+"   "+property.getValue());
				mp.put(property.getName(), property.getValue());
				//将map中的键值映射到aimBeanObject类中去
				BeanUtils.copyProperties( aimBeanObject, mp);
			}else if(!property.getRef().equals("")) {
				//获取引用的对象
				Object ref=context.get(property.getRef());
				//如果容器中没有此对象则递归调用此函数创建该类
				if(ref==null) {
					ref=createBeanObject(configBean.get(property.getRef()));
				}
				mp.put(property.getName(), ref);
				//将map中的键值映射到aimBeanObject类中去
				BeanUtils.copyProperties( aimBeanObject, mp);
			}else if(property.getProxyList()!=null) {
				List<String> proxyList=property.getProxyList();
				List<Object> objList=new ArrayList<Object>();
				for(String proxyName:proxyList) {
					//获取引用的对象
					Object ref=context.get(proxyName);
					//如果容器中没有此对象则递归调用此函数创建该类
					if(ref==null) {
						ref=createBeanObject(configBean.get(proxyName));
					}
					objList.add(ref);
				}
				mpList.put(property.getName(), objList);
				//将map中的键值映射到aimBeanObject类中去
				BeanUtils.copyProperties( aimBeanObject, mpList);
			}
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
		//如果这个是单例类直接去容器中找
		if(bean.getScope()==Bean.SINGLETON) {
			result=context.get(bean.getId());
		}else if(bean.getScope()==Bean.PROTOTYPE) {//否则重新创建
			result=createBeanObject(bean);
		}
		return result;
	}
}
