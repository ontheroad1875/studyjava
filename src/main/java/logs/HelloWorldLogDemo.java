package logs;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @Title HelloWorldLogDemo
 * <p>
 * 描述：日志，LoggerFactory,凭借logback的默认配置策略，当未找到默认配置文件时，logback将向ConsoleAppender根记录器添加a 。
 * <p>
 * @author huluy
 * @date 2018年10月11日 上午10:08:54
 * @Version
 */
public class HelloWorldLogDemo {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(HelloWorldLogDemo.class);
		logger.debug("Hello world.");
		logger.info("打印当前日期：" + new Date());
	}
}
