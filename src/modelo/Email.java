package modelo;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import controlador.ConexionBBDD;

public class Email {
	
	private String ASUNTO;
	private String EMAIL_FROM;
	private String EMAIL_PARA;
	private String EMAIL_COPIA;
	private String EMAIL_COPIA_OCULTA;
	
	private static Properties props;
	private static Session session;
	
	private final static Logger log = Logger.getLogger(ConexionBBDD.class);
	
	public Email(String aSUNTO, String eMAIL_FROM, String eMAIL_PARA, String eMAIL_COPIA, String eMAIL_COPIA_OCULTA) {
		super();
		ASUNTO = aSUNTO;
		EMAIL_FROM = eMAIL_FROM;
		EMAIL_PARA = eMAIL_PARA;
		EMAIL_COPIA = eMAIL_COPIA;
		EMAIL_COPIA_OCULTA = eMAIL_COPIA_OCULTA;
	}
	
	public Email() {
		super();
	}

	public String getASUNTO() {
		return ASUNTO;
	}

	public void setASUNTO(String aSUNTO) {
		ASUNTO = aSUNTO;
	}

	public String getEMAIL_FROM() {
		return EMAIL_FROM;
	}

	public void setEMAIL_FROM(String eMAIL_FROM) {
		EMAIL_FROM = eMAIL_FROM;
	}

	public String getEMAIL_PARA() {
		return EMAIL_PARA;
	}

	public void setEMAIL_PARA(String eMAIL_PARA) {
		EMAIL_PARA = eMAIL_PARA;
	}

	public String getEMAIL_COPIA() {
		return EMAIL_COPIA;
	}

	public void setEMAIL_COPIA(String eMAIL_COPIA) {
		EMAIL_COPIA = eMAIL_COPIA;
	}

	public String getEMAIL_COPIA_OCULTA() {
		return EMAIL_COPIA_OCULTA;
	}

	public void setEMAIL_COPIA_OCULTA(String eMAIL_COPIA_OCULTA) {
		EMAIL_COPIA_OCULTA = eMAIL_COPIA_OCULTA;
	}
	
	
	public void enviaMail(String mensaje){
		props = new Properties();
	   /* props.put("mail.smtp.port","80");
	    props.put("mail.smtp.host","gnfproxy2010.intranet.gasnatural.com");
	    props.put("java.net.username","90025807");
	    props.put("java.net.password","Mufore22");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.port", 587);
	    props.put("mail.smtp.host", "m.outlook.com");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.connectiontimeout", "20000");*/
		
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	    

	    session = Session.getInstance(props, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            //return new PasswordAuthentication("G0340994@es.gasnatural.com","ago2015");
	        	return new PasswordAuthentication("infoasesoriavyr@gmail.com","password");
	        }
	    });
		
		
	    
	    try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(EMAIL_FROM));
			
			
			//INFO : A descomentar en la versión final
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(EMAIL_PARA));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(EMAIL_COPIA));
			message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(EMAIL_COPIA_OCULTA));
			
			
			//TEST
			//message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("rlopezpe@capgemini.com"));
			//message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("francisco.nvara-ondo@capgemini.com,hector-miguel.terres-lloret@capgemini.com"));
			
			message.setSubject(ASUNTO);
			
			message.setContent(mensaje, "text/html; charset=utf-8");
			
			log.info("Enviando mensaje...");
			Transport.send(message);
			log.info("Mensaje enviado\n###########################################################");

		} catch (MessagingException e) {
			log.error(e.getMessage());
			log.info("###########################################################");
		}

	}
	

}
