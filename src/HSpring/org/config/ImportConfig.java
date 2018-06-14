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

//����xml�����ļ�
public class ImportConfig{
	//�����ļ�·��
	private String XmlName;
	
	public String getXmlName() {
		return XmlName;
	}
	public void setXmlName(String xmlName) {
		XmlName = xmlName;
	}
	//����xml����
	@SuppressWarnings("null")
	public static Map<String,Bean> parseXmlToBeanList(String xmlName) throws Exception{
		Map<String,Bean> beans = new HashMap<String,Bean>();
		//����SAXReader����
		SAXReader reader=new SAXReader();
		//ƴ��xml·��
		StringBuffer XmlUrl=new StringBuffer("E:\\GitHub\\HSpring\\conf\\");
		XmlUrl.append(xmlName);
		//����xml�ļ�
		Document doc=reader.read(XmlUrl.toString());
		//��ȡ��Ԫ��
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
			//��ȡbean�е����Բ���ֵ����Ӧ����
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
				//��ȡ�������Բ���ֵ
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
					//�������type���������type����
					//��ȡһ���������Bean��id
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
			//���scopeΪprototype��������
			if(scope.equals("prototype")) {
				bean.setScope(Bean.PROTOTYPE);
			}
			//��������ļ���������bean��Idһ�������쳣
			if(beans.get(bean.getId())!=null) {
				throw new Exception("�����ļ�������ͬId��bean");
			}else {
				beans.put(bean.getId(),bean);
			}
		}
		return beans;
	}
	//��ȡxml���õĲ��Է���
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
