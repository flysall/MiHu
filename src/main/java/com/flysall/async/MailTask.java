package com.flysall.async;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.flysall.util.MyConstant;

/**
 * 邮箱任务
 */
public class MailTask implements Runnable{
	private String code;
	private String email;
	private JavaMailSender javaMailSender;
	private int operation;
	
	public MailTask(String code, String email, JavaMailSender javaMailSender, int operation){
		this.code = code;
		this.email = email;
		this.javaMailSender = javaMailSender;
		this.operation = operation;
	}
	
	public void run(){
		javaMailSender.send(new MimeMessagePreparator(){
			public void prepare(MimeMessage mimeMessage) throws Exception{
				System.out.println("开始发送邮件");
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelper.setFrom(MyConstant.MAIL_FROM);
				mimeMessageHelper.setTo(email);
				mimeMessageHelper.setSubject("一封激活邮件");
				StringBuilder sb = new StringBuilder();
				sb.append("<html><head></head><body>");
				
				if(operation == 1){
					sb.append("<a href=" + MyConstant.DOMAIN_NAME + "activate?code=");
					sb.append(code);
					sb.append(">点击激活</a></body>");
				} else{
					sb.append("是否将你的密码修改为:");
					sb.append(code.substring(0, 8));
					sb.append(",<a href="+MyConstant.DOMAIN_NAME + "Verify.do?code" + code + ">");
					sb.append("点击是</a></body>");
				}
				
				mimeMessageHelper.setText(sb.toString(), true);
				
				System.out.println("结束发送邮件");
			}
		});
	}
}