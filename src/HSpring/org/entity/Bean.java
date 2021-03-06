package HSpring.org.entity;

import java.util.List;
//配置文件中Bean的实体类
public class Bean {
	//定义Bean的scope为单例的常量
	public static final int SINGLETON=1;
	//定义Bean为scope为多实体的常量
	public static final int PROTOTYPE=2;
	//配置文件中Bean元素的一些属性
	private String id;
	private String beanClass;
	private String proxyType;
	//scope默认设置为单例模式
	private int scope=SINGLETON;
	private List<Property> propertys;
	public Bean(String id, String beanClass, List<Property> propertys,String proxyType) {
		this.id = id;
		this.beanClass = beanClass;
		this.propertys = propertys;
		this.proxyType=proxyType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getProxyType() {
		return proxyType;
	}
	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}
	public String getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	public int getScope() {
		return scope;
	}
	public void setScope(int scope) {
		this.scope = scope;
	}
	public List<Property> getPropertys() {
		return propertys;
	}
	public void setPropertys(List<Property> propertys) {
		this.propertys = propertys;
	}
	
}
