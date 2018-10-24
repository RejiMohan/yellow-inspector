package com.dhwani.ideas.auto.kuauto.services;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import com.dhwani.ideas.auto.kuauto.datum.KUUpdate;
import com.dhwani.ideas.auto.kuauto.utils.AppConfigInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailSender {

	private static StringBuilder messageBodyBuilder = new StringBuilder();

	public static void sendEmailNotification(List<KUUpdate> updateList) {
	  AppConfigInterface.getAppConfigData()
	  .ifPresent(cfg -> sendNotification(cfg.getRecepients(), updateList));
	}

	private static void sendNotification(List<String> recipientsList, List<KUUpdate> updateList) {
	  
	  try {
        Properties props = new Properties();
          props.put("mail.smtp.user", "me.myemail@outlook.com");
          props.put("mail.smtp.host", "smtp-mail.outlook.com");
          props.put("mail.smtp.port", "587");
          props.put("mail.smtp.starttls.enable","true");
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.socketFactory.port", "587");
          props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
          props.put("mail.smtp.socketFactory.fallback", "true");
          
          List<InternetAddress> toAddressList = new ArrayList<>();
          recipientsList.forEach(mailId -> {
            try {
              toAddressList.add(new InternetAddress(mailId));
            } catch (AddressException e) {
              log.error("Invalid Email Address", e);
            }
          });
          InternetAddress[] toAddrs = toAddressList.toArray(new InternetAddress[toAddressList.size()]);
          
          Authenticator auth = new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication("me.myemail@outlook.com", "my-outlook-pass");
              }
            };

          Session session = Session.getInstance(props, auth);
          Message msg = new MimeMessage(session);
          msg.setSubject("KU Status Update");
          msg.setFrom(new InternetAddress("me.myemail@outlook.com", "KU Status Updator"));
          msg.setSentDate(new Date());
          msg.setRecipients(Message.RecipientType.TO, toAddrs);
          msg.setContent(getMessage(updateList), "text/html; charset=utf-8");
          Transport.send(msg);
          log.info("Successfully SEND message...");
      } catch (MessagingException | UnsupportedEncodingException ex) {
          log.error("Failed to process Email", ex);
      }
  }


  private static Object getMessage(List<KUUpdate> kuUpdatesList) {

		messageBodyBuilder.append(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"> <head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <meta name=\"viewport\" content=\"initial-scale=1.0\"> <meta name=\"format-detection\" content=\"telephone=no\"> <title>KU Status Update</title> <style type=\"text/css\">.socialLinks{font-size: 6px;}.socialLinks a{display: inline-block;}.socialIcon{display: inline-block;vertical-align: top;padding-bottom: 0px;border-radius: 100%;}table.vb-row.fullwidth{border-spacing: 0;padding: 0;}table.vb-container.fullpad{border-spacing: 18px;padding-left: 0;padding-right: 0;}table.vb-container.fullwidth{padding-left: 0;padding-right: 0;}</style> <style type=\"text/css\"> /* yahoo, hotmail */ .ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div{line-height: 100%;}.yshortcuts a{border-bottom: none !important;}.vb-outer{min-width: 0 !important;}.RMsgBdy, .ExternalClass{width: 100%; background-color: #3f3f3f; background-color: #3f3f3f}/* outlook */ table{mso-table-rspace: 0pt; mso-table-lspace: 0pt;}#outlook a{padding: 0;}img{outline: none; text-decoration: none; border: none; -ms-interpolation-mode: bicubic;}a img{border: none;}@media screen and (max-device-width: 600px), screen and (max-width: 600px){table.vb-container, table.vb-row{width: 95% !important;}.mobile-hide{display: none !important;}.mobile-textcenter{text-align: center !important;}.mobile-full{float: none !important; width: 100% !important; max-width: none !important; padding-right: 0 !important; padding-left: 0 !important;}img.mobile-full{width: 100% !important; max-width: none !important; height: auto !important;}}</style> <style type=\"text/css\">#ko_sideArticleBlock_3 .links-color a:visited, #ko_sideArticleBlock_3 .links-color a:hover{color: #3f3f3f;color: #3f3f3f;text-decoration: underline;}#ko_sideArticleBlock_5 .links-color a:visited, #ko_sideArticleBlock_5 .links-color a:hover{color: #3f3f3f;color: #3f3f3f;text-decoration: underline;}#ko_footerBlock_2 .links-color a:visited, #ko_footerBlock_2 .links-color a:hover{color: #ccc;color: #ccc;text-decoration: underline;}</style> </head> <body bgcolor=\"#3f3f3f\" text=\"#919191\" alink=\"#cccccc\" vlink=\"#cccccc\" style=\"margin: 0;padding: 0;background-color: #3f3f3f;color: #919191;\"> <center> <table class=\"vb-outer\" width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\" bgcolor=\"#3f3f3f\" style=\"background-color: #3f3f3f;\" id=\"ko_preheaderBlock_1\"> <tbody> <tr> <td class=\"vb-outer\" align=\"center\" valign=\"top\" bgcolor=\"#3f3f3f\" style=\"padding-left: 9px;padding-right: 9px;background-color: #3f3f3f;\"> <div style=\"display: none; font-size: 1px; color: #333333; line-height: 1px; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\"></div><!--[if (gte mso 9)|(lte ie 8)]> <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"> <tr> <td align=\"center\" valign=\"top\"><![endif]--> <div class=\"oldwebkit\" style=\"max-width: 570px;\"> <table width=\"570\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"vb-row halfpad\" bgcolor=\"#3f3f3f\" style=\"border-collapse: separate;border-spacing: 0;padding-left: 9px;padding-right: 9px;width: 100%;max-width: 570px;background-color: #3f3f3f;\"> <tbody> <tr> <td align=\"center\" valign=\"top\" bgcolor=\"#3f3f3f\" style=\"font-size: 0; background-color: #3f3f3f;\"> <h1 style=\"color:#eaf1f1;font-family:Lato,sans-serif;font-size:54px;font-weight:300;line-height:58px;;margin-bottom:0;margin-top:10px;margin-left:0;margin-right:0;\">TODAYS UPDATES</h1> </td></tr><tr> <td align=\"center\" valign=\"top\" bgcolor=\"#3f3f3f\" style=\"font-size: 0; background-color: #3f3f3f;\"><h6 style=\"text-align:center;color:#eaf1f1;font-family:Lato,sans-serif;font-size:24px;font-weight:200;margin-bottom:10px;margin-top:0;margin-left:0;margin-right:0;\">");
		messageBodyBuilder.append(LocalDate.now().format(DateTimeFormatter.ofPattern("cccc, dd MMMM yyyy")));
		messageBodyBuilder.append(
				"</h6></td></tr></tbody> </table> </div><!--[if (gte mso 9)|(lte ie 8)]> </td></tr></table><![endif]--> </td></tr></tbody> </table>");

		if (kuUpdatesList.isEmpty())
			messageBodyBuilder.append(
					" <table class=\"vb-outer\" width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\" bgcolor=\"#c1c070\" style=\"background-color: #c1c070;\" id=\"ko_sideArticleBlock_5\"> <tbody> <tr> <td class=\"vb-outer\" align=\"center\" valign=\"top\" bgcolor=\"#c1c070\" style=\"padding-left: 9px;padding-right: 9px;background-color: #c1c070;\"><!--[if (gte mso 9)|(lte ie 8)]> <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"> <tr> <td align=\"center\" valign=\"top\"><![endif]--> <div class=\"oldwebkit\" style=\"max-width: 570px;\"> <table width=\"570\" border=\"0\" cellpadding=\"0\" cellspacing=\"9\" class=\"vb-row fullpad\" bgcolor=\"#ffffff\" style=\"border-collapse: separate;border-spacing: 9px;width: 100%;max-width: 570px;background-color: #fff;\"> <tbody> <tr> <td align=\"center\" class=\"mobile-row\" valign=\"top\" style=\"font-size: 0;\"><!--[if (gte mso 9)|(lte ie 8)]> <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"552\"> <tr><![endif]--><!--[if (gte mso 9)|(lte ie 8)]> <td align=\"left\" valign=\"top\" width=\"414\"><![endif]--> <div class=\"mobile-full\" style=\"display: inline-block; max-width: 414px; vertical-align: top; width: 100%;\"> <table class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" width=\"414\" align=\"left\" style=\"border-collapse: separate;width: 100%;\"> <tbody> <tr> <td style=\"font-size: 18px; font-family: Comic Sans MS, Comic Sans MS5, cursive; color: #3f3f3f; text-align: left;\"> <span style=\"color: #3f3f3f;\">No New B.Tech updates!</span> </td></tr></tbody> </table> </div><!--[if (gte mso 9)|(lte ie 8)]> </td><![endif]--><!--[if (gte mso 9)|(lte ie 8)]> </tr></table><![endif]--> </td></tr></tbody> </table> </div><!--[if (gte mso 9)|(lte ie 8)]> </td></tr></table><![endif]--> </td></tr></tbody> </table>");
		else
			kuUpdatesList.forEach(EmailSender::addUpdateToMessageBody);

		messageBodyBuilder.append(
				" <table width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\" bgcolor=\"#3f3f3f\" style=\"background-color: #3f3f3f;\" id=\"ko_footerBlock_2\"> <tbody> <tr> <td align=\"center\" valign=\"top\" bgcolor=\"#3f3f3f\" style=\"background-color: #3f3f3f;\"><!--[if (gte mso 9)|(lte ie 8)]> <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"> <tr> <td align=\"center\" valign=\"top\"><![endif]--> <div class=\"oldwebkit\" style=\"max-width: 570px;\"> <table width=\"570\" style=\"border-collapse: separate;border-spacing: 9px;padding-left: 9px;padding-right: 9px;width: 100%;max-width: 570px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"9\" class=\"vb-container halfpad\" align=\"center\"> <tbody> <tr> <td class=\"long-text links-color\" style=\"text-align: center; font-size: 13px; color: #919191; font-weight: normal; text-align: center; font-family: Arial, Helvetica, sans-serif;\"> <p style=\"margin: 1em 0px;margin-bottom: 0px;margin-top: 0px;line-height: 1.3;\">You're receiving this mail because you were added to our list.<br>Having trouble reading this email? <a href=\"#\" style=\"color:#888;text-decoration:underline\">View it in your browser</a>. Not interested anymore? <a href=\"#\" style=\"color:#888;text-decoration:underline\">Unsubscribe Instantly</a>.</p></td></tr></tbody> </table> </div><!--[if (gte mso 9)|(lte ie 8)]> </td></tr></table><![endif]--> </td></tr></tbody> </table> </center> </body></html>");

		return messageBodyBuilder.toString();

	}

	private static void addUpdateToMessageBody(KUUpdate update) {
		messageBodyBuilder.append(
				" <table class=\"vb-outer\" width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\" bgcolor=\"#c1c070\" style=\"background-color: #c1c070;\" id=\"ko_sideArticleBlock_5\"> <tbody> <tr> <td class=\"vb-outer\" align=\"center\" valign=\"top\" bgcolor=\"#c1c070\" style=\"padding-left: 9px;padding-right: 9px;background-color: #c1c070;\"><!--[if (gte mso 9)|(lte ie 8)]> <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"> <tr> <td align=\"center\" valign=\"top\"><![endif]--> <div class=\"oldwebkit\" style=\"max-width: 570px;\"> <table width=\"570\" border=\"0\" cellpadding=\"0\" cellspacing=\"9\" class=\"vb-row fullpad\" bgcolor=\"#ffffff\" style=\"border-collapse: separate;border-spacing: 9px;width: 100%;max-width: 570px;background-color: #fff;\"> <tbody> <tr> <td align=\"center\" class=\"mobile-row\" valign=\"top\" style=\"font-size: 0;\"><!--[if (gte mso 9)|(lte ie 8)]> <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"552\"> <tr><![endif]--><!--[if (gte mso 9)|(lte ie 8)]> <td align=\"left\" valign=\"top\" width=\"414\"><![endif]--> <div class=\"mobile-full\" style=\"display: inline-block; max-width: 414px; vertical-align: top; width: 100%;\"> <table class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" width=\"414\" align=\"left\" style=\"border-collapse: separate;width: 100%;\"> <tbody> <tr> <td style=\"font-size: 18px; font-family: Comic Sans MS, Comic Sans MS5, cursive; color: #3f3f3f; text-align: left;\"> <span style=\"color: #3f3f3f;\">");
		messageBodyBuilder.append(update.getCategory().getLabel());
		messageBodyBuilder.append(
				"</span> </td></tr><tr> <td align=\"left\" class=\"long-text links-color\" style=\"text-align: left; font-size: 13px; font-family: Comic Sans MS, Comic Sans MS5, cursive; color: #3f3f3f;\"> <p style=\"margin: 1em 0px;margin-bottom: 0px;margin-top: 0px;\">");
		messageBodyBuilder.append(WordUtils.capitalizeFully(update.getDescription(), '.'));

		messageBodyBuilder.append(
				"</p></td></tr><tr> <td valign=\"top\"> <table cellpadding=\"0\" border=\"0\" align=\"left\" cellspacing=\"8\" class=\"mobile-full\"> <tbody> <tr> <td width=\"auto\" valign=\"middle\" bgcolor=\"#0070c0\" align=\"center\" height=\"26\" style=\"font-size: 12px; font-family: Lucida Console, Monaco, monospace; text-align: center; color: #ffffff; font-weight: normal; padding-left: 18px; padding-right: 18px; background-color: #0070c0; border-radius: 10px;\"> <a style=\"text-decoration: none; color: #ffffff; font-weight: normal;\" target=\"_new\" href=\"");
		messageBodyBuilder.append(update.getLink());
		messageBodyBuilder.append("\">GET DETAILS</a> </td>");
		if (StringUtils.isNotBlank(update.getPublishedOn())) {
			messageBodyBuilder.append(
					"<td width=\"auto\" valign=\"middle\" bgcolor=\"#0070c0\" align=\"center\" height=\"26\" style=\"font-size: 12px; font-family: Lucida Console, Monaco, monospace; text-align: center; color: #ffffff; font-weight: normal; padding-left: 18px; padding-right: 18px; background-color: #0070c0; border-radius: 10px;\"> <span style=\"color:#f2f2f2;font-size:12px;color: #ffffff; font-weight: normal;\">");
			messageBodyBuilder.append(update.getPublishedOn());
			messageBodyBuilder.append("</span></td>");
		}
		messageBodyBuilder.append(
				"</tr></tbody> </table> </td></tr></tbody> </table> </div><!--[if (gte mso 9)|(lte ie 8)]> </td><![endif]--><!--[if (gte mso 9)|(lte ie 8)]> </tr></table><![endif]--> </td></tr></tbody> </table> </div><!--[if (gte mso 9)|(lte ie 8)]> </td></tr></table><![endif]--> </td></tr></tbody> </table>");

	}

}