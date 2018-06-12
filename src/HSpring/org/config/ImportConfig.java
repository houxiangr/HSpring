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
	public static Map<String,Bean> parseXmlToBeanList(String xmlName) throws DocumentException{
		Map<String,Bean> beans = new HashMap<String,Bean>();
		//创建SAXReader对象
		SAXReader reader=new SAXReader();
		//拼接xml路径
		StringBuffer XmlUrl=new StringBuffer("D:\\workspace\\HSpring\\conf\\");
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
				}
			}
			@SuppressWarnings("unchecked")
			List<Element> propertyNodes=BeanNode.elements("property");
			for(Element propertyNode : propertyNodes) {
				String name="";
				String ref="";
				String value="";
				@SuppressWarnings("unchecked")
				List<Attribute> properattrs=propertyNode.attributes();
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
					}
				}
				Property tempProperty=new Property(name,ref,value);
				propertys.add(tempProperty);
			}
			Bean bean=new Bean(id,beanClass,propertys);
			//如果scope为prototype重新设置
			if(scope.equals("prototype")) {
				bean.setScope(Bean.PROTOTYPE);
			}
			beans.put(bean.getId(),bean);
		}
		return beans;
	}
	//获取xml配置的测试方法
	@Test
	public void test() {
		Map<String,Bean> beans=null;
		try {
			beans=parseXmlToBeanList("HSpring.xml");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Entry<String, Bean> bean:beans.entrySet()) {
			 System.out.println(bean.getKey() + ":" + bean.getValue());
		}
	}
}
