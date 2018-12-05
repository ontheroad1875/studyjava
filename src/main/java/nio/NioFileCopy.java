package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileCopy {
	private RandomAccessFile aFile = null;
	private FileChannel inChannel = null;
	private final ByteBuffer buf = ByteBuffer.allocate(1024);
	
	public void doWrite() throws IOException {
		aFile = new RandomAccessFile("D:/goods.txt", "rw");
		inChannel = aFile.getChannel();
		String newData = "New String to wirte to file... " + System.currentTimeMillis();
		buf.clear();
		buf.put(newData.getBytes());
		
		buf.flip();
		
		while (buf.hasRemaining()) 
			inChannel.write(buf);
		
		inChannel.close();
		System.out.println("Write Over");
	}
	
	public void doRead() throws IOException {
		aFile = new RandomAccessFile("D:/goods.txt", "rw");
		inChannel = aFile.getChannel();
		
		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {
			System.out.println("Read " + bytesRead);
			buf.flip();
			while (buf.hasRemaining())
				System.out.print((char) buf.get());
			
			buf.clear();
			bytesRead = inChannel.read(buf);
		}
		
		aFile.close();
	}
	
	@SuppressWarnings("resource")
	public void doCopy() throws IOException {
		aFile = new RandomAccessFile("D:/goods.txt", "rw");
		inChannel = aFile.getChannel();
		RandomAccessFile bFile = new RandomAccessFile("D:/22.log", "rw");
		FileChannel outChannel = bFile.getChannel();
		inChannel.transferTo(0, inChannel.size(), outChannel);
		System.out.println("Copy over");
	}
	
	public static void main(String[] args) throws IOException {
		NioFileCopy tool = new NioFileCopy();
		tool.doWrite();
		//tool.doRead();
		tool.doCopy();
	}
}
