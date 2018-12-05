package nio;

import java.nio.ByteBuffer;

/**
 * 
 * <p>
 * 描述：NIO 部分操作，在NIO中，Buffer是用来缓冲存储临时数据的，可以理解为I/O操作中数据的中转站，缓冲区为通道Channel，写入数据到通道，或者从通道中读取数据，这样的操作利用缓冲区
 * 来传递就可以达到对数据的高效处理，在NIO中主要有八种缓冲区类型（其中MappedByteBuffer是专门用来用于内存映射的一种ByteBuffer）
 * 常见的八种缓冲区类型为：ByteBuffer(MappedByteBuffer),CharBuffer,ShortBuffer,IntBuffer,LongBuffer,DoubleBuffer,FloatBuffer
 * 缓冲区属性为：capacity, limit, position,mark，四种属性：mark <= position <= limit <= capacity
 * capacity:容量，即可以容纳的最大的容量，在缓冲区创建时被设定并且不能够改变；
 * limit：表示缓冲区当前的终点位置，不能对缓冲区超过极限的位置进行读写操作。且极限是可以修改；
 * Position：位置，下一个要被读或写的元素的索引，每次读写缓冲区数据时都会改变改值，为下次读写做准备；
 * mark：标记，调用mark()来设置mark=position，再调用reset()可以让position恢复到标记的位置
 * 
 * ByteBuffer类提供了4个静态工厂方法来获得ByteBuffer的实例：但是提供了四种抽象方法：allocate，allocateDirect，wrap，wrap
 * <p>
 * @author huluy
 * @date 2018年8月1日
 */
public class NIOFileOperateDemo {
	public static void main(String[] args) {
		System.out.println("----------Test allocate--------");  
	    System.out.println("before alocate:"  
	            + Runtime.getRuntime().freeMemory());  
	      
	    // 如果分配的内存过小，调用Runtime.getRuntime().freeMemory()大小不会变化？  
	    // 要超过多少内存大小JVM才能感觉到？  
	    ByteBuffer buffer = ByteBuffer.allocate(102400);  
	    System.out.println("buffer = " + buffer);  
	      
	    System.out.println("after alocate:"  
	            + Runtime.getRuntime().freeMemory());  
	}
}
