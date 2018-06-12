package HSpring.aop.test;

//Cglib的目标对象没有继承任何接口
public class CglibTargetBean {
	public void show(String user) {
		// TODO Auto-generated method stub
		System.out.println("show:"+user);
	}
}
