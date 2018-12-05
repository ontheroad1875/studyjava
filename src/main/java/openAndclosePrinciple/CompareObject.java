package openAndclosePrinciple;

/**
 * @Title CompareObject
 * <p>描述：抽象类，把随着比较对象不同而改变的方法抽象出来<p>
 * @author huluy
 * @date 2018年11月29日 下午5:38:08
 * @Version
 */
public abstract class CompareObject {
	 
	protected Object object1;
	protected Object object2;
	
	public void setData(Object arg1, Object arg2){
		if (isAccept(arg1,arg2)) {
			object1 = arg1;
			object2 = arg2;
		}else {
			throw new RuntimeException( arg1 + " and " + arg2 + " isn't the them type ");
		}
	}
	
	protected abstract boolean isAccept(Object arg1, Object arg2);
	
	public abstract Object getMax();
	
	public abstract Object getMin();
	
}
