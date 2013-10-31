package com.my;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

public class TestReply {
	private static final Logger logger = Logger.getLogger(TestReply.class);

	private Session session;
	private Queue queue;

	public TestReply() throws Exception {
		session = SessionUtil.getSession();

		queue = session.createQueue("testQueue");

		setupConsumer("ConsumerA");
		setupConsumer("ConsumerB");

		setupProducer("ProducerA", "ConsumerA");
		setupProducer("ProducerB", "ConsumerB");
	}

	public static void main(String[] args) throws Exception {
		new TestReply();
	}

	private void setupConsumer(final String name) throws Exception {
		MessageConsumer consumer = session.createConsumer(queue, "name = '"
				+ name + "'");

		consumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				try {
					logger.info(name + "收到消息："
							+ ((TextMessage) message).getText());

					// 回复消息
					MessageProducer producer = session.createProducer(queue);
					Message replyMessage = session
							.createTextMessage("Reply from : " + name);

					replyMessage.setJMSCorrelationID(message
							.getJMSMessageID());

					producer.send(replyMessage);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setupProducer(final String name, String consumerName)
			throws Exception {

		MessageProducer producer = session.createProducer(queue);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		Message message = session.createTextMessage("message from : " + name);
		message.setStringProperty("name", consumerName);

		producer.send(message);

		// 等待回复的消息
		MessageConsumer replyConsumer = session.createConsumer(queue,
				"JMSCorrelationID='" + message.getJMSMessageID() + "'");

		replyConsumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				try {
					logger.info(name + "收到回复 : "
							+ ((TextMessage) message).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
