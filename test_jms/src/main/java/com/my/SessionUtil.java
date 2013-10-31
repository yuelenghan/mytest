package com.my;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SessionUtil {

	public static Session getSession() throws Exception {
		ConnectionFactory factory = new ActiveMQConnectionFactory(
				"vm://localhost");
			Connection connection = factory.createConnection();
			connection.start();

			// 通过Connection创建Session
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			return session;
	}
}
