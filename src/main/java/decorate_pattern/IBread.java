package decorate_pattern;

/**
 * 
 * <p>
 * 描述：准备材料，和面，蒸馒头，加工馒头
 * <p>
 * @author huluy
 * @date 2018年9月30日
 */
public interface IBread {
	 public void prepair();
	 public void kneadFlour();
	 public void steamed();
	 public void process();
}
