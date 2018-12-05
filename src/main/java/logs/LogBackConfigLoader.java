package logs;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * @author create by yingjie.chen on 2018/5/17.
 * @version 2018/5/17 16:08
 */
public class LogBackConfigLoader {
	private static final Logger logger = LoggerFactory.getLogger(LogBackConfigLoader.class);

	public static void main(String[] args) throws IOException, JoranException {
		System.out.println("获取当前编译目录路径：" + LogBackConfigLoader.class.getResource("logback.xml").getPath());
        //LogBackConfigLoader.load(LogBackConfigLoader.class.getResource("logback.xml").getPath());
        //代码
		//LogbackConfigurer.initLogging(LogBackConfigLoader.class.getResource("logback.xml").getPath());
    }

	public static void load(String externalConfigFileLocation) throws IOException, JoranException {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

		File externalConfigFile = new File(externalConfigFileLocation);
		if (!externalConfigFile.exists()) {
			throw new IOException("Logback External Config File Parameter does not reference a file that exists");
		} else {
			if (!externalConfigFile.isFile()) {
				throw new IOException("Logback External Config File Parameter exists, but does not reference a file");
			} else {
				if (!externalConfigFile.canRead()) {
					throw new IOException("Logback External Config File exists and is a file, but cannot be read.");
				} else {
					JoranConfigurator configurator = new JoranConfigurator();
					configurator.setContext(lc);
					lc.reset();
					configurator.doConfigure(externalConfigFileLocation);
					StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
				}
			}
		}
	}
}
