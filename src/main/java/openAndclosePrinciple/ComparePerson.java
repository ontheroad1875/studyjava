package openAndclosePrinciple;

/**
 * @Title ComparePerson
 * <p>描述：比较类，主要实现具体的比较方法，人是比较年龄，房子是比较面积，只有同类型的对象才可以进行比较<p>
 * 好了，demo已经结束，差不多已经明白了吧。这样做的好处就是，如果下次要添加比较2头猪的需求时，
 * 我们无需改动原本的代码，只要添加一个pig类、一个ComparePig类就好了。
 * @author huluy
 * @date 2018年11月29日 下午5:40:03
 * @Version
 */
public class ComparePerson extends CompareObject{
 
	@Override
	public Object getMax() {
		Person person1 = (Person) object1;
		Person person2 = (Person) object2;
		if (person1.getAge() > person2.getAge()) {
			return person1;
		}
		return person2;
	}
 
	@Override
	public Object getMin() {
		if (((Person) object1).getAge() > ((Person) object2).getAge()) {
			return (Person) object2;
		}
		return (Person) object1;
	}
 
	@Override
	protected boolean isAccept(Object arg1, Object arg2) {
		if ((arg1 instanceof Person) && (arg2 instanceof Person)) {
			return true;
		}
		return false;
	}
 
	
	
}

