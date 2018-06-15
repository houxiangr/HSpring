package HSpring.aop.test;

public class TargetBean implements TargetBeanImp {
	@Override
	public void show(String user) {
		// TODO Auto-generated method stub
		System.out.println("show:"+user);
	}
	@Override
	public void show2(String user) {
		// TODO Auto-generated method stub
		System.out.println("show2:"+user);
	}
}
