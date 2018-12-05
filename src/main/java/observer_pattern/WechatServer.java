package observer_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Title WechatServer.java
 * <p>
 * 描述：定义被观察者，实现了Observerable接口，对Observerable接口的三个方法进行了具体实现，同时有一个List集合，
 * 用以保存注册的观察者，等需要通知观察者时，遍历该集合即可
 * <p>
 * @author huluy
 * @date 2018年10月5日 下午7:53:09
 * @Version
 */
public class WechatServer implements Observerable{
	/*注意到这个List集合的泛型参数是Observer接口，设计原则：面向接口编程而不是面向实现编程*/
	private List<Observer> list;
	private String message;
	
	public WechatServer() {
		list = new ArrayList<Observer>();
	}
	@Override
	public void registerObserver(Observer e) {
		list.add(e);
	}

	@Override
	public void removeObserver(Observer e) {
		if (!list.isEmpty()) {
			list.remove(e);
		}
	}

	@Override
	public void notifyObserver() {
		for (int i = 0; i < list.size(); i++) {
			Observer e = list.get(i);
			e.update(message);
		}
	}
	
	public void setInfomation(String message) {
		this.message = message;
		System.out.println("微信服务更新消息：" + message);
		//消息更新，通知所有观察者
		notifyObserver();
	}

}
