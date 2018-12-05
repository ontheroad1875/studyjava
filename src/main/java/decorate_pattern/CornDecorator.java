package decorate_pattern;

public class CornDecorator extends AbstractBread{

	public CornDecorator(IBread bread) {
		super(bread);
	}

	public void paint() {
		System.out.println("#################        添加柠檬黄的着色剂     ##############");
	}
	
	@Override
	public void kneadFlour() {
		this.paint();
		super.kneadFlour();
	}
}
