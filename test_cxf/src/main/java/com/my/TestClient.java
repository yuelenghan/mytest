package com.my;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestClient {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring.xml");
		Hello hello = (Hello) context.getBean("hello");
		System.out.println(hello.sayHello("lihe"));
		
		
		// 根据wsdl生成java
		// HelloImplService helloImplService = new HelloImplService();
		// System.out.println(helloImplService.getHelloImplPort().sayHello("li he"));
	}

}
