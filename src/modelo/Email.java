package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import controlador.ConexionBBDD;

public class Email {
	
	private String ASUNTO;
	private static String EMAIL_FROM;
	private String EMAIL_PARA;
	private String EMAIL_COPIA;
	private static String EMAIL_COPIA_OCULTA;
	
	private static String user;
	private static String password;
	
	private static Properties props;
	private static Session session;
	
	private final static Logger log = Logger.getLogger(ConexionBBDD.class);
	
	public Email(String aSUNTO, String eMAIL_PARA, String eMAIL_COPIA) {
		super();
		ASUNTO = aSUNTO;
		EMAIL_PARA = eMAIL_PARA;
		EMAIL_COPIA = eMAIL_COPIA;
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
		
		cargaConfiguracion();
	 
		
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	    

	    session = Session.getInstance(props, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	        	return new PasswordAuthentication(user,password);
	        }
	    });
		
		
	    JFrame frame = new JFrame("");
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
			
			
			JOptionPane.showMessageDialog(frame,
				    "Mensaje enviado....",
				    "Info Asesoría VYR",
				    JOptionPane.INFORMATION_MESSAGE);
			
			
			
			log.info("Mensaje enviado\n###########################################################");

		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(frame,
				    "Mensaje no enviado....\n" + e.getMessage(),
				    "Info Asesoría VYR",
				    JOptionPane.ERROR_MESSAGE);
			log.error(e.getMessage());
			log.info("###########################################################");
		}

	}
	
	
	private static void cargaConfiguracion() {
		 File file = new File("config.txt");
		 
	        try {
	 
	            Scanner scanner = new Scanner(file);
	 
	            while (scanner.hasNextLine()) {
	                String word = scanner.next();
	                switch (word) {
					case "USUARIO":
						user = scanner.nextLine().trim();
						break;
					case "PASSWORD":
						password = scanner.nextLine().trim();
						break;
					/*case "ASUNTO":
						ASUNTO = scanner.nextLine().trim();
						break;
					*/	
					case "EMAIL_FROM":
						EMAIL_FROM = scanner.nextLine().trim();
						break;
					/*case "EMAIL_PARA":
						EMAIL_PARA = scanner.nextLine().trim();
						break;
					case "EMAIL_COPIA":
						EMAIL_COPIA = scanner.nextLine().trim();
						break;
					*/	
					case "EMAIL_COPIA_OCULTA":
						EMAIL_COPIA_OCULTA = scanner.nextLine().trim();
						break;

					default:
						break;
					}
	            }
	            scanner.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	}
	

}
