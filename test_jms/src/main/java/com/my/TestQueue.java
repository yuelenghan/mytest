package com.my;

import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;

/**
 * 点对点模式--多个消息
 * 
 * @author 鹤
 * 
 */
public class TestQueue {
	private static final Logger logger = Logger.getLogger(TestQueue.class);

	public static void main(String[] args) throws Exception {
		Session session = SessionUtil.getSession();

		Queue queue = new ActiveMQQueue("testQueue");
		MessageConsumer consumer1 = session.createConsumer(queue);
		MessageConsumer consumer2 = session.createConsumer(queue);
		MessageProducer producer = session.createProducer(queue);

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
