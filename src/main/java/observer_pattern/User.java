package observer_pattern;

public class User implements Observer {
	private String username;
	private String message;
	
	public User(String username) {
		this.username = username;
	}
	@Override
	public void update(String message) {
		this.message = message;
		read();
	}

	public void read() {
		System.out.println(username+" 收到推送消息：" + message);
	}
}
