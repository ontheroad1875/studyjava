package nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.logging.Logger;

//import com.itextpdf.text.Document;
//import com.itextpdf.text.pdf.PdfStream;
//import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * <p>
 * 描述：长用的文件操作工具类，包含一些基本的文件的基本操作方式 如果使用IOUtils该工具类中，包含了部分封装好的方法：获取流InputStream in
 * = getStreamBody(exchange); 1：流转字节：byte[] bytes =
 * IOUtils.toByteArray(inputStream); 2：字节转流：ByteArrayInputStream in = new
 * ByteArrayInputStream(bytes);
 * 
 * 1：流转String：IOUtils.toString(uri/read/in/bytes);
 * 2：String转流：IOUtils.toInputStream(String);
 * <p>
 * 
 * @author huluy
 * @date 2018年8月1日
 */
public class FileOperateUtils {
	private static final Logger logger = Logger.getLogger("FileOperateUtils");
	private static final int BUFFER_SIZE = 1024 * 5;

	/*
	 * 获取文件编码格式
	 * 
	 * @param fileName
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static String getFileEncoding(String fileName) throws Exception {
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
		int p = (bin.read() << 8) + bin.read();
		String code = null;
		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "Unicode";
			break;
		case 0xfeff:
			code = "UTF-16BE";
			break;
		default:
			code = "GBK";
		}

		return code;
	}

	/**
	 * 拷贝文件到指定的路径 (使用了自定义的CopyFileUtil工具类)
	 * 
	 * @param fileURL
	 * @param targetFile
	 */
	public void copy(String fileURL, String targetFile) {
		URLConnection urlconn = null;
		try {
			URL url = new URL(fileURL);
			urlconn = url.openConnection();
			InputStream in = urlconn.getInputStream();

			File newfile = new File(targetFile);
			FileOutputStream fos = new FileOutputStream(newfile);
			CopyFileUtils.copy(in, fos);// 使用了自定义的FileUtil工具类
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		logger.info("#################复制文件开始#################");
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);
		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);
		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();
		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
		logger.info("#################复制文件结束#################");
	}

	/**
	 * 复制文件夹
	 * 
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				if (sourceFile.getName().indexOf(".vax") < 0)
					copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}

	/**
	 * 得到文件的扩展名
	 * 
	 * @param f
	 * @return
	 */
	public static String getFileExtension(File f) {
		if (f != null) {
			String filename = f.getName();
			int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				return filename.substring(i + 1).toLowerCase();
			}
		}
		return null;
	}

	/**
	 * 得到文件名(排除文件扩展名)
	 * 
	 * @param f
	 * @return
	 */
	public static String getFileNameWithoutExt(File f) {
		if (f != null) {
			String filename = f.getName();
			int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				return filename.substring(0, i);
			}
		}
		return null;
	}

	/**
	 * 改变文件的扩展名
	 * 
	 * @param fileNM
	 * @param ext
	 * @return
	 */
	public static String changeFileExt(String fileNM, String ext) {
		int i = fileNM.lastIndexOf('.');
		if (i >= 0)
			return (fileNM.substring(0, i) + ext);
		else
			return fileNM;
	}

	/**
	 * 得到文件的全路径
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameWithFullPath(String filePath) {
		int i = filePath.lastIndexOf('/');
		int j = filePath.lastIndexOf("\\");
		int k;
		if (i >= j) {
			k = i;
		} else {
			k = j;
		}
		int n = filePath.lastIndexOf('.');
		if (n > 0) {
			return filePath.substring(k + 1, n);
		} else {
			return filePath.substring(k + 1);
		}
	}

	/**
	 * 判断目录是否存在
	 * 
	 * @param strDir
	 * @return
	 */
	public static boolean existsDirectory(String strDir) {
		File file = new File(strDir);
		return file.exists() && file.isDirectory();
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param strDir
	 * @return
	 */
	public static boolean existsFile(String strDir) {
		File file = new File(strDir);
		return file.exists();
	}

	/**
	 * 强制创建目录
	 * 
	 * @param strDir
	 * @return
	 */
	public static boolean forceDirectory(String strDir) {
		File file = new File(strDir);
		file.mkdirs();
		return existsDirectory(strDir);
	}

	/**
	 * 得到文件的大小
	 * 
	 * @param fileName
	 * @return
	 */
	public static int getFileSize(String fileName) {

		File file = new File(fileName);
		FileInputStream fis = null;
		int size = 0;
		try {
			fis = new FileInputStream(file);
			size = fis.available();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 流编码-加密
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] encrypt(InputStream in) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[BUFFER_SIZE];
		int r;
		while ((r = in.read(buffer)) != -1) {
			bos.write(buffer, 0, r);
			bos.flush();
		}
		byte[] bytes = bos.toByteArray();
		bos.close();
		in.close();
		return Base64.getEncoder().encode(bytes);
	}

	/**
	 * 流解码-解密
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] decrypt(InputStream in) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[BUFFER_SIZE];
		int r;
		while ((r = in.read(buffer)) != -1) {
			bos.write(buffer, 0, r);
			bos.flush();
		}
		byte[] bytes = bos.toByteArray();
		bos.close();
		in.close();
		return Base64.getDecoder().decode(bytes);
	}

	/**
	 * copy文件
	 */
	public static void rwFile(InputStream in) {
		FileWriter fw = null;
		BufferedReader br = null;
		try {
			fw = new FileWriter("A:\\text.txt", true);
			br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				fw.write(line);
				fw.flush();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	    * 流转成PDF
	    */
//	 public static void getPDF(InputStream in){
//	        try {
//	        	File file = new File("D:\\Pdffile\\fx.pdf");
//	        	if (!file.exists()) {
//	        		file.createNewFile();
//	        	}
//	            FileOutputStream fo = new FileOutputStream(file);
//	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	            for(int i;(i=in.read()) != -1; ){
//	                baos.write(i);
//	            }
//	            baos.flush();
//	            Document doc = new Document();
//	            PdfStream pdfStream=new PdfStream(baos.toByteArray());
//	            PdfWriter pw =PdfWriter.getInstance(doc, fo);
//	            pdfStream.toPdf(pw,fo);
//	            logger.info("doc内容.doc={" + doc.newPage() + "}");
//	            pw.flush();
//	            baos.close();
//	            pw.close();
//	            fo.close();
//	            in.close();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        } 
//	    }
//	 public static void main(String[] args) throws IOException{
//		 FileInputStream in = new FileInputStream(new File("D:\\Pdffile\\ceshi.txt"));
//		 logger.info("###########################生成pdf文件完成#####################");
//	}
	
	/*
	 *  读取超大文件网上的文章基本分为两大类，一类是使用BufferedReader类读写超大文件；
	 *  另一类是使用RandomAccessFile类读取，经过比较，最后使用了前一种方式进行超大文件的读取
	 */
	public void largeFileIO(String inputFile, String outputFile) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(inputFile)));
            BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);//10M缓存
            FileWriter fw = new FileWriter(outputFile);
            while (in.ready()) {
                String line = in.readLine();
                fw.append(line + " ");
            }
            in.close();
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
}
