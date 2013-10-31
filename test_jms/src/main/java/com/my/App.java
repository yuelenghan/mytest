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

public class App {
	private static final Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) throws Exception {
		Session session = SessionUtil.getSession();
		// 创建Message
		Message message = session.createTextMessage("测试jms");

		// 创建队列（点对点）
		Queue queue = new ActiveMQQueue("testQueue");
		// 创建消息制造者
		MessageProducer producer = session.createProducer(queue);

		// 发送消息
		producer.send(message);

		// 创建消息消费者
		MessageConsumer consumer = session.createConsumer(queue);

		// 主动接收方式
		// Message msg = consumer.receive();
		// System.out.println(((TextMessage)msg).getText());

		// 监听器接收方式
		consumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				try {
					logger.info(message);
					logger.info(((TextMessage) message).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

	}
}
