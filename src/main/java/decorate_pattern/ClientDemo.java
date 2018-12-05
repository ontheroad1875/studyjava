package decorate_pattern;

public class ClientDemo {
	public static void main(String[] args) {
		System.out.println("###############  开始生产馒头  ###########");
		IBread normalBread = new NormalBread();
		normalBread = new SweetDecorator(normalBread);
		normalBread = new CornDecorator(normalBread);
		normalBread.process();
		System.out.println("###############  装饰馒头结束  ###########");
	}
}
