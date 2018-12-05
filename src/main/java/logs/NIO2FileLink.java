package logs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
 
public class NIO2FileLink {
 
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		Path link = FileSystems.getDefault().getPath(
//				System.getProperty("user.home"), "www",
//				"pyweb.settings");
//		Path target = FileSystems.getDefault().getPath("testlink");
// 
//		// 创建软链接
//		try {
//			Files.createSymbolicLink(link, target);
// 
//			// 创建软链接时设置软链接的属性
//			PosixFileAttributes attrs = Files.readAttributes(target,
//					PosixFileAttributes.class);
//			FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions
//					.asFileAttribute(attrs.permissions());
//			Files.createSymbolicLink(link, target, attr);
// 
//		} catch (IOException | UnsupportedOperationException
//				| SecurityException e) {
//			if (e instanceof SecurityException) {
//				System.err.println("Permission denied!");
//			}
//			if (e instanceof UnsupportedOperationException) {
//				System.err.println("An unsupported operation was detected!");
//			}
//			if (e instanceof IOException) {
//				System.err.println("An I/O error occurred!");
//			}
//			System.err.println(e);
//		}
// 
//		// 检查是否是软链接
//		boolean link_isSymbolicLink_1 = Files.isSymbolicLink(link);
//		boolean target_isSymbolicLink_1 = Files.isSymbolicLink(target);
//		System.out.println(link.toString() + " is a symbolic link ? "
//				+ link_isSymbolicLink_1);
//		System.out.println(target.toString() + " is a symbolic link ? "
//				+ target_isSymbolicLink_1);
// 
//		try {
//			boolean link_isSymbolicLink_2 = (boolean) Files.getAttribute(link,
//					"basic:isSymbolicLink");
//			boolean target_isSymbolicLink_2 = (boolean) Files.getAttribute(
//					target, "basic:isSymbolicLink");
//			System.out.println(link.toString() + " is a symbolic link ? "
//					+ link_isSymbolicLink_2);
//			System.out.println(target.toString() + " is a symbolic link ? "
//					+ target_isSymbolicLink_2);
//		} catch (IOException | UnsupportedOperationException e) {
//			System.err.println(e);
//		}
// 
//		//读取软链接对应的文件
//		try {
//			Path linkedpath = Files.readSymbolicLink(link);
//			//Path linkedpath2 = Files.readSymbolicLink("");
//			System.out.println(linkedpath.toString());
//		} catch (IOException e) {
//			System.err.println(e);
//		}
// 
//		// 创建硬链接
//		try {
//			Files.createLink(link, target);
//			System.out.println("The link was successfully created!");
//		} catch (IOException | UnsupportedOperationException
//				| SecurityException e) {
//			if (e instanceof SecurityException) {
//				System.err.println("Permission denied!");
//			}
//			if (e instanceof UnsupportedOperationException) {
//				System.err.println("An unsupported operation was detected!");
//			}
//			if (e instanceof IOException) {
//				System.err.println("An I/O error occured!");
//			}
//			System.err.println(e);
//		}
//	}
	
	/**
	 * 获取接口数据,返回json格式字符串,方法2
	 * @param url 接口路径
	 * @param params 传递参数,自定义
	 * @param key 传递参数标识,自定义
	 * @return
	 */
	public static String getData2(String url,String params,String key){

	        HttpURLConnection conn = null;
	        BufferedReader reader = null;
	        String rs = null;
	        try {
	            //拼接参数，转义参数
	            //String connUrl = url+"?params="+URLEncoder.encode(params,HTTP.UTF-8)+"&key="+key;
	        	URL urlConnection = new URL(url);
	        	urlConnection.openConnection();
	            //创建连接
	            //URL url = new URL(connUrl); 
	            conn = (HttpURLConnection) urlConnection.openConnection();
	            conn.setUseCaches(false);
	            conn.setConnectTimeout(30000);
	            conn.setReadTimeout(30000);
	            conn.setInstanceFollowRedirects(false);
	            conn.connect();

	            //获取并解析数据
	            InputStream is = conn.getInputStream();
	            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	            StringBuffer sb = new StringBuffer();
	            String strRead = null;
	            while ((strRead = reader.readLine()) != null) {
	                sb.append(strRead);
	            }
	            rs = sb.toString();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            if (conn != null) {
	                conn.disconnect();
	            }
	        }
	        return rs;
	}

	public static void main(String [] args)
    {
		////getData2("https://blog.csdn.net/alan_liuyue/article/details/78982905","","");	
        try {
            URL url = new URL("http://124.205.255.18:28500/pdf/d/fec472c3a4d7c3b8");
            URLConnection uconn = url.openConnection();
            uconn.setDoOutput(true);
            uconn.connect();

            String temp;
            final StringBuffer sb=new StringBuffer();
            //注意编码格式 ，查看网页源码中charset的值 
            final BufferedReader in=new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
            while ((temp = in.readLine()) != null) {
                sb.append("\n");
                sb.append(temp);
            }
            in.close();
            System.out.println(sb);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出错啦！");
        }
    }
}
