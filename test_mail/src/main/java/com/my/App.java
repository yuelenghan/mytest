package com.my;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class App {

	public static void main(String[] args) throws Exception {
		String from = "yuelenghan@126.com";
		String to = "lh_1986@foxmail.com";
		String subject = "Hello";
		String text = "Hello java mail";

		// 必须在配置文件中配置传输协议、smtp服务器
		InputStream in = App.class.getResourceAsStream("/mail.properties");
		Properties prop = new Properties();
		prop.load(in);

		// 使用验证，创建一个Authenticator
		Authenticator auth = new MyAuthenticator("yuelenghan@126.com",
				"gunner86war3ne");

		// 根据Properties，Authenticator创建Session
		Session session = Session.getDefaultInstance(prop, auth);

		try {
			// Message存储发送的电子邮件信息
			Message message = new MimeMessage(session);
			
			// 设置发信邮箱
			message.setFrom(new InternetAddress(from));

			// 设置收信邮箱
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// 设置标题
			message.setSubject(subject);

			// 设置内容
			message.setText(text);
			
			// 发送
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
