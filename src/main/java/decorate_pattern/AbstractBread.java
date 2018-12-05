package decorate_pattern;

/**
 * 
 * <p>
 * 描述：抽象类实现了IBread这个制作面包的接口,同时包含IBread接口的实例
 * 对应上述的第2个注意点:装饰者类内有一个真实对象的引用
 * <p>
 * @author huluy
 * @date 2018年9月30日
 */
public abstract class AbstractBread implements IBread{
	private final IBread bread;
	
	public AbstractBread(IBread bread) {
		super();
		this.bread = bread;
	}

	@Override
	public void prepair() {
		this.bread.prepair();
	}

	@Override
	public void kneadFlour() {
		this.bread.kneadFlour();
	}

	@Override
	public void steamed() {
		this.bread.steamed();
	}

	@Override
	public void process() {
		prepair();
		kneadFlour();
		steamed();
	}
	
	
}
