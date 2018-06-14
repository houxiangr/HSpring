 package HSpring.org.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import HSpring.org.entity.Bean;
import HSpring.org.entity.Property;

//导入xml配置文件
public class ImportConfig{
	//配置文件路径
	private String XmlName;
	
	public String getXmlName() {
		return XmlName;
	}
	public void setXmlName(String xmlName) {
		XmlName = xmlName;
	}
	//解析xml方法
	@SuppressWarnings("null")
	public static Map<String,Bean> parseXmlToBeanList(String xmlName) throws Exception{
		Map<String,Bean> beans = new HashMap<String,Bean>();
		//创建SAXReader对象
		SAXReader reader=new SAXReader();
		//拼接xml路径
		StringBuffer XmlUrl=new StringBuffer("E:\\GitHub\\HSpring\\conf\\");
		XmlUrl.append(xmlName);
		//读入xml文件
		Document doc=reader.read(XmlUrl.toString());
		//获取根元素
		Element BeansNode=doc.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> BeanNodes=BeansNode.elements("bean");
		for(Element BeanNode : BeanNodes) {
			String id="";
			String beanClass="";
			String scope="";
			String proxyType="";
			List<Property> propertys=new ArrayList<Property>();
			@SuppressWarnings("unchecked")
			List<Attribute> beanattrs=BeanNode.attributes();
			//获取bean中的属性并赋值给相应变量
			for (Attribute attr : beanattrs) {  
				String attrkey=attr.getName();
				String attrvalue=attr.getValue();
				switch(attrkey) {
				case "id":
					id=attrvalue;
					break;
				case "class":
					beanClass=attrvalue;
					break;
				case "scope":
					scope=attrvalue;
					break;
				case "proxyType":
					proxyType=attrvalue;
					break;
				}
			}
			@SuppressWarnings("unchecked")
			List<Element> propertyNodes=BeanNode.elements("property");
			for(Element propertyNode : propertyNodes) {
				String name="";
				String ref="";
				String value="";
				List<String> proxyBeans=new ArrayList<String>();
				@SuppressWarnings("unchecked")
				List<Attribute> properattrs=propertyNode.attributes();
				//获取常见属性并赋值
				for (Attribute attr : properattrs) {  
					String attrkey=attr.getName();
					String attrvalue=attr.getValue();
					switch(attrkey) {
					case "name":
						name=attrvalue;
						break;
					case "ref":
						ref=attrvalue;
						break;
					case "value":
						value=attrvalue;
						break;
					}
					//如果包含type属性则分析type属性
					//获取一组代理方法的Bean的id
					if(attrkey.equals("type")) {
						if(attrvalue.equals("proxyList")) {
							@SuppressWarnings("unchecked")
							List<Element> ListNodes=propertyNode.elements("ref");
							for(Element ListNode:ListNodes) {
								@SuppressWarnings("unchecked")
								List<Attribute> Listattrs=ListNode.attributes();
								for (Attribute refAttr : Listattrs) {
									String proxyattrkey=refAttr.getName();
									String proxyattrvalue=refAttr.getValue();
									switch(proxyattrkey) {
									case "bean":
										proxyBeans.add(proxyattrvalue);
										break;
									}
								}
								
							}
						}
					}
				}
				Property tempProperty=new Property(name,ref,value);
				tempProperty.setProxyList(proxyBeans);
				propertys.add(tempProperty);
			}
			Bean bean=new Bean(id,beanClass,propertys,proxyType);
			//如果scope为prototype重新设置
			if(scope.equals("prototype")) {
				bean.setScope(Bean.PROTOTYPE);
			}
			//如果配置文件中有两个bean的Id一样则抛异常
			if(beans.get(bean.getId())!=null) {
				throw new Exception("配置文件中有相同Id的bean");
			}else {
				beans.put(bean.getId(),bean);
			}
		}
		return beans;
	}
	//获取xml配置的测试方法
	@Test
	public void test() throws Exception {
		Map<String,Bean> beans=null;
		try {
			beans=parseXmlToBeanList("HSpring.xml");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Entry<String, Bean> bean:beans.entrySet()) {
//			 System.out.println(bean.getKey() + ":" + bean.getValue());
			 //System.out.println(bean.getValue().getPropertys());
			 for(Property pro:bean.getValue().getPropertys()) {
				 System.out.println(pro.getProxyList());
			 }
		}
	}
}
