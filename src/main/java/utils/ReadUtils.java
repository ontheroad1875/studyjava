package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @Title ReadUtils.java
 * <p>
 * 描述：TODO
 * <p>
 * @author huluy
 * @date 2018年10月6日 上午12:24:50
 * @Version
 */
public class ReadUtils {
	private static Map<String, Object> cache = new HashMap<String, Object>();
	
	private static Properties properties = null;
	
	static {
		properties = new Properties();
		try {
			properties.load(ReadUtils.class.getResourceAsStream("/config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object read(String key) {
		Object value = cache.get(key);
		if (value == null) {
			synchronized (ReadUtils.class) {
				value = cache.get(key);
				if (value == null) {
					value = properties.get(key);
					cache.put(key, value);
				}
			}
		}
		return value;
	}
}
