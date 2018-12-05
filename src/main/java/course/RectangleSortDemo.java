package course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @Title RectangleSortDemo
 * <p>
 * 描述：现在有很多长方形，每一个长方形都有一个编号，这个编号可以重复；还知道这个长方形的宽和长，编号、长、宽都是整数
 * 现在要求按照一下方式排序（默认排序规则都是从小到大）:
 * 1.按照编号从小到大排序
 * 2.对于编号相等的长方形，按照长方形的长排序；
 * 3.如果编号和长都相同，按照长方形的宽排序；
 * 4.如果编号、长、宽都相同，就只保留一个长方形用于排序,删除多余的长方形；最后排好序按照指定格式显示所有的长方形；
 * <p>
 * @author huluy
 * @date 2018年10月10日 上午10:28:08
 * @Version
 */
public class RectangleSortDemo {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.out.println("请输入：");
		Scanner scanner = new Scanner(System.in);
		String testNum = scanner.nextLine();
		String rectangleNum = scanner.nextLine();
		Set<Rectangle> dataSet = new HashSet<Rectangle>();
		//String dataNextLine = scanner.nextLine();
		while (scanner.hasNextLine()) {
			//System.out.println("dataHashNext:" + scanner.hasNextLine());
			String rectangleData = scanner.nextLine();
			//System.out.println("rectangleData:" + rectangleData);
			if (rectangleData.trim().length() == 0) break;
			String[] data = rectangleData.split(" ");
			if (data.length != 3) continue;
			Rectangle rectangle = new Rectangle();
			Integer number = Integer.parseInt(data[0]);
			Integer data1 = Integer.parseInt(data[1]);
			Integer data2 = Integer.parseInt(data[2]);
			rectangle.setNumber(number);
			rectangle.setLength(data1 > data2 ? data1 : data2);
			rectangle.setWidth(data1 > data2 ? data2 : data1);
			dataSet.add(rectangle);
		}
		List<Rectangle> dataList = new ArrayList<Rectangle>(dataSet);
		Collections.sort(dataList);
		System.out.println("测试数据有：" + testNum + "组，长方形个数为：" + rectangleNum);
		//System.out.println("三角形排序为：" + dataList);
		printList(dataList);
	}
	
	public static void printList(List<Rectangle> list) {
		for (Rectangle rectangle : list) {
			System.out.println(rectangle);
		}
	}
	
	static class Rectangle implements Comparable<Rectangle>{
		private Integer number;
		private Integer length;
		private Integer width;
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}
		public Integer getLength() {
			return length;
		}
		public void setLength(Integer length) {
			this.length = length;
		}
		public Integer getWidth() {
			return width;
		}
		public void setWidth(Integer width) {
			this.width = width;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((length == null) ? 0 : length.hashCode());
			result = prime * result + ((number == null) ? 0 : number.hashCode());
			result = prime * result + ((width == null) ? 0 : width.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Rectangle other = (Rectangle) obj;
			if (length == null) {
				if (other.length != null)
					return false;
			} else if (!length.equals(other.length))
				return false;
			if (number == null) {
				if (other.number != null)
					return false;
			} else if (!number.equals(other.number))
				return false;
			if (width == null) {
				if (other.width != null)
					return false;
			} else if (!width.equals(other.width))
				return false;
			return true;
		}
		@Override
		public int compareTo(Rectangle o) {
			if (this.number - o.number != 0) return this.number - o.number;
			if (this.length - o.length != 0) return this.length - o.number;	
			return this.width - o.width;
		}
		@Override
		public String toString() {
			return "编号为：" + this.number + ", 长度为：" + this.length + ", 宽度为：" + this.width;
		}
		
	}
}
