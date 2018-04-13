package com.ping.test.juniversalchardet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.mozilla.universalchardet.UniversalDetector;

public class JuniversalchardetTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		byte[] buf = new byte[4096];
		// String fileName = args[0];
		String fileName = JuniversalchardetTest.class.getResource("").getPath() + "test.properties";
		java.io.FileInputStream fis = new java.io.FileInputStream(fileName);

		// (1)
		UniversalDetector detector = new UniversalDetector(null);

		// (2)
		int nread;
		while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}
		// (3)
		detector.dataEnd();

		// (4)
		String encoding = detector.getDetectedCharset();
		if (encoding != null) {
			System.out.println("Detected encoding = " + encoding);
		} else {
			System.out.println("No encoding detected.");
		}

		// (5)
		detector.reset();

		String cread;
		StringBuffer content = new StringBuffer();

		InputStreamReader r = new InputStreamReader(new FileInputStream(fileName), encoding);
		BufferedReader in = new BufferedReader(r);
		while ((cread = in.readLine()) != null) {
			content.append(cread);
		}
		System.out.print(content.toString());
	}

}
