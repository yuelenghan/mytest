package com.my;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.log4j.Logger;

/**
 * 订阅/发布模式
 * 
 * @author 鹤
 * 
 */
public class TestTop {
	private static final Logger logger = Logger.getLogger(TestTop.class);

	public static void main(String[] args) throws Exception {
		Session session = SessionUtil.getSession();
		Topic topic = session.createTopic("testTopic");

		MessageConsumer consumer1 = session.createConsumer(topic);
		MessageConsumer consumer2 = session.createConsumer(topic);

		MessageProducer producer = session.createProducer(topic);
		for (int i = 0; i < 10; i++) {
			TextMessage message = session.createTextMessage("test -- " + i);
			producer.send(message);
		}

		consumer1.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				try {
					logger.info("consumer1 : "
							+ ((TextMessage) message).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		consumer2.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				try {
					logger.info("consumer2 : "
							+ ((TextMessage) message).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
