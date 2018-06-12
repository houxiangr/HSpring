package HSpring.aop.test;

public class TargetBean implements TargetBeanImp {
	@Override
	public void show(String user) {
		// TODO Auto-generated method stub
		System.out.println("show:"+user);
	}
}
