package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @Title ZipFile
 *        <p>
 *        描述：压缩单个文件
 *        <p>
 * @author huluy
 * @date 2018年10月20日 下午9:21:21
 * @Version
 */
public class ZipFile {

	public static void main(String[] args) {
		String filePath = "D:\\Pdffile\\Effective+Java+第二版+中文版34.pdf";
		zipFile(filePath);
	}

	/**
	 * @Title zipFile
	 * @Description 压缩单个文件
	 * @param filePath
	 * @return void
	 */
	public static void zipFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			String zipFileName = file.getName().concat(".zip");
			System.out.println("#######zipFileName:" + zipFileName);
			try (FileOutputStream fos = new FileOutputStream(zipFileName);
					ZipOutputStream zos = new ZipOutputStream(fos)) {
				zos.putNextEntry(new ZipEntry(file.getName()));
				byte[] bytes = Files.readAllBytes(Paths.get(filePath));
				zos.write(bytes);
				zos.closeEntry();
				zos.close();
				System.out.println("#############end############");
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("I/O error: " + ex);
			}
		} else {
			System.out.println("该路径下文件不存在，请核对后在重新输入");
		}
	}

	/**
	 * @Title zipFiles
	 * @Description 压缩多个文件
	 * @param filePaths
	 * @return void
	 */
	public static void zipFiles(String... filePaths) {
		try {
			File firstFile = new File(filePaths[0]);
			String zipFileName = firstFile.getName().concat(".zip");

			FileOutputStream fos = new FileOutputStream(zipFileName);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (String aFile : filePaths) {
				zos.putNextEntry(new ZipEntry(new File(aFile).getName()));

				byte[] bytes = Files.readAllBytes(Paths.get(aFile));
				zos.write(bytes, 0, bytes.length);
				zos.closeEntry();
			}

			zos.close();

		} catch (FileNotFoundException ex) {
			System.err.println("A file does not exist: " + ex);
		} catch (IOException ex) {
			System.err.println("I/O error: " + ex);
		}
	}

	
}
