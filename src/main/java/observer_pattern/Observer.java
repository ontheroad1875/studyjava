package observer_pattern;

/**
 * 
 * @Title Observer.java
 * <p>
 * 描述：定义了一个update()方法，当被观察者调用notifyObservers()方法时，观察者的update()方法会被回调。
 * <p>
 * @author huluy
 * @date 2018年10月5日 下午6:23:02
 * @Version
 */
public interface Observer {
	void update(String message);
}
