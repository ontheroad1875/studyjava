package compressFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.xerial.snappy.Snappy;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

/**
 * 
 * @Title SnappyDemo
 * <p>描述：Snappy是Google开源的压缩/解压缩库。和其他压缩库相比，snappy的压缩率并不是最高的，兼容性也并非最好的。
 * 相反，它的诞生旨在以极高的压缩/解压缩速率提供合理的压缩率。<p>
 * @author huluy
 * @date 2018年11月20日 下午3:42:59
 * @Version
 */
public class SnappyDemo {
	
	public static void main(String[] args) throws IOException, Exception {
		//compressString("Hello snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser.");
		//compressFile("D:\\Pdffile\\EDAS开发者指南.pdf");
		uncompressFile("D:\\study_workspace\\study\\.\\EDAS开发者指南.pdf.zip");
		String input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser.";
		{ 
		    byte[] compressed = Snappy.compress(input.getBytes("UTF-8"));
		    byte[] uncompressed = Snappy.uncompress(compressed);
		    String result = new String(uncompressed, "UTF-8");
		    System.out.println(result);
		}

		{
		    byte[] compressed = Snappy.compress(input);
		    System.out.println(Snappy.uncompressString(compressed));
		}

		{
		    double [] arr = new double[]{123.456,234.567,345.678};
		    byte[] compressed = Snappy.compress(arr);
		    double [] unarr = Snappy.uncompressDoubleArray(compressed);
		    System.out.println(Arrays.toString(unarr));
		}

		
	}
	
	public static void compressString(String input) throws Exception, IOException {
		byte[] compressed = Snappy.compress(input.getBytes("UTF-8"));
	    byte[] uncompressed = Snappy.uncompress(compressed);
	    String result = new String(uncompressed, "UTF-8");
	    System.out.println(result);
	}
	
	public static void compressFile(String filePath) {
		File file = new File(filePath); //待压缩文件
		File out = new File("./", file.getName() + ".zip"); //压缩结果文件
		System.out.println(out.getAbsolutePath());
		byte[] buffer = new byte[1024 * 1024 * 8];
		FileInputStream  fi = null;
		FileOutputStream fo = null;
		SnappyOutputStream sout = null;
		try
		{
		    fi = new FileInputStream(file); 
		    fo = new FileOutputStream(out);
		    sout = new SnappyOutputStream(fo);
		    while(true)
		    {
		        int count = fi.read(buffer, 0, buffer.length);
		        if(count == -1) { break; }
		        sout.write(buffer, 0, count);
		    }
		    sout.flush();
		}
		catch(Throwable ex)
		{
		    ex.printStackTrace();
		}
		finally 
		{
		    if(sout != null) {try { sout.close();} catch (Exception e) {}}
		    if(fi != null) { try { fi.close(); } catch(Exception x) {} }
		    if(fo != null) { try { fo.close(); } catch(Exception x) {} }
		}
	}
	
	public static void uncompressFile(String filePath) {
		File file = new File("D:\\study_workspace\\study\\.\\EDAS开发者指南.pdf.zip"); //待解压文件
		File out = new File("D:\\study_workspace\\study\\snappyfile.pdf");  //解压后文件

		byte[] buffer = new byte[1024 * 1024 * 8];
		FileInputStream  fi = null;
		FileOutputStream fo = null;
		SnappyInputStream sin = null;
		try
		{
		    fo = new FileOutputStream(out);
		    fi = new FileInputStream(file.getPath());
		    sin = new SnappyInputStream(fi);        

		    while(true)
		    {
		        int count = sin.read(buffer, 0, buffer.length);
		        if(count == -1) { break; }
		        fo.write(buffer, 0, count);
		    }
		    fo.flush();
		}
		catch(Throwable ex)
		{
		    ex.printStackTrace();
		}
		finally 
		{
		    if(sin != null) { try { sin.close(); } catch(Exception x) {} }
		    if(fi != null) { try { fi.close(); } catch(Exception x) {} }
		    if(fo != null) { try { fo.close(); } catch(Exception x) {} }
		}
	}
}
