package utils;

import java.io.File;
import java.net.URISyntaxException;

/**
 * 
 * @Title ToolsClassPathDemo
 * <p>描述：TODO<p>
 * @author huluy
 * @date 2018年10月25日 上午9:34:57
 * @Version
 */
public class ToolsClassPathDemo {
	
	public static void main(String[] args) {
		getWebClassPath();
		System.out.println(System.getProperty("java.class.path"));
		System.out.println(System.getProperty("user.dir"));
	}
	public static String getWebClassPath() {
		String result = null;
		try {
			File file = new File(ToolsClassPathDemo.class.getResource("/").toURI());
            result = file.getAbsolutePath();
            System.out.println("getWebClassPath="+result);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
	}
}
