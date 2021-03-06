package HSpring.org.entity;

import java.util.List;

//配置文件中Property的实体类
public class Property {
	//配置文件中property元素的一些属性
	private String name=null;
	private String ref=null;
	private String value=null;
	private List<String> method=null;
	private List<String> proxyList=null;
	public List<String> getMethod() {
		return method;
	}
	public void setMethod(List<String> method) {
		this.method = method;
	}
	public List<String> getProxyList() {
		return proxyList;
	}
	public void setProxyList(List<String> proxyList) {
		this.proxyList = proxyList;
	}
	public Property(String name, String ref,String value) {
		this.name = name;
		this.ref = ref;
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	
}
