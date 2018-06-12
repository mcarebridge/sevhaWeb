package com.phr.ade.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.phr.ade.controller.health.CaredPortalController;
import com.phr.ade.values.CareMailDTO;


public class CareMailUtil implements UtilCommonConstants
{
	private static Logger       logger   = Logger.getLogger(CareMailUtil.class
	                                             .getName());
	
	private static CareMailUtil mailUtil = null;
	
	private CareMailUtil()
	{
	}
	
	/**
	 * 
	 * @return
	 */
	public static CareMailUtil getInstance()
	{
		if (mailUtil == null)
		{
			mailUtil = new CareMailUtil();
		}
		
		return mailUtil;
		
	}
	
	/**
	 * 
	 * @param careMail
	 * @throws Exception
	 */
	public static void sendSimpleMail(CareMailDTO careMail) throws Exception
	{
		// [START simple_example]
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		try
		{
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(EMAIL_SENDER, EMAIL_SENDER_NAME));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
			        careMail.getToAddress(), careMail.getRecepiantName()));
			msg.setSubject(careMail.getMailsubject());
			// msg.setText(careMail.getMailBody());
			msg.setContent(careMail.getMailBody(), "text/html");
			Transport.send(msg);
		} catch (AddressException e)
		{
			// e.printStackTrace();
			throw new Exception(e);
		} catch (MessagingException e)
		{
			// e.printStackTrace();
			throw new Exception(e);
		} catch (UnsupportedEncodingException e)
		{
			// e.printStackTrace();
			throw new Exception(e);
		}
		// [END simple_example]
	}
	
	/**
	 * 
	 */
	private void sendMultipartMail()
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		String msgBody = "...";
		
		try
		{
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("admin@example.com",
			        "Example.com Admin"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
			        "user@example.com", "Mr. User"));
			msg.setSubject("Your Example.com account has been activated");
			msg.setText(msgBody);
			
			// [START multipart_example]
			String htmlBody = ""; // ...
			byte[] attachmentData = null; // ...
			Multipart mp = new MimeMultipart();
			
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlBody, "text/html");
			mp.addBodyPart(htmlPart);
			
			MimeBodyPart attachment = new MimeBodyPart();
			InputStream attachmentDataStream = new ByteArrayInputStream(
			        attachmentData);
			attachment.setFileName("manual.pdf");
			attachment.setContent(attachmentDataStream, "application/pdf");
			mp.addBodyPart(attachment);
			
			msg.setContent(mp);
			// [END multipart_example]
			
			Transport.send(msg);
			
		} catch (AddressException e)
		{
			e.printStackTrace();
			
		} catch (MessagingException e)
		{
			e.printStackTrace();
			
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			
		}
	}
	
	/**
	 * Load Velocity Template
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String loadVelocityTemplate(String velocityTemplate,
	        Object templateData) throws Exception
	{
		VelocityContext context = new VelocityContext();
		context.put("emailContentsObject", templateData);
		VelocityEngine ve = VelocityHelper.getVelocityEngine();
		// Finds template in WEB-INF/classes
		Template template = ve.getTemplate(velocityTemplate);
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		
		logger.info("Email after merging : " + writer.toString());
		
		return writer.toString();
	}
}
