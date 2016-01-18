package controlador;

import java.sql.*;

import org.apache.log4j.Logger;



public class ConexionBBDD {
	
	private final static Logger log = Logger.getLogger(ConexionBBDD.class);
	
	private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private String dbName="vyrmail";
	private String connectionURL = "jdbc:derby:" + dbName + ";create=true";
	 
	 private Connection conn = null;
	 private Statement stmt = null;
	 //private ResultSet rs = null;
	 
	 public ConexionBBDD() {
		super();
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    try {
	    	log.info("Conectando a la base de datos...");
	    	conn = DriverManager.getConnection(connectionURL);
		    boolean existeTablaClientes = compruebaTablaClientes(); //Comprueba si existe la tabla.
		    if(!existeTablaClientes){
		    	creaTablaClientes();
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	 
	 public ResultSet ejecutaSelect(String sql){
		ResultSet rs=null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	 }
	 
	 public boolean ejecutaInsert(String sql){
		 try {
			return stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	 
	 public void liberaResultSet(ResultSet rs){
		 try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	 }
	 
	 public void cierraConexion(){

		 try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		    //finally block used to close resources
		    try{
		       if(stmt!=null)
		          stmt.close();
		    }catch(SQLException se2){
		    }// nothing we can do
		    try{
		       if(conn!=null)
		          conn.close();
		    }catch(SQLException se){
		       se.printStackTrace();
		    }//end finally try
		 }
		 
	 }
	 
	 public void muestraResultadoQuery(ResultSet rs){
		 try {
			while(rs.next()){
			       System.out.println("ID: " + rs.getInt("idcliente"));
			       System.out.println("Nombre: " + rs.getString("nombrecliente"));
			       System.out.println("Primer apellido: " + rs.getString("primerapellidocliente"));
			       System.out.println("Segundo apellido: " + rs.getString("segundoapellidocliente"));
			       System.out.println("DNI: " + rs.getString("dnicliente"));
			       System.out.println("Tlf fijo: " + rs.getString("telefonofijocliente"));
			       System.out.println("Tlf Móvil: " + rs.getString("telefonomovilcliente"));
			       System.out.println("Email: " + rs.getString("emailcliente"));
			    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 

	 private boolean compruebaTablaClientes() throws SQLException{ //devuelve true si la tabla existe, falso en otro caso.
		 try {
			 String sql="update clientes set nombreCliente = 'test' where 1=2";
			 log.info("Comprobando si existe la tabla clientes");
			 stmt = conn.createStatement();
			 stmt.execute(sql); 
		} catch (SQLException sqle) {
			String theError = (sqle).getSQLState();
	         /** If table exists will get -  WARNING 02000: No row was found **/
	         if (theError.equals("42X05"))   // Table does not exist
	         {  
	        	 log.info("La tabla clientes no existe");
	        	 return false;
	          }  else if (theError.equals("42X14") || theError.equals("42821"))  {
	             log.error("Incorrect table definition. Drop table clientes and rerun this program");
	             throw sqle;   
	          } else { 
	             log.error("Unhandled SQLException" );
	             throw sqle; 
	          }
		}
		 log.info("La tabla clientes existe");
		 return true;
	 }
	 
	 private void creaTablaClientes(){
		 String sql = "CREATE TABLE clientes (" +
					  "idcliente INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
					  "nombrecliente varchar(45) DEFAULT NULL," +
					  "primerapellidocliente varchar(45) DEFAULT NULL," +
					  "segundoapellidocliente varchar(45) DEFAULT NULL," +
					  "dnicliente varchar(45) DEFAULT NULL," +
					  "telefonofijocliente varchar(45) DEFAULT NULL," +
					  "telefonomovilcliente varchar(45) DEFAULT NULL," +
					  "emailcliente varchar(45) DEFAULT NULL," +
					  "PRIMARY KEY (idcliente)" +
					  ")";// ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;";
		 
		 log.info(sql);
		 try {
			log.info("Creando tabla clientes");
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			log.error("Error al crear la tabla clientes");
			log.error(e.getMessage());
			log.error(e.getSQLState());
		}					 
	 }
}
