package vista;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import controlador.ConexionBBDD;
import modelo.Email;

@SuppressWarnings("serial")
public class Inicio extends JFrame {
	
	private final static Logger log = Logger.getLogger(Inicio.class);

	private JPanel contentPane;
	private JTextField tfnombrecliente;
	private JTextField tfprimerapellidocliente;
	private JTextField tfsegundoapellidocliente;
	private JTextField tfdnicliente;
	private JTextField tftlffijocliente;
	private JTextField tftlfmovilcliente;
	private JTextField tfemailcliente;
	private JTable tableClientes;
	private DefaultTableModel modelo;
	private JTextField tfasunto;
	private JTextArea tamail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("lib/log4j.properties");
		log.info("Fichero de configuración de log4j cargado.");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio frame = new Inicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		//ConexionBBDD conn = new ConexionBBDD();
		/*String sql="select * from clientes";
		ResultSet rs = conn.ejecutaSelect(sql);
		conn.muestraResultadoQuery(rs);
		conn.liberaResultSet(rs);
		conn.cierraConexion();*/
	}

	/**
	 * Create the frame.
	 */
	public Inicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		Panel panel_1 = new Panel();
		tabbedPane.addTab("Env\u00EDo Emails", null, panel_1, null);
		panel_1.setLayout(null);
		
		
	     
		modelo = new DefaultTableModel() {
			   @Override
			   public boolean isCellEditable(int fila, int columna) {
			       return false; //Con esto conseguimos que la tabla no se pueda editar
			   }
			};
		
		tableClientes = new JTable(modelo);
		tableClientes.setBounds(29, 211, 750, 160);
		JScrollPane scrollpanetable = new JScrollPane(tableClientes);
		scrollpanetable.setBounds(29, 211, 750, 160);
		panel_1.add(scrollpanetable);
		

		 
		modelo.addColumn("Nombre"); //Añadimos las columnas a la tabla (tantas como queramos)
		modelo.addColumn("Primer Apellido");
		modelo.addColumn("Segundo Apellido");
		modelo.addColumn("DNI");
		modelo.addColumn("Tlf. Fijo");
		modelo.addColumn("Tlf. Móvil");
		modelo.addColumn("Email");
		 
		rellenarTabla(); //Llamamos al método que rellena la tabla con los datos de la base de datos
		 
		scrollpanetable.setViewportView(tableClientes);//Esto añade la tabla al portView del scrollPane, si estaba puesto anteriormente
		                                  //hay que borrarlo del otro sitio, sino puede dar error de NullPointerException
		
		
		
		tamail = new JTextArea();
		tamail.setBounds(29, 11, 750, 102);
		JScrollPane scrollpane = new JScrollPane(tamail);
		scrollpane.setBounds(29, 94, 750, 102);
		panel_1.add(scrollpane);
		
		JButton btnEnviarMail = new JButton("Enviar Correo");
		btnEnviarMail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Email email = new Email(tfasunto.getText(), "Laura <laurarosalrubio@gmail.com>"
						+ "", "Rafa<rafalp81@gmail.com>", "", "");
				email.enviaMail(tamail.getText());
			}
		});
		btnEnviarMail.setBounds(276, 382, 118, 23);
		panel_1.add(btnEnviarMail);
		
		tfasunto = new JTextField();
		tfasunto.setBounds(85, 63, 308, 20);
		panel_1.add(tfasunto);
		tfasunto.setColumns(10);
		
		JLabel lblAsunto = new JLabel("Asunto:");
		lblAsunto.setBounds(29, 66, 46, 14);
		panel_1.add(lblAsunto);
		
		Panel panel = new Panel();
		tabbedPane.addTab("Nuevo Contacto", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(10, 11, 114, 14);
		panel.add(lblNewLabel_1);
		
		tfnombrecliente = new JTextField();
		tfnombrecliente.setBounds(171, 11, 124, 20);
		panel.add(tfnombrecliente);
		tfnombrecliente.setColumns(10);
		
		tfprimerapellidocliente = new JTextField();
		tfprimerapellidocliente.setBounds(171, 43, 124, 20);
		panel.add(tfprimerapellidocliente);
		tfprimerapellidocliente.setColumns(10);
		
		tfsegundoapellidocliente = new JTextField();
		tfsegundoapellidocliente.setBounds(171, 74, 124, 20);
		panel.add(tfsegundoapellidocliente);
		tfsegundoapellidocliente.setColumns(10);
		
		tfdnicliente = new JTextField();
		tfdnicliente.setBounds(171, 105, 124, 20);
		panel.add(tfdnicliente);
		tfdnicliente.setColumns(10);
		
		tftlffijocliente = new JTextField();
		tftlffijocliente.setBounds(171, 136, 124, 20);
		panel.add(tftlffijocliente);
		tftlffijocliente.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Primer Apellido:");
		lblNewLabel_2.setBounds(10, 43, 114, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Segundo Apellido:");
		lblNewLabel_3.setBounds(10, 74, 114, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("DNI:");
		lblNewLabel_4.setBounds(10, 105, 114, 14);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Tel\u00E9fono Fijo:");
		lblNewLabel_5.setBounds(10, 136, 114, 14);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Tel\u00E9fono M\u00F3vil:");
		lblNewLabel_6.setBounds(10, 167, 114, 14);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Email:");
		lblNewLabel_7.setBounds(10, 198, 114, 14);
		panel.add(lblNewLabel_7);
		
		tftlfmovilcliente = new JTextField();
		tftlfmovilcliente.setBounds(171, 167, 124, 20);
		panel.add(tftlfmovilcliente);
		tftlfmovilcliente.setColumns(10);
		
		tfemailcliente = new JTextField();
		tfemailcliente.setBounds(171, 198, 124, 20);
		panel.add(tfemailcliente);
		tfemailcliente.setColumns(10);
		
		JButton btnGuardarCliente = new JButton("Guardar");
		btnGuardarCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				log.info("Botón guardar cliente pulsado");
				String sql="INSERT INTO clientes" + 
							"(nombrecliente,primerapellidocliente,segundoapellidocliente,dnicliente,telefonofijocliente,telefonomovilcliente,emailcliente)" + 
							"VALUES(" +
							"'"+ tfnombrecliente.getText() +"',"+
							"'"+ tfprimerapellidocliente.getText() +"',"+
							"'"+ tfsegundoapellidocliente.getText() +"',"+
							"'"+ tfdnicliente.getText() +"',"+
							"'"+ tftlffijocliente.getText() +"',"+
							"'"+ tftlfmovilcliente.getText() +"',"+
							"'"+ tfemailcliente.getText() +"'" +
							")";
				
				limpiaValoresCliente();
				
				ConexionBBDD conn = new ConexionBBDD();
				conn.ejecutaInsert(sql);
				conn.cierraConexion();
				
				vaciarTabla();
				rellenarTabla();
				
			}
		});
		btnGuardarCliente.setBounds(105, 250, 89, 23);
		panel.add(btnGuardarCliente);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(264, 250, 89, 23);
		panel.add(btnLimpiar);
	}
	

	private void rellenarTabla(){
	 
	        try {
	        	ConexionBBDD conn = new ConexionBBDD();
	        	String sql="select * from clientes";
	    		ResultSet rs = conn.ejecutaSelect(sql);
	    		
	            while(rs.next()){
	                Object[] fila = new Object[7];//Creamos un Objeto con tantos parámetros como datos retorne cada fila 
	                                              // de la consulta
	                fila[0] = rs.getString("nombrecliente"); //Lo que hay entre comillas son los campos de la base de datos
	                fila[1] = rs.getString("primerapellidocliente");
	                fila[2] = rs.getString("segundoapellidocliente");
	                fila[3] = rs.getString("dnicliente");
	                fila[4] = rs.getString("telefonofijocliente");
	                fila[5] = rs.getString("telefonomovilcliente");
	                fila[6] = rs.getString("emailcliente");
	                
	                modelo.addRow(fila); // Añade una fila al final del modelo de la tabla
	            }
	 
	            tableClientes.updateUI();//Actualiza la tabla
	            conn.liberaResultSet(rs);
	            conn.cierraConexion();
	 
	        } catch (SQLException e) {
	            e.printStackTrace();
	          
	        }
	 
	    }
	 
	    /**Métode per vaciar la un Jtable con modelo
	     *
	     */
	    private void vaciarTabla(){
	        while (modelo.getRowCount() > 0) modelo.removeRow(0);
	    }
	    
	    private void limpiaValoresCliente(){
	    	tfnombrecliente.setText("");
			tfprimerapellidocliente.setText("");
			tfsegundoapellidocliente.setText("");
			tfdnicliente.setText("");
			tftlffijocliente.setText("");
			tftlfmovilcliente.setText("");
			tfemailcliente.setText("");
	    }
}
