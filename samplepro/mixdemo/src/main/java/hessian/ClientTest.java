package hessian;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;


public class ClientTest {

	public static void noSpring() {
		String url = "http://localhost:8080/Hello";
		HessianProxyFactory factory = new HessianProxyFactory();
		try {
			ITest hello = (ITest) factory.create(ITest.class, url);
			System.out.println(hello.sayHello());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
