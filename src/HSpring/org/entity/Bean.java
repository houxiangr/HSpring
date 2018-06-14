package HSpring.org.entity;

import java.util.List;
//�����ļ���Bean��ʵ����
public class Bean {
	//����Bean��scopeΪ�����ĳ���
	public static final int SINGLETON=1;
	//����BeanΪscopeΪ��ʵ��ĳ���
	public static final int PROTOTYPE=2;
	//�����ļ���BeanԪ�ص�һЩ����
	private String id;
	private String beanClass;
	private String proxyType;
	//scopeĬ������Ϊ����ģʽ
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
