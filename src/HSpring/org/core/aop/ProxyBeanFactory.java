package HSpring.org.core.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyBeanFactory implements MethodInterceptor{
	private Object targetBean=null;
	private String targetInterface=null;
	private List<Object> adviceName=null;
	private List<String> methodsList=null;
	private MethodImp interceptor=null;

	public List<String> getMethodsList() {
		return methodsList;
	}
	public void setMethodsList(List<String> methodsList) {
		this.methodsList = methodsList;
	}
	public MethodImp getInterceptor() {
		return interceptor;
	}
	public void setInterceptor(MethodImp interceptor) {
		this.interceptor = interceptor;
	}
	public Object getTargetBean() {
		return targetBean;
	}
	public void setTargetBean(Object targetBean) {
		this.targetBean = targetBean;
	}
	public String getTargetInterface() {
		return targetInterface;
	}
	public void setTargetInterface(String targetInterface) {
		this.targetInterface = targetInterface;
	}
	public List<Object> getAdviceName() {
		return adviceName;
	}
	public void setAdviceName(List<Object> adviceName) {
		this.adviceName = adviceName;
	}
	public Object createProxy() {
		// TODO Auto-generated method stub
		if(targetInterface==null) {
			return createCglibProxy();
		}
		return createJdkProxy();
	}
	//jdk代理创建代理对象
	private Object createJdkProxy() {
		// TODO Auto-generated method stub
		Class<?> clazz = null;
	    try {
	        clazz = Class.forName(targetInterface);// 实现的接口
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
		Object jdkProxy=Proxy.newProxyInstance(targetBean.getClass().getClassLoader(),
				 new Class[] { clazz }, 
				new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) 
                    		throws Throwable {
                    	Object result=null;
                    	result=interceptor.intercept(targetBean, method, args, null,adviceName,methodsList);
                    	return result;
                    }
                });
		return jdkProxy;
	}
	private Object createCglibProxy() {
		//1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(targetBean.getClass());
        //3.设置回调函数
        en.setCallback(this);
        //4.创建子类(代理对象)
        return en.create();
	}
	
	@Override
    public Object intercept(Object obj, Method method,
    		Object[] args, MethodProxy proxy) throws Throwable {
		Object result=null;
        //判断该通知在方法调用前执行
    	result=interceptor.intercept(targetBean, method, args, null,adviceName,methodsList);
    	return result;
    }
}

