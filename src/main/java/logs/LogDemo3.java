package logs;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * 
 * @Title LogDemo3
 * <p>
 * 描述：此类定义静态记录器变量。然后它实例化一个Foo对象。
 * 假设设置配置文件logback-test.xml或logback.xml配置文件不存在，则logback将默认调用BasicConfigurator ，这将设置最小配置。此最小配置包含ConsoleAppender附加到根记录器。
 * 输出的格式 PatternLayoutEncoder设置为模式 ％d {HH：mm：ss.SSS} [％thread]％-5level％logger {36} - ％msg％n
 * 默认情况下，根记录器分配DEBUG级别
 * <p>
 * @author huluy
 * @date 2018年10月12日 上午8:57:45
 * @Version
 */
public class LogDemo3 {
	public static final Logger logger = LoggerFactory.getLogger(LogDemo3.class);
	public static void main(String[] args) {
		LoggerContext ctx = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(ctx);
		System.out.println("status:" + ctx.getStatusManager());
		logger.info("输入引用程序");
		LogMessage logMsg = new LogMessage("huly", "huly", new Date());
		logMsg.doIt();
		logger.info("退出应用程序");
	}
}
