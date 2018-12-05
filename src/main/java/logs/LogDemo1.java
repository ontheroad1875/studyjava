package logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;


/**
 * 
 * @Title LogDemo1
 * <p>
 * 描述：记录器分配级别.可选级别（TRACE，DEBUG，INFO，WARN和ERROR）的集合在ch.qos.logback.classic.Level类中定义
 * 如果给定的记录器没有分配级别，那么它将从具有指定级别的最近祖先继承一个级别
 * ***给定记录器L的有效级别等于其层次结构中的第一个非空级别，从L本身开始 并在层次结构中向上朝向根记录器继续。*****
 * 为确保所有记录器最终都能继承一个级别，根记录器始终具有指定的级别。默认情况下，此级别为DEBUG。
 * 下面是四个示例，其中包含各种已分配的级别值以及根据级别继承规则生成的有效（继承）级别。
 * 实例1
 * 记录器名称	分配级别	有效水平
 * 根	DEBUG	DEBUG
 * X	没有	DEBUG
 * XY	没有	DEBUG
 * XYZ	没有	DEBUG
 * 实力1中，仅为根记录器分配了级别。此级别值DEBUG由其他记录器继承X，X.Y和X.Y.Z
 * 实例2
 * 记录器名称	分配级别	有效水平
 * 根	error	error
 * X	info	info
 * XY	DEBUG	DEBUG
 * XYZ	warn	warn
 * 实例2中，所有记录器都具有指定的级别值。级别继承不起作用
 * 实例3
 * 记录器名称	分配级别	有效水平
 * 根	DEBUG	DEBUG
 * X	info	info
 * XY	没有		info
 * XYZ	error	error
 * 实例3中，记录器root， X和X.Y.Z被分配了等级 DEBUG，INFO和ERROR 分别。Logger X.Y从其父级继承其级别值X。
 * 实例4
 * 记录器名称	分配级别	有效水平
 * 根	DEBUG	DEBUG
 * X	info	info
 * XY	没有		info
 * XYZ	没有		info
 * 实例4中，记录器root和 X且分配了水平DEBUG和 INFO分别。记录器X.Y并 X.Y.Z从其最近的父级继承其级别值X，该父级具有指定的级别。
 * 
 * 印刷方法和基本选择规则
 * 打印方法确定日志记录请求的级别。
 * L.info("..")是级别INFO的日志记录语句。
 * 如果日志记录请求的级别高于或等于其记录器的有效级别，则称其已启用。否则，该请求被称为禁用
 * 基本选择规则 -- 》 如果p> = q，则启用 向具有有效级别q的记录器发出的级别p的日志请求。
 * 此规则是logback的核心。它假设级别按如下顺序排列： TRACE < DEBUG < INFO <  WARN < ERROR。
 * 以更加图形的方式，这是选择规则的工作方式。在下表中，垂直标题显示记录请求的级别，由p指定，而水平标题显示记录器的有效级别，
 * 由q指定。行（级别请求）和列（有效级别）的交集是基本选择规则产生的布尔值。
 * 的电平
 *					有效水平q
 *请求p		跟踪	DEBUG	信息	警告	错误	关闭
 *跟踪		是	没有	没有	没有	没有	没有
 *DEBUG		是	是	没有	没有	没有	没有
 *信息		是	是	是	没有	没有	没有
 *警告		是	是	是	是	没有	没有
 *错误		是	是	是	是	是	没有
 * <p>
 * @author huluy
 * @date 2018年10月11日 上午10:55:17
 * @Version
 */
public class LogDemo1 {
	public static void main(String[] args) {
		//根记录器位于记录器层次结构的顶部。它的特殊之处在于它是每个层次结构的一部分。像每个记录器一样，它可以通过其名称检索
		//Logger logger = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
		//设置日志级别
		ch.qos.logback.classic.Logger logger = 
		        (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("logs.LogDemo1");
		logger.setLevel(Level.INFO);
		System.out.println("判断是否为debug:" + logger.isDebugEnabled());
		logger.warn("Low fuel level.");
		// This request is disabled, because DEBUG < INFO. 
		logger.debug("Starting search for nearest gas station.");
		
		Logger barlogger = LoggerFactory.getLogger("logs.LogDemo1");
		// The logger instance barlogger, named "com.foo.Bar", 
		// will inherit its level from the logger named 
		// "com.foo" Thus, the following request is enabled 
		// because INFO >= INFO. 
		barlogger.info("Located nearest gas station.");
		barlogger.debug("Exiting gas station search debug");
		// This request is disabled, because DEBUG < INFO. 
		if (barlogger.isDebugEnabled()) {
			barlogger.debug("Exiting gas station search not debug 判断");
		}
	}
}
