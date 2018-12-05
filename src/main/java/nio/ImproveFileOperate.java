package nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
 
/**
 * <b>文件读取类</b><br />
 * 1、按字节读取文件内容<br />
 * 2、按字符读取文件内容<br />
 * 3、按行读取文件内容<br />
 * 
 * @author qin_xijuan
 * 
 */
public class ImproveFileOperate {
 
    private static final String FILE_PATH = "d:/work/jipinwodi.txt";
 
    /**
     * 以字节为单位读写文件内容
     * 
     * @param filePath
     *            ：需要读取的文件路径
     */
    public static void readFileByByte(String filePath) {
        File file = new File(filePath);
        // InputStream:此抽象类是表示字节输入流的所有类的超类。
        InputStream ins = null;
        OutputStream outs = null;
        try {
            // FileInputStream:从文件系统中的某个文件中获得输入字节。
            ins = new FileInputStream(file);
            outs = new FileOutputStream("d:/work/readFileByByte.txt");
            int temp;
            // read():从输入流中读取数据的下一个字节。
            while ((temp = ins.read()) != -1) {
                outs.write(temp);
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (ins != null && outs != null) {
                try {
                    outs.close();
                    ins.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }
 
    /**
     * 以字符为单位读写文件内容
     * 
     * @param filePath
     */
    public static void readFileByCharacter(String filePath) {
        File file = new File(filePath);
        // FileReader:用来读取字符文件的便捷类。
        FileReader reader = null;
        FileWriter writer = null;
        try {
            reader = new FileReader(file);
            writer = new FileWriter("d:/work/readFileByCharacter.txt");
            int temp;
            while ((temp = reader.read()) != -1) {
                writer.write((char)temp);
            }
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            if (reader != null && writer != null) {
                try {
                    reader.close();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
 
    /**
     * 以行为单位读写文件内容
     * 
     * @param filePath
     */
    public static void readFileByLine(String filePath) {
        File file = new File(filePath);
        // BufferedReader:从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。
        BufferedReader bufReader = null;
        BufferedWriter bufWriter = null;
        try {
            // FileReader:用来读取字符文件的便捷类。
            bufReader = new BufferedReader(new FileReader(file));
            bufWriter = new BufferedWriter(new FileWriter("d:/work/readFileByLine.txt"));
            // buf = new BufferedReader(new InputStreamReader(new
            // FileInputStream(file)));
            String temp = null;
            while ((temp = bufReader.readLine()) != null) {
                bufWriter.write(temp+"\n");
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (bufReader != null && bufWriter != null) {
                try {
                    bufReader.close();
                    bufWriter.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }
 
    /**
     * 使用Java.nio ByteBuffer字节将一个文件输出至另一文件
     * 
     * @param filePath
     */
    public static void readFileByBybeBuffer(String filePath) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            // 获取源文件和目标文件的输入输出流  
            in = new FileInputStream(filePath);
            out = new FileOutputStream("d:/work/readFileByBybeBuffer.txt");
            // 获取输入输出通道
            FileChannel fcIn = in.getChannel();
            FileChannel fcOut = out.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                // clear方法重设缓冲区，使它可以接受读入的数据
                buffer.clear();
                // 从输入通道中将数据读到缓冲区
                int r = fcIn.read(buffer);
                if (r == -1) {
                    break;
                }
                // flip方法让缓冲区可以将新读入的数据写入另一个通道  
                buffer.flip();
                fcOut.write(buffer);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null && out != null) {
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
	 * 
	 * @param fromFile 被复制的文件
	 * @param toFile 复制的目录文件
	 * @param rewrite 是否重新创建文件
	 * 
	 * <p>文件的复制操作方法
	 */
	public static void copyfile(File fromFile, File toFile,Boolean rewrite ){
		
		if(!fromFile.exists()){
			return;
		}
		
		if(!fromFile.isFile()){
			return;
		}
		if(!fromFile.canRead()){
			return;
		}
		if(!toFile.getParentFile().exists()){
			toFile.getParentFile().mkdirs();
		}
		if(toFile.exists() && rewrite){
			toFile.delete();
		}
		
		
		try {
			FileInputStream fosfrom = new FileInputStream(fromFile);
			FileOutputStream fosto = new FileOutputStream(toFile);
			
			byte[] bt = new byte[1024];
			int c;
			while((c=fosfrom.read(bt)) > 0){
				fosto.write(bt,0,c);
			}
			//关闭输入、输出流
			fosfrom.close();
			fosto.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//不考虑多线程优化，单线程文件复制最快的方法是(文件越大该方法越有优势，一般比常用方法快30+%):
	@SuppressWarnings("unused")
	private static void nioTransferCopy(File source, File target) {  
	    FileChannel in = null;  
	    FileChannel out = null;  
	    FileInputStream inStream = null;  
	    FileOutputStream outStream = null;  
	    try {  
	        inStream = new FileInputStream(source);  
	        outStream = new FileOutputStream(target);  
	        in = inStream.getChannel();  
	        out = outStream.getChannel();  
	        in.transferTo(0, in.size(), out);  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        //close(inStream);  
	        //close(in);  
	        //close(outStream);  
	        //close(out);  
	    }  
	} 
	
	@SuppressWarnings("unused")
	private static void nioBufferCopy(File source, File target) {  
	    FileChannel in = null;  
	    FileChannel out = null;  
	    FileInputStream inStream = null;  
	    FileOutputStream outStream = null;  
	    try {  
	        inStream = new FileInputStream(source);  
	        outStream = new FileOutputStream(target);  
	        in = inStream.getChannel();  
	        out = outStream.getChannel();  
	        ByteBuffer buffer = ByteBuffer.allocate(4096);  
	        while (in.read(buffer) != -1) {  
	        	//通过通道读写交叉进行。  
	            buffer.flip();  
	            out.write(buffer);  
	            buffer.clear();  
	        }  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        //close(inStream);  
	        //close(in);  
	        //close(outStream);  
	        //close(out);  
	    }  
	}  
	
	@SuppressWarnings("unused")
	private static void customBufferBufferedStreamCopy(File source, File target) {  
	    InputStream fis = null;  
	    OutputStream fos = null;  
	    try {  
	        fis = new BufferedInputStream(new FileInputStream(source));  
	        fos = new BufferedOutputStream(new FileOutputStream(target));  
	        byte[] buf = new byte[4096];  
	        int i;  
	        while ((i = fis.read(buf)) != -1) {  
	            fos.write(buf, 0, i);  
	        }  
	    }  
	    catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	        //close(fis);  
	        //close(fos);  
	    }  
	} 

	
    public static long getTime(){
        return System.currentTimeMillis();
    }
 
    public static void main(String args[]) {
        long time1 = getTime() ;
        // readFileByByte(FILE_PATH);// 8734,8281,8000,7781,8047
        // readFileByCharacter(FILE_PATH);// 734, 437, 437, 438, 422
        // readFileByLine(FILE_PATH);// 110, 94,  94,  110, 93
        readFileByBybeBuffer(FILE_PATH);// 125, 78,  62,  78, 62
        long time2 = getTime() ;
        System.out.println(time2-time1);
    }
}
