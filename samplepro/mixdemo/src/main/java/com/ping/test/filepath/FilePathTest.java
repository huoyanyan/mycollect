package com.ping.test.filepath;

import java.io.File;
import java.io.IOException;
import java.net.URL;
/**
 * https://www.cnblogs.com/jsStudyjj/p/5386980.html
 * @author zhangfei
 *
 */
public class FilePathTest {

	public static void main(String[] args) throws IOException {
		FilePathTest t = new FilePathTest();
		t.getClassFilePath();
	}

	public void getClassFilePath() throws IOException {
		// 获取当前类的所在工程路径;
		// 如果不加“/”
		File f = new File(this.getClass().getResource("").getPath());
		System.out.println("获取当前类的所在工程路径----" + f);
		
		File f1 = new File(this.getClass().getResource("/").getPath());
		System.out.println("获取当前类的所在工程的classes路径----" + f1);

		// 获取当前类的绝对路径；
		File directory = new File("");// 参数为空
		String courseFile = directory.getCanonicalPath();
		System.out.println("获取当前类的项目根路径----" + courseFile);

		// 获取当前类的所在工程路径;
		// 第三种：
		URL xmlpath = this.getClass().getClassLoader().getResource("");
		System.out.println("获取当前类的所在工程路径classes绝对路径第三种----" + xmlpath);
		
		URL txtpath = this.getClass().getClassLoader().getResource("test.txt");
		System.out.println("获取当前类的所在工程路径第三2种----" + txtpath);

		// 获取当前工程src目录下selected.txt文件的路径
		// 第四种：
		System.out.println("获取当前类的所在工程路径第四种----" + System.getProperty("user.dir"));

		// 获取当前工程路径第五种：
		System.out.println("获取当前工程classpath路径及所有jar包----" + System.getProperty("java.class.path"));
	}
	
	

}
