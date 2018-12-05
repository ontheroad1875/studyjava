package logs;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @Title LogMessage
 * <p>
 * 描述：日志记录基本信息
 * <p>
 * @author huluy
 * @date 2018年10月11日 下午3:44:41
 * @Version
 */
public class LogMessage {
	static final Logger logger = LoggerFactory.getLogger(LogMessage.class);
	private String message;			//记录信息
	private String logName;			//记录人
	private Date createDate;			//创建日期
	public LogMessage() {
		super();
	}
	public LogMessage(String message, String logName, Date createDate) {
		super();
		this.message = message;
		this.logName = logName;
		this.createDate = createDate;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void doIt() {
		logger.debug("再来一次");
	}
	@Override
	public String toString() {
		return "LogMessage [message=" + message + ", logName=" + logName + ", createDate=" + createDate + "]";
	}
	
}
