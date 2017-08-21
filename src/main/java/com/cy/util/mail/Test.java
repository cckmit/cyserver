package com.cy.util.mail;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

public class Test {
	public static void main(String[] args) throws MalformedURLException {
		SimpleMailSender mailSender = new SimpleMailSender("zhen.liu@bsapp.net","LIUzhen910713","smtp.exmail.qq.com","465","smtp");
//		SimpleMailSender mailSender = new SimpleMailSender("xx", "xxx");
		// SimpleMailSender mailSender = MailSenderFactory.getSender1("xx",
		// "xxx","mailserver.znufe.edu.cn","25");
		List<String> list = new ArrayList<String>();
		list.add("liu19910307_happy@163.com");
//		list.add("307585689@qq.com");
		SimpleMail mail = new SimpleMail();
		mail.setSubject("你好");
		List<String> fileList = new ArrayList<String>();
		// fileList.add("http://219.140.177.108:8088/image/20150525/20150525131027_794.png");
		mail.setContent("你好");
		try {
			mailSender.send(list, null, null, mail, fileList);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void sendHTMLMail() throws Exception {
		// 发送器
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("smtp.163.com");
		sender.setPort(25); // 默认就是25
		sender.setUsername("cha0res@163.com");
		sender.setPassword("Qpf123");
		sender.setDefaultEncoding("UTF-8");

		// 配置文件对象
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true"); // 是否进行验证
		Session session = Session.getInstance(props);
		sender.setSession(session); // 为发送器指定会话

		MimeMessage mail = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setTo("cha0res@163.com"); // 发送给谁
		helper.setSubject("Test"); // 标题
		helper.setFrom("cha0res@163.com"); // 来自
		// 邮件内容，第二个参数指定发送的是HTML格式
		helper.setText("来张图<br><img src='cid:myImg'>",true);
		// 增加CID内容
		FileSystemResource img = new FileSystemResource(new File("/Users/cha0res/Nutstore/Pics/gamersky_01origin_01_20165141818DA0.jpg"));
		helper.addInline("myImg", img);

		try {
			sender.send(mail);
			System.out.println("邮件发送成功");
		}catch (Exception e) {
			System.out.println("邮件发送失败");
			e.printStackTrace();
		}

	}
}
