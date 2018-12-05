package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Title LoadPageClassDemo.java
 * <p>
 * 描述：加载配置文件下对应的包下的所有的java文件
 * <p>
 * @author huluy
 * @date 2018年10月6日 下午8:15:56
 * @Version
 */
public class LoadPageClassDemo {
	public static void main(String[] args) {
//		LoadPageClassDemo loadDemo = new LoadPageClassDemo();
//		String totalPath = loadDemo.resolvePackagePath("utils.test.pack");
//		System.out.println("totalPath:" + totalPath);
		
		LoadPageClassDemo test = new LoadPageClassDemo();
        String packageData = (String) ReadUtils.read("page");
        String totalPath = test.resolvePackagePath(packageData);
        System.out.println(packageData +"   "+totalPath);
        List<String> datas = test.parseClassName(totalPath,packageData);
        System.out.println(datas);
        
	}
	/**
	 * 
	 * @Title resolvePackagePath
	 * @Description 解析包的全路径（比如你的包路径为：utils.test.pack）
	 * @param webPackage
	 * @return String
	 */
	public String resolvePackagePath(String webPackage) {
		// 扫码所有的包并把其放入到访问关系和方法放入到内存中
		File f = new File(this.getClass().getResource("/").getPath());
		String totalPath = f.getAbsolutePath();
		System.out.println("############totalPath:" + totalPath);
		String pageName = getClass().getPackage().getName().replace(".", "\\");
        totalPath = totalPath.replace(pageName, "");
        String packagePath = webPackage.replace(".", "\\");
        totalPath = totalPath + "\\" + packagePath;
        return totalPath; 
	}
	
	/**
	 * 
	 * @Title parseClassName
	 * @Description 解析包下所有的类
	 * @param packagePath 上一步获取包的全路径
	 * @param webPackage 包(utils.test.pack)
	 * @return List<String>
	 */
	public List<String> parseClassName(String packagePath, String webPackage) {
		List<String> array = new ArrayList<>();
        File root = new File(packagePath);
        resolveFile(root, webPackage, array);
        return array;
	}
	
	private void resolveFile(File root, String webPackage, List<String> classNames) {
        if (!root.exists())
            return;
        File[] childs = root.listFiles();
        if (childs != null && childs.length > 0) {
            for (File child : childs) {
                if (child.isDirectory()) {
                    String parentPath = child.getParent();
                    String childPath = child.getAbsolutePath();
                    String temp = childPath.replace(parentPath, "");
                    temp = temp.replace("\\", "");
                    resolveFile(child, webPackage + "." + temp, classNames);
                } else {
                    String fileName = child.getName();
                    if (fileName.endsWith(".class")) {
                        String name = fileName.replace(".class", "");
                        String className = webPackage + "." + name;
                        classNames.add(className);
                    }
                }
            }
        }
    }

}
