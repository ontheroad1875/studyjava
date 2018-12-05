package openAndclosePrinciple;

/**
 * 
 * @Title Person
 * <p>描述：需要比较的对象，暂时只有房子、人<p>
 * @author huluy
 * @date 2018年11月29日 下午5:37:12
 * @Version
 */
public class Person {

	private String name;
	private int age;
	
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
	
}