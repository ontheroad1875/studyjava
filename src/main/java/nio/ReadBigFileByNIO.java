package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * 
 * <p>
 * 描述：使用MappedByteBuffer读取大文件(1G以上)和释放MappedByteBuffer的资源
 * <p>
 * @author huluy
 * @date 2018年7月29日
 */
public class ReadBigFileByNIO {
	
	public static void main(String[] args) {
		ByteBuffer byteBuf = ByteBuffer.allocate(1024 * 14 * 1024);  
    	System.out.println("#############" + byteBuf.array().length);
		//MappedByteBufferTest();
	}
	
	public static void MappedByteBufferTest() {
	    try {
	        RandomAccessFile source = new RandomAccessFile("F:\\电影\\[阳光电影www.ygdy8.com].催眠大师.HD.720p.国语中字.rmvb", "r");
	        RandomAccessFile target = new RandomAccessFile("D:\\Pdffile\\122.rmvb", "rw");
	        FileChannel in = source.getChannel();
	        FileChannel out = target.getChannel();
	        long size = in.size();
	        MappedByteBuffer mbbi = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
	        MappedByteBuffer mbbo = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
	        long start = System.currentTimeMillis();
	        for (int i = 0; i < size; i++) {
	            byte b = mbbi.get(i);
	            mbbo.put(i, b);
	        }
	        source.close();
	        target.close();
	        System.out.println("Spend: " + (System.currentTimeMillis() - start) + "ms");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	   
    /**
     * MappedByteBuffer的确快，但也存在一些问题，主要就是内存占用和文件关闭等不确定问题。
     * 被MappedByteBuffer打开的文件只有在垃圾收集时才会被关闭，而这个点是不确定的
     */
//	 public static void clean(final Object buffer) throws Exception {
//         AccessController.doPrivileged(new PrivilegedAction() {
//             public Object run() {
//             try {
//                Method getCleanerMethod = buffer.getClass().getMethod("cleaner",new Class[0]);
//                getCleanerMethod.setAccessible(true);
//                sun.misc.Cleaner cleaner =(sun.misc.Cleaner)getCleanerMethod.invoke(buffer,new Object[0]);
//                cleaner.clean();
//             } catch(Exception e) {
//                e.printStackTrace();
//             }
//                return null;}});
//         
//	 }
	 
	 public static void copyFileAndRemoveResource()  {
		 
			/*
			 * 需求:拷贝一个文件，在拷贝完成之后将源文件删除 使用MappedByteBuffer 进行操作
			 * 但是MappedByteBuffer和它和他相关联的资源 在垃圾回收之前一直保持有效 但是MappedByteBuffer
			 * 保存着对源文件的引用 ，因此删除源文件失败
			 */
	 
			File source = null;
			File dest = null;
			MappedByteBuffer buf = null;
			try {
				source = new File("D:\\eee.txt");
				dest = new File("C:\\eee.txt");
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			try (FileChannel in = new FileInputStream(source).getChannel();
					FileChannel out = new FileOutputStream(dest).getChannel();) {
				
				long size = in.size();
				buf = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
				out.write(buf);
				buf.force();// 将此缓冲区所做的内容更改强制写入包含映射文件的存储设备中。
				System.out.println("文件复制完成!");
				// System.gc();
				// 同时关闭文件通道和释放MappedByteBuffer才能成功
				in.close();//如果在关闭之前抛异常也不怕，因为使用了try-with-resource
				// 强制释放MappedByteBuffer资源
				clean(buf);
	 
				// 文件复制完成后，删除源文件
				/*
				 * source.delete() 删除用此抽象路径名所表示的文件或目录，如果该路径表示的是一个目录 则该目录必须为空文件夹才可以删除
				 * 注意：使用java.nio.file.Files的delete方法能告诉你为什么会删除失败
				 * 所以尽量使用Files.delete(Paths.get(pathName));来替代File对象的delete
				 * System.out.println(source.delete() == true ? "删除成功！" : "删除失败！");
				 */
				Files.delete(Paths.get("D:\\eee.txt"));
				System.out.println("删除成功!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
		/*
		 * 其实讲到这里该问题的解决办法已然清晰明了了——就是在删除索引文件的同时还取消对应的内存映射，删除mapped对象。
		 * 不过令人遗憾的是，Java并没有特别好的解决方案——令人有些惊讶的是，Java没有为MappedByteBuffer提供unmap的方法，
		 * 该方法甚至要等到Java 10才会被引入 ,DirectByteBufferR类是不是一个公有类
		 * class DirectByteBufferR extends DirectByteBuffer implements DirectBuffer 使用默认访问修饰符
		 * 不过Java倒是提供了内部的“临时”解决方案——DirectByteBufferR.cleaner().clean() 切记这只是临时方法，
		 * 毕竟该类在Java9中就正式被隐藏了，而且也不是所有JVM厂商都有这个类。
		 * 还有一个解决办法就是显式调用System.gc()，让gc赶在cache失效前就进行回收。
		 * 不过坦率地说，这个方法弊端更多：首先显式调用GC是强烈不被推荐使用的，
		 * 其次很多生产环境甚至禁用了显式GC调用，所以这个办法最终没有被当做这个bug的解决方案。
		 */
	 @SuppressWarnings("restriction")
		public static void clean(final MappedByteBuffer buffer) throws Exception {
			if (buffer == null) {
				return;
			}
			buffer.force();
			AccessController.doPrivileged(new PrivilegedAction<Object>() {//Privileged特权
				@Override
				public Object run() {
					try {
						// System.out.println(buffer.getClass().getName());
						Method getCleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);
						getCleanerMethod.setAccessible(true);
						sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(buffer, new Object[0]);
						cleaner.clean();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			});
			/*
			 * 
			 * 在MyEclipse中编写Java代码时，用到了Cleaner，import sun.misc.Cleaner;可是Eclipse提示：
			 * Access restriction: The type Cleaner is not accessible due to
			 * restriction on required library *\rt.jar Access restriction : The
			 * constructor Cleaner() is not accessible due to restriction on
			 * required library *\rt.jar
			 * 
			 * 解决方案1（推荐）： 只需要在project build path中先移除JRE System Library，再添加库JRE
			 * System Library，重新编译后就一切正常了。 解决方案2： Windows -> Preferences -> Java ->
			 * Compiler -> Errors/Warnings -> Deprecated and trstricted API ->
			 * Forbidden reference (access rules): -> change to warning
			 */
		}

}	
