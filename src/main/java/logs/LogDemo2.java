package logs;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Title LogDemo2
 * <p>
 * 描述：调用LoggerFactory.getLogger 具有相同名称的方法将始终返回对完全相同的logger对象的引用
 * 因此，可以配置记录器，然后在代码中的其他位置检索相同的实例，而不传递引用。
 * 与生父母一样，父母总是先于孩子的基本矛盾，可以按任何顺序创建和配置logback记录器。
 * 特别是，“父”记录器将查找并链接到其后代，即使它们在它们之后被实例化。
 * 通常在应用程序初始化时完成对logback环境的配置。首选方法是读取配置文件。
 * 记录器L的日志语句的输出将转到L及其祖先中的所有appender 。这就是术语“appender additivity”的含义。
 * 但是，如果记录器L的祖先，比如P，将additivity标志设置为false，则L的输出将被定向到L中的所有appender 及其祖先，
 * 包括P，但不包括任何appender。P的祖先。
 * 记录器默认情况下将其可加性标志设置为true。
 * 只有在评估是否记录之后，并且只有在决策是肯定的情况下，记录器实现才会格式化消息并将“{}”对替换为字符串值entry
 * 换句话说，当禁用日志语句时，此表单不会产生参数构造的成本。
 * <p>
 * @author huluy
 * @date 2018年10月11日 下午2:18:48
 * @Version
 */
public class LogDemo2 {
	public static void main(String[] args) {
		Logger logger1 = LoggerFactory.getLogger(LogDemo2.class);
		Logger logger2 = LoggerFactory.getLogger(LogDemo2.class);
		LogMessage logMessage = new LogMessage("测试日志信息", "huly", new Date());
		System.out.println("判断相同名称的logger对象引用是否相同：" + (logger1 == logger2));
		logger2.debug("信息是：{}", logMessage);
		int i = 2;
		//任何方法调用都涉及参数构造的“隐藏”成本
		//导致构造消息参数的成本，即转换整数i和 entry[i]字符串，以及连接中间字符串，无论是否记录消息。
		logger2.info("entry a i =" + i + ",对象是：" + logMessage);
		//参数构造的成本可能非常高，并且取决于所涉及的参数的大小。为了避免参数构造的成本，您可以利用SLF4J的参数化日志记录：
		//该变体不会产生参数构造的成本与之前对该debug()方法的调用相比 ，它的速度会更快。仅当要将记录请求发送到附加的appender时，才会格式化该消息。
		//此外，格式化消息的组件也得到了高度优化。
		logger2.debug("entry a i avoid cost = {}, 对象是：{}", i, logMessage);
		//避免参数构造的一种可能方法就是使用测试包围日志语句.
		if (logger1.isDebugEnabled()) {
			logger1.debug("调试入口在这里");
		}
	}
}
