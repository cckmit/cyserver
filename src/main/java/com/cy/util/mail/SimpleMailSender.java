package com.cy.util.mail;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SimpleMailSender {
	private Properties props = System.getProperties();
	private MailAuthenticator authenticator;
	private Session session;
	private final String imgPattern = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";

	public SimpleMailSender(String username, String password) {
		String smtpHostName = "smtp." + username.split("@")[1];
		String smtpPort = "25";
		init(username, password, smtpHostName, smtpPort, "");
	}

	public SimpleMailSender(String username, String password, String smtpHostName, String smtpPort, String protocol) {
		init(username, password, smtpHostName, smtpPort, protocol);
	}

	private void init(String username, String password, String smtpHostName, String smtpPort, String protocol) {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtpHostName);
		props.put("mail.smtp.port", smtpPort);
		if (protocol != null && protocol.length() > 0) {
			props.setProperty("mail.transport.protocol", "smtps");
		}
		// 验证
		authenticator = new MailAuthenticator(username, password);
		// 创建session
		session = Session.getInstance(props, authenticator);
	}

	private void send(List<String> recipients, List<String> ccrecipients, List<String> bccrecipients, String subject, String content, List<String> fileUrls)

	throws AddressException, MessagingException, UnsupportedEncodingException, MalformedURLException {
		// 创建mime类型邮件
		MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人们
		int num = recipients.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipients.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		if (ccrecipients != null && ccrecipients.size() > 0) {
			// 设置抄送的人们
			int ccnum = ccrecipients.size();
			InternetAddress[] ccaddresses = new InternetAddress[ccnum];
			for (int i = 0; i < ccrecipients.size(); i++) {
				ccaddresses[i] = new InternetAddress(ccrecipients.get(i));
			}
			message.setRecipients(RecipientType.CC, ccaddresses);
		}
		if (bccrecipients != null && bccrecipients.size() > 0) {
			// 设置密送的人们
			int bccnum = bccrecipients.size();
			InternetAddress[] bccaddresses = new InternetAddress[bccnum];
			for (int i = 0; i < bccrecipients.size(); i++) {
				bccaddresses[i] = new InternetAddress(bccrecipients.get(i));
			}
			message.setRecipients(RecipientType.BCC, bccaddresses);
		}
		// 设置主题
		message.setSubject(subject);

		// 图文邮件
		Pattern p = Pattern.compile(imgPattern);
		Matcher m = p.matcher(content);
		MimeMultipart multipart = new MimeMultipart("mixed");
		String pl = "";
		while (m.find()) {
			MimeBodyPart imgBodyPart = new MimeBodyPart(); // 附件图标
			String group = m.group(1);
			imgBodyPart.setDataHandler(new DataHandler(new URLDataSource(new URL(group))));
			imgBodyPart.setContentID(group.substring(group.lastIndexOf("/") + 1));
			multipart.addBodyPart(imgBodyPart);
			if (pl.length() == 0) {
				pl = group.substring(0, group.lastIndexOf("/") + 1);
			}
		}
		BodyPart bodyPart = new MimeBodyPart();// 正文
		if (pl.length() == 0) {
			bodyPart.setDataHandler(new DataHandler(content, "text/html;charset=UTF-8"));// 网页格式
		} else {
			bodyPart.setDataHandler(new DataHandler(content.replace(pl, "cid:"), "text/html;charset=UTF-8"));// 网页格式
		}
		// 附件
		if (fileUrls != null && fileUrls.size() > 0) {
			for (String url : fileUrls) {
				MimeBodyPart fileBodyPart = new MimeBodyPart();
				String fileName = url.substring(url.lastIndexOf("/") + 1);
				fileName = fileName.substring(fileName.indexOf("_")+1);
				fileBodyPart.setFileName(MimeUtility.encodeWord(fileName));
				fileBodyPart.setDataHandler(new DataHandler(new URLDataSource(new URL(url))));
				multipart.addBodyPart(fileBodyPart);
			}
		}
		multipart.addBodyPart(bodyPart);
		message.setContent(multipart);
		Transport.send(message);
	}

	/**
	 * @param recipients
	 *            接收者
	 * @param ccrecipients
	 *            抄送
	 * @param bccrecipients
	 *            密送
	 * @param mail
	 *            主题，内容
	 * @param fileUrls
	 *            附件URL
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 */
	public void send(List<String> recipients, List<String> ccrecipients, List<String> bccrecipients, SimpleMail mail, List<String> fileUrls)
			throws AddressException, MessagingException, UnsupportedEncodingException, MalformedURLException {
		send(recipients, ccrecipients, bccrecipients, mail.getSubject(), mail.getContent(), fileUrls);
	}
}
