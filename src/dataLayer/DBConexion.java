package dataLayer;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class DBConexion {
	
	public static String ControladorMySQL = "com.mysql.cj.jdbc.Driver"; // new MySQL
	public static String ControladorMariaDB = "org.mariadb.jdbc.Driver"; // MariaDB
	public static String ControladorSQLServer = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // SQL Server

	@SuppressWarnings("finally")
	public static Connection GetConnection() {
		//"jdbc:mariadb://localhost:3306/DB?user=root&password=myPassword"
		
		Connection conexion=null;
		try {
			Class.forName(ControladorSQLServer);  
			//conexion=DriverManager.getConnection("jdbc:mariadb://localhost:3306/Topicos?user=Topicos&password=Topicos");	//param: servidor,Usuario,Contraseña
			conexion=DriverManager.getConnection("jdbc:sqlserver://localhost:1433/Topicos?user=sa&password=yourStrong#Password");
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, ex, "Error con la clase del Driver de MySQL: "+ex.getMessage(),JOptionPane.ERROR_MESSAGE);
			conexion=null;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex, "Error en la conexion con la BD: "+ex.getMessage(),JOptionPane.ERROR_MESSAGE);
			conexion=null;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error del programa: "+ex.getMessage(),JOptionPane.ERROR_MESSAGE);
			conexion=null;
		} finally {
			return conexion;
		}
	}
	
	@SuppressWarnings("finally")
	public static Connection GetConnection2() {
		Connection conexion=null;
		
		Properties connConfig = new Properties();
        connConfig.setProperty("user", "sa");
        connConfig.setProperty("password", "yourStrong#Password");

        try {
        		//conexion = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Topicos", connConfig);
        		conexion = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433/Topicos", connConfig);
        } catch (Exception ex) {
        	JOptionPane.showMessageDialog(null, ex, "Error del programa: "+ex.getMessage(),JOptionPane.ERROR_MESSAGE);
			conexion=null;
        } finally {
			return conexion;
		}
	}
	
	@SuppressWarnings("finally")
	public static Connection GetConnection3() {		
		Connection conexion=null;
		try {
			  String cadena = "jdbc:sqlserver://" + Config.getServidor() + ":1433;" +
	                    "databaseName=" + Config.getDB() + ";" + // Asegúrate de que Config.getDB() no sea nulo
	                    "user=" + Config.getUsuarioDB() + ";" + 
	                    "password=" + Config.getPasswordDB() + ";" + // Asegúrate de que Config.getPasswordDB() no sea nulo
	                    "loginTimeout=30;trustServerCertificate=true";

			conexion=DriverManager.getConnection(cadena);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex, "Error en la conexion con la BD: "+ex.getMessage(),JOptionPane.ERROR_MESSAGE);
			conexion=null;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error del programa: "+ex.getMessage(),JOptionPane.ERROR_MESSAGE);
			conexion=null;
		} finally {
			return conexion;
		}
	}
}
