package observer_pattern;

/**
 * 
 * <p>
 * 描述：抽象被观察者接口，声明了添加，删除，通知观察者方法
 * <p>
 * @author huluy
 * @date 2018年10月5日
 */
public interface Observerable {
	/**
	 * @Title registerObserver
	 * @Description 注册观察者
	 * @param e
	 * @return void
	 */
	void registerObserver(Observer e);
	void removeObserver(Observer e);
	void notifyObserver();
}
