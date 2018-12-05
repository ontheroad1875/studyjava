package logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * 
 * @Title HelloWorldLogDemo2
 * <p>
 * 描述：logback可以使用内置系统报告有关内部状态的信息，在logback的声明周期中发生的重要事件可以通过
 * 名为的组件访问 StatusManager。暂时，让我们通过调用类的静态print()方法指示logback打印其内部状态 StatusPrinter 
 * 打印记录器状态
 * logback通过调用StatusPrinter.print()方法来打印其内部状态,Logback的内部状态信息在诊断与logback相关的问题时非常有用。
 * 启动应用程序登陆所需要的三个步骤：
 * 1：配置Logback环境。您可以通过几种或多种复杂的方式实现这一目标。稍后会详细介绍。
 * 2：在您希望执行日志记录的每个类中，Logger通过调用org.slf4j.LoggerFactory类' getLogger()方法检索 实例 ，将当前类名或类本身作为参数传递。
 * 3：通过调用其打印方法（即debug（），info（），warn（）和error（）方法）来使用此记录器实例。这将在配置的appender上生成日志记录输出。
 * <p>
 * @author huluy
 * @date 2018年10月11日 上午10:23:30
 * @Version
 */
public class HelloWorldLogDemo2 {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger("logs.HelloWorldLogDemo2");
		logger.debug("Hello wold 2 , study how to print statusManager");
		// print internal state
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(context);
		// print result : 找不到 logback-test.xml和logback.xml配置文件（稍后讨论），它使用默认策略配置自己，这是一个基本策略ConsoleAppender
		//Could NOT find resource [logback-test.xml]
		//Could NOT find resource [logback.xml]
		//Setting up default configuration.
		
	}
}
