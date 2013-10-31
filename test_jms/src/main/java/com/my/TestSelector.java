package com.my;

import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * 消息过滤 
 * 
 * consumer1只接收name=1的消息 
 * consumer2只接收name=2的消息
 * 
 * @author 鹤
 * 
 */
public class TestSelector {
	private static final Logger logger = Logger.getLogger(TestSelector.class);

	public static void main(String[] args) throws Exception {
		Session session = SessionUtil.getSession();

		Topic topic = session.createTopic("testTopic");

		MessageConsumer consumer1 = session.createConsumer(topic, "name = 1");
		MessageConsumer consumer2 = session.createConsumer(topic, "name = 2");

		MessageProducer producer = session.createProducer(topic);

		for (int i = 0; i < 10; i++) {
			String name = (i % 2 == 0 ? "1" : "2");
			TextMessage message = session.createTextMessage();
			message.setStringProperty("name", name);

			message.setText("test -- " + i);
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
